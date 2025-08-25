package com.harsh.LibraryManagement.dto;

public class GenreDTO {
	
	private String genre;
	private int quantity;
	
	public GenreDTO(String genre, int quantity) {
		
		this.genre = genre;
		this.quantity = quantity;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

