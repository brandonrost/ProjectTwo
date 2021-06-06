# TasteBass

## Project Description

TasteBass is an application that allows users to experience music in a whole new light. Users are able to create, track, and collect a list of artists, songs, or albums that they listen to regularly and TasteBass will automatically refer the User to music that is similar to their taste. Beyond TasteBass' main utility, Users are also able to browse
other User's 'TasteBass' playlists to expand their horizon even further.

## Technologies Used

* Java - version 1.8
* Tomcat - version 9.0.46
* LogBack/SLF4J - version 1.2.3
* JacksonDataBind - version 2.12.2
* Java Servlet API - version 4.0.1
* Spring ORM - version 5.3.6
* Spring MVC - version 5.3.6
* Spring Test - version 5.3.6
* Mockito - version 3.9
* Hibernate - version 5.4.3
* Lombok - version 1.18.20
* JUnit 5/Jupiter - version 4.13.2
* MariaDB/JDBC - version 2.7.2
* Angular 2
* Maven

* Deployed on AWS: EC2, RDS
* Jenkins Pipeline utilized
* Sonar Cloud utilized for code coverage

## Working Environment
* Spring Tool Suites - version 4.10 (for creating/editing Java-based backend application)
* Visual Studio Code - version 1.55 (for creating/editing HTML, CSS, and JavaScript files)
* Postman - version 8.5.1 (for testing HTTP requests sent to RESTful API on the backend)
* DBWeaver - version 21.0.5 (for viewing database)
* GitHub/GitBash - used for version-controll
* SYSTEM: Windows 10

## Features

Two types of users:
1. Guest
2. General Users

* Guests are able to search for tracks.
* Guests are able to register to be a Registered User
* Guests are not able to save tracks
* Guests are not able to generate a list of recommended tracks
* General Users are able to log in
* General Users are able to log out
* General Users are able to save credentials
* General Users are able to edit their profile
* General Users are able to search for tracks
* General Users are able to save tracks that have been searched
* General Users are able to generate a list of recommended tracks based on the tracks they have saved to their profile
* General Users are able to view all the tracks they have saved


To-do list:
* Ability to remove tracks from the User's list of saved tracks
* Ability to view other User's "music list"
* Ability to filter search results based on parameters other than "track". I.e. artist, album, genere
* Register using Facebook, Gmail, or even Spotify

## Getting Started

To utilize this application, the user can navigate to our website that is being hosted on an EC2 instance. Below are a list of endpoints that will allow you to navigate through our site. If you wish, you can also utilize the navigation bar on our websites home page to circumnavigate our web-application. 

#### Home
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/home

#### Register
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/register

#### Login (Login to application)
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/loginUser

#### My Bucket (Users List - must be logged in)
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/user

#### Search (Search Track)
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/admin

#### Profile (Navigate to a logged-in user's profile)
 * http://ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/profile


### PostMan Requests (Backend API) - BASE URL: ec2-3-22-185-67.us-east-2.compute.amazonaws.com:8080/TasteBass/
* POST  /addUser  :  register a new user
```
{
    "firstName":"",
    "lastName":"",
    "username":"",
    "password":"",
    "email":""
}
```
* POST /login  :  login to application (server-side)
```
{
    "username":"",
    "password":""
}
```
* POST /logout  :  logout of the application (server-side) 
* GET /searchTrack/track/{trackName}  :  search for a track 
* POST /addTrack  :  add track to User list
```
{
    "music_name":"Trust",
    "music_artist": "Brent Faiyaz",
    "spotify_id":"0oufSLnKQDoBFX5mgkDCgR",
    "music_pic":"https://i.scdn.co/image/ab67616d0000b2731cd6e413266e97c0b6f24ed3",
    "music_type":"track"
}
```
* GET /track/getRecommended  :  get list of tracks based on (logged-in) user's music-list
* GET /tracks  :  gets a list of the user's tracks they have saved to their music-list

## Angular Front-End Application
* https://github.com/cdsutton/project-2-frontend

## User Stories
User Types:
   - General User
   - Guest

- User Story 1
   - As a guest
   - I want to be able to be able to register as a new user (General User)
   - So that I can add songs/artists to my specific profile and track them accordingly
   - Acceptance Criteria
    1. Able to submit personal details in order to create an account with corresponding input.
    2. Able to access the created account with appropriate login criteria.
    3. Not able to access account when wrong email-password combination is submitted.
 
- User Story 2
   - As a guest
   - I want to be able to browse the site 
   - So that I can better understand the concept of the site without registering as a new user
   - Acceptance Criteria
    1. Able to search for specific artist/song
    2. Able to access the home page
    3. Able to access login/registration page

- User Story 3
   - As a general user
   - I want to be able to submit a list of the music I listen to
   - So that I can find new music to listen through what the site recommends
   - Acceptance Criteria
    1. Able to submit a certain number of songs
    2. Even if a certain song cannot be found in the database, there is a way to add tags to still contribute to the recommendation algorithm
    3. Receive a number of recommended songs based on songs that were submitted that accurately captures the user’s music taste. 

## Stretch Goals

- User Story 4
   - As a guest
   - I want to be able to register using facebook/gmail
   - So that I can share my 'TasteBass' information with my peers
   - Acceptance Criteria
    1. Upon clicking register as new user, I should be able to register using Facebook/Gmail
    2. When choosing option, I should then be prompted to sign in using corresponding login details.
    3. After providing correct email-password combination, my Facebook/Gmail account should be linked to my 'TasteBass' account
    4. In the future, when loggin into 'TasteBass', I should be able to use my Facebook/Gmail email and password.

- User Story 5
    - As a general user
    - I want to be able to view other users’ lists
    - So that I can either find users and songs with a similar music taste or even discover new music tastes
    - Acceptance Criteria
     1. Should be able to search based by songs with tags marking properties such as genre, mood, etc.
     2. Should be able to view other users’ list in an organized format
     3. Should be able to connect with other users through a messaging/social media system

## Contributors

- Brandon Rost 
- Joseph Peterson
- Cody Sutton

