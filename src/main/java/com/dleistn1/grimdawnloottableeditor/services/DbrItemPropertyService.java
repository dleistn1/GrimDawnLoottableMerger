package com.dleistn1.grimdawnloottableeditor.services;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Service for operations with Grim Dawn item properties.
 *
 * @author Daniel Leistner
 */
public class DbrItemPropertyService implements ItemPropertyService {

	private static final String ITEM_NAME_TAG = "itemNameTag";
	private static final Map<String, String> ITEM_NAME_CACHE = new HashMap<String, String>();

	public DbrItemPropertyService() {
		try (Stream<String> stream = Files.lines(Paths.get(Configuration.TEXTS_PATH))) {
			if (!ITEM_NAME_CACHE.isEmpty()) {
				return;
			}
			stream.forEach((line) -> addToCache(line));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public String getItemName(Record record) {
		File itemFile = Paths.get(record.getAbsolutePath()).toFile();
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
			throw new UncheckedIOException(e);
		}
	}
}
