package com.dleistn1.grimdawnloottablemerger.services;

import com.dleistn1.grimdawnloottableeditor.infrastructure.ShowMessageEvent;
import com.google.common.eventbus.EventBus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Exception handler that shows alerts.
 *
 * @author Daniel Leistner
 */
public class ExceptionHandlerServiceImpl implements ExceptionHandlerService {

	private final EventBus eventBus;

	@Inject
	public ExceptionHandlerServiceImpl(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void handle(Exception e) {
		Logger.getLogger(ExceptionHandlerServiceImpl.class.getName()).log(Level.SEVERE, null, e);
		// TODO Move this to own service
		ShowMessageEvent event = new ShowMessageEvent().setMessage(e.getMessage());
		eventBus.post(event);
	}
}
