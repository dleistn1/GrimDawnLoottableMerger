/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dleistn1.grimdawnloottableeditor;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 *
 * @author Daniel Leistner
 */
public class RecordEntryView implements FxmlView<RecordEntryViewModel>, Initializable{
    
    @InjectViewModel
    private RecordEntryViewModel viewModel;
    
    @FXML
    private Label labelItemName;
    
    @FXML
    private Label labelEntryPath;
    
    @FXML
    private CheckBox cbxIsSelected;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        this.labelItemName.textProperty().bind(viewModel.itemNameProperty());
        this.labelEntryPath.textProperty().bind(viewModel.entryPathProperty());
        this.cbxIsSelected.selectedProperty().bindBidirectional(viewModel.isSelectedProperty());
    }    
}
