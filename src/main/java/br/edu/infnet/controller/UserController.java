package br.edu.infnet.controller;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.HeaderParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.client.SegurancaClient;
import br.edu.infnet.model.User;
import br.edu.infnet.model.UserDTO;
import br.edu.infnet.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
	
	Logger logger = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	UserService userService;
	
	@Autowired
	SegurancaClient segurancaClient;
	
	
	@PostMapping
	public void create(@RequestBody User u) {
		
		logger.info("Criando o user: " + u.getUsername());
		u.setPass(new BCryptPasswordEncoder().encode(u.getPass()));
		
		this.userService.create(u);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		try {
			User byId = this.userService.getById(id);
			logger.info("Deletando o user: id:" + byId.getId() + " nome:" + byId.getUsername());
			this.userService.delete(byId);
		} catch (NoSuchElementException e) {
			logger.info("User n??o encontrado " + "(id:"+id+") ");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		logger.info("Buscando user pelo id: " + id);
		
		User u = null;
		
		try {
			u = this.userService.getById(id);
			logger.info("User encontrado: id:" + u.getId() + " nome:" + u.getUsername());
			return new ResponseEntity<User>(u,HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.info("User n??o encontrado " + "(id:"+id+") ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody User comingUser){
		
		
		try {
			
			User existingUser = this.userService.getById(id);
			logger.info("User encontrado: id:" + existingUser.getId() + " nome:" + existingUser.getUsername());
			copyNonNullProperties(comingUser, existingUser);
			logger.info("Alterando user: " + id);
			this.userService.update(id, existingUser);
		} catch (NoSuchElementException e) {
			logger.info("User n??o encontrado " + "(id:"+id+") ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping
	public List<User> getAll() {
		logger.info("Listando todos os users");
		return userService.getAll();
	}
	
	@GetMapping("/search")
	public ResponseEntity<User> getByUsername (@RequestParam(required=true) String username) {
		
		logger.info("Procurando usu??rio com nome: " + username);
		
		Optional<User> u = userService.findByUsername(username);
		
		if (u.isPresent()){
			logger.info("Usuario de id:" + u.get().getId() + " encontrado.");
			return new ResponseEntity<User>(u.get(),HttpStatus.OK);
		}
		
		logger.info("N??o foi encontrado nenhum usu??rio com o nome: " + username);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
    }
	
//SEGURANCA ----------------------------------------------------------------------------------------
	
	@GetMapping("/whoami")
	public ResponseEntity<UserDTO> whoami(@RequestHeader("Authorization") String token) {
		
		try {
			UserDTO user = segurancaClient.getWhoami(token);
			return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
	}
	
//UTIL ---------------------------------------------------------------------------------------------
public static void copyNonNullProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
}

public static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();
    for(java.beans.PropertyDescriptor pd : pds) {
        Object srcValue = src.getPropertyValue(pd.getName());
        if (srcValue == null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
}

	

	
}
