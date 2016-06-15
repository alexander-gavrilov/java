/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gav.htmlparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author alexa
 */
public class HotelList extends ArrayList{
    public void fillByUrl(String url){
        try {
            Document html = Jsoup.connect(url).get();
            String parentUrl = url.substring(0,url.lastIndexOf('/')+1);
            for(Element hotelElement: html.select("a:contains(More)")){
                Hotel hotel = new Hotel();
                
                hotel.parse(parentUrl+hotelElement.attr("href"));
                System.out.println(hotel.toString()+"\n");
                this.add(hotel);
            }
             
        } catch (IOException ex) {
            Logger.getLogger(HotelList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
