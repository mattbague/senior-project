package com.mattbague;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchTag extends SimpleTagSupport {
  
  private static final int GAMES_PER_ROW = 4;
  private static final int RESULTS_PER_PAGE = 16;
  private boolean multiplePages;
  private int numResults;
  
  public void doTag() {       
    JspWriter out = getJspContext().getOut();
    PageContext pageContext = (PageContext)getJspContext(); 
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        
    try {
      out.println("<div class='row'><hr>");      
      
      String queryType = request.getParameter("qt"); // Query Type
      int pageNum = Integer.parseInt(request.getParameter("page"));
      ArrayList<GameEntry> results = new ArrayList<GameEntry>();
      multiplePages = false; // Gets updated by getXResults
      numResults = 0; // Gets updated by getXResults
      
      if (queryType.equals("search")) {        
        results = (ArrayList<GameEntry>)getSearchResults(request, pageNum);
        out.println("<h2>" + numResults + " Search Results for '" + request.getParameter("keywords") + "'</h2>"
            + "<div class='large-12 show-for-small columns'></div>");
                
        if (results.size() == 0) {
          out.println("No search results.");
        }       
      }
      else if (queryType.equals("browse")) {                       
        String browseType = request.getParameter("bt"); // Browse Type
        results = (ArrayList<GameEntry>)getBrowseResults(browseType, pageNum);
        
        out.println("<h2>Found " + numResults + " Games for '" + request.getParameter("name") + "'</h2>"
            + "<div class='large-12 show-for-small columns'></div>");        
      }
       
      for (GameEntry game : results) {
        game.printSearchGameEntry(out, request);
      }
      
      for (int i = results.size() % GAMES_PER_ROW; i < GAMES_PER_ROW && i != 0; i++) {
        GameEntry.printFillerGameEntry(out);
      } 
            
      
      if (multiplePages) {
        
        int totalNumPages = (int)Math.ceil(numResults/(double)RESULTS_PER_PAGE);
        
        out.println("<div class='row'><div class='large-12 columns' style='text-align:center'>");        
        
        // CASE: We're on the last page of results, so just need a back button
        if (pageNum * RESULTS_PER_PAGE - numResults >= 0) {          
          out.println("<a href='" 
              + request.getRequestURL() + "?" + request.getQueryString().substring(0, request.getQueryString().length() - 1) + (pageNum - 1)
              + "'><img width='10%' src='./styles/icons/fi-arrow-left.svg'/></a>"
              + "<span style='color: green; font-size: 32px !important; font-weight: bold; vertical-align:middle'>/ Page " 
              + pageNum + " of " + totalNumPages + " /</span>"
              + "<img width='10%' style='visibility: hidden' src='./styles/icons/fi-arrow-right.svg'/>");
        }        
        // CASE: We're on page 1 but there are more results (pageNum == null). Also back up case (so just go to page result 1.
        else if (pageNum == 1) {
          out.println("<img width='10%' style='visibility: hidden' src='./styles/icons/fi-arrow-left.svg'/>"
              + "<span style='color: green; font-size: 32px !important; font-weight: bold; vertical-align:middle'>/ Page " 
              + pageNum + " of " + totalNumPages + " /</span>"
              + "<a href='" 
              + request.getRequestURL() + "?" +request.getQueryString().substring(0, request.getQueryString().length() - 1) + (pageNum + 1)
              + "'><img width='10%' src='./styles/icons/fi-arrow-right.svg'/></a>");
        }
        // CASE: We're somewhere in between page 1 and the last page
        else {
          out.println("<a href='" 
              + request.getRequestURL() + "?" +request.getQueryString().substring(0, request.getQueryString().length() - 1) + (pageNum - 1)
              + "'><img width='10%' src='./styles/icons/fi-arrow-left.svg'/></a>"
              + "<span style='color: green; font-size: 32px !important; font-weight: bold; vertical-align:middle'>/ Page " 
              + pageNum + " of " + totalNumPages + " /</span>"
              + "<a href='" 
              + request.getRequestURL() + "?" +request.getQueryString().substring(0, request.getQueryString().length() - 1) + (pageNum + 1)
              + "'><img width='10%' src='./styles/icons/fi-arrow-right.svg'/></a>");
        }
        out.println("</div></div>");
        
        out.println("<div class='row'>"
            + "<div class='large-12 columns' style='text-align:center; font-size: 20px !important'>"
            + "<b>Jump to Page: </b>");
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < totalNumPages; i++) {
          if (i+1 == pageNum) {
            sb.append(" <i><span style='color: red; font-weight:bold'>" + (i+1) + "</span></i>");
          }
          else {
            sb.append(" <a href='" + request.getRequestURL() + 
              "?" + request.getQueryString().substring(0, request.getQueryString().length() - 1) + (i+1) + "'>" + (i+1) + "</a>");
          }
        }
        out.println(sb.toString().substring(1));
        out.println("</div></div>");        
      }      

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private List<GameEntry> getBrowseResults(String browseType, int pageNum) {
    try {
      ArrayList<GameEntry> toRet = new ArrayList<GameEntry>();
      
      JSONObject json = new JSONObject(UtilityTool.readUrl("http://www.giantbomb.com/api/games/"
          + "?api_key=cce72b8402cb0ee5018ad1d55d7227e5d18133a7"
          + "&filter=" + browseType
          + "&offset=" + ((pageNum-1) * RESULTS_PER_PAGE)
          + "&limit=" + RESULTS_PER_PAGE
          + "&format=json"));
//          + "&sort=original_release_date:desc"));

      numResults = json.getInt("number_of_total_results");
      
      if (numResults > RESULTS_PER_PAGE) {
        multiplePages = true;
      }

      JSONArray arr = json.getJSONArray("results");
      
      for (int i = 0; i < arr.length(); i++) {
        String gname = arr.getJSONObject(i).getString("name");               

        GameEntry temp = new GameEntry();
        temp.name = gname;
                
        if (arr.getJSONObject(i).isNull("image")) {
          temp.img = "http://placehold.it/500x500&text=NO%20IMAGE";
        } 
        else {
          temp.img = arr.getJSONObject(i).getJSONObject("image").getString("super_url");      
        }
        
        temp.gb_id = arr.getJSONObject(i).getInt("id");
        toRet.add(temp);
      }
      return toRet;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }            
  }   

  private List<GameEntry> getSearchResults(HttpServletRequest request, int pageNum) {
    try {
      ArrayList<GameEntry> toRet = new ArrayList<GameEntry>();

      String gameName = request.getParameter("name") != null ?  "name:" + request.getParameter("name") : "";
      String erm = request.getParameter("erm") != null ? ",expected_release_month:" + getMonth(request.getParameter("erm")) : "";
      String erq = request.getParameter("erq") != null ? ",expected_release_quarter:" + request.getParameter("erq") : "";
      String ery = request.getParameter("ery") != null ? ",expected_release_year:" + request.getParameter("ery") : "";
      String ord = request.getParameter("ord") != null ? ",original_release_date:" + request.getParameter("ord") : "";
      String platform = request.getParameter("pf") != null ? getPlatformString(request.getParameter("pf")) : "";
      
      JSONObject json = new JSONObject(UtilityTool.readUrl("http://www.giantbomb.com/api/games/"
          + "?api_key=cce72b8402cb0ee5018ad1d55d7227e5d18133a7"
          + "&filter=" + gameName + erm + erq + ery + ord + platform
          + "&offset=" + ((pageNum-1) * RESULTS_PER_PAGE)
          + "&limit=" + RESULTS_PER_PAGE
          + "&format=json"
          + "&sort=name:desc"));
//          + "&sort=original_release_date:desc"));

      numResults = json.getInt("number_of_total_results");
      
      if (numResults > RESULTS_PER_PAGE) {
        multiplePages = true;
      }

      JSONArray arr = json.getJSONArray("results");
      
      for (int i = 0; i < arr.length(); i++) {
        String gname = arr.getJSONObject(i).getString("name");               

        GameEntry temp = new GameEntry();
        temp.name = gname;
                
        if (arr.getJSONObject(i).isNull("image")) {
          temp.img = "http://placehold.it/500x500&text=NO%20IMAGE";
        } 
        else {
          temp.img = arr.getJSONObject(i).getJSONObject("image").getString("super_url");      
        }
        
        temp.gb_id = arr.getJSONObject(i).getInt("id");
        toRet.add(temp);
      }
      return toRet;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }            
  }

  private String getPlatformString(String parameter) {
    String toRet = "";

    // Matches the order in browse.jsp if you need reference
    HashMap<String, Integer> platformList = new HashMap<String, Integer>();
    
    platformList.put("xbox one", 145); // gen 8
    platformList.put("playstation 4", 146);
    platformList.put("ps4", 146);
    platformList.put("wii u", 139);
    platformList.put("wiiu", 139);
    platformList.put("playstation vita", 129);
    platformList.put("vita", 129);
    platformList.put("psn vita", 143);
    platformList.put("psn (vita)", 143);
    platformList.put("nintendo 3ds", 117);
    platformList.put("3ds", 117);    
    platformList.put("nintendo 3ds eshop", 138);    
    platformList.put("3ds eshop", 138);
    
    platformList.put("wii", 36); // gen 7
    platformList.put("wii eshop", 87);
    platformList.put("wii shop", 87);
    platformList.put("xbox 360", 20);
    platformList.put("x360", 20);
    platformList.put("xbox 360 games store", 86);
    platformList.put("playstation 3", 35);
    platformList.put("ps3", 35);
    platformList.put("psn playstation 3", 88);
    platformList.put("psn (playstation 3)", 88);
    platformList.put("psn (ps3)", 88);
    platformList.put("psn ps3", 88);
    platformList.put("nintendo ds", 52);
    platformList.put("ds", 52);
    platformList.put("dsiware", 106);
    platformList.put("dsi shop", 106);
    platformList.put("dsi eshop", 106);
    platformList.put("playstation portable", 18);
    platformList.put("psp", 18);
    platformList.put("psn (playstation portable)", 116);
    platformList.put("psn psp", 116);
    
    platformList.put("dreamcast", 37); // gen 6
    platformList.put("sega dreamcast", 37);
    platformList.put("playstation 2", 19);
    platformList.put("ps2", 19);
    platformList.put("gamecube", 23);
    platformList.put("gcn", 23);
    platformList.put("nintendo gamecube", 23);
    platformList.put("xbox", 32);
    platformList.put("game boy advance", 4);
    platformList.put("gba", 4);
    platformList.put("gameboy advance", 4);
    
    platformList.put("nintendo 64", 43); // gen 5
    platformList.put("n64", 43);
    platformList.put("nintendo 64dd", 101);
    platformList.put("n64dd", 101);
    platformList.put("playstation", 22);
    platformList.put("psone", 22);
    platformList.put("ps1", 22);
    platformList.put("playstation 1", 22);    
    platformList.put("sega saturn", 42);
    platformList.put("saturn", 42);
    platformList.put("atari jaguar", 28);
    platformList.put("jaguar", 28);
    platformList.put("3do interactive multiplayer", 26);
    platformList.put("3do", 26);
    platformList.put("game boy color", 57);
    platformList.put("gameboy color", 57);
    platformList.put("gbc", 57);
    platformList.put("neo geo pocket color", 81);
    platformList.put("neo geo pocket", 80);
    
    platformList.put("pc engine", 55); // gen 4
    platformList.put("turbografx-16", 55);
    platformList.put("turbograf 16", 55);
    platformList.put("sega genesis", 6);
    platformList.put("genesis", 6);
    platformList.put("super nintendo entertainment system", 9);
    platformList.put("snes", 9);
    platformList.put("super nes", 9);
    platformList.put("compact disc interactive", 27);
    platformList.put("cdi", 27);
    platformList.put("neo geo", 26);
    platformList.put("game boy", 3);
    platformList.put("game boy pocket", 3);
    platformList.put("gb", 3);
    platformList.put("gbp", 3);
    platformList.put("atari lynx", 7);
    platformList.put("lynx", 7);
    platformList.put("sega game gear", 8);
    platformList.put("game gear", 8);
    
    platformList.put("nintendo entertainment system", 21); // gen 3
    platformList.put("nes", 21);
    platformList.put("atari 7800", 70);
    platformList.put("7800", 70);
    platformList.put("sega sg-1000", 141);
    platformList.put("sg-1000", 141);
    platformList.put("sega master system", 32);
    platformList.put("master system", 32);
    platformList.put("sms", 32);        
    
    platformList.put("pc", 94);
    platformList.put("computer", 94);
    platformList.put("mac", 17);
        
    if (platformList.containsKey(parameter.toLowerCase())) {
      toRet = ",platforms:" + platformList.get(parameter.toLowerCase());
    }
    
    return toRet;
  }  
  
  private String getMonth(String month) {
    if (UtilityTool.isNumeric(month)) {
      return month;
    }
    
    switch (month.toLowerCase()) {
      case "january":
        return "1";
      case "jan":
        return "1";
      case "february":
        return "2";
      case "feb":
        return "2";
      case "march":
        return "3";
      case "mar":
        return "3";
      case "april":
        return "4";
      case "apr":
        return "4";
      case "may":
        return "5";
      case "june":
        return "6";
      case "july":
        return "7";
      case "august":
        return "8";
      case "aug":
        return "8";
      case "september":
        return "9";
      case "sept":
        return "9";
      case "october":
        return "10";
      case "oct":
        return "10";
      case "november":
        return "11";
      case "nov":
        return "11";
      case "december":
        return "12";
      case "dec":
        return "12";
      default:
         break;
    }
    
    return "1"; // default to january
  }
}
