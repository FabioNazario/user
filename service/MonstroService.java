package br.edu.infnet.service;

import java.util.List;

import br.edu.infnet.model.Monstro;

public interface MonstroService {
	void create(Monstro entity);
	void delete(Monstro entity);
	Monstro getById(Long id);
	void update(Long id, Monstro entity);
	List<Monstro> getAll();
    Monstro getRandom();
}
