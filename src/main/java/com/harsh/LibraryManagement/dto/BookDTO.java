package com.harsh.LibraryManagement.dto;

public class BookDTO {
	
	private String name;
	private String genre;
	private String author;
	private String edition;
	private String isbn_no;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getIsbn_no() {
		return isbn_no;
	}
	public void setIsbn_no(String isbn_no) {
		this.isbn_no = isbn_no;
	}
	public BookDTO(String name, String genre, String author, String edition, String isbn_no) {
		this.name = name;
		this.genre = genre;
		this.author = author;
		this.edition = edition;
		this.isbn_no = isbn_no;
	}
	
	
}

