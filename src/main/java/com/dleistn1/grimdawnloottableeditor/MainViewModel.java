/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.*;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import com.dleistn1.grimdawnloottableeditor.services.RecordService;

/**
 *
 * @author Daniel Leistner
 */
public class MainViewModel implements ViewModel{
    
    @Inject
    private RecordService recordService;
    
    private final Command listRecordsCommand; 
    private final Command createFileCommand;
    
    private final StringProperty outputFilePath = new SimpleStringProperty();
    private final StringProperty inputItemsFolder = new SimpleStringProperty();
    
    private final ObservableList<RecordEntryViewModel> records = FXCollections.observableArrayList();
    
    public MainViewModel() {       
        
        listRecordsCommand = new DelegateCommand(() -> new Action(){
            @Override
            protected void action() throws Exception {
                listRecords();
            }
        });
        
        createFileCommand = new DelegateCommand(() -> new Action(){
            @Override
            protected void action() throws Exception {
                createFile();
            }
        });
    }   
    
    public Command getListRecordsCommand(){
        return listRecordsCommand;
    }   
    
    public Command getCreateFileCommand(){
        return createFileCommand;
    }   
    
    public ObservableList<RecordEntryViewModel> recordsProperty(){
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
    
    private void listRecords(){
        records.clear();
        
        List<String> paths = Arrays.asList(inputItemsFolder.get().split(";"));
        List<Record> entries = recordService.query(paths);            
        
        Collections.sort(entries, (Record a, Record b) -> {
            return b.getFileName().compareTo(a.getFileName());
        });
        
        Collections.reverse(entries);
        
        entries.forEach((entry) -> records.add(new RecordEntryViewModel(entry)));
    }
    
    private void createFile(){
        List<Record> selectedRecords = this.records.stream()
                .filter(record -> record.isSelectedProperty().get())
                .map(RecordEntryViewModel::getRecord)
                .collect(Collectors.toList());
        
        String outputPath = outputFilePath.get();
        
        if(outputPath == null || outputPath.equals("")){
            return; // TODO
        }
        
        recordService.writeLoottableFile(selectedRecords, outputPath);
    }    
}
