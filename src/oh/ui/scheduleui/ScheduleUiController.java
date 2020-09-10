/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.scheduleui;

import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import oh.data.OfficeHoursData;
import oh.data.ScheduleData;
import oh.data.ScheduleItem;
import oh.ui.MainGuiController;

/**
 * FXML Controller class
 *
 * @verson final
 */
public class ScheduleUiController implements Initializable {
    
    @FXML Label schedule_calendar_l, schedule_starting_l, schedule_ending_l, schedule_items_l,
            schedule_add_edit_l, schedule_type_l, schedule_date_l, schedule_title_l, schedule_topic_l,
            schedule_link_l;
    
    @FXML TableView schedule;
    
    @FXML TableColumn sc_type, sc_date, sc_title, sc_topic;
    
    @FXML Button schedule_add_update, schedule_clear;
    
    @FXML DatePicker sc_ending_friday, sc_starting_monday, sc_date_picker;
    
    @FXML TextField sc_title_text, sc_topic_text, sc_link_text;
    
    @FXML ComboBox sc_type_text;
    
    AppTemplate app;
    ScheduleData data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.sc_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.sc_date.setCellValueFactory(new PropertyValueFactory<>("day_month"));
        this.sc_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.sc_topic.setCellValueFactory(new PropertyValueFactory<>("topic"));
    }
    
    @FXML void changeOnStarting(ActionEvent e){
        LocalDate date = this.sc_starting_monday.getValue();
        if(date != null){
            this.data.startingMondayDay = String.valueOf(date.getDayOfMonth());
            this.data.startingMondayMonth = String.valueOf(date.getMonthValue());
            mainGuiController.setUnSaved();
        }
    }
    
    @FXML void changeOnEnding(ActionEvent  e){
        LocalDate date = this.sc_ending_friday.getValue();
        if(date != null){
            this.data.endingFridayDay = String.valueOf(date.getDayOfMonth());
            this.data.endingFridayMonth = String.valueOf(date.getMonthValue());
            mainGuiController.setUnSaved();
        }
    }
    
    public void bind_language(AppLanguageModule languageSettings){
        languageSettings.addLabeledControlProperty("sc_type_TEXT", this.sc_type.textProperty());
        languageSettings.addLabeledControlProperty("sc_date_TEXT", this.sc_date.textProperty());
        languageSettings.addLabeledControlProperty("sc_title_TEXT", this.sc_title.textProperty());
        languageSettings.addLabeledControlProperty("sc_topic_TEXT", this.sc_topic.textProperty());
        languageSettings.addLabeledControlProperty("schedule_add_update_TEXT", this.schedule_add_update.textProperty());
        languageSettings.addLabeledControlProperty("schedule_clear_TEXT", this.schedule_clear.textProperty());
        languageSettings.addLabeledControlProperty("schedule_calendar_l_TEXT", this.schedule_calendar_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_starting_l_TEXT", this.schedule_starting_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_ending_l_TEXT", this.schedule_ending_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_items_l_TEXT", this.schedule_items_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_add_edit_l_TEXT", this.schedule_add_edit_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_type_l_TEXT", this.schedule_type_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_date_l_TEXT", this.schedule_date_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_title_l_TEXT", this.schedule_title_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_topic_l_TEXT", this.schedule_topic_l.textProperty());
        languageSettings.addLabeledControlProperty("schedule_link_l_TEXT", this.schedule_link_l.textProperty());
    }
    
    public void clear_all(){
        this.sc_ending_friday.setValue(null);
        this.sc_starting_monday.setValue(null);
        this.sc_date_picker.setValue(null);
        this.sc_type_text.getEditor().setText("");
        this.sc_title_text.setText("");
        this.sc_topic_text.setText("");
        this.sc_link_text.setText("");
        this.schedule.getItems().clear();
        
        this.data.endingFridayDay = "";
        this.data.endingFridayMonth = "";
        this.data.startingMondayDay = "";
        this.data.startingMondayMonth = "";
        this.data.schedule.clear();
    }
    
    @FXML
    void detect_change(ActionEvent e){
        
    }
    
    MainGuiController mainGuiController;
    public void setExtraVariable(AppTemplate app, MainGuiController mainGuiController){
        this.app = app;
        OfficeHoursData td = (OfficeHoursData) app.getDataComponent();
        this.data = td.scheduleData;
        this.mainGuiController = mainGuiController;
    }
    
    public void load_json_data(){
        this.sc_starting_monday.setValue(LocalDate.of(2018, Month.of(Integer.valueOf(this.data.startingMondayMonth)), Integer.valueOf(this.data.startingMondayDay)));
        this.sc_ending_friday.setValue(LocalDate.of(2018, Month.of(Integer.valueOf(this.data.endingFridayMonth)), Integer.valueOf(this.data.endingFridayDay)));
        for(Map.Entry<String, List<ScheduleItem>> entry : this.data.schedule.entrySet()){
            List<ScheduleItem> ls = entry.getValue();
            for(ScheduleItem si : ls){
                this.schedule.getItems().add(si);
            }
        }
    }
    
}
