/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gav.htmlparser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author alexa
 */
public class Hotel implements CityObject{
    private String title;
    private String decription;
    private final String type = "Hotel";

    public Hotel() {
    }
    


    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return decription;
    }

    public String getType() {
        return type;
    }
    

    @Override
    public void parse(String url) {
        try {
            Document html = Jsoup.connect(url).get();
            Element genElement = html.select("div.descrGen").first();
            title = genElement.select("div.scheda").first().select("li.tit").first().text();
            decription = genElement.select("p").first().text();            
        } catch (IOException ex) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        return Hotel.class.getName()+"{"+"\n title = "+title+"\n decription = "+decription+"\n type = "+type+"\n}";
    }
    
}
