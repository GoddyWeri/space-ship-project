package com.example.spaceship.Exception;

import com.example.spaceship.model.SpaceshipDTO;

public class CustomExceptionHandler extends SpaceshipDTO{

	private String errorMessage;
	
	public CustomExceptionHandler(final String errorMsg) {
		errorMessage = errorMsg;
	}
	

    public String getErrorMessage() {
        return errorMessage;
    }

}
