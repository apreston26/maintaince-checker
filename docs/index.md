# Maintenance Checker

## Introduction

This app would be designed with the sole intent to keep your car up to date from everything to an oil change to getting new control arms. It would help remind you of even the simple things and help even the most cluttered person remember to take good care of their car. On top of this, it would also help locate the most reliable mechanics in your nearby area using the revolutionary technology of google maps. With this app we'll make sure your car and everyone else's can keep their car reliable and safe. 

For all of this to work the app would use a few key points of functionality which include, but could be changed or altered in the future,  making the user put in their location, so the Google Places API can make it a lot easier to see nearby mechanics. Once the user makes it to the first screen they would be presented with a blank screen until they use the button on the bottom of the screen. This button allows the user to make a card that shows what kind of maintenance was performed, when it was performed, and who performed it. Once the card was added it would turn green and gradually turn red signifying how long it has been since that maintenance was performed and when it should be taken into the shop. Then, if the user wanted to find mechanics near them, they would then click on the button above which takes them to the second screen that is very close to Google Maps in that it would show where to find to mechanics and look at reviews for each of them. The user would then add the name of the mechanic or mechanic shop that performed the maintenance in their cards.
 
When it came to making the app I grew most of my inspiration from buying my first car and how much work needed to be done and I wanted to find or make some way that was faster and would be easier to manage than simply trusting my memory. Hopefully, when I get everything implemented it not only is useful to me but other people that want reminders instead of waiting for something to break or trying to remember every little detail about their car.
 
So far, the app only has the first screen functioning and while the second screen exists it’s nearly an empty fragment and soon should be populated with a map of the nearby area around the user and reviews to go along with it. However, within the first screen the app currently has a functional way to add maintenance and mechanic names. 
 

## Intended users

* People who are always busy and have trouble remembering to maintain their car

    > As someone who is constantly busy and forgets some of the crucial aspects of maintaining a car and I want to have a reminder so that my car can always stay in its current, reliable state. 

* People who want to be sure they have the most reliable service 

    > As someone who has been ripped off by the mechanic before it's important to me that I am going to the most reliable place in town so that it does not happen again.

## Summary of Current State of the App

The app’s initial purpose had been to add the date in which the maintenance was completed, as well as the mechanics name that is responsible for completing the work. The app also would implement the Google Places API that would be used to find local mechanics and generate reviews for each mechanic. However, as production went on the app would not meet all of these goals and would only implement the first page of the app which is where the user saves the maintenance that was performed on their vehicle.

* Bugs 
   
    * No way to change a `No Preference` maintenance card once it has been added 
    
* Aesthetics that could be improved
   
   * Maintenance cards changing colors to help show the user when to bring the car into the shop
   
* Stretch Goals

   * Having a way to call the mechanic shops
 
   * Be able to have an option to schedule a time with the respective mechanic by clicking on them
   
   * Add a floating action button sub-menu that uses the [animation code](https://github.com/apreston26/maintence-checker/tree/master/app/src/main/res/anim)

## Design Documentation

[Wireframe diagram](wireframe.md)

[Entity Relationship Diagram](ERD.md)

[Data Definition Language (DDL)](sql/ddl.md)
    
## Technical Requirements

* Google maps 

    * This service will be used in order to locate nearby maintenance shops by showing the address and how far away it is from the phone's current location.
    
* Stetho 

* Minimum Android API 21 (lollipop)

    
* Safe permissions

    * Permission to the internet

* Dangerous permissions 
    
    * Permission to the user's location
    
        * At the moment if the user were to deny this permission the app would be fine. However, if at any point the Google Places API is implemented it would likely restrict access to see nearby mechanics and their respective reviews 
    
* Persistent data 

    * Maintenance types
    
    * Maintenance dates

## Implementation Code

To view the code directly please visit our implementation page [here](implementation.md)

## Build Instructions 

Cloning the repository 

* To clone or download this repository please visit the main github site [here](https://github.com/apreston26/maintence-checker) and find the green code button where there will be the option to `Close with SSH`. If you do not see this option there should instead be an option in the upper right of this dialog box that says `Use SSH`. Once you've located the SSH key there will be a clipboard next to it to copy. Now onto IntelliJ. 

Importing the project into Android Studio/Intellij IDEA

* Due to the lack of an API at the current state of the app all you have to do to import this project into your local IDE is go to new project and select project from version control.

Executing the build 

* Again because there is no API in the current state of the app to execute the build all you need is an emulator or hardware device with an Android API 21 or higher. 

## Basic User Instructions 

In this version of the app, the app's main function is log all the maintenance that was worked on and when it was worked on, which is added by clicking on the floating action button that has the tools on it. It also has a way to get to the second fragment despite having nothing in it by hitting the button in the bottom middle of the screen. There is a third button which has an add person image on it which will replace the standard `Get Mechanic` button.

## Copyrights & Licences 

For information please visit our notice page [here](notice.md). 