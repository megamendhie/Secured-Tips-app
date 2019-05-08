# Secured Tips App

## Summary

Secured Tips app is a sports-focused android application with news feed, predictions, and discussion forums. User can share their opinion or predictions on a particular game and also view, like and reply to posts from other members. 

The app uses Firebase for authentication, database, storage, and push notification.

Available on Google Play Store with over 7000 users. Check it out.

[![Download](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img9.png)](https://play.google.com/store/apps/details?id=secured.tips)


## User Registration

New members sign up with their email and password. Firebase Authentication handles the user authentication and the information is stored on Firebase Realtime Database with security rules updated for privacy. ![Registration](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img5.png)

## Profile Management

Users have personal profile where they can view and update their personal information at anytime. This includes name, username, phone number, or profile picture. This information is saved offline if the device is not connected to the internet, and later synced when the device comes online.

Implemented elements and features:
-   Pick image from gallery or take photo
-   TextView for user data
-   Firebase storage for image storage.
-   Realtime Database for text data![Profile](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img7.png)

## Chats and Discussion

The app has six discussion forums where members interact daily. Registered members can make post and also like or reply to post from other members. 

Implemented elements and features:
-   ListView and Adapter to display posts
-   Like button animation and sound
-   Reply to post
-   Collapse/expand text in the long post
-   Real time update of posts
-   Verification of won predictions

![room1](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img2.png)

![room2](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img6.png)

## News Update

The app displays top 20 most recent sports news using [news API](https://newsapi.org/).

Implemented elements and features:
-   Background task using AsyncTask
-   Data retrieval with REST api
-   RecyclerView and RecyclerViewAdapter to display news content
-   Picasso Library to display images

![room2](https://raw.githubusercontent.com/megamendhie/Secured-Tips-app/media/img1.png)


We do not support Secured Tips app anymore, but if you need a similar app, do get in touch.