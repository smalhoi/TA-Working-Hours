/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.data;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @verson final
 */
public class MeetingTimesData {
    public List<Lecture> lectures = new LinkedList<>();
    public List<Recitation> recitations = new LinkedList<>();
    public List<Lab> labs = new LinkedList<>();
    
    public JsonObject toJson(){
        JsonArrayBuilder lec_b = Json.createArrayBuilder();
        JsonArrayBuilder rec_b = Json.createArrayBuilder();
        JsonArrayBuilder lab_b = Json.createArrayBuilder();
        for(Lecture l : this.lectures){
            lec_b.add(l.toJson());
        }
        for(Recitation r :  this.recitations){
            rec_b.add(r.toJson());
        }
        for(Lab l : this.labs){
            lab_b.add(l.toJson());
        }
        return Json.createObjectBuilder()
                .add("lectures", lec_b.build())
                .add("labs", lab_b.build())
                .add("recitations", rec_b.build())
                .build();
    }
    
    public void loadJson(JsonObject jd){
        JsonArray jlec = jd.getJsonArray("lectures");
        JsonArray jlab = jd.getJsonArray("labs");
        JsonArray jrec = jd.getJsonArray("recitations");
        for(int i = 0; i < jlec.size(); i ++){
            JsonObject tj = jlec.getJsonObject(i);
            this.lectures.add(new Lecture(tj.getString("section"), tj.getString("days"), tj.getString("time"), tj.getString("room")));
        }
        for(int i = 0; i < jlab.size(); i ++){
            JsonObject tj = jlab.getJsonObject(i);
            this.labs.add(new Lab(tj.getString("section"), tj.getString("day_time"), tj.getString("location"), tj.getString("ta_1"), tj.getString("ta_2")));
        }
        for(int i = 0; i < jrec.size(); i ++){
            JsonObject tj = jrec.getJsonObject(i);
            this.recitations.add(new Recitation(tj.getString("section"), tj.getString("day_time"), tj.getString("location"), tj.getString("ta_1"), tj.getString("ta_2")));
        }
    }
}
