package br.com.meutudo.banksystem.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meutudo.banksystem.exception.ResourceNotFoundException;
import br.com.meutudo.banksystem.model.User;
import br.com.meutudo.banksystem.repository.UserRepository;
import br.com.meutudo.banksystem.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> userDb = this.userRepository.findById(user.getId());

		if (userDb.isPresent()) {
			User userToUpdate = userDb.get();
			userToUpdate.setName(user.getName());
			userToUpdate.setLogin(user.getLogin());
			userToUpdate.setPassword(user.getPassword());
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setCpf(user.getCpf());
			userToUpdate.setRg(user.getRg());
			userToUpdate.setPhone(user.getPhone());
			userRepository.save(userToUpdate);
			return userToUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + user.getId());
		}
	}

	@Override
	public List<User> getUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public User getUserById(long userId) {
		Optional<User> userDb = this.userRepository.findById(userId);

		if (userDb.isPresent()) {
			return userDb.get();
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + userId);
		}
	}

	@Override
	public void deleteUser(long userId) {
		Optional<User> userDb = this.userRepository.findById(userId);

		if (userDb.isPresent()) {
			this.userRepository.delete(userDb.get());
		} else {
			throw new ResourceNotFoundException("Record not found with id : " + userId);
		}
	}
}
