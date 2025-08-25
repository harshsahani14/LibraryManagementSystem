package com.harsh.LibraryManagement.services;



import java.util.List;
import java.util.Map;

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
	
	public ResponseEntity<String> registerService(RegisterDTO registerDTO) {
		
		try {
			
			if(userRepositry.existByUsername(registerDTO.getUsername())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
			}
			
			userRepositry.register(registerDTO);
			
			return ResponseEntity.ok("Account created sucessfully");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}



	public ResponseEntity<Map<String, Object>> loginService(LoginDTO loginDTO) {
		
		try {
			Map<String, Object> map = userRepositry.login(loginDTO); 
			
			return ResponseEntity.ok(map);
		}
		catch (Exception e) {
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
			
			return ResponseEntity.ok("Book added sucessfully");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}



	public ResponseEntity<Map<String, List<BookDTO>>> searchBookService(String name) {
		
		try {
			
			Map<String, List<BookDTO>> map = bookRepositry.searchBook(name);
			
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(), List.of()));
		}
	}


	public ResponseEntity<Map<String, List<BorrowDTO>>> viewBookService(String name, String genre, String author,String edition) {
		
		try {
			Map<String, List<BorrowDTO>> map = borrowDetailsRepositry.viewBook(name, genre, author, edition);
			
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(), List.of()));
		}
		
	}



	public ResponseEntity<String> borrowBook(BorrowBookDTO borrowBookDTO) {
		
		try {
			
			if( bookRepositry.isBookAvaialble(borrowBookDTO)) {
				
				int bookId = bookRepositry.decrementBookQuantity(borrowBookDTO);
				borrowDetailsRepositry.updateBorrowDetails(borrowBookDTO,bookId);
				
				return ResponseEntity.ok("Book borrowed sucessfully");
				
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not available for borrowing");
			}
			
			
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
				
	}



	public ResponseEntity<Map<String, List<? extends Object>>> viewReportService() {
		
		try {
			
			List<ReportDTO> list1 = borrowDetailsRepositry.getBooksDueToday();
			List<GenreDTO> list2 = bookRepositry.getBooksByGenre();
			
			
			return ResponseEntity.ok(Map.of("report",list1,"books",list2));
			
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(e.getMessage(),List.of()));
		}
	}

	
}
