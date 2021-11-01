package br.com.meutudo.banksystem.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok().body(this.userService.getUsers());
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) {
		return ResponseEntity.ok().body(this.userService.getUserById(id));
	}

	@PostMapping("/user/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		user.setActive(true);
		user.setCreationDate(new Date());
		return ResponseEntity.ok().body(this.userService.createUser(user));
	}

	@PutMapping("/user/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user) {
		user.setId(id);
		return ResponseEntity.ok().body(this.userService.updateUser(user));
	}

	@DeleteMapping("/user/delete/{id}")
	public HttpStatus deleteUser(@PathVariable long id) {
		this.userService.deleteUser(id);
		return HttpStatus.OK;
	}
}
