/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.dialogs;

import static djf.AppPropertyType.APP_LOGO;
import static djf.AppPropertyType.APP_PATH_IMAGES;
import static djf.AppPropertyType.WELCOME_DIALOG_TITLE;
import djf.AppTemplate;
import static djf.modules.AppLanguageModule.FILE_PROTOCOL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oh.ui.dialogs.controller.MyAppWelcomeDialogController;
import properties_manager.PropertiesManager;

/**
 *
 * @verson final
 */
public class MyAppWelcomeDialog extends Stage {
    
    MyAppWelcomeDialog(AppTemplate app) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/MyAppWelcomeDialog.fxml"));
        Parent root = null;
        try {
            root = (Parent)loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MyAppWelcomeDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Set initial size
        Scene scene = new Scene(root);
        
        // Set title and the logo
        String dialogTitle = props.getProperty(WELCOME_DIALOG_TITLE);
        this.setTitle(dialogTitle);
        String imagesPath = props.getProperty(APP_PATH_IMAGES);
        String appLogo = FILE_PROTOCOL + imagesPath + props.getProperty(APP_LOGO);
        Image appWindowLogo = new Image(appLogo);
        this.getIcons().add(appWindowLogo);
        
        // Initial Controller
        MyAppWelcomeDialogController controller = (MyAppWelcomeDialogController) loader.getController();
        controller.setStageAndSetupListeners(this, app, scene);
        
        this.setScene(scene);
    }
    
}
