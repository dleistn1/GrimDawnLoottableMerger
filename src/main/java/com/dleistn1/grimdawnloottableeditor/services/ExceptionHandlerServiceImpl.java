package com.dleistn1.grimdawnloottableeditor.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Exception handler that shows alerts.
 *
 * @author Daniel Leistner
 */
public class ExceptionHandlerServiceImpl implements ExceptionHandlerService {

	private final StatusMessageService statusMessageService;

	@Inject
	public ExceptionHandlerServiceImpl(StatusMessageService statusMessageService) {
		this.statusMessageService = statusMessageService;
	}

	@Override
	public void handle(Exception e) {
		Logger.getLogger(ExceptionHandlerServiceImpl.class.getName()).log(Level.SEVERE, null, e);
		statusMessageService.show(e.getMessage());
	}
}
