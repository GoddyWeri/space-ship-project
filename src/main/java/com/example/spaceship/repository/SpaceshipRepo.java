package com.example.spaceship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spaceship.model.SpaceshipDTO;

@Repository
public interface SpaceshipRepo extends JpaRepository<SpaceshipDTO, Long>{

}
