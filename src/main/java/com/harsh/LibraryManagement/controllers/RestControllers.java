package com.harsh.LibraryManagement.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsh.LibraryManagement.dto.BookDTO;
import com.harsh.LibraryManagement.dto.LoginDTO;
import com.harsh.LibraryManagement.dto.RegisterDTO;
import com.harsh.LibraryManagement.services.LibraryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestControllers {
	
	@Autowired
	private LibraryService libraryService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
		
		return libraryService.registerService(registerDTO);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO){
		
		return libraryService.loginService(loginDTO);
	}

	@PostMapping("/addBook")
	public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO){
		
		return libraryService.addBookService(bookDTO);
	}
	
	@GetMapping("/searchBook")
	public ResponseEntity<Map<String , List<BookDTO>>> searchBook(@RequestParam String name){
		
		return libraryService.searchBookService(name);
	}
	
//	@GetMapping("/viewBook")
//	public ResponseEntity<Map<String, List<BookDTO>>> viewBook(@RequestBody BookDTO bookDTO){
}
