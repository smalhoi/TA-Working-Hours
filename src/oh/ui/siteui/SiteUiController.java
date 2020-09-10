/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.ui.siteui;

import djf.AppTemplate;
import djf.modules.AppFileModule;
import djf.modules.AppLanguageModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import oh.data.OfficeHoursData;
import oh.data.SiteData;
import oh.ui.MainGuiController;

/**
 * FXML Controller class
 *
 * @verson final
 */
public class SiteUiController implements Initializable {
    
    AppTemplate app;
    OfficeHoursData data;
    MainGuiController mainGuiController;
    
    Map<String, Set<String>> combox_store = new HashMap<>();

    @FXML ComboBox subject, number, semester, year, css_file;
    
    @FXML ImageView favicon_img, navbar_img, left_footer_img, right_footer_img;
    
    @FXML TextField site_title, site_name, site_email, site_room, site_home_page;
    
    @FXML CheckBox site_pages_home, site_pages_syllabus, site_pages_hws, site_pages_schedule;
    
    @FXML TextArea officehours_textarea;
    
    @FXML Button officehours_bt;
    
    @FXML Label site_subject_l, site_number_l, site_semester_l, site_year_l, site_title_l, 
            site_exportdir_l, site_pages_l, site_style_l, site_css_file_l, site_name_l,
            site_room_l, site_emial_l, site_home_l, site_banner_l, site_office_l, site_instructor_l;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // From File Load Values
        this.load_site_combox();
        if(this.combox_store.size() > 0){
            Set<String> ls = this.combox_store.get("subject");
            help_load_combox(ls, this.subject);
            ls = this.combox_store.get("number");
            help_load_combox(ls, this.number);
            ls = this.combox_store.get("semester");
            help_load_combox(ls, this.semester);
            ls = this.combox_store.get("year");
            help_load_combox(ls, this.year);
            ls = this.combox_store.get("css_file");
            help_load_combox(ls, this.css_file);
        }
        this.officehours_textarea.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.instructor_office_hours = (String) newValue;
            }
        });
        this.site_title.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.title = (String) newValue;
            }
        });
        this.site_name.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.instructor_name = (String) newValue;
            }
        });
        this.site_room.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.instructor_room = (String) newValue;
            }
        });
        this.site_email.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.instructor_email = (String) newValue;
            }
        });
        this.site_home_page.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                mainGuiController.setUnSaved();
                data.siteData.instructor_home = (String) newValue;
            }
        });
        
    }
    
    @FXML void choose_image(ActionEvent e){
        Button btn = (Button)e.getSource();
        String tt = btn.getText();
        File file = select_file();
        if(file == null)
            return ;
        this.mainGuiController.setUnSaved();
        String img_path = file.toURI().toString();
        if(tt.startsWith("Fav")){
            this.favicon_img.setImage(new Image(img_path));
            this.data.siteData.style_favicon = img_path;
        }else if(tt.startsWith("Navbar")){
            this.navbar_img.setImage(new Image(img_path));
            this.data.siteData.style_navbar = img_path;
        }else if(tt.startsWith("Left")){
            this.left_footer_img.setImage(new Image(img_path));
            this.data.siteData.style_left_footer = img_path;
        }else if(tt.startsWith("Right")){
            this.right_footer_img.setImage(new Image(img_path));
            this.data.siteData.style_right_footer = img_path;
        }
    }
    
    public void setExtraVariable(AppTemplate app, MainGuiController mainGuiController){
        this.app = app;
        this.data = (OfficeHoursData)app.getDataComponent();
        this.mainGuiController = mainGuiController;
    }
    
    public void load_json_data(){
        SiteData sdata = this.data.siteData;
        this.subject.getEditor().setText(sdata.subject);
        this.number.getEditor().setText(sdata.number);
        this.semester.getEditor().setText(sdata.semester);
        this.year.getEditor().setText(sdata.year);
        this.site_title.setText(sdata.title);
        if(sdata.p_home_check){
            this.site_pages_home.setSelected(true);
        }
        if(sdata.p_syllabus_check){
            this.site_pages_syllabus.setSelected(true);
        }
        if(sdata.p_schedule_check){
            this.site_pages_schedule.setSelected(true);
        }
        if(sdata.p_hws_check){
            this.site_pages_hws.setSelected(true);
        }
        this.favicon_img.setImage(new Image(sdata.style_favicon));
        this.navbar_img.setImage(new Image(sdata.style_navbar));
        this.left_footer_img.setImage(new Image(sdata.style_left_footer));
        this.right_footer_img.setImage(new Image(sdata.style_right_footer));
        this.css_file.getEditor().setText(sdata.style_css);
        this.site_name.setText(sdata.instructor_name);
        this.site_room.setText(sdata.instructor_room);
        this.site_email.setText(sdata.instructor_email);
        this.site_home_page.setText(sdata.instructor_home);
        if(!sdata.instructor_office_hours.equals("")){
            this.officehours_textarea.setText(sdata.instructor_office_hours);
            this.officehours_bt.setText("-");
            this.officehours_textarea.setVisible(true);
            this.officehours_textarea.setPrefHeight(150);
        }
        setExportDir();
    }
    
    File select_file(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Select Image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg/png", "*.jpg", "*.png")
        );
        File selectedFile = fc.showOpenDialog(app.getGUIModule().getWindow());
        return selectedFile;
    }
    
    public void load_site_combox(){
        try {
            File toRead=new File("site_combox.setting");
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);
            
            this.combox_store=(HashMap<String,Set<String>>)ois.readObject();
            
            ois.close();
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
            this.combox_store = new HashMap<>();
        }
    }
    
    @FXML
    void type_into_combox(ActionEvent e){
        AppFileModule fileSettings = app.getFileModule();
        this.mainGuiController.setUnSaved();
        
        ComboBox cb = (ComboBox) e.getSource();
        String type = cb.getId();
        String text = cb.getEditor().getText();
        String target = null;
        if(type.equals("subject")){
            target = "subject";
            this.data.siteData.subject = text;
        }else if(type.equals("number")){
            target = "number";
            this.data.siteData.number = text;
        }else if(type.equals("semester")){
            target = "semester";
            this.data.siteData.semester = text;
        }else if(type.equals("year")){
            target = "year";
            this.data.siteData.year = text;
        }else if(type.contains("css_file")){
            target = "css_file";
            this.data.siteData.style_css = text;
        }
        if(target != null){
            Set<String> ls = this.combox_store.get(target);
            if(ls == null){
                ls = new HashSet<String>();
            }
            ls.add(text);
//            System.out.println("Save==>" + type + "==>>" + text);
            this.combox_store.put(target, ls);
        }
        setExportDir();
    }
    
    @FXML
    protected void trigger_officehours_bt(){
        if(officehours_bt.getText().equals("+")){
            officehours_textarea.setVisible(true);
            officehours_textarea.setPrefHeight(150);
            officehours_bt.setText("-");
        }else{
            officehours_textarea.setPrefHeight(0);
            officehours_textarea.setVisible(false);
            officehours_bt.setText("+");
        }
    }
    
    private void help_load_combox(Set<String> ls, ComboBox cb){
        if(ls != null && ls.size() > 0){
            for(String tmp : ls){
                cb.getItems().add(tmp);
            }
        }
    }
    
    @FXML void checkbox_changed(ActionEvent e){
        CheckBox elem = (CheckBox) e.getSource();
        boolean is_checked = elem.isSelected();
        String type = elem.getId();
        this.mainGuiController.setUnSaved();
        if(type.contains("home")){
            this.data.siteData.p_home_check = is_checked;
        }else if(type.contains("syllabus")){
            this.data.siteData.p_syllabus_check = is_checked;
        }else if(type.contains("schedule")){
            this.data.siteData.p_schedule_check = is_checked;
        }else if(type.contains("hws")){
            this.data.siteData.p_hws_check = is_checked;
        }
    }
    
    public void clear_all(){
        this.subject.getEditor().setText("");
        this.data.siteData.subject = "";
        
        this.number.getEditor().setText("");
        this.data.siteData.number = "";
        
        this.semester.getEditor().setText("");
        this.data.siteData.semester = "";
        
        this.year.getEditor().setText("");
        this.data.siteData.year = "";
        
        this.site_title.setText("");
        this.data.siteData.title = "";
        
        this.site_pages_home.setSelected(false);
        this.data.siteData.p_home_check = false;
        
        this.site_pages_syllabus.setSelected(false);
        this.data.siteData.p_syllabus_check = false;
        
        this.site_pages_hws.setSelected(false);
        this.data.siteData.p_hws_check = false;
        
        this.site_pages_schedule.setSelected(false);
        this.data.siteData.p_schedule_check = false;
        
        this.favicon_img.setImage(new Image("/images/SBUShieldFavicon.png"));
        this.data.siteData.style_favicon = "/images/SBUShieldFavicon.png";
        
        this.navbar_img.setImage(new Image("/images/NavbarImage.png"));
        this.data.siteData.style_navbar = "/images/NavbarImage.png";
        
        this.left_footer_img.setImage(new Image("/images/Left_Footer_Image.jpg"));
        this.data.siteData.style_left_footer = "/images/Left_Footer_Image.jpg";
        
        this.right_footer_img.setImage(new Image("/images/Right_Footer_Image.png"));
        this.data.siteData.style_right_footer = "/images/Right_Footer_Image.png";
        
        this.css_file.getEditor().setText("");
        this.data.siteData.style_css = "";
        
        this.site_name.setText("");
        this.data.siteData.instructor_name = "";
        
        this.site_email.setText("");
        this.data.siteData.instructor_email = "";
        
        this.site_room.setText("");
        this.data.siteData.instructor_room = "";
        
        this.site_home_page.setText("");
        this.data.siteData.instructor_home = "";
        
        this.officehours_textarea.setText("");
        this.data.siteData.instructor_office_hours = "";
        
    }
    
    public void bind_language(AppLanguageModule languageSettings){
        languageSettings.addLabeledControlProperty("site_subject_l_TEXT", this.site_subject_l.textProperty());
        languageSettings.addLabeledControlProperty("site_number_l_TEXT", this.site_number_l.textProperty());
        languageSettings.addLabeledControlProperty("site_semester_l_TEXT", this.site_semester_l.textProperty());
        languageSettings.addLabeledControlProperty("site_year_l_TEXT", this.site_year_l.textProperty());
        languageSettings.addLabeledControlProperty("site_title_l_TEXT", this.site_title_l.textProperty());
        languageSettings.addLabeledControlProperty("site_exportdir_l_TEXT", this.site_exportdir_l.textProperty());
        languageSettings.addLabeledControlProperty("site_pages_l_TEXT", this.site_pages_l.textProperty());
        languageSettings.addLabeledControlProperty("site_css_file_l_TEXT", this.site_css_file_l.textProperty());
        languageSettings.addLabeledControlProperty("site_name_l_TEXT", this.site_name_l.textProperty());
        languageSettings.addLabeledControlProperty("site_room_l_TEXT", this.site_room_l.textProperty());
        languageSettings.addLabeledControlProperty("site_emial_l_TEXT", this.site_emial_l.textProperty());
        languageSettings.addLabeledControlProperty("site_home_l_TEXT", this.site_home_l.textProperty());
        languageSettings.addLabeledControlProperty("site_banner_l_TEXT", this.site_banner_l.textProperty());
        languageSettings.addLabeledControlProperty("site_office_l_TEXT", this.site_office_l.textProperty());
        languageSettings.addLabeledControlProperty("site_instructor_l_TEXT", this.site_instructor_l.textProperty());
    }
    
    public void save_site_combox(){
        try {
            File fileOne=new File("site_combox.setting");
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this.combox_store);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML Label exportDir;
    public void setExportDir(){
        exportDir.setText(String.format(".\\export\\%s_%s_%s_%s\\public_html", this.subject.getEditor().getText()
        ,this.number.getEditor().getText(), this.semester.getEditor().getText(), this.year.getEditor().getText()));
    }
}
