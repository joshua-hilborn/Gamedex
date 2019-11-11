# Gamedex
Discover new upcoming games.  Watch trailers and gameplay videos.  Create a wishlist.  Add release dates to your calendar.

## Project Setup
IGDB API SETUP:
1. Sign up for an account and obtain an IGDB API Key from www.igdb.com
2. Add the following line to gradle.properties: (use the key you get in step 1)
   ```
   IgdbApiKey="YOUR KEY HERE"
   ```
3. Add the following line to module:app build.gradle file, in the defaultConfig:
   ```
   buildConfigField('String', 'IGDB_API_KEY', IgdbApiKey)
   ```

## Intended User
Gamers, Game Collectors, Shoppers

## Features
- Saves list of Favorites/Wishlist selected by user
- Tracks selected release dates on google calendar
- View YouTube videos of trailers and gameplay
- Search by name
- Discover upcoming and newly released games
- All tabs can be filtered by platform in options menu

## UI
### Main Activity
![Main Activity](https://github.com/joshua-hilborn/Gamedex/blob/master/img/Gamedex%20Mocks-MainActivity.png)

The MainActivity contains three tabs.  Each tab contains a RecyclerView of cards.  The Admob banner displays at all times.  The options menu may contain filters for the list, if time allows.
- Discover tab opens by default, and populates the recyclerview with 50 upcoming games.
  - Discover tab will have a refresh option that triggers an AsyncTask to update the list
- Search tab populates the recyclerview with 50 search results if the search function is used.
  - Search button will trigger an AsyncTask to fetch new results
- Wishlist tab populates the recyclerview with the saved favorites, from ROOM storage.


![Main Activity - Search](https://github.com/joshua-hilborn/Gamedex/blob/master/img/Gamedex%20Mocks-MainActivity%20-%20Search.png)

The Search tab will differ from the others with the addition of the search bar at the top.

### Detail Activity
![Detail Activity](https://github.com/joshua-hilborn/Gamedex/blob/master/img/Gamedex%20Mocks-DetailActivity.png)

The DetailActivity contains all the information for a single game.  
- Buttons at the top to add release date to the calendar and add the game to wishlist.
- The menu to the right will contain a “Send to Widget” option
- Back button returns to the previous active tab on the MainActivity.  
- Clicking on a Gameplay Trailer/Video will launch and intent to play youtube video in a player window, or in browser if that fails.  
- Summary and Trailer lists are scrollable. 


### Widget
![Widget](https://github.com/joshua-hilborn/Gamedex/blob/master/img/Gamedex%20Mocks-Widget.png)

The Widget will be a simple 2x1 that displays text only.  It will display the number of days until a selected game is released.

## Libraries / Services Used
- Picasso 2.71828: Image loading, Placeholders, OnError 
- OkHttp 4.2.1:  Handle http connections to the IGDB API
- Moshi 1.8.0:  JSON deserialization from IGDB API

- Admob 18.3.0: Banner Ad



