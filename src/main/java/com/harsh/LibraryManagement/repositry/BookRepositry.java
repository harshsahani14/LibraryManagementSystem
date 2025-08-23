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
		
    	String searchQuery = "Select name,genre,author,edition,isbn_no from books_details where name like ? ";
    	
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
		return new BookDTO(name, genre, author, edition, isbnNo);
	}
		
	
	
	
}
