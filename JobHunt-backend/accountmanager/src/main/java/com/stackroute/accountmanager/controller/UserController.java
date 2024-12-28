package com.stackroute.accountmanager.controller;

import java.util.Map;

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
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        try {
            String userId = request.get("userId");
            String oldPassword = request.get("oldPassword");
            String newPassword = request.get("newPassword");

            if (userId == null || oldPassword == null || newPassword == null) {
                throw new Exception("All fields (userId, oldPassword, newPassword) are required.");
            }

            boolean isPasswordChanged = userService.changePassword(userId, oldPassword, newPassword);

            if (isPasswordChanged) {
                return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Old password is incorrect", HttpStatus.UNAUTHORIZED);
            }

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("{ \" message\": \"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
