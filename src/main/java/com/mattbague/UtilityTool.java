package com.mattbague;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.Normalizer;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class UtilityTool {
  public static String readUrl(String urlString) throws Exception {
    BufferedReader reader = null;
    try {
        URL url = new URL(urlString);
        URLConnection urlConn = url.openConnection();
        urlConn.setDoInput(true);
        reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1)
            buffer.append(chars, 0, read); 

        return buffer.toString();
    } finally {
        if (reader != null)
            reader.close();
    }  
  }

  public static Document newDocumentFromInputStream(InputStream in) {
    DocumentBuilderFactory factory = null;
    DocumentBuilder builder = null;
    Document ret = null;
  
    try {
      factory = DocumentBuilderFactory.newInstance();
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  
    try {
      ret = (Document) builder.parse(new InputSource(in));
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return ret;
  }

  public static String convertToEmbeddedYoutube(String query) {
    try {
      String vidID = URLDecoder.decode(query, "UTF-8").substring("v=".length());
      
      return "http://www.youtube.com/embed/" + vidID;
      
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "";
  }
  
  public static String unAccent(String s) {
    //
    // JDK1.5
    //   use sun.text.Normalizer.normalize(s, Normalizer.DECOMP, 0);
    //
    String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(temp).replaceAll("");
  }
  
  public static boolean isNumeric(String str)  
  {  
    try  
    {  
      Double.parseDouble(str);  
    }  
    catch(NumberFormatException nfe)  
    {  
      return false;  
    }  
    return true;  
  }
  
  public static boolean isRomanNumeral(String str) {
    String pattern="M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})";
    
    if (str.matches(pattern)){
        return true;
    }
    else {
        return false;
    }
  }
}