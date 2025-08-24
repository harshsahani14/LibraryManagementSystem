package com.harsh.LibraryManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDTO {
	

	String borrowedBy;
	String returnDate;
	
	
	public BorrowDTO(String borrowedUser, String returnDate) {
		this.borrowedBy = borrowedUser;
		this.returnDate = returnDate;
	}


	public String getBorrowedBy() {
		return borrowedBy;
	}
	
	public void setBorrowedBy(String borrowedBy) {
		this.borrowedBy = borrowedBy;
	}
	
	public String getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
}
