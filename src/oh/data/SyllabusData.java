/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oh.data;

import java.io.StringReader;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @verson final
 */
public class SyllabusData {
    public String description, topics, prerequisites, outcomes, textbooks, graded, grading, academic, special;
    
    public SyllabusData(){
        description = "";
        this.topics = "";
        this.prerequisites = "";
        this.outcomes = "";
        this.textbooks = "";
        this.graded = "";
        this.grading = "";
        this.academic = "";
        this.special = "";
    }
    
    public JsonObject toJson(){
        JsonArray jtopics, jout, jtext, jgraded;
        try{
            jtopics = Json.createReader(new StringReader(this.topics)).readArray();
        }catch(Exception e){
            jtopics = null;
        }
        try{
            jout = Json.createReader(new StringReader(this.outcomes)).readArray();
        }catch(Exception e){
            jout = null;
        }
        try{
            jtext = Json.createReader(new StringReader(this.textbooks)).readArray();
        }catch(Exception e){
            jtext = null;
        }
        try{
            jgraded = Json.createReader(new StringReader(this.graded)).readArray();
        }catch(Exception e){
            jgraded = null;
        }
        JsonObject jdata = Json.createObjectBuilder()
                .add("description", this.description)
                .add("topics", jtopics != null ? jtopics : Json.createArrayBuilder().build())
                .add("prerequisites", this.prerequisites)
                .add("outcomes", jout != null ? jout : Json.createArrayBuilder().build())
                .add("textbooks", jtext != null ? jtext : Json.createArrayBuilder().build())
                .add("gradedComponents", jgraded != null ? jgraded : Json.createArrayBuilder().build())
                .add("gradingNote", this.grading)
                .add("academicDishonesty", this.academic)
                .add("specialAssistance", this.special)
                .build();
        return jdata;
    }
    
    public void loadJson(JsonObject jd){
        this.description = jd.getString("description");
        this.topics = jd.getJsonArray("topics").toString();
        this.prerequisites = jd.getString("prerequisites");
        this.outcomes = jd.getJsonArray("outcomes").toString();
        this.textbooks = jd.getJsonArray("textbooks").toString();
        this.graded = jd.getJsonArray("gradedComponents").toString();
        this.grading = jd.getString("gradingNote");
        this.academic = jd.getString("academicDishonesty");
        this.special = jd.getString("specialAssistance");
    }
}
