package com.persona.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
	
	@Id
	private String id;
	
	private String typePerson;
	
	private Address address;
	
	private Documents documents;
	
	private PersonLegal personLegal;
	
	private PersonNatural personNatural;
	
	

}
