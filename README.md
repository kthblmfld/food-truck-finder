food-truck-finder
-----------------

This is a Java-based terminal application that displays currently open food trucks in the San Francisco area. To get the raw data, it uses the (mobile food schedule api)[https://dev.socrata.com/foundry/data.sfgov.org/bbb8-hzi6]. Intended to behave as a terminal application, it relies on (lanterna)[https://github.com/mabe02/lanterna] for a text-only user experience.


Per the requirements of the project, there are no tests for this application. However, it is written to be testable.

## Running the App


Use the right and left arrow keys to move between pages
Press enter/return to exit the application


## Building a new jar
mvn clean package shade:shade
