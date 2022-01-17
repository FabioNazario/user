package br.edu.infnet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>   {
	Optional<User> findByUsername(String username);
}
