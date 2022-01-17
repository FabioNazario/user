package br.edu.infnet.service;

import java.util.List;

import br.edu.infnet.model.User;

public interface UserService {
	void create(User entity);
	void delete(User entity);
	User getById(Long id);
	void update(Long id, User entity);
	List<User> getAll();
}
