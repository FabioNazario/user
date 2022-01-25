package br.edu.infnet.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.infnet.model.Perfil;
import br.edu.infnet.model.User;
import br.edu.infnet.repository.PerfilRepository;
import br.edu.infnet.repository.UserRepository;
import br.edu.infnet.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@Override
	public void create(User user) {
		
		user.setPass(new BCryptPasswordEncoder().encode(user.getPass()));
		user.setPerfis(preparePerfisToSave(user));
		this.userRepository.save(user);	
	}
	
	
	@Override
	public void delete(User entity) {
		this.userRepository.delete(entity);	
	}
	
	@Override
	public User getById(Long id) {
		return this.userRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}
	
	@Override
	public void update(Long id, User user) {
		user.setPass(new BCryptPasswordEncoder().encode(user.getPass()));
		user.setPerfis(preparePerfisToSave(user));
		this.userRepository.save(user);	
		
		user.setId(id);
		this.userRepository.save(user);
	}

	@Override
	public List<User> getAll() {
		return this.userRepository.findAll();
	}


	@Override
	public Optional<User> findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	
	//UTIL--------------------------------------------------------------------------------
	public Set<Perfil> preparePerfisToSave(User user){
		
		if (user.getPerfis() == null) 
			return null;
		
		Set<Perfil> perfilList = new HashSet<>();
		
		for (Perfil perfil : user.getPerfis()) {
			
			Optional<Perfil> bdPerfil = this.perfilRepository.findByName(perfil.getName());
			if (!bdPerfil.isPresent()) {
				perfilList.add(this.perfilRepository.save(perfil));
			}else {
				perfilList.add(bdPerfil.get());
			}
		}
		
		return perfilList;
	}
}
