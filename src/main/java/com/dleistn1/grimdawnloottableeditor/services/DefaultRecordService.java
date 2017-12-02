/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor.services;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.inject.Inject;

/**
 *
 * @author Daniel Leistner
 */
public class DefaultRecordService implements RecordService{
    
    private static final String RECORD_PATH_SPLIT_VALUE = "database";
    private static final String TEMPLATE_REPLACEMENT_MARKER = "<REPLACER>";
    private static final String DEFAULT_ENTRY_WEIGHT = "100";
       
    private static final int MAX_RECORD_ENTRIES = 80;
       
    @Inject
    private ItemPropertyService itemPropertyService;
        
    @Override
    public List<Record> query(List<String> paths){            
        List<Record> recordList = new ArrayList();           
        paths.forEach((path)-> addRecordsOfPath(recordList, path));
        
        return recordList;
    }   
    
    @Override
    public void writeLoottableFile(List<Record> records, String path) {
        try{
            List<String> loottableEntries = new ArrayList<>();         
       
            for(int currentEntryIndex = 1; currentEntryIndex <= MAX_RECORD_ENTRIES; currentEntryIndex++){      
                int currentRecordIndex = currentEntryIndex - 1;
                String recordEntry = records.size() >= currentEntryIndex ? 
                        records.get(currentRecordIndex).getLoottableRecordEntry() :
                        "";            
                addRecordEntryInLoottable(loottableEntries, recordEntry, currentEntryIndex);
            }        
            
            List<String> templateFileLines = Files.readAllLines(Paths.get(Configuration.TEMPLATE_LOOTTABLE_PATH));
            String joinedTemplateContent = String.join(System.lineSeparator(), templateFileLines);
            String joinedEntryContent = String.join(System.lineSeparator(), loottableEntries);            
            String loottableContent = joinedTemplateContent.replace(TEMPLATE_REPLACEMENT_MARKER, joinedEntryContent);
                        
            Path createdFile = Files.createFile(Paths.get(path));
            Files.write(createdFile, loottableContent.getBytes());              
        }catch(Exception e){
            // TODO
        }        
    }
    
    private void addRecordsOfPath(List<Record> recordList, String path){
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
               paths
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach((file) -> {                    
                    recordList.add(createRecord(file));                    
                 });
        }catch(IOException e){
            // TODO
        }
    }
    
    private Record createRecord(File file){
        
        Record record = new Record();        
        record.setFileName(file.getName());
        record.setAbsolutePath(file.getAbsolutePath());
        record.setLoottableRecordEntry(mapFilePathToLoottablePath(record.getAbsolutePath()));
        record.setItemName(itemPropertyService.getItemName(file));
              
        return record;
    }
    
    private String mapFilePathToLoottablePath(String absolutePath){
        final int pathStartIndex = 1;
        String[] pathParts = absolutePath.split(RECORD_PATH_SPLIT_VALUE);
        return pathParts[1].replace("\\", "/").substring(pathStartIndex);
    }    
    
    private void addRecordEntryInLoottable(List<String> newLines, String loottableRecordEntry, int index){                   
        String recordEntry = String.format("lootName%s,%s,", index, loottableRecordEntry);        
        newLines.add(recordEntry);
        String lootWeight = loottableRecordEntry.equals("") ? "" : DEFAULT_ENTRY_WEIGHT;
        newLines.add(String.format("lootWeight%s,%s,", index, lootWeight));
    }
}
