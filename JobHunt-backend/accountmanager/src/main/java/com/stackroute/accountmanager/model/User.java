package com.stackroute.accountmanager.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

	@Id
	private String userId;
	private String firstName;
	private String lastName;
	private String password;

	@CreationTimestamp
	private Date created;


	
	


}

