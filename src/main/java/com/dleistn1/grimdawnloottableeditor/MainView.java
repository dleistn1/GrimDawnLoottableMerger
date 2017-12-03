package com.dleistn1.grimdawnloottableeditor;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import de.saxsys.mvvmfx.utils.viewlist.ViewListCellFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * The main application view controller.
 * @author Daniel Leistner
 */
public class MainView implements FxmlView<MainViewModel>, Initializable {
    
    @InjectViewModel
    private MainViewModel viewModel;
    
    @FXML
    private ListView<RecordEntryViewModel> listDbrRecords;
    
    @FXML
    private TextField txtOutputFilePath;
    
    @FXML
    private TextField txtInputItemsFolder;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ViewListCellFactory<RecordEntryViewModel> recordEntryCellFactory =
            CachedViewModelCellFactory.createForFxmlView(RecordEntryView.class);
        
        this.listDbrRecords.setItems(viewModel.recordsProperty());
        this.listDbrRecords.setCellFactory(recordEntryCellFactory);
        
        this.txtOutputFilePath.textProperty().bindBidirectional(viewModel.outputFilePathProperty());
        this.txtInputItemsFolder.textProperty().bindBidirectional(viewModel.inputItemsFolderProperty());
    }    
    
    @FXML
    public void listRecordsAction(){
        viewModel.getListRecordsCommand().execute();
    }
    
    @FXML
    public void createFileAction(){
        viewModel.getCreateFileCommand().execute();
    }
}
