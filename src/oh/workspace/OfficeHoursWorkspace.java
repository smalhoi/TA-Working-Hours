package oh.workspace;

import djf.components.AppWorkspaceComponent;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import java.awt.Font;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import oh.OfficeHoursApp;
import oh.OfficeHoursPropertyType;
import static oh.OfficeHoursPropertyType.*;
import oh.data.OfficeHoursData;
import oh.data.TeachingAssistantPrototype;
import oh.data.TimeSlot;
import oh.workspace.controllers.OfficeHoursController;
import oh.workspace.dialogs.TADialog;
import oh.workspace.foolproof.OfficeHoursFoolproofDesign;
import static oh.workspace.style.OHStyle.*;

/**
 *
 * @verson pre-final
 */
public class OfficeHoursWorkspace extends AppWorkspaceComponent {

    public OfficeHoursWorkspace(OfficeHoursApp app) {
        super(app);

        // LAYOUT THE APP
        initLayout();

        // INIT THE EVENT HANDLERS
        initControllers();

        // 
        initFoolproofDesign();

        // INIT DIALOGS
        initDialogs();
    }

    private void initDialogs() {
        TADialog taDialog = new TADialog((OfficeHoursApp) app);
        app.getGUIModule().addDialog(OH_TA_EDIT_DIALOG, taDialog);
    }

    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private String help_start_time = "", help_end_time = "";
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder ohBuilder = app.getGUIModule().getNodesBuilder();

        // INIT THE HEADER ON THE LEFT
        VBox leftPane = ohBuilder.buildVBox(OH_LEFT_PANE, null, CLASS_OH_PANE, ENABLED);
        HBox tasHeaderBox = ohBuilder.buildHBox(OH_TAS_HEADER_PANE, leftPane, CLASS_OH_BOX, ENABLED);
        ohBuilder.buildLabel(OfficeHoursPropertyType.OH_TAS_HEADER_LABEL, tasHeaderBox, CLASS_OH_HEADER_LABEL, ENABLED);
        HBox typeHeaderBox = ohBuilder.buildHBox(OH_GRAD_UNDERGRAD_TAS_PANE, tasHeaderBox, CLASS_OH_RADIO_BOX, ENABLED);
        ToggleGroup tg = new ToggleGroup();
        ohBuilder.buildRadioButton(OH_ALL_RADIO_BUTTON, typeHeaderBox, CLASS_OH_RADIO_BUTTON, ENABLED, tg, true);
        ohBuilder.buildRadioButton(OH_GRAD_RADIO_BUTTON, typeHeaderBox, CLASS_OH_RADIO_BUTTON, ENABLED, tg, false);
        ohBuilder.buildRadioButton(OH_UNDERGRAD_RADIO_BUTTON, typeHeaderBox, CLASS_OH_RADIO_BUTTON, ENABLED, tg, false);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        TableView<TeachingAssistantPrototype> taTable = ohBuilder.buildTableView(OH_TAS_TABLE_VIEW, leftPane, CLASS_OH_TABLE_VIEW, ENABLED);
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn nameColumn = ohBuilder.buildTableColumn(OH_NAME_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        TableColumn emailColumn = ohBuilder.buildTableColumn(OH_EMAIL_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        TableColumn slotsColumn = ohBuilder.buildTableColumn(OH_SLOTS_TABLE_COLUMN, taTable, CLASS_OH_CENTERED_COLUMN);
        TableColumn typeColumn = ohBuilder.buildTableColumn(OH_TYPE_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        nameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<String, String>("email"));
        slotsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("slots"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0 / 5.0));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(2.0 / 5.0));
        slotsColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0 / 5.0));
        typeColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0 / 5.0));

        // ADD BOX FOR ADDING A TA
        HBox taBox = ohBuilder.buildHBox(OH_ADD_TA_PANE, leftPane, CLASS_OH_PANE, ENABLED);
        ohBuilder.buildTextField(OH_NAME_TEXT_FIELD, taBox, CLASS_OH_TEXT_FIELD, ENABLED);
        ohBuilder.buildTextField(OH_EMAIL_TEXT_FIELD, taBox, CLASS_OH_TEXT_FIELD, ENABLED);
        ohBuilder.buildTextButton(OH_ADD_TA_BUTTON, taBox, CLASS_OH_BUTTON, !ENABLED);

        // MAKE SURE IT'S THE TABLE THAT ALWAYS GROWS IN THE LEFT PANE
        VBox.setVgrow(taTable, Priority.ALWAYS);

        // INIT THE HEADER ON THE RIGHT
        VBox rightPane = ohBuilder.buildVBox(OH_RIGHT_PANE, null, CLASS_OH_PANE, ENABLED);
        HBox officeHoursHeaderBox = ohBuilder.buildHBox(OH_OFFICE_HOURS_HEADER_PANE, rightPane, CLASS_OH_PANE, ENABLED);
        ohBuilder.buildLabel(OH_OFFICE_HOURS_HEADER_LABEL, officeHoursHeaderBox, CLASS_OH_HEADER_LABEL, ENABLED);
        officeHoursHeaderBox.setAlignment(Pos.CENTER_LEFT);
        officeHoursHeaderBox.setSpacing(30);
        
        Label start_time_l = new Label("Strat Time");
        Label end_time_l = new Label("End Time");
        start_time_l.getStyleClass().add("my-head-label");
        end_time_l.getStyleClass().add("my-head-label");
        String[] cbx_time = {
            "9:00am", "9:30am", "10:00am", "10:30am", "11:00am", "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm",
            "2:00pm", "2:30pm", "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm", "6:30pm",
            "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm"
        };
        ComboBox start_cbx = new ComboBox();
        start_cbx.getItems().addAll(cbx_time);
        ComboBox end_cbx = new ComboBox();
        end_cbx.getItems().addAll(cbx_time);
        start_cbx.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                OfficeHoursData odata = (OfficeHoursData) app.getDataComponent();
                Iterator<TimeSlot> officeHoursIt = odata.officeHours.iterator();
                help_start_time = newValue;
                while (officeHoursIt.hasNext()) {
                    TimeSlot timeSlot = officeHoursIt.next();
                    timeSlot.filter(help_start_time, help_end_time);
                }
            }
        });
        end_cbx.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String starttime = start_cbx.getEditor().getText();
                OfficeHoursData odata = (OfficeHoursData) app.getDataComponent();
                Iterator<TimeSlot> officeHoursIt = odata.officeHours.iterator();
                help_end_time = newValue;
                while (officeHoursIt.hasNext()) {
                    TimeSlot timeSlot = officeHoursIt.next();
                    timeSlot.filter(help_start_time, help_end_time);
                }
            }
        });
        
        officeHoursHeaderBox.getChildren().add(start_time_l);
        officeHoursHeaderBox.getChildren().add(start_cbx);
        officeHoursHeaderBox.getChildren().add(end_time_l);
        officeHoursHeaderBox.getChildren().add(end_cbx);
//        ComboBox start_time = ohBuilder.buildComboBox("office_start_time", new Object(), new Object() ,officeHoursHeaderBox, EMPTY_TEXT, ENABLED);
//        ComboBox end_time = ohBuilder.buildComboBox("office_end_time", new Object(), new Object() ,officeHoursHeaderBox, EMPTY_TEXT, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<TimeSlot> officeHoursTable = ohBuilder.buildTableView(OH_OFFICE_HOURS_TABLE_VIEW, rightPane, CLASS_OH_OFFICE_HOURS_TABLE_VIEW, ENABLED);
        setupOfficeHoursColumn(OH_START_TIME_TABLE_COLUMN, officeHoursTable, CLASS_OH_TIME_COLUMN, "startTime");
        setupOfficeHoursColumn(OH_END_TIME_TABLE_COLUMN, officeHoursTable, CLASS_OH_TIME_COLUMN, "endTime");
        setupOfficeHoursColumn(OH_MONDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN, "monday");
        setupOfficeHoursColumn(OH_TUESDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN, "tuesday");
        setupOfficeHoursColumn(OH_WEDNESDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN, "wednesday");
        setupOfficeHoursColumn(OH_THURSDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN, "thursday");
        setupOfficeHoursColumn(OH_FRIDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN, "friday");

        // MAKE SURE IT'S THE TABLE THAT ALWAYS GROWS IN THE LEFT PANE
        VBox.setVgrow(officeHoursTable, Priority.ALWAYS);

        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, rightPane);
        sPane.setDividerPositions(.4);
        workspace = new BorderPane();

        // AND PUT EVERYTHING IN THE WORKSPACE
        ((BorderPane) workspace).setCenter(sPane);
    }

    private void setupOfficeHoursColumn(Object columnId, TableView tableView, String styleClass, String columnDataProperty) {
        AppNodesBuilder builder = app.getGUIModule().getNodesBuilder();
        TableColumn<TeachingAssistantPrototype, String> column = builder.buildTableColumn(columnId, tableView, styleClass);
        column.setCellValueFactory(new PropertyValueFactory<TeachingAssistantPrototype, String>(columnDataProperty));
        column.prefWidthProperty().bind(tableView.widthProperty().multiply(1.0 / 7.0));
        column.setCellFactory(col -> {
            return new TableCell<TeachingAssistantPrototype, String>() {
                @Override
                protected void updateItem(String text, boolean empty) {
                    super.updateItem(text, empty);
                    if (text == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // CHECK TO SEE IF text CONTAINS THE NAME OF
                        // THE CURRENTLY SELECTED TA
                        setText(text);
                        TableView<TeachingAssistantPrototype> tasTableView = (TableView) app.getGUIModule().getGUINode(OH_TAS_TABLE_VIEW);
                        TeachingAssistantPrototype selectedTA = tasTableView.getSelectionModel().getSelectedItem();
                        if (selectedTA == null) {
                            setStyle("");
                        } else if (text.contains(selectedTA.getName())) {
                            setStyle("-fx-background-color: yellow");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });
    }

    public void initControllers() {
        OfficeHoursController controller = new OfficeHoursController((OfficeHoursApp) app);
        AppGUIModule gui = app.getGUIModule();

        // FOOLPROOF DESIGN STUFF
        TextField nameTextField = ((TextField) gui.getGUINode(OH_NAME_TEXT_FIELD));
        TextField emailTextField = ((TextField) gui.getGUINode(OH_EMAIL_TEXT_FIELD));

        nameTextField.textProperty().addListener(e -> {
            controller.processTypeTA();
        });
        emailTextField.textProperty().addListener(e -> {
            controller.processTypeTA();
        });

        // FIRE THE ADD EVENT ACTION
        nameTextField.setOnAction(e -> {
            controller.processAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.processAddTA();
        });
        ((Button) gui.getGUINode(OH_ADD_TA_BUTTON)).setOnAction(e -> {
            controller.processAddTA();
        });

        TableView officeHoursTableView = (TableView) gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        officeHoursTableView.getSelectionModel().setCellSelectionEnabled(true);
        officeHoursTableView.setOnMouseClicked(e -> {
            controller.processToggleOfficeHours();
        });

        // DON'T LET ANYONE SORT THE TABLES
        TableView tasTableView = (TableView) gui.getGUINode(OH_TAS_TABLE_VIEW);
        for (int i = 0; i < officeHoursTableView.getColumns().size(); i++) {
            ((TableColumn) officeHoursTableView.getColumns().get(i)).setSortable(false);
        }
        for (int i = 0; i < tasTableView.getColumns().size(); i++) {
            ((TableColumn) tasTableView.getColumns().get(i)).setSortable(false);
        }

        tasTableView.setOnMouseClicked(e -> {
            app.getFoolproofModule().updateAll();
            if (e.getClickCount() == 2) {
                controller.processEditTA();
            }
            controller.processSelectTA();
        });

        RadioButton allRadio = (RadioButton) gui.getGUINode(OH_ALL_RADIO_BUTTON);
        allRadio.setOnAction(e -> {
            controller.processSelectAllTAs();
        });
        RadioButton gradRadio = (RadioButton) gui.getGUINode(OH_GRAD_RADIO_BUTTON);
        gradRadio.setOnAction(e -> {
            controller.processSelectGradTAs();
        });
        RadioButton undergradRadio = (RadioButton) gui.getGUINode(OH_UNDERGRAD_RADIO_BUTTON);
        undergradRadio.setOnAction(e -> {
            controller.processSelectUndergradTAs();
        });
    }

    public void initFoolproofDesign() {
        AppGUIModule gui = app.getGUIModule();
        AppFoolproofModule foolproofSettings = app.getFoolproofModule();
        foolproofSettings.registerModeSettings(OH_FOOLPROOF_SETTINGS,
                new OfficeHoursFoolproofDesign((OfficeHoursApp) app));
    }

    @Override
    public void processWorkspaceKeyEvent(KeyEvent ke) {
        // WE AREN'T USING THIS FOR THIS APPLICATION
    }

    @Override
    public void showNewDialog() {
        // WE AREN'T USING THIS FOR THIS APPLICATION
    }
}
