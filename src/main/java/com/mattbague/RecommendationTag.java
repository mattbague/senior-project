package com.mattbague;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class RecommendationTag extends SimpleTagSupport {
  
  Set<String> articles = new HashSet<String>();
  
  public void doTag() {   
    JspWriter out = getJspContext().getOut();
    PageContext pageContext = (PageContext)getJspContext(); 
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    
    initArticles();
    
    try {
      request.setCharacterEncoding("UTF-8");
//      out.println("<div class='row'><div class='large-12 columns'><h2>Possible Related Searches</h2>");
      out.println("<div class='row'><div class='large-12 columns'><h2>Related Searches</h2>");
      
      
      String gName = request.getParameter("game").toLowerCase();
      
      Set<String> keywords = (HashSet<String>)getKeywords(gName);             
      StringBuilder kwsb = new StringBuilder();
      
      
      for (String kw : keywords) {
        kwsb.append(", <a href='./results?qt=search&name=" + URLEncoder.encode(kw, "UTF-8") + "&page=1'>" + kw + "</a>");
      }
      
      kwsb.delete(0, ", ".length());      
      kwsb.insert(0, "<b>>> Keyword(s):</b> ");      
      out.println(kwsb.toString());
      
      String phrase = getPhrase(gName);             
      
      if (phrase.equals("No phrases found.")) {
        out.println("<br><br><b>>> Phrase:</b> " + phrase);
      }
      else {
        out.println("<br><br><b>>> Phrase:</b> <a href='./results?qt=search&name=" + URLEncoder.encode(phrase, "UTF-8") + "&page=1'>" + phrase + "</a>");
      }
      
                  
      out.println("<div class='gap'></div><span style='color: green' data-tooltip class='has-tip'"
          + "title='Related searches are determined by analyzing the game&#39;s title. In the future, I want to also add a section called &#39;Other Users&#39; "
          + "which would be a list of searches performed by users who just viewed this game&#39;s page.'>"
          + "About Related Searches</span><br>");
      out.println("<hr></div></div>");
    }
    catch (Exception e) {
      
    }
  }

  private String getPhrase(String gName) throws IOException {
    String[] gParts = gName.split(" ");
    String toRet = "No phrases found.";
    
    int indexOfNum = 0;
    boolean colonCase = false;
    
    for (int i = 0; i < gParts.length; i++) {
      if (gParts[i].contains(":")) {
        gParts[i] = gParts[i].replaceAll(":", "").toUpperCase();
        if (UtilityTool.isNumeric(gParts[i]) || UtilityTool.isRomanNumeral(gParts[i])) {
          indexOfNum = i;
                              
        }
        // It's a 'word:' case
        else {
          gParts[i] = gParts[i].toLowerCase();
          indexOfNum = i+1;
        }        
        colonCase = true;
        // Want to end the loop early since anything after the colon is usually an identifier of that title and not part of the series name
        i = gParts.length; 
      }
      // This will get the last number in the title. E.g. Left 4 Dead 2
      else if (UtilityTool.isNumeric(gParts[i]) || UtilityTool.isRomanNumeral(gParts[i].toUpperCase())) {
        indexOfNum = i;   
      }
    }
            
    if (indexOfNum > 0 && (indexOfNum == gParts.length-1 || colonCase)) {
      StringBuilder sb = new StringBuilder();
      
      for (int i = 0; i < indexOfNum; i++) {
        sb.append(" " + gParts[i]);
      }
      
      toRet = sb.toString().substring(1);
    }
    
    return toRet;
  }

  private void initArticles() {    
    articles.add("a");
    articles.add("an");
    articles.add("the");
    articles.add("this");
    articles.add("that");
    articles.add("these");
    articles.add("those");
    articles.add("each");
    articles.add("every");
    articles.add("either");
    articles.add("neither");
    articles.add("much");
    articles.add("enough");
    articles.add("which");
    articles.add("what");
    articles.add("my");
    articles.add("our");
    articles.add("their");
    articles.add("her");
    articles.add("its");
    articles.add("his");
    articles.add("any");
    articles.add("some");    
    articles.add("of");
  }

  private Set<String> getKeywords(String gName) {
    String[] gParts = gName.replaceAll("[^A-Za-z0-9\\s]", "").split(" ");
    
    HashSet<String> toRet = new HashSet<String>();    
    
    for (String temp : gParts) {
      if (!articles.contains(temp) && !UtilityTool.isNumeric(temp) && !UtilityTool.isRomanNumeral(temp.toUpperCase())) {
        toRet.add(temp);
      }
    }
    
    return toRet;
  }
}
