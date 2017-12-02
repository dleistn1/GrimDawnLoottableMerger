/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor.model;

/**
 *
 * @author Daniel Leistner
 */
public class Record {
    
    private String fileName;
    private String itemName;
    private String absolutePath;    
    private String loottableRecordEntry;
    
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the path
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * @param path the path to set
     */
    public void setAbsolutePath(String path) {
        this.absolutePath = path;
    }

    /**
     * @return the loottableRecordEntry
     */
    public String getLoottableRecordEntry() {
        return loottableRecordEntry;
    }

    /**
     * @param loottableRecordEntry the loottableRecordEntry to set
     */
    public void setLoottableRecordEntry(String loottableRecordEntry) {
        this.loottableRecordEntry = loottableRecordEntry;
    }    
}
