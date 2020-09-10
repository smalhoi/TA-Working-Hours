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
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

/**
 *
 * @verson final
 */
public class SiteData {
    
    public String subject, number, semester, year, title;
    
    public boolean p_home_check, p_syllabus_check, p_hws_check, p_schedule_check;
    
    public String style_favicon, style_navbar, style_left_footer, style_right_footer, style_css;
    
    public String instructor_name, instructor_room, instructor_email, instructor_home, instructor_office_hours;

    public SiteData() {
        subject = "";
        number = "";
        semester = "";
        year = "";
        title = "";
        p_home_check = false;
        p_syllabus_check = false;
        p_hws_check = false;
        p_schedule_check = false;
        style_favicon = "/images/SBUShieldFavicon.png";
        style_navbar = "/images/NavbarImage.png";
        style_left_footer = "/images/Left_Footer_Image.jpg";
        style_right_footer = "/images/Right_Footer_Image.png";
        style_css = "";
        instructor_name = "";
        instructor_room = "";
        instructor_email = "";
        instructor_home = "";
        instructor_office_hours = "";
    }
    
    public JsonObject toJson(){
        JsonObject logos = Json.createObjectBuilder()
                .add("favicon", Json.createObjectBuilder().add("src", this.style_favicon).build())
                .add("navbar", Json.createObjectBuilder().add("src", this.style_navbar).build())
                .add("bottom_left", Json.createObjectBuilder().add("src", this.style_left_footer).build())
                .add("bottom_right", Json.createObjectBuilder().add("src", this.style_right_footer).build())
                .build();
        JsonArray i_hours;
        try{
            i_hours = Json.createReader(new StringReader(this.instructor_office_hours)).readArray();
        }catch(Exception e){
            i_hours = null;
        }
        JsonObject instructor = Json.createObjectBuilder()
                .add("name", this.instructor_name)
                .add("link", this.instructor_home)
                .add("email", this.instructor_email)
                .add("room", this.instructor_room)
                .add("hours", i_hours != null ? i_hours : Json.createArrayBuilder().build())
                .build();
        
        JsonArrayBuilder pages = Json.createArrayBuilder();
        if(this.p_home_check){
            pages.add(Json.createObjectBuilder()
                    .add("name", "Home")
                    .add("link", "index.html")
                    .build());
        }
        if(this.p_syllabus_check){
            pages.add(Json.createObjectBuilder()
                    .add("name", "Syllabus")
                    .add("link", "syllabus.html")
                    .build());
        }
        if(this.p_schedule_check){
            pages.add(Json.createObjectBuilder()
                    .add("name", "Schedule")
                    .add("link", "schedule.html")
                    .build());
        }
        if(this.p_hws_check){
            pages.add(Json.createObjectBuilder()
                    .add("name", "HWs")
                    .add("link", "hws.html")
                    .build());
        }
        
        JsonObject jdata = Json.createObjectBuilder()
                .add("subject", this.subject)
                .add("number", this.number)
                .add("semester", this.semester)
                .add("year", this.year)
                .add("title", this.title)
                .add("logos", logos)
                .add("instructor", instructor)
                .add("pages", pages.build())
                .build();
        return jdata;
    }
    
    public void loadJson(JsonObject jd){
        this.subject = jd.getString("subject");
        this.number = jd.getString("number");
        this.semester = jd.getString("semester");
        this.year = jd.getString("year");
        this.title = jd.getString("title");
        JsonObject logos = jd.getJsonObject("logos");
        String favicon = logos.getJsonObject("favicon").getString("src");
        String navbar = logos.getJsonObject("navbar").getString("src");
        String bottom_left = logos.getJsonObject("bottom_left").getString("src");
        String bottom_right = logos.getJsonObject("bottom_right").getString("src");
        if(!favicon.equals("")){
            this.style_favicon = favicon;
        }
        if(!navbar.equals("")){
            this.style_navbar = navbar;
        }
        if(!bottom_left.equals("")){
            this.style_left_footer = bottom_left;
        }
        if(!bottom_right.equals("")){
            this.style_right_footer = bottom_right;
        }
        
        JsonObject instructor = jd.getJsonObject("instructor");
        this.instructor_name = instructor.getString("name");
        this.instructor_home = instructor.getString("link");
        this.instructor_email = instructor.getString("email");
        this.instructor_room = instructor.getString("room");
        this.instructor_office_hours = instructor.getJsonArray("hours").toString();
        
        JsonArray jpages = jd.getJsonArray("pages");
        for(int i = 0; i < jpages.size(); i ++){
            JsonObject tjo = jpages.getJsonObject(i);
            if(tjo.getString("name").equals("Home")){
                this.p_home_check = true;
            }else if(tjo.getString("name").equals("Syllabus")){
                this.p_syllabus_check = true;
            }else if(tjo.getString("name").equals("Schedule")){
                this.p_schedule_check = true;
            }else if(tjo.getString("name").equals("HWs")){
                this.p_hws_check = true;
            }
        }
        
    }
    
}
