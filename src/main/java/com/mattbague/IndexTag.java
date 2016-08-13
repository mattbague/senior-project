package com.mattbague;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.json.JSONArray;
import org.json.JSONObject;

public class IndexTag extends SimpleTagSupport {
  
  private static final String API_KEY_GIANTBOMB = "cce72b8402cb0ee5018ad1d55d7227e5d18133a7";
  private static final int GAMES_PER_ROW = 4;
  private static final int MAX_GAMES_DISPLAYED = 24;

  public void doTag() {  
    ArrayList<GameEntry> comingOut = (ArrayList<GameEntry>)getMonthlyReleases();    
//    ArrayList<GameEntry> recentlyReleased = getReleased();

    PageContext pageContext = (PageContext)getJspContext(); 
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();    
    JspWriter out = getJspContext().getOut();   

    //Display Games coming out this month first
    try {
      out.println("<div class='row'>"
//      + "<h2>Coming Out This Month: " + Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + "</h2>"
      + "<div class='large-12 columns'><hr>"
      + "<h2>Games Coming Out Soon</h2>");
      
      for (int i = 0; i < comingOut.size() && i < MAX_GAMES_DISPLAYED; i++) {
        comingOut.get(i).printUpcomingGameEntry(out, request);                
      }
      if (comingOut.size() == 0) {
        out.println("No games coming out this month.");
      }
      else {
        for (int i = comingOut.size() % GAMES_PER_ROW; i < GAMES_PER_ROW && i != 0; i++) {
          GameEntry.printFillerGameEntry(out);
        }
      }
      
      out.println("</div></div>");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  
  private List<GameEntry> getMonthlyReleases() {
    try {
      ArrayList<GameEntry> toRet = new ArrayList<GameEntry>();
      int year = Calendar.getInstance().get(Calendar.YEAR);
      int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
      int nextMonth = month % 12 + 1;
      JSONObject json = new JSONObject(UtilityTool.readUrl("http://www.giantbomb.com/api/games/?api_key="+API_KEY_GIANTBOMB
          + "&filter=expected_release_year:"+year+",expected_release_month:"+month+"&format=json&sort=original_release_date:asc"));
      JSONArray arr = json.getJSONArray("results");
      
      for (int i = 0; i < arr.length(); i++) {
        String gname = arr.getJSONObject(i).getString("name");               
        
        if(!arr.getJSONObject(i).isNull("expected_release_year") && !arr.getJSONObject(i).isNull("expected_release_month")) {
          GameEntry temp = new GameEntry();
          temp.name = gname;
          
          StringBuilder platformlist = new StringBuilder();
          
          for (int j = 0; j < arr.getJSONObject(i).getJSONArray("platforms").length(); j++) {
            platformlist.append(", " + arr.getJSONObject(i).getJSONArray("platforms").getJSONObject(j).getString("name"));
          }
          
          temp.platform = platformlist.substring(2);
           
          
          if (arr.getJSONObject(i).isNull("image")) {
            temp.img = "http://placehold.it/500x500&text=NO%20IMAGE";
          } else {
            temp.img = arr.getJSONObject(i).getJSONObject("image").getString("super_url");      
          }
          
          String release_date = Integer.toString(arr.getJSONObject(i).getInt("expected_release_month"));

          if (arr.getJSONObject(i).isNull("expected_release_day")) {
            release_date += "-??";
          } else {
            release_date += "-" + arr.getJSONObject(i).getInt("expected_release_day");
          }

          temp.release_date = arr.getJSONObject(i).getInt("expected_release_year") + "-" + release_date;
          
          temp.gb_id = arr.getJSONObject(i).getInt("id");
          toRet.add(temp);
        }
      }
      
      json = new JSONObject(UtilityTool.readUrl("http://www.giantbomb.com/api/games/?api_key="+API_KEY_GIANTBOMB
          + "&filter=expected_release_year:"+year+",expected_release_month:"+nextMonth+"&format=json&sort=original_release_date:asc"));
      arr = json.getJSONArray("results");

      for (int i = 0; i < arr.length(); i++) {
        String gname = arr.getJSONObject(i).getString("name");               
        
        if(!arr.getJSONObject(i).isNull("expected_release_year") && !arr.getJSONObject(i).isNull("expected_release_month")) {
          GameEntry temp = new GameEntry();
          temp.name = gname;
          
          StringBuilder platformlist = new StringBuilder();
          
          for (int j = 0; j < arr.getJSONObject(i).getJSONArray("platforms").length(); j++) {
            platformlist.append(", " + arr.getJSONObject(i).getJSONArray("platforms").getJSONObject(j).getString("name"));
          }
          
          temp.platform = platformlist.substring(2);
           
          
          if (arr.getJSONObject(i).isNull("image")) {
            temp.img = "http://placehold.it/500x500&text=NO%20IMAGE";
          } else {
            temp.img = arr.getJSONObject(i).getJSONObject("image").getString("super_url");      
          }
          
          String release_date = Integer.toString(arr.getJSONObject(i).getInt("expected_release_month"));

          if (arr.getJSONObject(i).isNull("expected_release_day")) {
            release_date += "-??";
          } else {
            release_date += "-" + arr.getJSONObject(i).getInt("expected_release_day");
          }

          temp.release_date = arr.getJSONObject(i).getInt("expected_release_year") + "-" + release_date;
          
          temp.gb_id = arr.getJSONObject(i).getInt("id");
          toRet.add(temp);
        }
      }      
      
      return toRet;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }            
  }
}
