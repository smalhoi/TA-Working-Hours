package oh;

import static djf.AppPropertyType.APP_CLIPBOARD_FOOLPROOF_SETTINGS;
import static djf.AppPropertyType.APP_CSS;
import static djf.AppPropertyType.APP_ERROR_CONTENT;
import static djf.AppPropertyType.APP_ERROR_TITLE;
import static djf.AppPropertyType.APP_FILE_FOOLPROOF_SETTINGS;
import static djf.AppPropertyType.APP_LOGO;
import static djf.AppPropertyType.APP_PATH_CSS;
import static djf.AppPropertyType.APP_PATH_IMAGES;
import static djf.AppPropertyType.APP_UNDO_FOOLPROOF_SETTINGS;
import static djf.AppPropertyType.HAS_CLIPBOARD_TOOLBAR;
import static djf.AppPropertyType.HAS_FILE_TOOLBAR;
import static djf.AppPropertyType.HAS_UNDO_TOOLBAR;
import static djf.AppPropertyType.HAS_WELCOME_DIALOG;
import static djf.AppPropertyType.PREF_HEIGHT;
import static djf.AppPropertyType.PREF_WIDTH;
import static djf.AppPropertyType.PROPERTIES_ERROR_CONTENT;
import static djf.AppPropertyType.PROPERTIES_ERROR_TITLE;
import static djf.AppPropertyType.START_MAXIMIZED;
import static djf.AppPropertyType.WELCOME_DIALOG_TITLE;
import djf.AppTemplate;
import static djf.AppTemplate.PATH_PROPERTIES;
import djf.components.AppClipboardComponent;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import static djf.modules.AppLanguageModule.APP_PROPERTIES_FILE_NAME;
import static djf.modules.AppLanguageModule.FILE_PROTOCOL;
import djf.ui.dialogs.AppDialogsFacade;
import djf.ui.foolproof.ClipboardFoolproofDesign;
import djf.ui.foolproof.FileFoolproofDesign;
import djf.ui.foolproof.UndoFoolproofDesign;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import oh.data.OfficeHoursData;
import oh.files.OfficeHoursFiles;
import oh.clipboard.OfficeHoursClipboard;
import oh.workspace.OfficeHoursWorkspace;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oh.ui.MainGuiController;
import oh.ui.dialogs.MyAppDialogsFacade;
import oh.ui.dialogs.MyAppWelcomeDialog;
import properties_manager.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;

public class OfficeHoursApp extends AppTemplate {   
    
    public MainGuiController mainGuiController;

    /**
     * Start build custom view
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        // FIRST SETUP THE PropertiesManager WITH
        // IT'S MINIMAL LANGUAGE PROPERTIES IN CASE OF ERROR
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(APP_ERROR_TITLE, "Application Error");
        props.addProperty(APP_ERROR_CONTENT, "An Error Occured in the Application");
        props.addProperty(PROPERTIES_ERROR_TITLE, "Properties File Error");
        props.addProperty(PROPERTIES_ERROR_CONTENT, "There was an Error Loading a Properties File");        
        
	try {
            // NOW LOAD THE APP PROPERTIES, WHICH CONTAINS THE UI SETTINGS
            // THAT ARE NOT LANGUAGE-SPECIFIC.
	    boolean success = loadProperties(APP_PROPERTIES_FILE_NAME);
	    
            // IF THE APPLICATION PROPERTIES LOADS
            // SUCCESSFULLY THEN WE WILL PROCEED
	    if (success) {         
                // MAKE SURE ALL OUR NODES WITH TEXT WILL
                // BE PROPERLY MANAGED SUCH THAT WE CAN LOAD
                // LANGUAGE-DEPENDENT TEXT
                languageModule.init();
                
                // LOAD THE GUI INTO THE WINDOW (i.e. Stage)
		guiModule.loadGUI(primaryStage);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/MainGui.fxml"));
                Parent root = null;
                try {
                    root = (Parent)loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(MyAppWelcomeDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Set Controller
                MainGuiController controller = (MainGuiController) loader.getController();
		buildAppComponents();
                controller.setExtraVariable(this);
                
                // Set initial size
                double prefWidth = Double.parseDouble(props.getProperty(PREF_WIDTH));
                double prefHeight = Double.parseDouble(props.getProperty(PREF_HEIGHT));
                Scene scene = new Scene(root, prefWidth, prefHeight);
                primaryStage.setMaximized("true".equals(props.getProperty(START_MAXIMIZED)));
                
                
                // link css file to the scence
                String stylesheet = props.getProperty(APP_PATH_CSS);
                stylesheet += props.getProperty(APP_CSS);
                Class appClass = this.getClass();
                URL stylesheetURL = appClass.getResource(stylesheet);
                String stylesheetPath = stylesheetURL.toExternalForm();
                scene.getStylesheets().add(stylesheetPath);

                // Set title and the logo
                String dialogTitle = props.getProperty(WELCOME_DIALOG_TITLE);
                primaryStage.setTitle(dialogTitle);
                String imagesPath = props.getProperty(APP_PATH_IMAGES);
                String appLogo = FILE_PROTOCOL + imagesPath + props.getProperty(APP_LOGO);
                Image appWindowLogo = new Image(appLogo);
                primaryStage.getIcons().add(appWindowLogo);
                
                primaryStage.setScene(scene);

                // SETUP THE CLIPBOARD, IF IT'S BEING USED, NOTE THAT
                // THIS WOULD HAVE BEEN SPECIFIED IN THE PROPERTIES FILE
                // THAT WE LOADED
                if (props.isTrue(HAS_CLIPBOARD_TOOLBAR)) {
                    guiModule.registerClipboardComponent();
                }

                // LOAD THE FOOLPROOF SETTINGS THAT WE'LL BE USING. THESE
                // MANAGE THE ENABLING AND DISABLING OF SPECIFIC APP
                // CONTROLS DEPENDING ON CERTAIN CONDITIONS
                if (props.isTrue(HAS_FILE_TOOLBAR)) {
                    foolproofModule.registerModeSettings(APP_FILE_FOOLPROOF_SETTINGS, 
                                                        new FileFoolproofDesign(this));
                }
                if (props.isTrue(HAS_CLIPBOARD_TOOLBAR)) {
                    foolproofModule.registerModeSettings(APP_CLIPBOARD_FOOLPROOF_SETTINGS, 
                                                        new ClipboardFoolproofDesign(this));
                }
                if (props.isTrue(HAS_UNDO_TOOLBAR)) {
                    foolproofModule.registerModeSettings(APP_UNDO_FOOLPROOF_SETTINGS, 
                                                         new UndoFoolproofDesign(this));
                }

                // LOAD ALL THE PROPER TEXT INTO OUR CONTROLS
                // USING THE DEFAULT LANGUAGE SETTING
                languageModule.resetLanguage();
                
                // OPEN THE WELCOME DIALOG, IF IT'S BEING USED
                if (props.isTrue(HAS_WELCOME_DIALOG)) {
                    // NOTE, THIS WINDOW WILL HAVE TO BE CLOSED
                    // BEFORE CONTINUING ON TO THE APPLICATION WINDOW
                    MyAppDialogsFacade.showWelcomeDialog(this);                
                }
                
                // NOW OPEN UP THE APPLICATION WINDOW
                primaryStage.show();
	    } 
	}catch (Exception e) {
            // THIS TYPE OF ERROR IS LIKELY DUE TO PROGRAMMER ERROR IN
            // THE APP ITSELF SO WE'LL PROVIDE A STACK TRACE DIALOG AND EXIT
            e.printStackTrace();
//            AppDialogsFacade.showStackTraceDialog(guiModule.getWindow(), e, APP_ERROR_TITLE, APP_ERROR_CONTENT);
            System.exit(0);
	}
    }
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     * 
     * @param args Command-line arguments, there are no such settings used
     * by this application.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }

    @Override
    public AppClipboardComponent buildClipboardComponent(AppTemplate app) {
        return new OfficeHoursClipboard(this);
    }

    @Override
    public AppDataComponent buildDataComponent(AppTemplate app) {
        return new OfficeHoursData(this);
    }

    @Override
    public AppFileComponent buildFileComponent() {
        return new OfficeHoursFiles(this);
    }

    @Override
    public AppWorkspaceComponent buildWorkspaceComponent(AppTemplate app) {
        return new OfficeHoursWorkspace(this);        
    }
    
    // loadProperties LOADS THIS APPLICATION'S PROPERTIES FILE, WHICH HAS
    // A NUMBER OF SETTINGS FOR INITIALIZING THE USER INTERFACE EXCLUDING
    // THE TEXT TO BE DISPLAYED INSIDE CONTROLS, WHICH IS TO BE LOADED
    // ON A LANGUAGE-TO-LANGUAGE BASIS
    private boolean loadProperties(String propertiesFileName) {
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	try {
	    // LOAD THE SETTINGS FOR STARTING THE APP
	    props.setPropertiesDataPath(PATH_PROPERTIES);
	    props.loadProperties(propertiesFileName);
	    return true;
	} catch (InvalidXMLFileFormatException ixmlffe) {
	    // SOMETHING WENT WRONG INITIALIZING THE XML FILE
	    AppDialogsFacade.showMessageDialog(guiModule.getWindow(), PROPERTIES_ERROR_TITLE, PROPERTIES_ERROR_CONTENT);
	    return false;
	}
    }

    // THIS FUNCTION BUILDS THE COMPONENTS BY CALLING THE abstract
    // FUNCTIONS OF THIS OBJECT THAT ARE TO BE IMPLEMENTED BY 
    // THE APPLICATION.
    private void buildAppComponents() {
        // MAKE ALL FOUR COMPONENTS FIRST
        this.fileComponent = buildFileComponent();
        this.workspaceComponent = buildWorkspaceComponent(this);
        this.dataComponent = buildDataComponent(this);
        this.clipboardComponent = buildClipboardComponent(this);        
    }

    @Override
    public void load_json_data() {
        this.mainGuiController.load_json_data();
    }
    
    
}