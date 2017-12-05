package com.dleistn1.grimdawnloottableeditor.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.inject.Inject;

/**
 * Service for operations with Grim Dawn item properties.
 *
 * @author Daniel Leistner
 */
public class DefaultItemPropertyService implements ItemPropertyService {

	private static final String ITEM_NAME_TAG = "itemNameTag";
	private static final Map<String, String> ITEM_NAME_CACHE = new HashMap<String, String>();

	@Inject
	private ExceptionHandlerService exceptionHandler;

	public DefaultItemPropertyService() {
		try (Stream<String> stream = Files.lines(Paths.get(Configuration.TEXTS_PATH))) {
			if (!ITEM_NAME_CACHE.isEmpty()) {
				return;
			}
			stream.forEach((line) -> addToCache(line));
		} catch (IOException e) {
			exceptionHandler.handle(e);
		}
	}

	@Override
	public String getItemName(File itemFile) {
		String itemName = getItemTagByFile(itemFile);
		return itemName == null ? "" : ITEM_NAME_CACHE.get(getItemTagByFile(itemFile));
	}

	private void addToCache(String entry) {
		String[] entryParts = entry.split("=");

		if (entryParts.length != 2) {
			return;
		}

		ITEM_NAME_CACHE.put(entryParts[0], entryParts[1]);
	}

	private String getItemTagByFile(File itemFile) {
		try (Stream<String> stream = Files.lines(Paths.get(itemFile.getAbsolutePath()))) {
			return stream
					.filter((line) -> line.contains(ITEM_NAME_TAG))
					.findFirst()
					.map(item -> item.split(",")[1])
					.get();
		} catch (IOException e) {
			return null;
		}
	}
}
