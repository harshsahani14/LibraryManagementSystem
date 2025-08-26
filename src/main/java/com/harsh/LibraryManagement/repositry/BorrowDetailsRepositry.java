package com.harsh.LibraryManagement.repositry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.harsh.LibraryManagement.dto.BorrowBookDTO;
import com.harsh.LibraryManagement.dto.BorrowDTO;
import com.harsh.LibraryManagement.dto.ReportDTO;

@Repository
public class BorrowDetailsRepositry {

	@Autowired
	private  DataSource dataSource;
	
	
	
	public void addBorrowDetails(int bookId) {
		
		String borrowedBooksQuery = " Insert into borrow_details(book_id) values ( ? )";
		
		try(Connection c = dataSource.getConnection()) {
			PreparedStatement borrowedBooksPs = c.prepareStatement(borrowedBooksQuery);
			
			borrowedBooksPs.setInt(1, bookId);
			
			borrowedBooksPs.executeUpdate();
			
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while inserting in borrow details table");
		}
	}
	
		public Map<String, List<BorrowDTO>> viewBook(String name, String genre, String author, String edition) {
		
		
		List<BorrowDTO> list = new ArrayList<>();
    	
    	//Understand    	
    	String searchQuery = "Select "
    						+ "Coalesce(t3.username,'Not borrowed') as borrowed_user,"
    						+ "Coalesce( CAST(t2.return_date AS CHAR),'No return date') as return_date "
    						+ "from books_details t1 left join  "
    						+ "borrow_details t2 on "
    			 			+ "t1.book_id=t2.book_id "
    						+ "Left Join users t3 "
    			 			+ "on t2.borrowed_by=t3.user_id "
    			 			+ "where t1.name=? and t1.genre=? and t1.author=? and t1.edition=?";
    	
    	try (Connection c = dataSource.getConnection()){
    		
    		PreparedStatement ps = c.prepareStatement(searchQuery);
    		
    		ps.setString(1, name);
    		ps.setString(2, genre);
    		ps.setString(3, author);
    		ps.setString(4, edition);
    		
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			
    			String borrowedUser = rs.getString("borrowed_user");
    			String returnDate = rs.getString("return_date");
    			
    			BorrowDTO borrowDTO = toBorrowDTO(borrowedUser,returnDate);
    			
    			list.add(borrowDTO);
    			
    		}
    		
    		return Map.of("books",list);
    	}
    	catch(Exception e) {
    		throw new RuntimeException("Server error while viewing books: "+ e.getMessage());
    	}
    	
	}

		private BorrowDTO toBorrowDTO(String borrowedUser, String returnDate) {
			
			return new BorrowDTO(borrowedUser, returnDate);
		}

		public void updateBorrowDetails(BorrowBookDTO borrowBookDTO,int bookId) {
			
			String borrowQuery = "Update borrow_details "
					+"set borrowed_by=?, return_date=DATE_ADD(CURDATE(), INTERVAL ? DAY) "
					+"where book_id=? and borrowed_by is null "
					+"Limit 1";
		
			try(Connection c = dataSource.getConnection()) {
				
				PreparedStatement borrowPs = c.prepareStatement(borrowQuery);
				
				borrowPs.setInt(1, borrowBookDTO.getUserId());
				borrowPs.setInt(2,borrowBookDTO.getBorrowingPeriod());
				borrowPs.setInt(3, bookId);
				
				borrowPs.executeUpdate();
				
//				logger.info("Book was borrowed");
			}
			catch(Exception e) {
				throw new RuntimeException("Server error while updating borrow details");
//				logger.debug("Error while borrowing book");
			}
			
			return;
						
		}

		public List<ReportDTO> getBooksDueToday() {
			
			List<ReportDTO> list = new ArrayList<>();
			
			String reportQuery = "Select "
								+ "t1.name,t1.genre,t1.edition,t1.author, "
								+ "t3.username "
								+ "from books_details t1 left join "
								+ "borrow_details t2 on "
								+ "t1.book_id=t2.book_id "
								+ "left join users t3 on "
								+ "t3.user_id=t2.borrowed_by "
								+ "where DATE(t2.return_date)=CURDATE()";
			
			try(Connection c = dataSource.getConnection()) {
				PreparedStatement reportPs = c.prepareStatement(reportQuery);
				
				ResultSet rs = reportPs.executeQuery();
				
				
				while(rs.next()) {
					
					ReportDTO reportDTO = toReportDTO(rs.getString("name"),rs.getString("genre"),rs.getString("edition"),rs.getString("author"),rs.getString("username"));
				    list.add(reportDTO);
				}
				
				
				return list;
//				logger.info("Book report was viewed");
				
				
			}
			catch(Exception e) {
				throw new RuntimeException("Server error fetching the books to be returned today:"+ e.getMessage());
			}

		}

		private ReportDTO toReportDTO(String name, String genre, String edition, String author, String username) {
			
			return new ReportDTO(name, genre, edition, author, username);
		}	
		
	
}
