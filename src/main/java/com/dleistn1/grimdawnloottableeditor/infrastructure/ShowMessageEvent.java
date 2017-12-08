package com.dleistn1.grimdawnloottableeditor.infrastructure;

/**
 *
 * @author Daniel Leistner
 */
public class ShowMessageEvent {

	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 * @return The event
	 */
	public ShowMessageEvent setMessage(String message) {
		this.message = message;
		return this;
	}
}
