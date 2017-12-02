/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableStringValue;

/**
 *
 * @author Daniel Leistner
 */
public class RecordEntryViewModel implements ViewModel{
    
    private final BooleanProperty isSelected = new SimpleBooleanProperty();
    private final ReadOnlyStringWrapper fileName = new ReadOnlyStringWrapper();
    private final ReadOnlyStringWrapper itemName = new ReadOnlyStringWrapper();
    private final ReadOnlyStringWrapper entryPath = new ReadOnlyStringWrapper();
        
    private final Record record;

    public RecordEntryViewModel(Record record) {
        this.record = record;
        this.fileName.set(record.getFileName());
        this.itemName.set(record.getItemName());
        this.entryPath.set(record.getLoottableRecordEntry());
    }
    
     /**
     * @return the record
     */
    public Record getRecord() {
        return record;
    }
    
    public BooleanProperty isSelectedProperty(){
        return isSelected;
    }   
    
    public ObservableStringValue fileNameProperty(){
        return this.fileName.getReadOnlyProperty();
    }   
    
    public ObservableStringValue itemNameProperty(){
        return this.itemName.getReadOnlyProperty();
    }   
    
    public ObservableStringValue entryPathProperty(){
        return this.entryPath.getReadOnlyProperty();
    }    
}
