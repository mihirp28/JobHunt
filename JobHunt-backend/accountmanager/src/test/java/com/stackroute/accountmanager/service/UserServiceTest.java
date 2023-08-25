package com.stackroute.accountmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;

public class UserServiceTest {

	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	private User user ;
	
	Optional<User> options;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		user = new User("admin", "mihir", "patel", "password", new Date());
		options = Optional.of(user);
	}

	@Test
	public void testSaveUserSuccess() throws UserAlreadyExistsException,UserNotFoundException {
		when(userRepo.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertEquals("Registration failed",true,flag);
		verify(userRepo, times(1)).save(user);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void testSaveUserFailure() throws UserAlreadyExistsException,UserNotFoundException {
		when(userRepo.findById(user.getUserId())).thenReturn(options);
		when(userRepo.save(user)).thenReturn(user);
		boolean  flag = userServiceImpl.saveUser(user);
		assertEquals("User saved.Test case failed",false,flag);
		verify(userRepo, times(1)).save(user);
	}
	
	@Test
	public void testValidateSuccess() throws UserNotFoundException {
	
		when(userRepo.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User userResult = userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(userResult);
		assertEquals("admin",userResult.getUserId());
		verify(userRepo, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testValidateFailure() throws UserNotFoundException{
		when(userRepo.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(null);
		userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

}
