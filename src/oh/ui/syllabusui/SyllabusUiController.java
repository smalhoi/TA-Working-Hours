/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.syllabusui;

import djf.AppTemplate;
import djf.modules.AppLanguageModule;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import oh.data.OfficeHoursData;
import oh.data.SyllabusData;
import oh.ui.MainGuiController;

/**
 * FXML Controller class
 *
 * @verson final
 */
public class SyllabusUiController implements Initializable {
    
    @FXML Label syllabus_description, syllabus_topics, syllabus_prerequisites, syllabus_outcomes, 
            syllabus_textbooks, syllabus_graded, syllabus_grading, syllabus_academic, syllabus_special;
    
    @FXML Button officehours_bt, description_bt, topics_bt, prerequisites_bt, outcomes_bt, textbooks_bt,
            graded_bt, grading_bt, academic_bt, special_bt;
    
    @FXML TextArea description_textarea, topics_textarea, prerequisites_textarea,
            outcomes_textarea, textbooks_textarea, graded_area, grading_area, academic_area,
            special_area;
    
    AppTemplate app;
    SyllabusData data;
    public boolean saved = true;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.description_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.description = (String) newValue;
            }
        });
        this.topics_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.topics = (String) newValue;
            }
        });
        this.prerequisites_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.prerequisites = (String) newValue;
            }
        });
        this.outcomes_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.outcomes = (String) newValue;
            }
        });
        this.textbooks_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.textbooks = (String) newValue;
            }
        });
        this.graded_area.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.graded = (String) newValue;
            }
        });
        this.grading_area.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.grading = (String) newValue;
            }
        });
        this.academic_area.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.academic = (String) newValue;
            }
        });
        this.special_area.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                saved = false;
                data.special = (String) newValue;
            }
        });
    }
    
    public void bind_language(AppLanguageModule languageSettings){
        languageSettings.addLabeledControlProperty("syllabus_description_TEXT", this.syllabus_description.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_topics_TEXT", this.syllabus_topics.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_prerequisites_TEXT", this.syllabus_prerequisites.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_outcomes_TEXT", this.syllabus_outcomes.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_textbooks_TEXT", this.syllabus_textbooks.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_graded_TEXT", this.syllabus_graded.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_grading_TEXT", this.syllabus_grading.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_academic_TEXT", this.syllabus_academic.textProperty());
        languageSettings.addLabeledControlProperty("syllabus_special_TEXT", this.syllabus_special.textProperty());
    }
    
    @FXML
    protected void trigger_description_bt(){
        this.triggger_bt(this.description_bt, this.description_textarea);
    }
    
    @FXML
    protected void trigger_topics_bt(){
        this.triggger_bt(this.topics_bt, this.topics_textarea);
    }
    
    @FXML
    protected void trigger_prerequisites_bt(){
        this.triggger_bt(this.prerequisites_bt, this.prerequisites_textarea);
    }
    
    @FXML
    protected void trigger_outcomes_bt(){
        this.triggger_bt(this.outcomes_bt, this.outcomes_textarea);
    }
    
    @FXML
    protected void trigger_textbooks_bt(){
        this.triggger_bt(this.textbooks_bt, this.textbooks_textarea);
    }
    
    @FXML
    protected void trigger_graded_bt(){
        this.triggger_bt(this.graded_bt, this.graded_area);
    }
    
    @FXML
    protected void trigger_grading_bt(){
        this.triggger_bt(this.grading_bt, this.grading_area);
    }
    
    @FXML
    protected void trigger_academic_bt(){
        this.triggger_bt(this.academic_bt, this.academic_area);
    }
    
    @FXML
    protected void trigger_special_bt(){
        this.triggger_bt(this.special_bt, this.special_area);
    }
    
    private void triggger_bt(Button bt, TextArea textarea){
        if(bt.getText().equals("+")){
            textarea.setVisible(true);
            textarea.setPrefHeight(150);
            bt.setText("-");
        }else{
            textarea.setPrefHeight(0);
            textarea.setVisible(false);
            bt.setText("+");
        }
    }
    
    public void clear_all(){
        this.description_textarea.setText("");
        this.topics_textarea.setText("");
        this.prerequisites_textarea.setText("");
        this.outcomes_textarea.setText("");
        this.textbooks_textarea.setText("");
        this.graded_area.setText("");
        this.grading_area.setText("");
        this.academic_area.setText("");
        this.special_area.setText("");
        
        this.data.description = "";
        this.data.topics = "";
        this.data.prerequisites = "";
        data.outcomes = "";
        data.textbooks = "";
        data.graded = "";
        data.grading = "";
        data.academic = "";
        data.special = "";
    }
    
    MainGuiController mainGuiController;
    public void setExtraVariable(AppTemplate app, MainGuiController mainGuiController){
        this.app = app;
        OfficeHoursData od = (OfficeHoursData) app.getDataComponent();
        this.data = od.syllabusData;
        this.mainGuiController = mainGuiController;
    }
    
    public void load_json_data(){
        if(!data.description.equals("")){
            this.description_textarea.setText(data.description);
            this.triggger_bt(this.description_bt, this.description_textarea);
        }
        if(!data.topics.equals("")){
            this.topics_textarea.setText(data.topics);
            this.triggger_bt(this.topics_bt, this.topics_textarea);
        }
        if(!data.prerequisites.equals("")){
            this.prerequisites_textarea.setText(data.prerequisites);
            this.triggger_bt(this.prerequisites_bt, this.prerequisites_textarea);
        }
        if(!data.outcomes.equals("")){
            this.outcomes_textarea.setText(data.outcomes);
            this.triggger_bt(this.outcomes_bt, this.outcomes_textarea);
        }
        if(!data.textbooks.equals("")){
            this.textbooks_textarea.setText(data.textbooks);
            this.triggger_bt(this.textbooks_bt, this.textbooks_textarea);
        }
        if(!data.graded.equals("")){
            this.graded_area.setText(data.graded);
            this.triggger_bt(this.graded_bt, this.graded_area);
        }
        if(!data.grading.equals("")){
            this.grading_area.setText(data.grading);
            this.triggger_bt(this.grading_bt, this.grading_area);
        }
        if(!data.academic.equals("")){
            this.academic_area.setText(data.academic);
            this.triggger_bt(this.academic_bt, this.academic_area);
        }
        if(!data.special.equals("")){
            this.special_area.setText(data.special);
            this.triggger_bt(this.special_bt, this.special_area);
        }
    }
}
