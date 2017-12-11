package com.dleistn1.grimdawnloottableeditor;

import com.dleistn1.grimdawnloottableeditor.infrastructure.ShowMessageEvent;
import com.dleistn1.grimdawnloottableeditor.model.Record;
import com.dleistn1.grimdawnloottableeditor.model.services.RecordService;
import com.dleistn1.grimdawnloottableeditor.services.ExceptionHandlerService;
import com.dleistn1.grimdawnloottableeditor.services.LocaleKeys;
import com.dleistn1.grimdawnloottableeditor.services.LocaleService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.inject.Inject;

/**
 * Viewmodel for the application
 *
 * @author Daniel Leistner
 */
public class MainViewModel implements ViewModel {

	private final EventBus eventBus;
	private final RecordService recordService;
	private final ExceptionHandlerService exceptionHandlerService;
	private final LocaleService localeService;

	private final Command listRecordsCommand;
	private final Command createFileCommand;

	private final StringProperty outputFilePath = new SimpleStringProperty();
	private final StringProperty inputItemsFolder = new SimpleStringProperty();
	private final StringProperty statusMessage = new SimpleStringProperty("");

	private final ObservableList<RecordEntryViewModel> records = FXCollections.observableArrayList();

	@Inject
	public MainViewModel(EventBus eventBus, RecordService recordService, ExceptionHandlerService exceptionHandlerService,
			LocaleService localeService) {

		this.eventBus = eventBus;
		this.recordService = recordService;
		this.exceptionHandlerService = exceptionHandlerService;
		this.localeService = localeService;

		this.eventBus.register(this);

		listRecordsCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
				listRecords();
			}
		});

		createFileCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
				createFile();
			}
		});
	}

	public Command getListRecordsCommand() {
		return listRecordsCommand;
	}

	public Command getCreateFileCommand() {
		return createFileCommand;
	}

	public ObservableList<RecordEntryViewModel> recordsProperty() {
		return records;
	}

	/**
	 * @return the outputFilePath
	 */
	public StringProperty outputFilePathProperty() {
		return outputFilePath;
	}

	/**
	 * @return the inputItemsFolder
	 */
	public StringProperty inputItemsFolderProperty() {
		return inputItemsFolder;
	}

	/**
	 * @return the statusMessage
	 */
	public StringProperty statusMessageProperty() {
		return statusMessage;
	}

	private void setStatusMessage(String message) {
		this.statusMessage.set(message);
	}

	@Subscribe
	public void onShowMessage(ShowMessageEvent e) {
		setStatusMessage(e.getMessage());
	}

	private void listRecords() {
		try {
			records.clear();

			List<String> paths = Arrays.asList(inputItemsFolder.get().split(";"));
			List<Record> entries = recordService.query(paths);

			Collections.reverse(
					entries
							.stream()
							.sorted((Record a, Record b) -> {
								return b.getFileName().compareTo(a.getFileName());
							})
							.collect(Collectors.toList())
			);
			entries.forEach((entry) -> records.add(new RecordEntryViewModel(entry)));
			setStatusMessage(localeService.get(LocaleKeys.MESSAGE_RECORDS_LOADED, records.size()));
		} catch (Exception e) {
			exceptionHandlerService.handle(e);
		}
	}

	private void createFile() {
		try {
			List<Record> selectedRecords = this.records.stream()
					.filter(record -> record.isSelectedProperty().get())
					.map(RecordEntryViewModel::getRecord)
					.collect(Collectors.toList());

			String outputPath = outputFilePath.get();

			if (outputPath == null || outputPath.equals("")) {
				return; // TODO
			}

			recordService.writeLoottableFile(selectedRecords, outputPath);
			setStatusMessage(localeService.get(LocaleKeys.MESSAGE_LOOTTABLE_CREATED, selectedRecords.size()));
		} catch (Exception e) {
			exceptionHandlerService.handle(e);
		}
	}
}
