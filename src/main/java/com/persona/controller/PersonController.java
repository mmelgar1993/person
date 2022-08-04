package com.persona.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persona.model.Person;
import com.persona.service.IPersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	IPersonService personService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Person>>>  findAll(){
		Flux<Person> fx = personService.findAll();
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fx));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> findById(@PathVariable("id") String id){
		return personService.findById(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Person>> save(@RequestBody Person person, final ServerHttpRequest req){
		return personService.save(person)
				.map( p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p));
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Person>> update(@PathVariable("id") String id,@RequestBody Person person){
		Mono<Person> monoBody = Mono.just(person);
		Mono<Person> monoBD = personService.findById(id);
		return monoBD.zipWith(monoBody, (bd, ps) -> {
					bd.setId(id);
					bd.setDocuments(ps.getDocuments());
					bd.setPersonLegal(ps.getPersonLegal());
					bd.setPersonNatural(ps.getPersonNatural());
					bd.setTypePerson(ps.getTypePerson());
					bd.setAddres(ps.getAddres());
					return bd;
				})
				.flatMap(personService::update)
				.map(y -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON).
						body(y))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
