package com.dleistn1.grimdawnloottableeditor.model.services;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import java.io.IOException;
import java.util.List;

/**
 * Defines the service for operations on DBR record files.
 *
 * @author Daniel Leistner
 */
public interface RecordService {

	List<Record> query(List<String> paths);

	void writeLoottableFile(List<Record> records, String path) throws IOException;
}
