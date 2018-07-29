food-truck-finder
-----------------

This is a Java-based terminal application that displays currently open food trucks in the San Francisco area. To get the raw data, it uses the [mobile food schedule api](https://dev.socrata.com/foundry/data.sfgov.org/bbb8-hzi6). Intended to behave as a terminal application, it relies on [lanterna](https://github.com/mabe02/lanterna) for a text-only user experience.


Per the requirements of the project, there are no tests for this application. However, it is written to be testable.

## Running the App
Clone the project, build with Maven, execute FoodTruckFinder.main() from your IDE of choice

-- OR --

Grab a pre-build jar file from the latest release in Github and run command

    java -jar <jarFileName>.jar

## Using the App

Use the right and left arrow keys to move between pages
Press enter/return to exit the application


*note*: Kicking off the app from a terminal may result in lost focus. alt-tab or click in to resume paging if that happens.


## Building a new jar

    mvn clean package shade:shade
