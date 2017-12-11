package com.dleistn1.grimdawnloottableeditor.services;

import com.dleistn1.grimdawnloottableeditor.infrastructure.ShowMessageEvent;
import com.google.common.eventbus.EventBus;
import javax.inject.Inject;

/**
 * Implements Service for showning status messages.
 *
 * @author Daniel Leistner
 */
public class StatusMessageServiceImpl implements StatusMessageService {

	private final EventBus eventBus;

	@Inject
	public StatusMessageServiceImpl(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void show(String message) {
		ShowMessageEvent event = new ShowMessageEvent().setMessage(message);
		eventBus.post(event);
	}

}
