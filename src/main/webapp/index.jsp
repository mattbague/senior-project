<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Home - GameHubbub</title>
<link rel="shortcut icon" href="./images/favicon.ico" type="image/icon"> 
<link rel="icon" href="./images/favicon.ico" type="image/icon">
<link rel="stylesheet" href="foundation-5.1.1/css/foundation.css" />
<script src="foundation-5.1.1/js/vendor/modernizr.js"></script>

<link rel="stylesheet" href="styles/mystyles.css" />
<script src="js/myscripts.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="mytags" uri="WEB-INF/custom.tld"%>
</head>

<body>
  <div class="wrapper">

    <!-- Banner -->
    <div class="row">
      <div class="large-12 columns">
        <img src="./images/gamehubbub_banner.png">
      </div>
    </div>
    <!-- END Banner -->

    <!-- Navigation -->
    <div class="row" style="padding-bottom: 20px">
      <div class="large-12 columns">
        <nav class="top-bar"
          style="background-color: #008CBA" data-topbar>
          <ul class="title-area">
            <li class="name">
              <h1>
                <a href="./">Home</a>
              </h1>
            </li>
            <li class="toggle-topbar menu-icon"><a href="#">Menu</a></li>
          </ul>

          <section class="top-bar-section">
            <!-- Right Nav Section -->
            <ul class="right">
              <li class="divider"></li>
              <li class="active"><a class="pad_topbar" href="#">Site News</a></li>
              <li class="divider"></li>
              <li class="active"><a class="pad_topbar" href="browse">Browse Games</a></li>
              <li class="divider"></li>
              <li class="active"><a class="pad_topbar" href="registration">Create
                  Account</a></li>
              <li class="divider"></li>
              <li class="active"><a class="pad_topbar" href="#">Log In</a></li>
            </ul>
          </section>
        </nav>
      </div>
    </div>
    
    <!-- END Navigation -->

    <!-- Orbit Slider -->
    <div class="row">
      <div class="large-12 columns">
        <ul data-orbit>
          <li><img src="./images/site_construction_orbit.png"></li>
          <li><img src="./images/upcoming_features_orbit.png"></li>
          <li><img src="./images/report_issue_orbit.png"></li>
        </ul>
      </div>
    </div>
    <!-- END Orbit Slider -->

    <!-- Search Bar -->
    <div class="row">
      <div class="large-12 columns">
        <div class="radius panel">
          <div class="row collapse">
            <div class="large-10 small-8 columns">
              <input type="text" id="search_words" value=""
                onkeypress="searchKeyPress(event)" />
            </div>
            <div class="large-2 small-3 columns">
              <a id="search_btn" onclick="performSearch()"
                class="postfix button expand">Search Games</a>
              
              <a href="#" data-reveal-id="search_tips"><b>Helpful Search Tips</b></a>              
              <div id="search_tips" class="reveal-modal small search_tip" data-reveal>
                <h3><b>How to Use the Search Tool</b></h3>
                <p>The search tool allows you filter your results using the format:<br>
                <b>&lt;filterType&gt;:&lt;value&gt;</b><br>To use multiple filters, separate them with a comma (e.g. filter1:value1, filter2:value2).
                If no filters are specified, the search will default to using game's name as the filter.
                Below are the filter types allowed, along with example usage</p>
                <ul>
                  <li><b>name</b> - <i>the name of the game.</i><br><b>(e.g. 'name:zelda')</b></li>
                  <li><b>platform</b> - <i>the platform (aka console) the game is available on.</i><br><b>(e.g. 'platform:ps4')</b></li>
                  <li><b>expected_year</b> - <i>the expected release year for unreleased games.</i><br><b>(e.g. 'expected_year:2015')</b></li>
                  <li><b>expected_month</b> - <i>the expected release month for unreleased games.</i><br><b>(e.g. 'expected_month:December' or 'expected_month:12')</b></li>
                  <li><b>release_date</b> - <i>the game's initial release date. Follows YYYY-MM-DD format.</i><br><b>(e.g. 'release_date:2010-09-28' )</b></li>
                </ul>
                <a class="close-reveal-modal">&#215;</a>
             </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- END Search Bar -->

    <!-- Thumbnails for this Month's games-->
    <mytags:Index></mytags:Index>
    <!-- END Thumbnails for this Month's games -->

    <!-- Header Content -->
    <div class="row" style="padding-top: 30px"></div>
    <!-- Header Content -->
    <div class="row">
      <hr>
      <div class="large-6 columns">
        <img src="http://placehold.it/500x500&text=Image"><br>
      </div>
      <div class="large-6 columns">
        <h3 class="show-for-small">Header</h3>
        <div class="panel">
          <h4 class="hide-for-small">About GameHubbub</h4>
          <hr />
          <h5 class="subheader">Fusce ullamcorper mauris in eros
            dignissim molestie posuere felis blandit. Aliquam erat
            volutpat. Mauris ultricies posuere vehicula. Sed sit amet
            posuere erat. Quisque in ipsum non augue euismod dapibus non
            et eros. Pellentesque consectetur tempus mi iaculis
            bibendum. Ut vel dolor sed eros tincidunt volutpat ac eget
            leo.</h5>
        </div>


      </div>
    </div>

    <!-- End Header Content -->
    <!-- Footer -->
    <footer class="row">
      <div class="large-12 columns">
        <hr />
        <div class="row">
          <div class="large-4 columns">
            <p>© Copyright Matt Bague.</p>
          </div>
          <div class="large-8 columns">
            <ul class="inline-list right">
              <li>Site Data Provided By:</li>
              <li><a href="http://www.giantbomb.com/">GiantBomb</a></li>
              <li><a href="http://www.metacritic.com/">Metacritic</a></li>
              <li><a href="http://thegamesdb.net/">TheGamesDB</a></li>
            </ul>
          </div>
        </div>
      </div>
    </footer>

    </div>
    <!-- End Footer -->
    <!-- Needed to do the orbit slider on the home page -->
    <script src="foundation-5.1.1/js/vendor/jquery.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.orbit.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.tooltip.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.reveal.js"></script>
    <script>
      $(document).foundation({
        orbit: {
          
        }
      });
  </script>
</body>
</html>
