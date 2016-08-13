/**
 * 
 */

function performSearch() {
  var params = parseSearchString(document.getElementById("search_words").value);
  var query = "";

  if (params["name"] != undefined) {
    query += "&name=" + params["name"];
  }
  if (params["platform"] != undefined) {
    query += "&pf=" + params["platform"];
  }  
  if (params["expected_year"] != undefined) {
    query += "&ery=" + params["expected_year"]; 
  }
  if (params["expected_quarter"] != undefined) {
    query += "&erq=" + params["expected_quarter"];    
  }
  if (params["expected_month"] != undefined) {
    query += "&erm=" + params["expected_month"];    
  }
  if (params["release_date"] != undefined) {
    query += "&ord=" + params["release_date"]; 
  }
  
  window.location.href =  "./results?qt=search&keywords=" 
    + encodeURIComponent(document.getElementById("search_words").value) + query + "&page=1";
//  window.location.href =  "./results?qt=search&name=" + document.getElementById("search_words").value + "&page=1";
//  console.log(window.location.hostname);
}

function searchKeyPress(e)
{
    // look for window.event in case event isn't passed in
    if (typeof e == 'undefined' && window.event) { e = window.event; }
    if (e.keyCode == 13) {
        performSearch();
    }
}

function updateGamePageTitle() {    
  var params = parseQueryString(window.location.search.substring(1)); 
  document.title = decodeURIComponent(params["game"].replace(/\+/g, " ")) + " - GameHubbub";
} 

function updateSearchPageTitle() {
  var params = parseQueryString(window.location.search.substring(1)); 
  document.title = decodeURIComponent(params["name"].replace(/\+/g, " ")) + " - GameHubbub";
}

function parseQueryString (queryString) {
    var params = {}, queries, temp, i, l;
 
    // Split into key/value pairs
    queries = queryString.split("&");
 
    // Convert the array of strings into an object
    for ( i = 0, l = queries.length; i < l; i++ ) {
        temp = queries[i].split('=');
        params[temp[0]] = temp[1];
    }
 
    return params;
}

function parseSearchString (searchString) {
  // butts
  var params = {}, searchwords, temp, i, l;    
  
  if (searchString.indexOf(":") === -1 && searchString.indexOf(",") === -1) {
    params["name"] = searchString;
    return params;
  }
  
  if (searchString.indexOf(",") != -1) {
//    searchString = searchString.replace(/, /g, ",");
//    // Split into key/value pairs    
//    console.log(searchString);
    searchwords = searchString.split(",");
  }
  else {
    searchwords = [searchString];    
  }
  
  // Convert the array of strings into an object
  for ( i = 0, l = searchwords.length; i < l; i++ ) {
    if (searchwords[i].indexOf(":") != -1) {
      temp = searchwords[i].split(':');
      params[temp[0].trim()] = temp[1].trim();
      console.log(params[temp[0]]);
    }
    else {
      if (params["name"] === undefined) {
        params["name"] = searchwords[i];
      }
      else {
        params["name"] += (" " + searchwords[i]);
      }
    }
  }
  
  if (params["name"] != undefined) {
    params["name"] = params["name"].trim();
  }
  
  return params;
}