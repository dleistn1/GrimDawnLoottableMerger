package com.dleistn1.grimdawnloottableeditor.infrastructure;

import com.dleistn1.grimdawnloottableeditor.services.DbrItemPropertyService;
import com.dleistn1.grimdawnloottableeditor.services.ExceptionHandlerService;
import com.dleistn1.grimdawnloottableeditor.services.ItemPropertyService;
import com.dleistn1.grimdawnloottableeditor.services.ExceptionHandlerServiceImpl;
import com.dleistn1.grimdawnloottableeditor.services.RecordService;
import com.dleistn1.grimdawnloottableeditor.services.RecordServiceImpl;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 *
 * @author Daniel Leistner
 */
public class AppModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBus.class).in(Scopes.SINGLETON);
		bind(ExceptionHandlerService.class).to(ExceptionHandlerServiceImpl.class);
		bind(RecordService.class).to(RecordServiceImpl.class);
		bind(ItemPropertyService.class).to(DbrItemPropertyService.class);
	}
}
