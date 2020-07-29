# Upbite
An android app to make deciding where to eat out easier - Upbite helps you browse restaurants near you, add them to your favorites, message your friends about and vote on a place to eat out! 

The app allows users to sign up and maintain their accounts, search for places nearby with the help of the Yelp API, and message friends registered on Upbite. The users can also add their favorite restaurants to a personalized favorites list. 

## Features 

Upbite provides the user with multiple features such as browsing restaurants or cafe by location, saving their favorite places, and messaging their friends all about it. These are supported through the following features: 
  - Registration and Login of new and existing users supported by Firebase Authentication.
  - Search results for queries relating to local restaurants, cafes, etc. via calls made to the Yelp API via HTTP client for Android, Retrofit. 
  - Categorizing places by favorites and saving them for each user in the real-time database by Firebase, Firestore. 
  - Messages that can be sent to registered users on Upbite - these conversations are stored uniquely for each user in the Firebase Storage. 
  
<p align="center">
  <img src="UI/01splashScreen.png" width="256" height="495">
  <img src="UI/02SignUp.png" width="256" height="495">
  <img src="UI/03Home.png" width="256" height="495">
</p>

<p align="center">
  <img src="UI/05Messages.png" width="256" height="495">
  <img src="UI/06Chat.png" width="256" height="495">
  <img src="UI/07Account.png" width="256" height="495">
</p>

## Repository Structure

```
├── README.md                                 : ReadMe description of the project
│
└── ShoppingList/app                          : Project files
    ├── src/main                    
    │   ├── java/.../shoppinglist       
    │   │   ├── touch                         : Touch helper callback component classes - allows drag and drop reordering message/favorite items and swipe to delete
    │   │   ├── fragments                     : Contains all the Fragment that are used to replace stand-in Fragment in HomeActivity, includes MessagesFragment etc.
    │   │   ├── data                          : Contains Data classes for multiple attributes of the app e.g. UserItem, ChatMessageItems, YelpSearchResult
    │   │   ├── network                       : Yelp API Interface that serves as intermediate for the Yelp API and makes calls via Retrofit when prompted
    │   │   ├── HomeScreen.kt                 : Dialog fragment for adding and editing items
    │   │   ├── LoginActivity.kt              : User login screen for exdisting users
    |   |   ├── RegisterActivity.kt           : Register/Sign up screen for new users.
    │   │   └── SplashScreenActivity.kt       : Splash screen activity - Upbite logo splash
    │   │
    │   ├── res                               : Companion resource files for the project
    │   │   ├── anim                          : Animation xml file for the splash screen
    │   │   ├── drawable                      : XML files for icons/images etc.
    │   │   ├── layout                        : XML layout files for activities
    │   │   ├── mipmap-...                    : Launcher icon versions for different screen densities
    │   │   └── values                        : XML files for extracted strings and customized styles
    │   │
    │   └── AndroidManifest.xml               : manifest file for the application
    │
    └── build.gradle                          : gradle file for the app module
      

```

## Future Improvements 

The app is an ongoing project and is currently under development. The favorites and location specifying features for search results are currently incomplete and are expected to be launched with next version. 

### Contributors

Nukhbah Majid - sole contributor.

