package com.stackroute.accountmanager.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {


	private final UserRepository userRepo;
	
	//constructor based injection
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo ;
	}
	
	@Override
	public boolean saveUser(User user) throws UserAlreadyExistsException {
		Optional<User> option = userRepo.findById(user.getUserId());
		
		if(option.isPresent())
		{
			throw new UserAlreadyExistsException("User with Id already exists");
		}
		else userRepo.save(user);
		return true;
	}

	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {

		User user = userRepo.findByUserIdAndPassword(userId, password);
		if(user == null)
		{
			
			throw new UserNotFoundException("UserId does not exists");
		}
			
		return user;
	}

		public boolean changePassword(String userId, String oldPassword, String newPassword) throws UserNotFoundException {
		    Optional<User> userOptional = userRepo.findById(userId);

		    if (!userOptional.isPresent()) {
		        throw new UserNotFoundException("User not found with id: " + userId);
		    }

		    User user = userOptional.get();

		    if (!user.getPassword().equals(oldPassword)) {
		        return false; // Old password is incorrect
		    }

		    user.setPassword(newPassword);
		    userRepo.save(user);
		    return true;
		}


}
