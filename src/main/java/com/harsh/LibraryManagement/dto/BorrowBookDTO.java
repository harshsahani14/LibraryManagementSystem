package com.harsh.LibraryManagement.dto;

public class BorrowBookDTO {

	private int userId;
	private String name;
	private String genre;
	private String author;
	private String edition;
	private int borrowingPeriod;
	
	public BorrowBookDTO(String name, String genre, String author, String edition,int borrowingPeriod) {
		this.name = name;
		this.genre = genre;
		this.author = author;
		this.edition = edition;
		this.borrowingPeriod=borrowingPeriod;
	}
	
	public int getBorrowingPeriod() {
		return borrowingPeriod;
	}

	public void setBorrowingPeriod(int borrowingPeriod) {
		this.borrowingPeriod = borrowingPeriod;
	}

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
	
}
