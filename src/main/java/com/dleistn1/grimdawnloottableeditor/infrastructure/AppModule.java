package com.dleistn1.grimdawnloottableeditor.infrastructure;

import com.dleistn1.grimdawnloottableeditor.model.services.*;
import com.dleistn1.grimdawnloottableeditor.services.*;
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
		bind(StatusMessageService.class).to(StatusMessageServiceImpl.class);
		bind(LocaleService.class).to(LocaleServiceImpl.class);
	}
}
