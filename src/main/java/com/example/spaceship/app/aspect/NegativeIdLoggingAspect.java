package com.example.spaceship.app.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class NegativeIdLoggingAspect {
	 @Before("execution(* com.example.spaceship.service.SpaceshipService.findSpaceShipById(..)) && args(id)")
	    public void logNegativeId(Long id) {
	        if (id < 0) {
	            System.out.println("Negative ID requested: " + id);
	            System.out.println("Sorry, Negative ids are not accepted in our system. Please try with a valid id.");

	    		//logger.trace();
	        }
	    }
}
