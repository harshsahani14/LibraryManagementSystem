package com.harsh.LibraryManagement.repositry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.harsh.LibraryManagement.dto.BookDTO;
import com.harsh.LibraryManagement.dto.BorrowBookDTO;
import com.harsh.LibraryManagement.dto.GenreDTO;


@Repository
public class BookRepositry {
	
	@Autowired
    private DataSource dataSource;
	
	
	
	
	
	public int exists(BookDTO bookDTO) {
		
		String searchBookQuery = "Select * from books_details where name=? and genre=? and edition=? and author=? ";
		
		
		try(Connection c = dataSource.getConnection()) {
			
			PreparedStatement ps = c.prepareStatement(searchBookQuery);
			
			ps.setString(1, bookDTO.getName());
			ps.setString(2, bookDTO.getGenre());
			ps.setString(3, bookDTO.getEdition());
			ps.setString(4, bookDTO.getAuthor());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("book_id");	
			}
			else {
				return -1;
			}
				
		}
		catch(Exception e){
			throw new RuntimeException("Server error while checking if book exists or not");
		}
	}

	public String getIsbnNo(BookDTO bookDTO) {
		
		String searchBookQuery = "Select isbn_no from books_details where name=? and genre=? and edition=? and author=? ";
		
		try(Connection c = dataSource.getConnection()) {
			PreparedStatement ps = c.prepareStatement(searchBookQuery);
			
			ps.setString(1, bookDTO.getName());
			ps.setString(2, bookDTO.getGenre());
			ps.setString(3, bookDTO.getEdition());
			ps.setString(4, bookDTO.getAuthor());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("isbn_no");
			}
			
			return "";
		}
		catch(Exception e){
			throw new RuntimeException("Server error while fetching isbn number of the book");
		}
	}

	public void updateBook(BookDTO bookDTO) {
		String updateBookQuery = " Update books_details "
				+ "Set available_quantity=available_quantity+1, total_quantity=total_quantity+1 "
				+ "Where name=? and genre=? and edition=? and author=?";

		try(Connection c = dataSource.getConnection()) {
			
			PreparedStatement updateBookPs = c.prepareStatement(updateBookQuery);
			
			updateBookPs.setString(1, bookDTO.getName());
			updateBookPs.setString(2, bookDTO.getGenre());
			updateBookPs.setString(3, bookDTO.getEdition());
			updateBookPs.setString(4, bookDTO.getAuthor());
			
			updateBookPs.execute();
			
			
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while updating quantity of the book: " +  e.getMessage());
		}
		
		}

	
	public int insertBook(BookDTO bookDTO) {
		
		String addBookQuery = " Insert into books_details(name,genre,author,edition,isbn_no) values ( ? , ? , ? , ? , ? ) ";
        
		try(Connection c = dataSource.getConnection()) {
			
			PreparedStatement addBookPs = c.prepareStatement(addBookQuery,Statement.RETURN_GENERATED_KEYS);
			
			addBookPs.setString(1, bookDTO.getName());
			addBookPs.setString(2, bookDTO.getGenre());
			addBookPs.setString(3, bookDTO.getAuthor());
			addBookPs.setString(4, bookDTO.getEdition());
			addBookPs.setString(5, bookDTO.getIsbn_no());
			
			addBookPs.executeUpdate();
			
			ResultSet rs = addBookPs.getGeneratedKeys();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while inserting the book: "+ e.getMessage());
		}
		
		return -1;
		
	}

	
	public Map<String, List<BookDTO>> searchBook(String name) {
		
		
		List<BookDTO> list = new ArrayList<>();
		
    	String searchQuery = "Select name,genre,author,edition,isbn_no,available_quantity,total_quantity from books_details where name like ? ";
    	
    	try(Connection c = dataSource.getConnection()) {
    		PreparedStatement searchPs = c.prepareStatement(searchQuery);
    		
    		searchPs.setString(1, name + "%");
    		
    		ResultSet rs = searchPs.executeQuery();
    		
    		
    		while(rs.next()) {
    			BookDTO bookDTO = toBookDTO(rs.getString("name"), rs.getString("genre"), rs.getString("author"), rs.getString("edition"), rs.getString("isbn_no"));
                list.add(bookDTO);        
    		}
    		
    		
    		return Map.of("books",list);
    	}
    	catch(Exception e) {
    		throw new RuntimeException("Server error while searching books: "+ e.getMessage());
    		
    	}
	}
	
	public BookDTO toBookDTO(String name,String genre,String author,String edition,String isbnNo) {
		return new BookDTO(name, genre,edition,author, isbnNo);
	}

	public boolean isBookAvaialble(BorrowBookDTO borrowBookDTO) {
		
		
		
		String searchQuery = "Select * from books_details"
				+" where name=? and genre=? and edition=? and author=? and available_quantity>0 ";
		
		try(Connection c = dataSource.getConnection()) {
			
			BookDTO bookDTO = this.toBookDTO(borrowBookDTO.getName(),borrowBookDTO.getGenre(),borrowBookDTO.getEdition(),borrowBookDTO.getAuthor(),null);
		
			if(this.exists(bookDTO)==-1) {
				return false;
			}
			
    		PreparedStatement ps = c.prepareStatement(searchQuery);
    		
    		ps.setString(1, borrowBookDTO.getName());
    		ps.setString(2, borrowBookDTO.getGenre());
    		ps.setString(3, borrowBookDTO.getEdition());
    		ps.setString(4, borrowBookDTO.getAuthor());
    		
    		ResultSet rs = ps.executeQuery();
    		
    		if(rs.isBeforeFirst()) {
    			
    			return true;
    		}
    		else {
    			return false;
    		}
    		
    	}
    	catch(Exception e) {
    		throw new RuntimeException("Server error while checking if book is available or not");
    	}
	}

	
	public int decrementBookQuantity(BorrowBookDTO borrowBookDTO) {
		
		
		String selectQuery = "SELECT book_id FROM books_details "
		        + "WHERE name = ? AND genre = ? AND edition = ? AND author = ?";

		
		String decreaseQuery = " Update books_details "
				  +" set available_quantity=available_quantity-1 "
				  + "where name=? and genre=? and edition=? and author=?"; 

		try(Connection c = dataSource.getConnection()) {
	
			PreparedStatement decreasePs = c.prepareStatement(decreaseQuery);
			PreparedStatement selectPs = c.prepareStatement(selectQuery);
		
			decreasePs.setString(1, borrowBookDTO.getName());
			decreasePs.setString(2, borrowBookDTO.getGenre());
			decreasePs.setString(3, borrowBookDTO.getEdition());
			decreasePs.setString(4, borrowBookDTO.getAuthor());
			
			selectPs.setString(1, borrowBookDTO.getName());
			selectPs.setString(2, borrowBookDTO.getGenre());
			selectPs.setString(3, borrowBookDTO.getEdition());
			selectPs.setString(4, borrowBookDTO.getAuthor());

			decreasePs.executeUpdate();
			ResultSet rs = selectPs.executeQuery();
			
			if (rs.next()) {
			    return rs.getInt("book_id");
			}
			
			return -1;
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while decrementing book quantity by one" + e.getMessage());
			
		}
		
	}

	public List<GenreDTO> getBooksByGenre() {
					
		
			List<GenreDTO> list = new ArrayList<>();
			
			String genreQuery = "Select genre,sum(total_quantity) as count "
							+"from books_details "
							+ "group by genre ";
			
			try(Connection c = dataSource.getConnection()) {

				PreparedStatement genrePs = c.prepareStatement(genreQuery);
				
				ResultSet rs = genrePs.executeQuery();
				
				// understand formatting    		
				
				
				while(rs.next()) {
					  GenreDTO genreDTO = toGenreDTO(rs.getString("genre"),rs.getInt("count"));
					  list.add(genreDTO);
				}
				
				return list;
		
//			logger.info("Book report was viewed");
			
			}
			catch(Exception e) {
				throw new RuntimeException("Server error while getting books by genre: "+ e.getMessage());
			}

	}

	private GenreDTO toGenreDTO(String genre, int quantity) {
		
		return new GenreDTO(genre, quantity);
	}

	
	
	
	
}
