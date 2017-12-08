package com.dleistn1.grimdawnloottableeditor;

import com.dleistn1.grimdawnloottableeditor.infrastructure.AppModule;
import com.google.inject.Module;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;
import java.util.List;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class.
 *
 * @author Daniel Leistner
 */
public class MainApp extends MvvmfxGuiceApplication {

	/**
	 * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
	 * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
	 * ignores main().
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		ViewTuple viewTuple = FluentViewLoader.fxmlView(MainView.class).load();
		Parent root = viewTuple.getView();

		Scene scene = new Scene(root);
		scene.getStylesheets().add("/styles/Styles.css");

		stage.setTitle("Grim Dawn Common Loottable Merger");
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initGuiceModules(List<Module> modules) {
		modules.add(new AppModule());
	}
}
