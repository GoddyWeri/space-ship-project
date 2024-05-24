package com.example.spaceship.controller;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spaceship.Exception.CustomExceptionHandler;
import com.example.spaceship.kafka.producer.MyKafkaProducer;
import com.example.spaceship.model.SpaceshipDTO;
import com.example.spaceship.model.app.PageableInfoObject;
import com.example.spaceship.service.SpaceshipService;
import com.example.spaceship.utils.Utils;

@RestController
@Component
@RequestMapping("/spaceships")
public class SpaceShipController {
	
	private static final Logger logger = LogManager.getLogger(SpaceShipController.class);
	
	@Autowired
	private SpaceshipService spaceshipService;
	
	 @Autowired
	    private MyKafkaProducer kafkaProducer;

	/**
	 * This API returns all the spaceships from database
	 * @param pageableInfoObjectMap
	 * @return Page<SpaceshipDTO>
	 */
	@PostMapping(path = "/all-spaceships", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<SpaceshipDTO>> listAllSpaceShips(@RequestBody PageableInfoObject<Map<String, String>> pageableInfoObjectMap) {
		logger.trace("extracting all spaceships..");
		Page<SpaceshipDTO> paginatedInfo = spaceshipService.findAllSpaceshipsByName(Utils.EMPTY_STRING_FILTER, pageableInfoObjectMap.getPageRequest());
		return new ResponseEntity<Page<SpaceshipDTO>>(paginatedInfo, HttpStatus.OK);
	}
	
	/**
	 * Creates the spaceship in database
	 * @param nameMap
	 * @return ResponseEntity<SpaceshipDTO>
	 */
	@PostMapping(path = "/create-spaceship", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SpaceshipDTO> createSpaceShip(@RequestBody Map<String, String> nameMap) {
		logger.trace("Creating spaceship..");
		if(spaceshipService.getSpaceShipExistenceStatus(nameMap.get("name"))) {
	        return new ResponseEntity<SpaceshipDTO>(new CustomExceptionHandler(Utils.CONFLICT_NAMES_DB), HttpStatus.CONFLICT);
		}
		SpaceshipDTO spaceshipDTO = new SpaceshipDTO(nameMap.get("name"));
		spaceshipService.saveSpaceShip(spaceshipDTO);
		return new ResponseEntity<SpaceshipDTO>(spaceshipDTO, HttpStatus.OK);
	}
	
	/**
	 * API for getting spaceship by id
	 * @param id
	 * @return ResponseEntity<SpaceshipDTO>
	 */
   @GetMapping(path ="/get-spaceship/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpaceshipDTO> findSpaceShipById(@PathVariable("id") final Long id){
		logger.trace("Searching for spaceship..");
	   SpaceshipDTO spaceshipDTO = spaceshipService.findSpaceShipById(id);
	   if(spaceshipDTO == null) {
	        return new ResponseEntity<SpaceshipDTO>(new CustomExceptionHandler(Utils.ID_NOT_FOUND), HttpStatus.NOT_FOUND);
	   }
	   return new ResponseEntity<SpaceshipDTO>(spaceshipDTO, HttpStatus.OK);
   }
   
   //Normally, this would have been a patchMapping, but as our entity just have one field aside from ID, we use put.
   /**
    * API for updating spaceships
    * @param id
    * @param nameMap
    * @return ResponseEntity<SpaceshipDTO>
    */
   @PutMapping(path ="/update-spaceship/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<SpaceshipDTO> updateSpaceshipName(@PathVariable("id") final Long id, @RequestBody Map<String, String> nameMap){
		logger.trace("Updating spaceship..");
	   SpaceshipDTO spaceshipDTO = spaceshipService.findSpaceShipById(id);
	   if(spaceshipDTO == null) {
	        return new ResponseEntity<SpaceshipDTO>(new CustomExceptionHandler(Utils.ID_NOT_FOUND), HttpStatus.NOT_FOUND);
	   }
	   spaceshipDTO.setName(nameMap.get("name"));
	   spaceshipService.saveSpaceShip(spaceshipDTO);

	   return new ResponseEntity<SpaceshipDTO>(spaceshipDTO, HttpStatus.OK);
   }
   
   /**
    * API for filtering by string list of spaceships
    * @param pageableInfoObjectMap
    * @return ResponseEntity<Page<SpaceshipDTO>>
    */
   @PostMapping(path = "/filter-spaceships", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<SpaceshipDTO>> filterSpaceShipList(@RequestBody PageableInfoObject<Map<String, String>> pageableInfoObjectMap) {
		logger.trace("Filtering spaceship list..");
		String filter = pageableInfoObjectMap.getObject().get("filter");
		Page<SpaceshipDTO> paginatedInfo = spaceshipService.findAllSpaceshipsByName(filter, pageableInfoObjectMap.getPageRequest());
		return new ResponseEntity<Page<SpaceshipDTO>>(paginatedInfo, HttpStatus.OK);
	}
   
   /**
    * API for deleting spaceship by id
    * @param id
    * @return ResponseEntity<SpaceshipDTO>
    */
   @DeleteMapping(path = "/delete-spaceship/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  	public ResponseEntity<SpaceshipDTO> deleteSpaceshipById(@PathVariable("id") final Long id) {
  		logger.trace("Deleting spaceship from system..");
  		SpaceshipDTO spaceshipDTO = spaceshipService.findSpaceShipById(id);
  		if(spaceshipDTO == null) {
	        return new ResponseEntity<SpaceshipDTO>(new CustomExceptionHandler(Utils.ID_NOT_FOUND), HttpStatus.NOT_FOUND);
  		}
  		spaceshipService.deleteSpaceshipById(id);
  		return new ResponseEntity<SpaceshipDTO>(new SpaceshipDTO(), HttpStatus.NO_CONTENT);
  	}
   
   
   /**
    * Kafka API for messaging into console
    * @param message
    * @return ResponseEntity<String>
    */
   @PostMapping("/message-spaceships")
   public ResponseEntity<String> sendMessage(@RequestBody Map<String, String> messageMap) {
       kafkaProducer.sendMessage(Utils.SPACESHIP_TOPIC, messageMap.get("message"));
       return new ResponseEntity<String>(Utils.OK_SENT, HttpStatus.OK);
   }
}
