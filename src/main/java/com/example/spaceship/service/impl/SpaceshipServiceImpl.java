package com.example.spaceship.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.spaceship.model.SpaceshipDTO;
import com.example.spaceship.repository.SpaceshipRepo;
import com.example.spaceship.service.SpaceshipService;

@Service
public class SpaceshipServiceImpl implements SpaceshipService{
	@Autowired
	private SpaceshipRepo spaceshipRepo;

	@Override
	@CacheEvict(value = "spaceships", key = "#spaceshipDTO.id")
	public void saveSpaceShip(SpaceshipDTO spaceshipDTO) {
		
		spaceshipRepo.save(spaceshipDTO);
	}

	@Override
	@Cacheable("allSpaceships")
	public List<SpaceshipDTO> findAllSpaceships() {
		return spaceshipRepo.findAll();
	}

	@Override
    @Cacheable("spaceships")
	public SpaceshipDTO findSpaceShipById(Long id) {
		Optional<SpaceshipDTO> spaceshipDTOOpt = spaceshipRepo.findById(id);
		if(spaceshipDTOOpt.isPresent()) {
			return spaceshipDTOOpt.get();
		}
		return null;
	}

	
	@Override
	public void deleteSpaceship(SpaceshipDTO spaceshipDTO) {
		spaceshipRepo.delete(spaceshipDTO);
	}

	@Override
	public Page<SpaceshipDTO> findAllSpaceshipsByName(String filter, Pageable pageable) {
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO(filter);		
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withMatcher("name", match -> match.contains().ignoreCase());		
		Example<SpaceshipDTO> spaceshipDTOExample = Example.of(spaceshipDTO, matcher);
		
		return spaceshipRepo.findAll(spaceshipDTOExample, pageable);
	}

	@Override
	@Cacheable(value = "spaceshipExistence", key = "#name")
	public boolean getSpaceShipExistenceStatus(String name) {
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO(name);		
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withMatcher("name", match -> match.exact().ignoreCase());		
		Example<SpaceshipDTO> spaceshipDTOExample = Example.of(spaceshipDTO, matcher);
		
		Optional<SpaceshipDTO> spaceshipDTOOpt = spaceshipRepo.findOne(spaceshipDTOExample);
		
		if(spaceshipDTOOpt.isPresent()) {
			return true;
		}
		return false;
	}

	@Override
	@CacheEvict(value = "spaceships", key = "#spaceshipDTO.id")
	public void deleteSpaceshipById(Long id) {
		spaceshipRepo.deleteById(id);
	}
}
