package br.edu.infnet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.model.Perfil;
import br.edu.infnet.model.User;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Long>   {
	Optional<Perfil> findByName(String name);
}
