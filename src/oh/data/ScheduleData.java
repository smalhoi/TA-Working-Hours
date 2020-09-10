/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @verson final
 */
public class ScheduleData {
    
    public String startingMondayMonth, startingMondayDay, endingFridayMonth, endingFridayDay;
    public Map<String, List<ScheduleItem>> schedule = new HashMap<>();
    
    public ScheduleData(){
        this.startingMondayDay = "";
        this.startingMondayMonth = "";
        this.endingFridayDay = "";
        this.endingFridayMonth = "";
    }
    
    public JsonObject toJson(){
        JsonObjectBuilder jb  = Json.createObjectBuilder()
                .add("startingMondayDay", this.startingMondayDay)
                .add("startingMondayMonth", this.startingMondayMonth)
                .add("endingFridayDay", this.endingFridayDay)
                .add("endingFridayMonth", this.endingFridayMonth);
        for(Map.Entry<String, List<ScheduleItem>> entry : this.schedule.entrySet()){
            List<ScheduleItem> itemls = entry.getValue();
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for(ScheduleItem item : itemls){
                jab.add(item.toJson());
            }
            jb.add(entry.getKey(), jab.build());
        }
        return jb.build();
    }
    
    public void loadJson(JsonObject jd){
        for(String key : jd.keySet()){
            if(key.equals("startingMondayDay")){
                this.startingMondayDay = jd.getString(key);
            }else if(key.equals("startingMondayMonth")){
                this.startingMondayMonth = jd.getString(key);
            }else if(key.equals("endingFridayDay")){
                this.endingFridayDay = jd.getString(key);
            }else if(key.equals("endingFridayMonth")){
                this.endingFridayMonth = jd.getString(key);
            }else{
                JsonArray ja = jd.getJsonArray(key);
                List<ScheduleItem> sls = new LinkedList<>();
                for(int i = 0; i < ja.size(); i ++){
                    JsonObject tjo = ja.getJsonObject(i);
                    String month, day, title, topic, link;
                    month = tjo.getString("month");
                    day = tjo.getString("day");
                    title = tjo.getString("title");
                    try{
                        topic = tjo.getString("topic");}
                    catch(Exception e){
                        topic = "";
                    }
                    link = tjo.getString("link");
                    sls.add(new ScheduleItem(key, month, day, title, topic, link));
                }
                this.schedule.put(key, sls);
            }
        }
    }
}
