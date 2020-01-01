# Pet Finder Application
Author: Carlos Cervantes

[Database Selection Service](http://petfinderapp.x10host.com/pettable.php)

[Database Insertion Service](http://petfinderapp.x10host.com/petinsert.php)

**Find a Pet Tab:**
- Gets data JSON Array from the x10hosting server.
- Can get pictures from the server using Picasso Library
- The web service for generating a JSON array from MySQL database was built using PHP
- Needs a way for the list to be refreshed/updated without restarting the activity (Currently, the app restarts entirely to refresh the list. Very inefficient)
- **Needs** a comment section.
- **Needs** to hide a post or move it to a different section when the pet gets adopted.


**Post a Pet Tab:**
- The app can successfully upload new pets for adoption.
- The web service for uploading MySQL database was built using PHP 
- It now correctly uploads image to server and posts data to server database.
- **Needs** to implement feature to notify all users who have the app installed when a pet with medical urgency is found.
- **Needs** to display a progress bar while the publication finishes uploading.

[This is the root of the server](http://petfinderapp.x10host.com/). In the folder `pet_images`,
the subdirectories under this directory will each be named the same as the `id` of the pet adoption publication. Inside those subdirectories,
an image named `main.jpg` will be found. This is the image that will be uploaded every time a user creates a new pet adoption publication using
the image taken from the camera.

**Chat Tab:**
- **Partially done**. The chat service is now active using Google's Firebase. However, still need to add functionality to have 
multiple users with different names, profile pictures, and additional contact info. Messages update in real time.
- **Needs** to have different conversation threads instead of it being a giant group chat.
- Followers shall be able to request images and videos at any time they want.

**Startup Screen:**
- A Startup layout XML has been created. Need to add Java code/activities to make the layouts perform actions in the back end.

**Login Screen**
- The login screen has been created.
- Although the Java code for the backend activity is also created, the screen does not yet show upon opening the app.
- Need to create DB tables in the server to save user credentials.

**Create Account Screen**
- No layout has been created yet.
- The activity shall upload user's data to the server.