package br.edu.infnet.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.model.User;
import br.edu.infnet.repository.UserRepository;
import br.edu.infnet.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Override
	public void create(User entity) {
		this.repository.save(entity);
	}
	
	
	@Override
	public void delete(User entity) {
		this.repository.delete(entity);	
	}
	
	@Override
	public User getById(Long id) {
		return this.repository.findById(id).orElseThrow(NoSuchElementException::new);
	}
	
	@Override
	public void update(Long id, User entity) {
		entity.setId(id);
		this.repository.save(entity);
	}

	@Override
	public List<User> getAll() {
		return this.repository.findAll();
	}
}
