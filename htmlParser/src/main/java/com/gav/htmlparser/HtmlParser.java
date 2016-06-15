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
public class HtmlParser {

    public static void main(String[] args) {
//        ArrayList hotels =new ArrayList();
//        
//        Hotel h = new Hotel();
//        h.parse("http://abcroma.com/hotels/HotelVisGold_i.asp?N=13102");
//        System.out.println(h.toString());
        HotelList hotelList = new HotelList();
        hotelList.fillByUrl("http://abcroma.com/hotels/listaalberghi_i.asp");

    }
}
