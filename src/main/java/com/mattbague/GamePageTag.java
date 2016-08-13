package com.mattbague;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GamePageTag extends SimpleTagSupport {
  
  public void doTag() {
    JspWriter out = getJspContext().getOut();
    PageContext pageContext = (PageContext)getJspContext(); 
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    
    try {
      request.setCharacterEncoding("UTF-8");
      out.println("<div class='row'><div class='large-12 columns'>");
    
      if (request.getQueryString() == null || request.getQueryString().length() <= ("game=").length()) {
        out.println("Unable to create this game's page.\nIf you think this was an error on our part, please feel free to contact us at "
            + "dev.gamehubbub@gmail.com.");
        return;
      }
      else {
        int gameId = Integer.parseInt(request.getParameter("gb_id"));
        String gameName = request.getParameter("game");
  
        out.println("<h1><b>" + gameName + "</b></h1>");
        
        if (gameName.contains("/")) {
          gameName = gameName.substring(0, gameName.indexOf('/'));
        }
  
        GameEntry game = getGameInfo(gameName, gameId);
        game.printDetailedGameEntry(out);
        out.println("<hr></div></div>");
      }    
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private GameEntry getGameInfo(String gameName, int gameId) {
    GameEntry toRet = new GameEntry();
    toRet.name = gameName;
    
    try {
      GameEntry[] gamedata = {
        getGiantBombData(gameName, gameId),
        getMetacriticData(gameName),
        getIGNData(gameName),
        getGamesDBData(gameName)
      }; 
      
      toRet = consolidateData(gamedata);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return toRet;
  }  

  private GameEntry consolidateData(GameEntry[] data) throws IOException {
    GameEntry theOne = new GameEntry();
    
    StringBuilder scores = new StringBuilder();
    Set<String> genreSet = new HashSet<String>();
    Set<String> devSet = new HashSet<String>();
    for (int i = 0; i < data.length; i++) {
      switch (data[i].data_src) {
        case "GiantBomb":
          theOne.name = data[i].name;
          if (theOne.desc.equals("Unknown")) {
            theOne.desc = data[i].desc;
          }
          if (!data[i].img.equals("http://placehold.it/500x500&text=NO%20IMAGE")) {
            theOne.img = data[i].img;
          }
          if (theOne.publisher.equals("Unknown")) {
            theOne.publisher = data[i].publisher;
          }
          if (!data[i].genre.equals("Unknown")) { 
            addToSet(genreSet, data[i].genre);
          }
          if (!data[i].release_date.equals("Unknown")) {            
            theOne.release_date = data[i].release_date;
          }
          if (!data[i].score.equals("Unknown")) {
            scores.append(", " + data[i].score + " / 5 (GiantBomb)");
          }
          theOne.platform = data[i].platform;
          addToSet(devSet, data[i].developer);
          break;
        case "Metacritic":
          if (!data[i].score.equals("Unknown")) {
            scores.append(", " + data[i].score + " / 100 (Metacritic)");
          }
          if (theOne.release_date.equals("Unknown")) {
            theOne.release_date = data[i].release_date;
          }
          if (theOne.publisher.equals("Unknown")) {
            theOne.publisher = data[i].publisher;
          }
          if (theOne.img.equals("http://placehold.it/500x500&text=NO%20IMAGE")) {
            theOne.img = data[i].img;
          }
          if (theOne.rating.equals("Unknown")) {
            theOne.rating = data[i].rating;
          }
          break;
        case "IGN":
          if (!data[i].score.equals("Unknown")) {
            scores.append(", " + data[i].score + " / 10 (IGN)");
          }
          if (theOne.platform.equals("Unknown")) {
            theOne.platform = data[i].platform;
          }
          break;
        case "TheGamesDB":
          if (theOne.desc.length() < data[i].desc.length()) {
            theOne.desc = data[i].desc;
          }
          addToSet(genreSet, data[i].genre);
          if (!data[i].score.equals("Unknown")) {
            DecimalFormat df = new DecimalFormat("#.##");
            scores.append(", " + df.format(Double.parseDouble(data[i].score)) + " / 10 (TheGamesDB)");
          }
          if (theOne.rating.equals("Unknown")) {
            theOne.rating = data[i].rating;
          }
          if (theOne.release_date.equals("Unknown")) {
            theOne.release_date = data[i].release_date;
          }
          if (theOne.publisher.equals("Unknown")) {
            theOne.publisher = data[i].publisher;
          }          
          theOne.players = data[i].players;
          theOne.coop = data[i].coop;
          addToSet(devSet, data[i].developer);
          theOne.video = data[i].video;
          break;  
        default:
          break;
      }
    }
    
    if (scores.length() > 2) {
      theOne.score = scores.toString().substring(", ".length());
    }
    
    theOne.genre = generateSetString(genreSet);
    theOne.developer = generateSetString(devSet);
    
    if (theOne.release_date.equals("")) {
      theOne.release_date = "Unknown";
    }

    return theOne;
  }
  
  private String generateSetString(Set<String> aSet) {
    aSet.remove("Unknown");
    StringBuilder sb = new StringBuilder();
    
    for (String s : aSet) {
      sb.append(", " + s);
    }
    
    if (sb.length() > 2) {
      return sb.toString().substring(", ".length());
    }
    
    return sb.toString();
  }
  
  private void addToSet(Set<String> theSet, String csv) {
    String[] genreList = csv.split(",");
    
    for (String temp : genreList) {
      theSet.add(temp);
    }
    
  }

  private GameEntry getGiantBombData(String gName, int gb_id) throws JSONException, Exception {
    GameEntry toRet = new GameEntry();    
    toRet.data_src = "GiantBomb";
    
    // Get data from Giantbomb
    JSONObject gb_json = new JSONObject(UtilityTool.readUrl("http://www.giantbomb.com/api/game/" + gb_id
        + "/?api_key=cce72b8402cb0ee5018ad1d55d7227e5d18133a7"
        + "&format=json"));
    
    JSONObject gb_res = gb_json.getJSONObject("results");
    
    
    if (!gb_res.isNull("deck")) {
      toRet.desc = gb_res.getString("deck");   
    }
    
    if (!gb_res.isNull("image")) {
      toRet.img = gb_res.getJSONObject("image").getString("super_url");
    } 

    if (!gb_res.isNull("genres")) {
      JSONArray genres = gb_res.getJSONArray("genres");
      
      StringBuilder sb = new StringBuilder();
        
      for (int i = 0; i < genres.length(); i++) {
        sb.append("," + genres.getJSONObject(i). getString("name"));
      }
      
      if (sb.toString().length() > 0) {
        toRet.genre = sb.toString().substring(",".length());
      }
    }
    
    if (!gb_res.isNull("platforms")) {
      JSONArray plats = gb_res.getJSONArray("platforms");
      
      StringBuilder sb = new StringBuilder();
      
      for (int i = 0; i < plats.length(); i++) {
        sb.append(", " + plats.getJSONObject(i).getString("name"));
      }
      
      if (sb.toString().length() > 0) {
        toRet.platform = sb.toString().substring(", ".length());
      }
    }
    
    if (!gb_res.isNull("developers")) {
      JSONArray devs = gb_res.getJSONArray("developers");
      
      StringBuilder sb = new StringBuilder();
      
      for (int i = 0; i < devs.length(); i++) {
        sb.append("," + devs.getJSONObject(i).getString("name"));
      }
      
      if (sb.toString().length() > 0) {
        toRet.developer = sb.toString().substring(",".length());
      }        
    }
    
    if (!gb_res.isNull("original_release_date")) {
      toRet.release_date = gb_res.getString("original_release_date").split(" ")[0];
    }
    else if (!gb_res.isNull("expected_release_year")) {
      if (!gb_res.isNull("expected_release_month")) {
        if (!gb_res.isNull("expected_release_day")) {
          toRet.release_date = gb_res.getInt("expected_release_year") + "-" + gb_res.getInt("expected_release_month") + "-" + gb_res.getInt("expected_release_day");
        }
        else {
          toRet.release_date = gb_res.getInt("expected_release_year") + "-" + gb_res.getInt("expected_release_month") + "-??";
        }
      }
      else {
        toRet.release_date = gb_res.getInt("expected_release_year") + "-??-??";
      }
    }
    
    // Have to open new query for score
    if (!gb_res.isNull("reviews")) {
      String reviewURL = gb_res.getJSONArray("reviews").getJSONObject(0).getString("api_detail_url");
      gb_json = new JSONObject(UtilityTool.readUrl(reviewURL
        + "?api_key=cce72b8402cb0ee5018ad1d55d7227e5d18133a7"
        + "&format=json"));
      
      gb_res = gb_json.getJSONObject("results");
      
      if (!gb_res.isNull("score")) {
        toRet.score = Double.toString(gb_res.getDouble("score"));
      }
    }
    return toRet;
  }
  
  private GameEntry getMetacriticData(String gName) throws UnirestException, IOException {
    GameEntry toRet = new GameEntry();    
    toRet.data_src = "Metacritic";
    
    gName = UtilityTool.unAccent(gName);
    
    // Get data from Metacritic
    HttpResponse<JsonNode> meta_req = Unirest.post("https://byroredux-metacritic.p.mashape.com/search/game")
        .header("X-Mashape-Authorization", "GH2h1nyORW6KOrS8XgC1agnM5f7HuFBa")
        .field("title", gName)
        .asJson();
    
    JSONArray meta_res = meta_req.getBody().getArray().getJSONObject(0).getJSONArray("results");

    if (meta_res == null || meta_res.length() == 0) {
      return toRet;
    }
    
    toRet.publisher = meta_res.getJSONObject(0).isNull("publisher") ? "Unknown" : meta_res.getJSONObject(0).getString("publisher");
    toRet.rating = meta_res.getJSONObject(0).isNull("rating") ? "Unknown" : meta_res.getJSONObject(0).getString("rating");
    
    double score = 0;
    int count = 0;
    boolean gotMetacriticData = false;
    for (int i = 0; i < meta_res.length(); i++) {
      String tempName = meta_res.getJSONObject(i).getString("name");
      if (!meta_res.getJSONObject(i).getString("score").equals("") && tempName.toLowerCase().equals(gName.toLowerCase())) {
        gotMetacriticData = true;
        count++;
        score += Integer.parseInt(meta_res.getJSONObject(i).getString("score"));
        toRet.release_date += ", " + meta_res.getJSONObject(i).getString("rlsdate") + " (" + meta_res.getJSONObject(i).getString("platform") + ")";
        
        if (toRet.release_date.substring(0, ", known".length()).equals(", known")) {
          toRet.release_date = toRet.release_date.substring(0, ", known".length());
        }
        
        toRet.platform += ", " + meta_res.getJSONObject(i).getString("platform");
      }
    }
    if (gotMetacriticData) {
      toRet.release_date = toRet.release_date.substring(", ".length());
      toRet.platform = toRet.platform.substring(", ".length());
      toRet.score = Integer.toString((int)Math.round(score/count));
    }    
    
    return toRet;
  }
  
  private GameEntry getIGNData(String gName) throws UnirestException, IOException {
    GameEntry toRet = new GameEntry();    
    toRet.data_src = "IGN";
    
    gName = UtilityTool.unAccent(gName);
    
    // Get data from IGN
    HttpResponse<JsonNode> ign_req = Unirest.get("https://videogamesrating.p.mashape.com/get.php?game=" + URLEncoder.encode(gName, "UTF-8")
        + "&count=20")
        .header("X-Mashape-Authorization", "GH2h1nyORW6KOrS8XgC1agnM5f7HuFBa")
        .asJson();
    
    JSONArray ign_res = ign_req.getBody().getArray();

    if (ign_res.length() == 0) {
      return toRet;
    }
    
    toRet.publisher = ign_res.getJSONObject(0).getString("publisher");       
    
    double score = 0;
    int count = 0;
    boolean gotData = false;
    for (int i = 0; i < ign_res.length(); i++) {
      String tempName = ign_res.getJSONObject(i).getString("title");
      if (!ign_res.getJSONObject(i).getString("score").equals("") && tempName.toLowerCase().equals(gName.toLowerCase())) {
        gotData = true;
        count++;
        score += Double.parseDouble(ign_res.getJSONObject(i).getString("score"));        
      }
    }
    
    if (gotData) {
      toRet.score = Double.toString(score/count);
    }    
    
    if (!ign_res.getJSONObject(0).isNull("thumb")) {
      toRet.img = ign_res.getJSONObject(0).getString("thumb");
    }
    
    if (!ign_res.getJSONObject(0).isNull("platforms")) {
      JSONObject plats = ign_res.getJSONObject(0).getJSONObject("platforms");
      
      StringBuilder sb = new StringBuilder();
      
      int platCount = 1;
      while (!plats.isNull(Integer.toString(platCount))) {
        sb.append(", " + plats.getString(Integer.toString(platCount++)));
      }
      
      if (sb.toString().length() > 2) {
        toRet.platform = sb.toString().substring(", ".length());
      }
    }
    
    return toRet;
  }  
  
  private GameEntry getGamesDBData(String gName) {
    GameEntry toRet = new GameEntry();
    toRet.data_src = "TheGamesDB";   
    
    
    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLEventReader reader; 
    try {
      if (gName.contains("Pok\u00E9mon") && !gName.contains("X")) {
        gName += " Version";
      }
      
      URLConnection connection = new URL("http://thegamesdb.net/api/GetGamesList.php?name=" + URLEncoder.encode(gName, "UTF-8")).openConnection();
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
      connection.connect();
      
      reader = factory.createXMLEventReader(connection.getInputStream(), "UTF-8");

      boolean notFound = true;
      String id = null;
      while (reader.hasNext() && notFound) {
        XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          // Save id first
          if (event.asStartElement().getName().getLocalPart().equals("id")) {
            event = reader.nextEvent();
            id = event.asCharacters().getData();
            continue;
          }
            
          // Check if the game name matches
          if (event.asStartElement().getName().getLocalPart().equals("GameTitle")) {           
            notFound = reader.getElementText().equals(gName) ? false : true;
          }
          // Else set id back to null
          else {
            id = null;              
          }
        }
      }

      
      if (id == null) {
        return toRet;
      }          
      

      
      connection = new URL("http://thegamesdb.net/api/GetGame.php?id=" + id).openConnection();
      connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
      connection.connect();
      
      reader = factory.createXMLEventReader(connection.getInputStream());
      
      StringBuilder sb = new StringBuilder();
      
      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          switch (event.asStartElement().getName().getLocalPart()) {
            case "ReleaseDate":
              event = reader.nextEvent();
              toRet.release_date = event.asCharacters().getData();
              break;
            case "Overview":
              event = reader.nextEvent();
              toRet.desc = event.asCharacters().getData().replaceAll("\u2122", "");
              break;
            case "ESRB":
              event = reader.nextEvent();
              toRet.rating = event.asCharacters().getData();
              break;
            case "genre":
              event = reader.nextEvent();
              sb.append("," + event.asCharacters().getData());
              break;
            case "Players":
              event = reader.nextEvent();
              toRet.players = event.asCharacters().getData();
              break;
            case "Co-op":
              event = reader.nextEvent();
              toRet.coop = event.asCharacters().getData();
              break;
            case "Youtube":
              event = reader.nextEvent();
              toRet.video = event.asCharacters().getData();
              break;
            case "Publisher":
              event = reader.nextEvent();
              toRet.publisher = event.asCharacters().getData();
              break;
            case "Developer":
              event = reader.nextEvent();
              toRet.developer = event.asCharacters().getData();
              break;
            case "Rating":
              event = reader.nextEvent();
              toRet.score = event.asCharacters().getData();
            default:
              break;
          }
        }
      }
            
      if (sb.toString().length() > ", ".length()) {
        toRet.genre = sb.toString().substring(",".length());
      }  
      
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
     
    return toRet;    
  }
}
