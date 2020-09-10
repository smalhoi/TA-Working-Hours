/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui;

import static djf.AppPropertyType.ABOUT_BUTTON;
import static djf.AppPropertyType.CLOSE_BUTTON;
import static djf.AppPropertyType.COPY_BUTTON;
import static djf.AppPropertyType.CUT_BUTTON;
import static djf.AppPropertyType.EXIT_BUTTON;
import static djf.AppPropertyType.EXPORT_BUTTON;
import static djf.AppPropertyType.HAS_ABOUT;
import static djf.AppPropertyType.HAS_CLIPBOARD_TOOLBAR;
import static djf.AppPropertyType.HAS_CLOSE;
import static djf.AppPropertyType.HAS_EXIT;
import static djf.AppPropertyType.HAS_EXPORT;
import static djf.AppPropertyType.HAS_FILE_TOOLBAR;
import static djf.AppPropertyType.HAS_HELP;
import static djf.AppPropertyType.HAS_HELP_TOOLBAR;
import static djf.AppPropertyType.HAS_LANGUAGE;
import static djf.AppPropertyType.HAS_LOAD;
import static djf.AppPropertyType.HAS_NEW;
import static djf.AppPropertyType.HAS_NEW_DIALOG;
import static djf.AppPropertyType.HAS_SAVE;
import static djf.AppPropertyType.HAS_TOP_TOOLBAR;
import static djf.AppPropertyType.HAS_UNDO_TOOLBAR;
import static djf.AppPropertyType.HELP_BUTTON;
import static djf.AppPropertyType.LANGUAGE_BUTTON;
import static djf.AppPropertyType.LOAD_BUTTON;
import static djf.AppPropertyType.NEW_BUTTON;
import static djf.AppPropertyType.PASTE_BUTTON;
import static djf.AppPropertyType.REDO_BUTTON;
import static djf.AppPropertyType.SAVE_BUTTON;
import static djf.AppPropertyType.UNDO_BUTTON;
import djf.AppTemplate;
import djf.modules.AppFileModule;
import static djf.modules.AppGUIModule.DISABLED;
import static djf.modules.AppGUIModule.ENABLED;
import djf.modules.AppLanguageModule;
import djf.ui.AppNodesBuilder;
import djf.ui.controllers.AppHelpController;
import static djf.ui.style.DJFStyle.CLASS_DJF_ICON_BUTTON;
import static djf.ui.style.DJFStyle.CLASS_DJF_TOOLBAR_PANE;
import static djf.ui.style.DJFStyle.CLASS_DJF_TOP_TOOLBAR;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import oh.OfficeHoursApp;
import oh.data.OfficeHoursData;
import oh.ui.controllers.MyAppFileController;
import oh.ui.meetingui.MeetingUiController;
import oh.ui.scheduleui.ScheduleUiController;
import oh.ui.siteui.SiteUiController;
import oh.ui.syllabusui.SyllabusUiController;
import properties_manager.PropertiesManager;

/**
 * FXML Controller class
 *
 * @verson test
 */
public class MainGuiController implements Initializable {
    
    @FXML
    HBox topToolbarPane;
    
    @FXML
    Tab office_hours_tab, site_tab, syllabus_tab, meeting_times_tab, schedule_tab;
    
    @FXML TabPane workspace;
    
    protected AppNodesBuilder nodesBuilder;
    AppTemplate app;
    OfficeHoursData data;
    @FXML SiteUiController siteUiController;
    @FXML SyllabusUiController syllabusUiController;
    @FXML MeetingUiController meetingUiController;
    @FXML ScheduleUiController scheduleUiController;
    
    public void setExtraVariable(AppTemplate app){
        this.app = app;
        this.data = (OfficeHoursData) app.getDataComponent();
        this.nodesBuilder = this.app.getGUIModule().getNodesBuilder();
        
        this.siteUiController.setExtraVariable(app, this);
        this.syllabusUiController.setExtraVariable(app, this);
        this.meetingUiController.setExtraVariable(app, this);
        this.scheduleUiController.setExtraVariable(app, this);
        OfficeHoursApp tapp = (OfficeHoursApp) app;
        tapp.mainGuiController = this;
        
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        // TODO
        if (props.isTrue(HAS_TOP_TOOLBAR)) {
            // INIT THE TOOLBAR ONLY IF THERE IS ONE
            if (props.isTrue(HAS_FILE_TOOLBAR)) {
                initFileToolbar();
            }
            if (props.isTrue(HAS_CLIPBOARD_TOOLBAR)) {
                initClipboardToolbar();
            }
            if (props.isTrue(HAS_UNDO_TOOLBAR)) {
                initUndoToolbar();
            }
            if (props.isTrue(HAS_HELP_TOOLBAR)) {
                initHelpToolbar();
            }
        }
        // MAKE SURE WE'RE NOT TRYING TO ADD SUB TOOLBARS
        // WITHOUT THE TOP TOOLBAR
        else {
            String FALSE = "false";
            props.addProperty(HAS_FILE_TOOLBAR, FALSE);
            props.addProperty(HAS_CLIPBOARD_TOOLBAR, FALSE);
            props.addProperty(HAS_UNDO_TOOLBAR, FALSE);
            props.addProperty(HAS_HELP_TOOLBAR, FALSE);
        }
        
        // Add Css Style To toppane
        topToolbarPane.getStyleClass().add(CLASS_DJF_TOP_TOOLBAR);
        for (Node toolbar : topToolbarPane.getChildren()) {
            ObservableList<String> styleClasses = toolbar.getStyleClass();
            styleClasses.add(CLASS_DJF_TOOLBAR_PANE);
        }
        
        // Add officehours
        office_hours_tab.setContent(this.app.getWorkspaceComponent().getWorkspace());
        bind_language();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add Listener to textarea to detect changes
        
//        ChangeListener listener = new ChangeListener<String>(){
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                is_saved = false;
//            }
//        };
//        
//        this.description_textarea.textProperty().addListener(listener);
//        this.topics_textarea.textProperty().addListener(listener);
//        this.prerequisites_textarea.textProperty().addListener(listener);
//        this.outcomes_textarea.textProperty().addListener(listener);
//        this.textbooks_textarea.textProperty().addListener(listener);
//        this.graded_area.textProperty().addListener(listener);
//        this.grading_area.textProperty().addListener(listener);
//        this.academic_area.textProperty().addListener(listener);
//        this.special_area.textProperty().addListener(listener);
    }
    Button loadButton, closeButton, saveButton, exportButton, exitButton;
    
    private void initFileToolbar() {
        HBox fileToolbar = new HBox();
        topToolbarPane.getChildren().add(fileToolbar);
        MyAppFileController fileController = new MyAppFileController(app);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (props.isTrue(HAS_NEW)) {
            Button newButton = nodesBuilder.buildIconButton(NEW_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            newButton.setOnAction(e -> {
                AppFileModule fileSettings = app.getFileModule();
                if(!this.saved || !fileSettings.isSaved()){
                    try {
                        boolean save_status = fileController.promptToSave();
                        if(save_status){
                            this.clear_all();
                            if (props.isTrue(HAS_NEW_DIALOG)) {
                                app.getWorkspaceComponent().showNewDialog();
                            } else {
                                app.getFileModule().newWork();
                            }
                            this.setSaved();
                            this.workspace.setVisible(true);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    if (props.isTrue(HAS_NEW_DIALOG)) {
                        app.getWorkspaceComponent().showNewDialog();
                    } else {
                        app.getFileModule().newWork();
                    }
                    this.clear_all();
                    this.setSaved();
                    this.workspace.setVisible(true);
                }
            });
        }
        if (props.isTrue(HAS_LOAD)) {
            loadButton = nodesBuilder.buildIconButton(LOAD_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            loadButton.setOnAction(e -> {
                boolean continueToClose = true;
                AppFileModule fileSettings = app.getFileModule();
                if(!this.saved || !fileSettings.isSaved()){
                    try {
                        continueToClose = fileController.promptToSave();
                        if (continueToClose) {
                            this.clear_all();
                            fileController.processLoadRequest();
                            this.setSaved();
                            this.workspace.setVisible(true);
                        }
                    }catch (IOException ex) {
                        Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    this.clear_all();
                    fileController.processLoadRequest();
                    this.setSaved();
                    this.workspace.setVisible(true);
                }
            });
        }
        if (props.isTrue(HAS_CLOSE)) {
            closeButton = nodesBuilder.buildIconButton(CLOSE_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
            closeButton.setOnAction(e -> {
                boolean continueToClose = true;
                AppFileModule fileSettings = app.getFileModule();
                if(!this.saved || !fileSettings.isSaved()){
                    try {
                        continueToClose = fileController.promptToSave();
                        if (continueToClose) {
                            this.siteUiController.save_site_combox();
                            this.clear_all();
                            this.workspace.setVisible(false);
                            this.closeButton.setDisable(true);
                            this.setSaved();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    this.siteUiController.save_site_combox();
                    this.clear_all();
                    this.workspace.setVisible(false);
                    this.closeButton.setDisable(true);
                    this.setSaved();
                }
            });
        }
        if (props.isTrue(HAS_SAVE)) {
            saveButton = nodesBuilder.buildIconButton(SAVE_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
            saveButton.setOnAction(e -> {
                fileController.processSaveRequest();
                this.setSaved();
            });
        }
        if (props.isTrue(HAS_EXPORT)) {
            exportButton = nodesBuilder.buildIconButton(EXPORT_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
            exportButton.setOnAction(e -> {
                
            });
        }
        if (props.isTrue(HAS_EXIT)) {
            exitButton = nodesBuilder.buildIconButton(EXIT_BUTTON, fileToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            exitButton.setOnAction(e -> {
                boolean continueToExit = true;
                AppFileModule fileSettings = app.getFileModule();
                if(!this.saved || !fileSettings.isSaved()){
                    try {
                        continueToExit = fileController.promptToSave();
                        if (continueToExit) {
                            this.siteUiController.save_site_combox();
                            System.exit(0);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    System.exit(0);
                }
            });
        }
    }
            
    // Clear all elements when click new site
    private void clear_all(){
        // 1) Clear Site Tab
        this.siteUiController.clear_all();
        // 2) Clear Syllabus
        this.syllabusUiController.clear_all();
        // 3) Clear Schedule Tab
        this.scheduleUiController.clear_all();
        // 4) Clear MeetingTimes Tab
        this.meetingUiController.clear_all();
        // 5) Clear Office Hours
        this.app.getDataComponent().reset();
    }
    
    private void bind_language(){
        AppLanguageModule languageSettings = this.app.getLanguageModule();
        
        // 0) Add Tab Bind
        languageSettings.addLabeledControlProperty("office_hours_tab_TEXT", this.office_hours_tab.textProperty());
        languageSettings.addLabeledControlProperty("site_tab_TEXT", this.site_tab.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_tab_TEXT", this.syllabus_tab.textProperty());
        languageSettings.addLabeledControlProperty("meeting_times_tab_TEXT", this.meeting_times_tab.textProperty());
        languageSettings.addLabeledControlProperty("schedule_tab_TEXT", this.schedule_tab.textProperty());
        
        // 1) Add Site Tab
        this.siteUiController.bind_language(languageSettings);
        
        // 2) Add Syllabus Tab
        this.syllabusUiController.bind_language(languageSettings);
        
        // 3) Add Meeting Tab
        this.meetingUiController.bind_language(languageSettings);
        
        // 4) Add Schedule Tab
        this.scheduleUiController.bind_language(languageSettings);
    }
    
    // HELPER METHOD FOR INITIALIZING THE CLIPBOARD TOOLBAR
    private void initClipboardToolbar() {
        // NOTE THAT IN ORDER FOR THIS INITIALIZATION TO WORK 
        // PROPERLY THE AppClipboardComponent MUST ALREADY EXIST
        HBox clipboardToolbar = new HBox();
        topToolbarPane.getChildren().add(clipboardToolbar);

        // THIS IS AN ALL OR NOTHING TOOLBAR
        Button cutButton = nodesBuilder.buildIconButton(CUT_BUTTON, clipboardToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
        Button copyButton = nodesBuilder.buildIconButton(COPY_BUTTON, clipboardToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
        Button pasteButton = nodesBuilder.buildIconButton(PASTE_BUTTON, clipboardToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
    }

    // HELPER METHOD FOR INITIALIZING THE UNDO TOOLBAR    
    private void initUndoToolbar() {
        HBox undoToolbar = new HBox();
        topToolbarPane.getChildren().add(undoToolbar);

        // THIS IS AN ALL OR NOTHING TOOLBAR
        Button undoButton = nodesBuilder.buildIconButton(UNDO_BUTTON, undoToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
        undoButton.setOnAction(e -> {
            
        });
        Button redoButton = nodesBuilder.buildIconButton(REDO_BUTTON, undoToolbar, CLASS_DJF_ICON_BUTTON, DISABLED);
        redoButton.setOnAction(e -> {
            
        });
    }

    // HELPER METHOD FOR INITIALIZING THE HELP TOOLBAR        
    private void initHelpToolbar() {
        HBox helpToolbar = new HBox();
        topToolbarPane.getChildren().add(helpToolbar);
        AppHelpController helpController = new AppHelpController(app);
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (props.isTrue(HAS_HELP)) {
            Button helpButton = nodesBuilder.buildIconButton(HELP_BUTTON, helpToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            helpButton.setOnAction(e -> {
                helpController.processHelpRequest();
            });
        }
        if (props.isTrue(HAS_LANGUAGE)) {
            Button languageButton = nodesBuilder.buildIconButton(LANGUAGE_BUTTON, helpToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            languageButton.setOnAction(e -> {
                helpController.processLanguageRequest();
            });
        }
        if (props.isTrue(HAS_ABOUT)) {
            Button aboutButton = nodesBuilder.buildIconButton(ABOUT_BUTTON, helpToolbar, CLASS_DJF_ICON_BUTTON, ENABLED);
            aboutButton.setOnAction(e -> {
                helpController.processAboutRequest();
            });
        }
    }
    
    public void load_json_data(){
        this.siteUiController.load_json_data();
        this.syllabusUiController.load_json_data();
        this.meetingUiController.load_json_data();
        this.scheduleUiController.load_json_data();
        this.setSaved();
    }
    
    boolean saved = true;
    
    public void setUnSaved(){
        this.saved = false;
        this.saveButton.setDisable(false);
    }
    
    public void setSaved(){
        this.saved = true;
        this.saveButton.setDisable(true);
    }
    
}
