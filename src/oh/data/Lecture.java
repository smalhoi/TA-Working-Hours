/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.data;

import javafx.beans.property.SimpleStringProperty;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @verson final
 */
public class Lecture {
    public SimpleStringProperty section, days, time, room;
    
    public Lecture(){
        section = new SimpleStringProperty("");
        days = new SimpleStringProperty("");
        time = new SimpleStringProperty("");
        room = new SimpleStringProperty("");
    }
    
    public Lecture(String section, String days, String time, String room){
        this.section = new SimpleStringProperty(section);
        this.days = new SimpleStringProperty(days);
        this.time = new SimpleStringProperty(time);
        this.room = new SimpleStringProperty(room);
    }
    
    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("section", this.section.get())
                .add("days", this.days.get())
                .add("time", this.time.get())
                .add("room", this.room.get())
                .build();
    }

    public String getSection() {
        return section.get();
    }

    public void setSection(String section) {
        this.section.set(section);
    }

    public String getDays() {
        return days.get();
    }

    public void setDays(String days) {
        this.days.set(days);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getRoom() {
        return room.get();
    }

    public void setRoom(String room) {
        this.room.set(room);
    }
}
