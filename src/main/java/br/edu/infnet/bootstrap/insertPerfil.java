package br.edu.infnet.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.edu.infnet.model.Perfil;
import br.edu.infnet.repository.PerfilRepository;

@Component
public class insertPerfil {
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		this.perfilRepository.save(Perfil.builder().name("ROLE_ROOT").build());
		this.perfilRepository.save(Perfil.builder().name("ROLE_ADMIN").build());
		this.perfilRepository.save(Perfil.builder().name("ROLE_USER").build());
	}
	
	
}
