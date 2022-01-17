package br.edu.infnet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.model.Perfil;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Long>   {

}
