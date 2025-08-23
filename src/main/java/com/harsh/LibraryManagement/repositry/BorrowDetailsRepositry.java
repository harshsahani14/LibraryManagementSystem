package com.harsh.LibraryManagement.repositry;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.harsh.LibraryManagement.dto.BookDTO;

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
	
}
