/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.data;

import java.math.BigDecimal;
import javafx.beans.property.SimpleStringProperty;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @verson final 
 */
public class Recitation {
    public SimpleStringProperty section, day_time, location, ta_1, ta_2;
    public Recitation(){
        this.section = new SimpleStringProperty("");
        this.day_time = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
        this.ta_1 = new SimpleStringProperty("");
        this.ta_2 = new SimpleStringProperty("");
    }
    public Recitation(String section, String day_time, String location, String ta_1, String ta_2) {
        this.section = new SimpleStringProperty(section);
        this.day_time = new SimpleStringProperty(day_time);
        this.location = new SimpleStringProperty(location);
        this.ta_1 = new SimpleStringProperty(ta_1);
        this.ta_2 = new SimpleStringProperty(ta_2);
    }
    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("section", this.section.get())
                .add("day_time", this.day_time.get())
                .add("location", this.location.get())
                .add("ta_1", this.ta_1.get())
                .add("ta_2", this.ta_2.get())
                .build();
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getDay_time() {
        return day_time.get();
    }

    public void setDay_time(String day_time) {
        this.day_time.set(day_time);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getTa_1() {
        return ta_1.get();
    }

    public void setTa_1(String ta_1) {
        this.ta_1.set(ta_1);
    }

    public String getTa_2() {
        return ta_2.get();
    }

    public void setTa_2(String ta_2) {
        this.ta_2.set(ta_2);
    }
    
}
