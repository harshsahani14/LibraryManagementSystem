package com.harsh.LibraryManagement.repositry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.harsh.LibraryManagement.dto.LoginDTO;
import com.harsh.LibraryManagement.dto.RegisterDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositry {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private DataSource dataSource;

	public boolean existByUsername(String username) {
		
		String query = "Select * from users"
						+" where username=?";
		
	
		try(Connection c = dataSource.getConnection()) {
			PreparedStatement ps = c.prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet rs  = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			else {
				return false;
			}
			
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while checking if user exists or not");
		}
	}

	public void register(RegisterDTO registerDTO) {
		
		String signUpQuery = " Insert into users(username,phone_no,password) values ( ? , ? , ?)";
		
		try(Connection c = dataSource.getConnection()) {
			
			PreparedStatement ps = c.prepareStatement(signUpQuery);
			
			ps.setString(1,registerDTO.getUsername());
			ps.setString(2, registerDTO.getPhoneNo());
			ps.setString(3, passwordEncoder.encode(registerDTO.getPassword()));

			ps.execute();
						
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while registering user");
			
		}	
		
	}
	
	public Map<String, Object> login(LoginDTO loginDTO){
		
		String loginQuery = " Select * from users where username=?  ";
		
		try(Connection c = dataSource.getConnection()) {
			
			PreparedStatement ps = c.prepareStatement(loginQuery);
			
			ps.setString(1, loginDTO.getUsername());
			
			ResultSet rs = ps.executeQuery();
		
			if(!rs.next()) {
				return Map.of("message","Username is invalid");
			}
			
			int userId = rs.getInt("user_id"); 
			String username = rs.getString("username");
			String phoneNo = rs.getString("phone_no");
			String password= rs.getString("password");
			
			
			if( !passwordEncoder.matches( loginDTO.getPassword(), password ) ) {
				return Map.of("message","Password is invalid");
			}
			
			RegisterDTO registerDTO = toRegisterDto(username,phoneNo);
			
			return Map.of("message","Login Sucessful",
							"user", registerDTO,
							"user_id",userId);
			
		
		}
		catch(Exception e) {
			throw new RuntimeException("Server error while signing in user: "+e.getMessage());
		}
		
	}

	private RegisterDTO toRegisterDto(String username,String phoneNo ) {
		
		return new RegisterDTO( username,phoneNo ,null );  
	}

	
}
