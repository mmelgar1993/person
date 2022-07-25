package com.persona.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persona.dao.IPersonDao;
import com.persona.model.Person;
import com.persona.service.IPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	IPersonDao iPerson;

	@Override
	public Mono<Person> save(Person person) {
		
		return iPerson.save(person);
	}

	@Override
	public Mono<Person> update(Person person) {
		
		return iPerson.save(person);
	}

	@Override
	public Flux<Person> findAll() {
		
		return iPerson.findAll();
	}

	@Override
	public Mono<Person> findById(String id) {
		
		return iPerson.findById(id);
	}

}
