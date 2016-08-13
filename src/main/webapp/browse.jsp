<!doctype html>
<html class="no-js" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Browse - GameHubbub</title>
<link rel="shortcut icon" href="./images/favicon.ico" type="image/icon"> 
<link rel="icon" href="./images/favicon.ico" type="image/icon">
<link rel="stylesheet" href="foundation-5.1.1/css/foundation.css" />
<link rel="stylesheet" href="styles/mystyles.css" />
<script src="foundation-5.1.1/js/vendor/modernizr.js"></script>
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
        <nav class="top-bar" style="background-color: #008CBA"
          data-topbar>
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
              <li class="alert"><a class="pad_topbar" href="browse">Browse Games</a></li>
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

    <div class="gap"></div>

    <!-- Browse Buttons -->
    <div class="row">
      <div class="large-8 columns large-centered">
        <ul class="large-block-grid-3 large-centered">
          <li><a id="l1" class="button radius round expand"
            onclick="displaySection('genres')">Browse By Genre</a></li>
          <li><a id="l2" class="button radius round expand"
            onclick="displaySection('platforms')">Browse By Platform</a></li>
          <li><a id="l3" class="button radius round expand"
            onclick="displaySection('searchbox')">Search By Title</a></li>
        </ul>
      </div>
    </div>
    <!-- END Browse Buttons -->

    <!-- Genre Section -->
    <div id="genres" style="display:none">
    </div>
    <!-- END Genre Section -->

    <!-- Platform Section -->
    <div id="platforms" style="display:none">
      <div class="row">
        <div class="large-4 columns">
          <h4>8th Generation</h4>
          <ul> 
            <li><a href="./results?qt=browse&bt=platforms:145&name=Xbox One&page=1">Xbox One</a></li>
            <li><a href="./results?qt=browse&bt=platforms:146&name=PlayStation 4&page=1">PlayStation 4</a></li>
            <li><a href="./results?qt=browse&bt=platforms:139&name=Wii U&page=1">Wii U</a></li>
            <li><a href="./results?qt=browse&bt=platforms:129&name=PlayStation Vita&page=1">PlayStation Vita</a></li>
            <li><a href="./results?qt=browse&bt=platforms:143&name=PSN (Vita)&page=1">PSN (Vita)</a></li>
            <li><a href="./results?qt=browse&bt=platforms:117&name=Nintendo 3DS&page=1">Nintendo 3DS</a></li>
            <li><a href="./results?qt=browse&bt=platforms:138&name=Nintendo 3DS eShop&page=1">Nintendo 3DS eShop</a></li>      
          </ul>
          </div>
        <div class="large-4 columns">
          <h4>7th Generation</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:36&name=Wii&page=1">Wii</a></li>
            <li><a href="./results?qt=browse&bt=platforms:87&name=Wii Shop&page=1">Wii Shop</a></li>
            <li><a href="./results?qt=browse&bt=platforms:20&name=Xbox 360&page=1">Xbox 360</a></li>
            <li><a href="./results?qt=browse&bt=platforms:86&name=Xbox 360 Games Store&page=1">Xbox 360 Games Store</a></li>
            <li><a href="./results?qt=browse&bt=platforms:35&name=PlayStation 3&page=1">Playstation 3</a></li>
            <li><a href="./results?qt=browse&bt=platforms:88&name=PSN (PlayStation 3)&page=1">PSN (PlayStation 3)</a></li>
            <li><a href="./results?qt=browse&bt=platforms:52&name=Nintendo DS&page=1">Nintendo DS</a></li>
            <li><a href="./results?qt=browse&bt=platforms:106&name=DSiWare&page=1">DSiWare</a></li>
            <li><a href="./results?qt=browse&bt=platforms:18&name=PlayStation Portable&page=1">PlayStation Portable</a></li>
            <li><a href="./results?qt=browse&bt=platforms:116&name=PSN (PlayStation Portable)&page=1">PSN (PlayStation Portable)</a></li>              
          </ul>
        </div>
        <div class="large-4 columns">
          <h4>6th Generation</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:37&name=Dreamcast&page=1">Dreamcast</a></li>
            <li><a href="./results?qt=browse&bt=platforms:19&name=PlayStation 2&page=1">PlayStation 2</a></li>
            <li><a href="./results?qt=browse&bt=platforms:23&name=GameCube&page=1">GameCube</a></li>
            <li><a href="./results?qt=browse&bt=platforms:32&name=Xbox&page=1">Xbox</a></li>
            <li><a href="./results?qt=browse&bt=platforms:4&name=Game Boy Advance&page=1">Game Boy Advance</a></li>   
          </ul>
        </div>  
      </div>
      <div class="row">
        <div class="large-4 columns">
          <h4>5th Generation</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:43&name=Nintendo 64&page=1">Nintendo 64</a></li>
            <li><a href="./results?qt=browse&bt=platforms:101&name=Nintendo 64DD&page=1">Nintendo 64DD</a></li>
            <li><a href="./results?qt=browse&bt=platforms:22&name=PlayStation&page=1">PlayStation</a></li>
            <li><a href="./results?qt=browse&bt=platforms42&name=Sega Saturn&page=1">Sega Saturn</a></li>
            <li><a href="./results?qt=browse&bt=platforms:28&name=Atari Jaguar&page=1">Atari Jaguar</a></li>
            <li><a href="./results?qt=browse&bt=platforms:26&name=3DO Interactive Multiplayer&page=1">3DO Interactive Multiplayer</a></li>
            <li><a href="./results?qt=browse&bt=platforms:57&name=Game Boy Color&page=1">Game Boy Color</a></li>
            <li><a href="./results?qt=browse&bt=platforms:81&name=Neo Geo Pocket Color&page=1">Neo Geo Pocket Color</a></li> 
            <li><a href="./results?qt=browse&bt=platforms:80&name=Neo Geo Pocket&page=1">Neo Geo Pocket</a></li>      
          </ul>
          </div>
        <div class="large-4 columns">
          <h4>4th Generation</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:55&name=PC Engine/TurboGrafx-16&page=1">PC Engine/TurboGrafx-16</a></li>
            <li><a href="./results?qt=browse&bt=platforms:6&name=Sega Genesis&page=1">Sega Genesis</a></li>
            <li><a href="./results?qt=browse&bt=platforms:9&name=Super Nintendo Entertainment System&page=1">Super Nintendo Entertainment System</a></li>
            <li><a href="./results?qt=browse&bt=platforms:27&name=Compact Disc Interactive&page=1">Compact Disc Interactive</a></li>
            <li><a href="./results?qt=browse&bt=platforms:25&name=Neo Geo&page=1">Neo Geo</a></li>
            <li><a href="./results?qt=browse&bt=platforms:3&name=Game Boy / Game Boy Pocket&page=1">Game Boy / Game Boy Pocket</a></li>
            <li><a href="./results?qt=browse&bt=platforms:7&name=Atari Lynx&page=1">Atari Lynx</a></li>
            <li><a href="./results?qt=browse&bt=platforms:8&name=Sega Game Gear&page=1">Sega Game Gear</a></li>            
          </ul>
        </div>
        <div class="large-4 columns">
          <h4>3rd Generation</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:21&name=Nintendo Entertainment System&page=1">Nintendo Entertainment System</a></li>
            <li><a href="./results?qt=browse&bt=platforms:70&name=Atari 7800&page=1">Atari 7800</a></li>
            <li><a href="./results?qt=browse&bt=platforms:141&name=Sega SG-1000&page=1">Sega SG-1000</a></li>
            <li><a href="./results?qt=browse&bt=platforms:32&name=Sega Master System&page=1">Sega Master System</a></li>
          </ul>
        </div>  
        <div class="large-4 columns">
          <h4>Other</h4>
          <ul>
            <li><a href="./results?qt=browse&bt=platforms:94&name=PC&page=1">PC</a></li>
            <li><a href="./results?qt=browse&bt=platforms:17&name=Mac&page=1">Mac</a></li>
          </ul>
        </div>          
      </div>      
    </div>
    <!-- END Platform Section -->

    <!-- Search Section -->
    <div id="searchbox" style="display:none">
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
    </div>
    <!-- Seach Genre Section -->

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

    <script src="foundation-5.1.1/js/vendor/jquery.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.orbit.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.tooltip.js"></script>
    <script src="foundation-5.1.1/js/foundation/foundation.reveal.js"></script>
    <script>
      $(document).foundation();
  </script>
  <script type="text/javascript">
  	var sections = ["platforms", "genres", "searchbox"];
    function displaySection(sectName) {
	  for (var i = 0; i < sections.length; i++) {
		  if (sections[i] != sectName) {
		    document.getElementById(sections[i]).style.display = 'none';
		  }
		  else {
		    document.getElementById(sections[i]).style.display = 'inline';
		  }
		}
    }
  </script>
  
</body>
</html>