package com.example.spaceship.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Spaceship")
public class SpaceshipDTO {
	
	@Id
    @GeneratedValue()
    @Column(name = "id")
    private Long id;

	@Column(name = "name")
    private String name;

    public SpaceshipDTO( ) {
		
	}
    
    public SpaceshipDTO(String name) {
		this.name = name;
	}
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


}
