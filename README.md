###Nutcracker
A little personal project for parsing monthly salaries from a csv file and exposes them on a simple website.

The data is read from \src\main\resources\HourList201403.csv and test data is read from \src\test\resources\testhours.csv

Implemented using Spring Boot Web framework and Thymeleaf as the templating engine for frontend.
The front end is a simple website composed mostly of simple tables that expose the data, using Bootstrap.

As this handles currencies, I've used BigDecimal's to keep the currencies as accurate as possible, could also propabbly have used integer/long to represent cents.

###Currency calculations
Logic is that overtime compensation increases every 2 hours, tho this can be changed.
Overtime goes through 1.25, 1.5, 2 multipliers to regular salary..
Evening compensation is $1.15.
Regular salary is $3.75.

The following variables are stored in MyUtilities.java all tho they could be externalized to a settings file.
You can modify any of these to affect the application logic.
```
public static final double REGULAR_WAGE = 3.75;
public static final double EVENING_COMPENSATION = 1.15;
public static final  int OVERTIME_INCREMENT = 2;
public static final  double[] OVERTIME_COMPENSATIONS = {1.25, 1.5, 2};
```
###Additional libraries used
- jackson-datatype-jsr310
    Datatype module to make Jackson (http://jackson.codehaus.org) recognize Java 8 Date & Time API data types (JSR-310).
    Enabled jackson to convert Java 8 Date & Time to json
- apache commons-io
    for easier file handling
- webjars for bootstrap and jquery

###Running
You can build or run the web application with the following commands in the projects root directory

```
gradlew build
gradlew bootrun
```

Or by running NutcrackerApplication.java from your favorite ide.

after which you can find the application running at http://localhost:8080/

###Paths
- /workshifts
    Displays daily Workshifts for each person
- /persons
    Displays all persons
- /workdays
    Displays daily work hours for each person
- /salaries
    Displays daily salaries for each person
- /salaries/monthly
    Displays daily salaries for each person
    
Each path also has sub path of /raw that just dumps the json data to your browser.


###Tests
There are some tests for checking that the data got parsed and calculated correctly, you can run these by.
```
gradlew test
```

Or if possible, directly run \src\test\java\com\marantle\nutcracker\TestRunner.java for the same tests.
These test the included testhours.csv by comparing the parsed data to handmade calculations for multiple persons.

There is also \src\test\java\com\marantle\nutcracker\RunSimpleTest.java which just outputs the monthly wages to console

Also included is a data comparisons.xlsx for checking the data with manual calculations compared to the output got from the application.
###Packages
- /bootstrap
    Intializes the data when running the web application      
- /configuration
    Generic spring configuration 
- /controller
    Web controllers
- /model
    WorkDay, Salary, WorkShift and Person java classes
- /parser
    The parser that maps the csv data to the respective models
- /repository
    The DataHolder that has as static sets, list of all the parsed data
    and NutRepo that is used to access this data from elsewhere in the app
- /util
    Contaigns time formats, value converts and other utilitary methods and fields used in multiple places
    
