package com.harsh.LibraryManagement.dto;

public class ReportDTO {

	String bookName;
	String bookGenre;
	String bookAuthor;
	String bookEdition;
	String userName;
	
	public ReportDTO(String bookName, String bookGenre,  String bookEdition, String bookAuthor,String userName) {
		this.bookName = bookName;
		this.bookGenre = bookGenre;
		this.bookAuthor = bookAuthor;
		this.bookEdition = bookEdition;
		this.userName = userName;
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookGenre() {
		return bookGenre;
	}
	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookEdition() {
		return bookEdition;
	}
	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
