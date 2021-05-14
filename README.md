# TasteBass
## TeamName: TasteBass

## Description 
TasteBass is an application that allows users to experience music in a whole new light. Users are able to create, track, and collect a list of artists, songs, or albums that they listen to regularly and TasteBass will automatically refer the User to music that is similar to their taste. Beyond TasteBass' main utility, Users are also able to browse
other User's 'TasteBass' playlists to expand their horizon even further. 

## Technologies Utilized
- Spring Framework
- Hibernate
- Logback
- JUnit
- Java
- Mockito
- Selenium
- Angular
- TasteDrive API

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