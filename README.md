# FIT3077 Assignment 2 - Part 2 

## Students 
* Name: David San
* Email : dsan46@student.monash.edu

* Name: Wilbert Ongosari
* Email : wong9@student.monash.edu

## Design Decisions

In order to create a scenario for all the requirements needed, several classes were made to handle it. The aim of this application is to make a website app that could help clients access quote data from SOAP to gather information about Stock.
    
* Starting from the first requirement, the "GUI" class is made to cover all the needed information attributes such as title, inputbox, button, display box and the main window. The GUI we made will be a composite of "InputBox" and "Button" class as the two of these class is a part of the GUI with 1 to 1 relation. The GUI is made by running the "main" function class when the Client first started inputting a quote. The "GUI" class knows about the "main" class but there is no relationship that persists between them. The "main" function will also be run automatically by the "timer" class that will be explained further down.
    
* Next we are told that the user can choose to have monitors running for more than one stock simultaneously and could stop monitoring at anytime. Thus we made 3 new class function "Search", "Add" and "Delete" that inherits from the "Button" class. They are seperated because they have different attributes and functions.
    
* Continuing onto the main part, we made a "StockMonitor" class that is responsible for the "Button" class. This class inherits from "Monitor" and will be the class that keeps the currentState data of the stock and will be displayed on the "Monitor" interface abstract class. As there will be possibility of having more than one monitor, "MonitorCollection" class is made to store and aggregate all monitors. "StockMonitor" will get its data from "StockData" class which has all the stock information gotten from the server.
    
* Another requirement is that our program would need to check the StockQuoteWS web service no more than once every five minutes and update the monitor(s) as needed. To do this, we made a "timer" class which gives a countdown of 5 min before fetching new data from the web and refreshing the current data being shown on the monitor by running the "main" function again. So when the "main" program starts, the "timer" will also be run. After each 5 min, "Timer" would need to access the service through "Soap" in order to refresh the stock value. Then the retreived data will be passed to the "StockData" to be displayed later on the "Monitor".
    
* Lastly, the "Soap" class is made which is a class for communicating between the client and the server. This "Soap" class inherits from the "WebService". In order for the client to gain access to the stock information on the web service, the client would need to getAccess from "Soap".
    
The overall design is using the MVC(Model-View-Controller) pattern as the design seperates different aspects in order to have an ease of testing because MVC seperates the concerns of storing, displaying, and updating data into three components that can be tested individually.

Extension in Stage 2:

* The classes that inherits from button (Search, Add and Delete) were removed because the button class is sufficient enough encase them.

* As we changed our pattern to an observer pattern. The "IObservable" class was made to notify another new interface called "IObserver" about the changes to update. "IObservable" has 0 to many Observers. It is the class that adds or removes Observers and notify them to update when some data has changed

* As there is a new requirement now to have two types of monitor for the user to choose, we made the "MonitorDisplays" amd "GraphDisplays" classes that inherits from the IObserver class. These two classes were made to differentiate the two types of displays. 

* To summarise, whenever the application starts or when a data change is needed. The "StockMonitor" will pull data from the "StockData" and then "StockMonitor" will push and notify the "IObserver" to update through the update() method. In simple terms, "StockMonitor" will keep asking the "StockData" whether the data has changed. If it has, it will pull the data and push it to the "IObserver".

## Using the System
* Open your Java IDE 
* When opening the file select stockquoteapp (This ensures that the IDE recognises the main as it doesn't if you open the whole Assignment 2 Folder)
* Run the main file 
* A Gui should pop up asking to enter in ASX Stock Code  
* Enter in for example NAB 
* Select whether you what to use the StockQuoteWS Service to get Information 
* Or the StockQuoteTimeLapse Service to get Information
* If you want to get a graph you have to select the StockQuoteTimeLapse WebService and Select Display Type as Graph instead of Information
* You can now add multiple stock to your table which will be updated in real time 
* Also if you want to remove the last one click remove last item or to clear all select remove all
* To close a Graph Monitor select Stop Monitoring 

## What was Used
* Java 
* JavaSwing 
* Jcommon-1.0.23
* JfreeChart-1.0.19

## What was it tested on 
* Intellij IDE 
* Java version 8 or 1.8 JDK 




