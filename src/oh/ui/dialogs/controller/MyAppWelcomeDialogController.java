/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.dialogs.controller;

import static djf.AppPropertyType.APP_BANNER;
import static djf.AppPropertyType.APP_PATH_IMAGES;
import static djf.AppPropertyType.LOAD_ERROR_CONTENT;
import static djf.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.AppPropertyType.WELCOME_DIALOG_NONE_LABEL;
import djf.AppTemplate;
import djf.modules.AppRecentWorkModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import oh.OfficeHoursApp;
import oh.ui.MainGuiController;
import properties_manager.PropertiesManager;

/**
 * FXML Controller class
 *
 * @author 
 */
public class MyAppWelcomeDialogController implements Initializable {
    
    Stage stage;
    AppTemplate app;
    Scene dialogScene;
    String selectedWorkName = null;
    
    @FXML
    VBox recentwork;
    
    @FXML
    ImageView imageView;
    
    public void setStageAndSetupListeners(Stage stage, AppTemplate app, Scene dialogScene){
        this.stage = stage;
        this.app = app;
        this.dialogScene = dialogScene;
        
        // Add HyperLink
        AppRecentWorkModule recentWork = this.app.getRecentWorkModule();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (recentWork.size() == 0) {
            String noneText = props.getProperty(WELCOME_DIALOG_NONE_LABEL);
            Label noneLabel = new Label(noneText);
            this.recentwork.getChildren().add(noneLabel);
        }
        else {        
            Iterator<String> it = recentWork.getWorkIterator();
            while (it.hasNext()) {
               String workName = it.next();            
               Hyperlink workButton = new Hyperlink(workName);
               workButton.setFont(Font.font(14));
                workButton.setUserData(workName);
                workButton.setOnAction(e -> {
                    Hyperlink b = (Hyperlink) e.getSource();
                    selectedWorkName = (String)b.getUserData();
                    String workPath = recentWork.getPath(workName);
                    File workFile = new File(workPath);
                    recentWork.startWork(workFile);
                    try {
                        app.getFileModule().loadWork(workFile);
                        this.stage.hide();
                        app.getGUIModule().getWindow().show();
                    }
                    catch(IOException ioe) {
                        AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(), LOAD_ERROR_TITLE, LOAD_ERROR_CONTENT);
                    }
                });
                this.recentwork.getChildren().add(workButton);
            }
        }
    }
    
    @FXML
    void createNewSite(){
        this.stage.hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
            String bannerFileName = props.getProperty(APP_BANNER);
            String bannerPath = props.getProperty(APP_PATH_IMAGES) + "/MyOfficeHoursWelcomeBanner.jpg";
            File bannerFile = new File(bannerPath);
            BufferedImage bufferedImage = ImageIO.read(bannerFile);
            Image bannerImage = SwingFXUtils.toFXImage(bufferedImage, null);
            this.imageView.setImage(bannerImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
}
