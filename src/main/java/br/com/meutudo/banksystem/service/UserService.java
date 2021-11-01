package br.com.meutudo.banksystem.service;

import java.util.List;

import br.com.meutudo.banksystem.model.User;

public interface UserService {
	User createUser(User user);

	User updateUser(User user);

	List<User> getUsers();

	User getUserById(long userId);

	void deleteUser(long userId);
}
