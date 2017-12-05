package com.dleistn1.grimdawnloottableeditor.services;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

/**
 * Service for handling of DBR record files.
 *
 * @author Daniel Leistner
 */
public class RecordServiceImpl implements RecordService {

	private static final String RECORD_PATH_SPLIT_VALUE = "database";
	private static final String TEMPLATE_REPLACEMENT_MARKER = "<REPLACER>";
	private static final String DEFAULT_ENTRY_WEIGHT = "100";
	private static final String TEMPLATE_FILE_PATH = "/file/tdyn_dan_template_common_mi.dbr";

	private static final int MAX_RECORD_ENTRIES = 80;

	@Inject
	private ItemPropertyService itemPropertyService;

	@Override
	public List<Record> query(List<String> paths) {
		List<Record> recordList = new ArrayList();
		paths.forEach((path) -> {
			try {
				addRecordsOfPath(recordList, path);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		});
		return recordList;
	}

	@Override
	public void writeLoottableFile(List<Record> records, String path) throws IOException{
		createLoottableFile(createLoottableEntries(records), path);
	}

	private List<String> createLoottableEntries(List<Record> records) {
		List<String> loottableEntries = new ArrayList<>();
		for (int currentEntryIndex = 1; currentEntryIndex <= MAX_RECORD_ENTRIES; currentEntryIndex++) {
			int currentRecordIndex = currentEntryIndex - 1;
			String recordEntry = records.size() >= currentEntryIndex
					? records.get(currentRecordIndex).getLoottableRecordEntry()
					: "";
			addRecordEntryInLoottable(loottableEntries, recordEntry, currentEntryIndex);
		}
		return loottableEntries;
	}

	private void createLoottableFile(List<String> loottableEntries, String path) throws IOException {

		try (InputStream resource = this.getClass().getResourceAsStream(TEMPLATE_FILE_PATH)) {

			Stream<String> templateFileStream = new BufferedReader(
					new InputStreamReader(resource, StandardCharsets.UTF_8))
					.lines();

			List<String> templateFileLines = templateFileStream
					.map(item -> {
						if (item.equals(TEMPLATE_REPLACEMENT_MARKER)) {
							return String.join(System.lineSeparator(), loottableEntries);
						} else {
							return item;
						}
					})
					.collect(Collectors.toList());

			Path createdFile = Files.createFile(Paths.get(path));
			Files.write(createdFile, templateFileLines);
		}
	}

	private void addRecordsOfPath(List<Record> recordList, String path) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(path))) {
			paths
					.filter(Files::isRegularFile)
					.map(Path::toFile)
					.forEach((file) -> {
						recordList.add(createRecord(file));
					});
		}
	}

	private Record createRecord(File file) {

		Record record = new Record();
		record.setFileName(file.getName());
		record.setAbsolutePath(file.getAbsolutePath());
		record.setLoottableRecordEntry(mapFilePathToLoottablePath(record.getAbsolutePath()));
		record.setItemName(itemPropertyService.getItemName(record));

		return record;
	}

	private String mapFilePathToLoottablePath(String absolutePath) {
		final int pathStartIndex = 1;
		String[] pathParts = absolutePath.split(RECORD_PATH_SPLIT_VALUE);
		return pathParts[1].replace("\\", "/").substring(pathStartIndex);
	}

	private void addRecordEntryInLoottable(List<String> newLines, String loottableRecordEntry, int index) {
		String recordEntry = String.format("lootName%s,%s,", index, loottableRecordEntry);
		newLines.add(recordEntry);
		String lootWeight = loottableRecordEntry.equals("") ? "" : DEFAULT_ENTRY_WEIGHT;
		newLines.add(String.format("lootWeight%s,%s,", index, lootWeight));
	}
}
