package com.dleistn1.grimdawnloottableeditor.services;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception handler that shows alerts.
 * @author Daniel Leistner
 */
public class AlertExceptionHandlerService implements ExceptionHandlerService{

	@Override
	public void handle(Exception e) {
		Logger.getLogger(AlertExceptionHandlerService.class.getName()).log(Level.SEVERE, null, e);
	}
}
