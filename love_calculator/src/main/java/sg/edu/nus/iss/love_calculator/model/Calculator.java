package sg.edu.nus.iss.love_calculator.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Calculator {
    private String fname;
    private String sname;
    private String percentage;
    private String result;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static Calculator createFromJSON(String json) throws IOException {
        Calculator c = new Calculator();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jsObj = jr.readObject();
            c.setFname(jsObj.getString("fname"));
            c.setSname(jsObj.getString("sname"));
            c.setPercentage(jsObj.getString("percentage"));
            c.setResult(jsObj.getString("result"));
        }
        return c;
    }

}
