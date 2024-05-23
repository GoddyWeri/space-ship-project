package com.example.spaceship.service;

import java.util.List;


import com.example.spaceship.model.SpaceshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SpaceshipService {

	void saveSpaceShip(SpaceshipDTO spaceshipDTO);

	List<SpaceshipDTO> findAllSpaceships();

	SpaceshipDTO findSpaceShipById(Long id);

	void deleteSpaceship(SpaceshipDTO spaceshipDTO);

	Page<SpaceshipDTO> findAllSpaceshipsByName(String string, Pageable pageable);

	boolean getSpaceShipExistenceStatus(String string);

	void deleteSpaceshipById(Long id);

}
