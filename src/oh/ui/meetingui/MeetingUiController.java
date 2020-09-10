/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.meetingui;

import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oh.data.MeetingTimesData;
import oh.data.OfficeHoursData;
import oh.ui.MainGuiController;

/**
 * FXML Controller class
 *
 * @cerson pre-final
 */
public class MeetingUiController implements Initializable {
    
    @FXML Label meeting_lectures, meeting_recitations, meeting_labs;
    
    @FXML TableView lectures, recitations, labs;
    
    @FXML TableColumn ml_section, ml_days, ml_time, ml_room, mr_section, mr_days_and_time, mr_room, mr_ta1, mr_ta2,
            mb_section, mb_days_and_time, mb_room, mb_ta1, mb_ta2;
    
    AppTemplate app;
    MeetingTimesData data;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         this.ml_section.setCellValueFactory(new PropertyValueFactory<>("section"));
         this.ml_days.setCellValueFactory(new PropertyValueFactory<>("days"));
         this.ml_time.setCellValueFactory(new PropertyValueFactory<>("time"));
         this.ml_room.setCellValueFactory(new PropertyValueFactory<>("room"));
         
         this.mr_section.setCellValueFactory(new PropertyValueFactory<>("section"));
         this.mr_days_and_time.setCellValueFactory(new PropertyValueFactory<>("day_time"));
         this.mr_room.setCellValueFactory(new PropertyValueFactory<>("location"));
         this.mr_ta1.setCellValueFactory(new PropertyValueFactory<>("ta_1"));
         this.mr_ta2.setCellValueFactory(new PropertyValueFactory<>("ta_2"));
         
         this.mb_section.setCellValueFactory(new PropertyValueFactory<>("section"));
         this.mb_days_and_time.setCellValueFactory(new PropertyValueFactory<>("day_time"));
         this.mb_room.setCellValueFactory(new PropertyValueFactory<>("location"));
         this.mb_ta1.setCellValueFactory(new PropertyValueFactory<>("ta_1"));
         this.mb_ta2.setCellValueFactory(new PropertyValueFactory<>("ta_2"));
    }
    
    public void bind_language(AppLanguageModule languageSettings){
        languageSettings.addLabeledControlProperty("meeting_lectures_TEXT", this.meeting_lectures.textProperty());
        languageSettings.addLabeledControlProperty("meeting_recitations_TEXT", this.meeting_recitations.textProperty());
        languageSettings.addLabeledControlProperty("meeting_labs_TEXT", this.meeting_labs.textProperty());
        languageSettings.addLabeledControlProperty("meeting_section_TEXT", this.ml_section.textProperty());
        languageSettings.addLabeledControlProperty("meeting_sectionmr_TEXT", this.mr_section.textProperty());
        languageSettings.addLabeledControlProperty("meeting_sectionmb_TEXT", this.mb_section.textProperty());
        languageSettings.addLabeledControlProperty("meeting_days_TEXT", this.ml_days.textProperty());
        languageSettings.addLabeledControlProperty("meeting_time_TEXT", this.ml_time.textProperty());
        languageSettings.addLabeledControlProperty("meeting_room_TEXT", this.ml_room.textProperty());
        languageSettings.addLabeledControlProperty("meeting_roommr_TEXT", this.mr_room.textProperty());
        languageSettings.addLabeledControlProperty("meeting_roommb_TEXT", this.mb_room.textProperty());
        languageSettings.addLabeledControlProperty("meeting_days_and_timemr_TEXT", this.mr_days_and_time.textProperty());
        languageSettings.addLabeledControlProperty("meeting_days_and_timemb_TEXT", this.mb_days_and_time.textProperty());
        languageSettings.addLabeledControlProperty("meeting_ta1_TEXT", this.mr_ta1.textProperty());
        languageSettings.addLabeledControlProperty("meeting_ta1_TEXT", this.mb_ta1.textProperty());
        languageSettings.addLabeledControlProperty("meeting_ta2_TEXT", this.mr_ta2.textProperty());
        languageSettings.addLabeledControlProperty("meeting_ta2_TEXT", this.mb_ta2.textProperty());
    }
    
    MainGuiController mainGuiController;
    public void setExtraVariable(AppTemplate app, MainGuiController mainGuiController){
        this.app = app;
        OfficeHoursData td = (OfficeHoursData) app.getDataComponent();
        this.data = td.meetingTimesData;
        this.mainGuiController = mainGuiController;
    }
    
     public void load_json_data(){
         for(int i = 0; i < this.data.lectures.size(); i ++){
             this.lectures.getItems().add(this.data.lectures.get(i));
         }
         for(int i = 0; i < this.data.recitations.size(); i ++){
             this.recitations.getItems().add(this.data.recitations.get(i));
         }
         for(int i = 0; i < this.data.labs.size(); i ++){
             this.labs.getItems().add(this.data.labs.get(i));
         }
     }
     
     public void clear_all(){
         this.lectures.getItems().clear();
         this.recitations.getItems().clear();
         this.labs.getItems().clear();
         this.data.labs.clear();
         this.data.lectures.clear();
         this.data.recitations.clear();
     }
    
}
