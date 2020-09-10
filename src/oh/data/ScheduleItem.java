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
public class ScheduleItem {
    public SimpleStringProperty type, month, day, title, link, topic, day_month;
    
    public ScheduleItem(){
        this.type = new SimpleStringProperty("");
        this.month = new SimpleStringProperty("");
        this.day = new SimpleStringProperty("");
        this.day_month = new SimpleStringProperty("");
        this.title = new SimpleStringProperty("");
        this.link = new SimpleStringProperty("");
        this.topic = new SimpleStringProperty("");
    }
    
    public ScheduleItem(String type, String month, String day, String title, String topic, String link){
        this.type = new SimpleStringProperty(type);
        this.month = new SimpleStringProperty(month);
        this.day = new SimpleStringProperty(day);
        this.day_month = new SimpleStringProperty(String.format("%s/%s", day, month));
        this.title = new SimpleStringProperty(title);
        this.link = new SimpleStringProperty(link);
        this.topic = new SimpleStringProperty(topic);
    }
    
    public JsonObject toJson(){
        return Json.createObjectBuilder()
                .add("month", this.month.get())
                .add("day", this.day.get())
                .add("title", this.title.get())
                .add("topic", this.topic.get())
                .add("link", this.link.get())
                .build();
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getMonth() {
        return month.get();
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getDay_month() {
        return day_month.get();
    }

    public void setDay_month(String day_month) {
        this.day_month.set(day_month);
    }
    
    
}
