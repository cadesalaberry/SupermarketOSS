# SupermarketOSS


The Supermarket Operation Support System (SOSS) is an information system built to support the counter activities in a supermarket. The system must also support the backend activities, such as statistics on the sales performed by the employees and on products bought by customers.

## Setup

This application requires a database application in order to run.

The application is pre-packaged to work with the open source database
Apache Derby project DBMS version 10.0.2.1
See:	http://incubator.apache.org/derby/
Please read the LICENSE.txt file.

========================================================

In order to run and test the program you have to setup the database:

 - create the "SuperMarket" db; 
    e.g. from a console prompt in the project folder
 
 	java -cp derby.jar;derbytools.jar org.apache.derby.tools.ij setupDerby.sql
