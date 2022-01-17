package br.edu.infnet.service.Impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.model.Monstro;
import br.edu.infnet.repository.MonstroRepository;
import br.edu.infnet.service.MonstroService;

@Service
public class MonstroServiceImpl implements MonstroService{

	@Autowired
	MonstroRepository repository;
	
	@Override
	public void create(Monstro entity) {
		this.repository.save(entity);
	}
	
	
	@Override
	public void delete(Monstro entity) {
		this.repository.delete(entity);	
	}
	
	@Override
	public Monstro getById(Long id) {
		return this.repository.findById(id).orElseThrow(NoSuchElementException::new);
	}
	
	@Override
	public void update(Long id, Monstro entity) {
		entity.setId(id);
		this.repository.save(entity);
	}

	@Override
	public List<Monstro> getAll() {
		return this.repository.findAll();
	}


	@Override
	public Monstro getRandom() {
		
		List<Monstro> monstros = getAll();

		int random = (int) Math.round(Math.random() * (monstros.size()-1));
		System.out.println(random);
		
		return monstros.get(random);
	}






	
	
}
