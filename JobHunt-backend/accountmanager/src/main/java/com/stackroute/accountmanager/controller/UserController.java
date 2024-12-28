package com.stackroute.accountmanager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.service.TokenGenerator;
import com.stackroute.accountmanager.service.UserService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/v1/userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenGenerator tokenGenerator;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return new ResponseEntity<String>("User registered successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User loginDetail) {
		try {
			if (null == loginDetail.getUserId() || null == loginDetail.getPassword()) {
				throw new Exception("User Id or Password canot be empty.");
			}
			User user = userService.findByUserIdAndPassword(loginDetail.getUserId(), loginDetail.getPassword());
			Map<String, String> map = tokenGenerator.generateToken(user);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request, HttpServletRequest req) {
	    try {
	        // Extract claims from request attributes
	        Claims claims = (Claims) req.getAttribute("claims");
	        if (claims == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                                 .body(Map.of("message", "Unauthorized access"));
	        }

	        // Fetch user ID and input passwords
	        String userId = claims.getSubject();
	        String oldPassword = request.get("oldPassword");
	        String newPassword = request.get("newPassword");

	        // Validate input
	        if (oldPassword == null || newPassword == null) {
	            return ResponseEntity.badRequest()
	                                 .body(Map.of("message", "Old and new passwords are required"));
	        }
	        
	     // Check if old password and new password are the same
	        if (oldPassword.equals(newPassword)) {
	            return ResponseEntity.badRequest()
	                                 .body(Map.of("message", "Old password and new password cannot be the same"));
	        }

	        // Perform password change
	        boolean isPasswordChanged = userService.changePassword(userId, oldPassword, newPassword);

	        if (isPasswordChanged) {
	            return ResponseEntity.ok(Map.of("message", "Password updated successfully"));
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                                 .body(Map.of("message", "Old password is incorrect"));
	        }

	    } catch (UserNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(Map.of("message", e.getMessage()));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest()
	                             .body(Map.of("message", "An error occurred: " + e.getMessage()));
	    }
	}

}
