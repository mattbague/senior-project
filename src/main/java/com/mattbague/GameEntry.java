package com.mattbague;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

//PROTOCOL FOR HREF: blah.jsp?game=SOMEGAME

public class GameEntry {
  private static int MAX_IMG_WIDTH = 370; // See mystyles.css .img_gamepage to confirm value
  
  // TODO: Maybe make some say "unknown" and some "unavailable" depending on field
  String data_src = "N/A";
  String name = "Unknown";
  String desc = "Unknown";
  String img = "http://placehold.it/500x500&text=NO%20IMAGE";
  String genre = "Unknown";
  String score = "Unknown";
  String rating = "Unknown";  
  String platform = "Unknown";
  String release_date = "Unknown";
  String players = "Unknown";
  String coop = "Unknown";
  String publisher = "Unknown";
  String developer = "Unknown";
  String video = "Unknown";
  int gb_id = -1;   
   
  public void printUpcomingGameEntry(JspWriter out, HttpServletRequest request) throws IOException {     
    out.println("<a href='./gamepage?game=" + URLEncoder.encode(this.name, "UTF-8") + "&gb_id="+ this.gb_id + "'>\n"
      + "<div class='large-3 small-6 columns'>\n"
      + "<div class='panel panel_hl release_panel_size'>\n"
      + "<img class='entry_img center img_border' src='" + this.img + "'>\n"
      + "<div class='game_title'><h5><b>" + this.name + "</b></h5></div>\n"      
      + "<div style='color:green; font-size: 16px'><b><i>On: " + this.platform + "</i></b></div>\n"
      + "<div class='sit_down' style='color:blue;'><b>Releases: " + this.release_date + "</b></div>\n"
      + "</div></div></a>");    
  }  
  
  public void printSimilarGameEntry(JspWriter out) throws IOException {
    
  }
  
  public  void printSearchGameEntry(JspWriter out, HttpServletRequest request) throws IOException {
   out.println("<a href='./gamepage?game=" + URLEncoder.encode(this.name, "UTF-8") + "&gb_id="+ this.gb_id +"'>\n"
        + "<div class='large-3 small-6 columns'>\n"
        + "<div class='panel panel_hl search_panel_size'>\n"
        + "<img class='entry_img center img_border' src='" + this.img + "'>\n"
        + "<div class='game_title'><h5><b>" + this.name + "</b></h5></div>\n"      
        + "</div></div></a>");    
  }
  
  public void printDetailedGameEntry(JspWriter out) throws IOException {
    // Calculate image height
    BufferedImage tImg = ImageIO.read(new URL(this.img));
    int height = tImg.getHeight();
    int width = tImg.getWidth();
    double down_ratio = 1;
    if (width > MAX_IMG_WIDTH) {
      down_ratio = MAX_IMG_WIDTH/(double)width; 
    }
    
    height *= down_ratio;        
    
    String videoHTML = "<span>No Video Available</span>\n";  
    
    if (!this.video.equals("Unknown")) {
      videoHTML = "<a target='_blank' href='" + this.video + "'>Link to Video</a>\n";      
      URL vidlink = new URL(this.video);
      
      if (vidlink.getHost().equals("www.youtube.com")) {
        videoHTML = "<div class='flex-video'>"
            + "<iframe width='420' height='315'"
            + "src='" + UtilityTool.convertToEmbeddedYoutube(vidlink.getQuery())
            + "' frameborder='1' allowfullscreen></iframe></div>";        
      }      
    }
    
    this.score = this.score.replaceAll(", ", ",<br>" );
    
    out.println("<div class='row'><div class='large-12 columns'>\n"
        + "<div class='large-5 columns'>\n"
        + "<img class='img_gamepage img_border' src='" + this.img + "'></div>\n"
        + "<div class='panel large-7 columns' style='min-height:"+height+"px'>\n"
        + "<h3><b><i>General Info</i></b></h3>\n" 
        + "<table><tbody>"
        + "<tr><td>Developer(s):</td><td>" + this.developer + "</td></tr>\n"
        + "<tr><td>Publisher:</td><td>" + this.publisher + "</td></tr>\n"
        + "<tr><td>Genre(s):</td><td>" + this.genre + "</td></tr>\n"
        + "<tr><td>Release Date(s):</td><td>" + this.release_date + "</td></tr>\n"
        + "<tr><td># of Players (Local):</td><td>" + this.players + "</td></tr>\n"
        + "<tr><td>Co-op:</td><td>" + this.coop + "</td></tr>\n"
        + "<tr><td>Platform(s):</td><td>" + this.platform + "</td></tr>\n"        
        + "<tr><td>Scores:</td><td style='line-height:1.5em'>" + this.score + "</td></tr>\n"
        + "<tr><td>ESRB Rating:</td><td>" + this.rating + "</td></tr></tbody></table>\n"
        + "</div></div></div>\n"

        + "<!-- Description -->\n"
        + "<div class='row'><div class='large-12 columns'>\n"
        + "<h4 style='text-align:left'>Game Description</h4>\n"
        + "<div class='panel'>\n"
        + "<p>" + this.desc + "</p>\n"   
        + "<h5><b>Trailer Video</b></h5>" + videoHTML
        + "</div></div></div>");
  }
  
  public static void printFillerGameEntry(JspWriter out) throws IOException {
    out.println("<div class='large-3 small-6 columns'>"
        + "<div class='panel search_panel_size hide'></div></div>");  
  }
}
