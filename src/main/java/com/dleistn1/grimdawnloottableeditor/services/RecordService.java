/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor.services;

import com.dleistn1.grimdawnloottableeditor.model.Record;
import java.util.List;

/**
 *
 * @author Daniel Leistner
 */
public interface RecordService {
    List<Record> query(List<String> paths);
    void writeLoottableFile(List<Record> records, String path);
}