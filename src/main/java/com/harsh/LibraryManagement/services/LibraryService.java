package com.harsh.LibraryManagement.services;



import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.harsh.LibraryManagement.dto.BookDTO;
import com.harsh.LibraryManagement.dto.BorrowBookDTO;
import com.harsh.LibraryManagement.dto.BorrowDTO;
import com.harsh.LibraryManagement.dto.GenreDTO;
import com.harsh.LibraryManagement.dto.LoginDTO;
import com.harsh.LibraryManagement.dto.RegisterDTO;
import com.harsh.LibraryManagement.dto.ReportDTO;
import com.harsh.LibraryManagement.repositry.BookRepositry;
import com.harsh.LibraryManagement.repositry.BorrowDetailsRepositry;
import com.harsh.LibraryManagement.repositry.UserRepositry;

@Service
public class LibraryService {
	
	@Autowired
	private UserRepositry userRepositry;
	
	@Autowired
	private BookRepositry bookRepositry;
	
	@Autowired
	private BorrowDetailsRepositry borrowDetailsRepositry;
	
	private static final Logger logger = LogManager.getLogger(LibraryService.class);
	
	public ResponseEntity<String> registerService(RegisterDTO registerDTO) {
		
		try {
			
			if(userRepositry.existByUsername(registerDTO.getUsername())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
			}
			
			userRepositry.register(registerDTO);
			
			logger.info("Account was registered");
			return ResponseEntity.ok("Account created sucessfully");
		}
		catch(Exception e) {
			logger.debug("Error while creating an account");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}



	public ResponseEntity<Map<String, Object>> loginService(LoginDTO loginDTO) {
		
		try {
			Map<String, Object> map = userRepositry.login(loginDTO); 
			
			if(map.get("message").equals("Username is invalid")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
			
			if(map.get("message").equals("Password is invalid")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
			
			logger.info("A user logged in the system");
			return ResponseEntity.ok(map);
		}
		catch (Exception e) {
			logger.debug("Failed login attempt");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message",e.getMessage()));
		}
	}



	public ResponseEntity<String> addBookService(BookDTO bookDTO) {
		
		try {
			
			int bookId = bookRepositry.exists(bookDTO);
			
			
			if( bookId != -1 ) {
				
				String dbIsbnNo = bookRepositry.getIsbnNo(bookDTO);
				
				if(dbIsbnNo.equals(bookDTO.getIsbn_no())) {
					bookRepositry.updateBook(bookDTO);
				}
				else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid isbn number");
				}
				
			}
			else {
				bookId = bookRepositry.insertBook(bookDTO);
			}
			borrowDetailsRepositry.addBorrowDetails(bookId);
			
			logger.info("Book was added");
			return ResponseEntity.ok("Book added sucessfully");
		}
		catch (Exception e) {
			logger.debug("Error while adding a book");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}



	public ResponseEntity<Map<String, List<BookDTO>>> searchBookService(String name) {
		
		try {
			
			Map<String, List<BookDTO>> map = bookRepositry.searchBook(name);
			
			logger.info("Books were searched");
			
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			logger.debug("Error while searching books");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(), List.of()));
		}
	}


	public ResponseEntity<Map<String, List<BorrowDTO>>> viewBookService(String name, String genre, String author,String edition) {
		
		try {
			Map<String, List<BorrowDTO>> map = borrowDetailsRepositry.viewBook(name, genre, author, edition);
			
			logger.info("A book was viewed");
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			logger.debug("Error while viewing book");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(), List.of()));
		}
		
	}



	public ResponseEntity<String> borrowBook(BorrowBookDTO borrowBookDTO) {
		
		try {
			
			if( bookRepositry.isBookAvaialble(borrowBookDTO)) {
				
				int bookId = bookRepositry.decrementBookQuantity(borrowBookDTO);
				borrowDetailsRepositry.updateBorrowDetails(borrowBookDTO,bookId);
				
				logger.info("A book was borrowed");
				
				return ResponseEntity.ok("Book borrowed sucessfully");
				
			}
			else {
				logger.debug("Error while borrowing book");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available for borrowing");
			}
			
			
		}
		catch (Exception e) {
			logger.debug("Error while borrowing book");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
				
	}



	public ResponseEntity<Map<String, List<? extends Object>>> viewReportService() {
		
		try {
			
			List<ReportDTO> list1 = borrowDetailsRepositry.getBooksDueToday();
			List<GenreDTO> list2 = bookRepositry.getBooksByGenre();
			
			logger.info("Book report was viewed");
			return ResponseEntity.ok(Map.of("report",list1,"books",list2));
			
		}
		catch (Exception e) {
			logger.debug("Error while viewing report of a book");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(),List.of()));
		}
	}

	
}
