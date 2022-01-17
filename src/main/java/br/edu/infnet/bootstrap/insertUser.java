package br.edu.infnet.bootstrap;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.edu.infnet.model.Perfil;
import br.edu.infnet.model.User;
import br.edu.infnet.repository.PerfilRepository;
import br.edu.infnet.repository.UserRepository;

@Component
public class insertUser {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PerfilRepository perfilRepository;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		User user = new User();
		
		user.setUsername("fabio");
		user.setPass(new BCryptPasswordEncoder().encode("123456"));
		
		Perfil perfil = perfilRepository.findById(2L).get();
		HashSet<Perfil> perfis = new HashSet<Perfil>();
		perfis.add(perfil);
		user.setPerfis(perfis);
		
		this.userRepository.save(user);
	
	}
	
	
}
