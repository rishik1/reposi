[
  adopted from http://www.cs.rochester.edu/u/www/courses/171/Fall-03/files/readme.txt
  by Deger Cenk Erdil
  for CS654 Distributed Systems
  This is a template README file about how you should form your own README file.
  In general,
        you should remove anything in between square brackets (i.e. [..]), and
        you should replace anything in between <> with a value.
]

CS542 Design Patterns
Spring 2014
PROJECT 3 

Due Date: Thursday, March 27, 2014
Submission Date:  Thursday, March 27, 2014
Grace Period Used This Project: 0 Days
Grace Period Remaining: 0 Days

Author(s): Aashay Waghmare
e-mail(s): awaghma11@binghamton.edu
Author(s): Rishikesh Khire
e-mail(s):rkhire1@binghamton.edu

PURPOSE:

[
Use Design pattern to simulate taskmanager for windows.
]

PERCENT COMPLETE:

[
  I believe I have completed 100% of this project.
]

PARTS THAT ARE NOT COMPLETE:

[

]

BUGS:

[
 None.
]

FILES:

[
  Included with this project are 22 files:
src/
src/taskmanager/
src/taskmanager/display/
src/taskmanager/driver/
src/taskmanager/filters/
src/taskmanager/util/
src/taskmanager/worker/
build.xml
src/taskmanager/display/Display.java
src/taskmanager/driver/Driver.java
src/taskmanager/filters/Fileter.java
src/taskmanager/filters/PerforamanceFilter.java
src/taskmanager/filters/ProcessFilter.java
src/taskmanager/filters/UserFilter.java
src/taskmanager/util/FReader.java
src/taskmanager/worker/I_Observer.java
src/taskmanager/worker/I_Subject.java
src/taskmanager/worker/Performace_Observer.java
src/taskmanager/worker/Performance_tab.java
src/taskmanager/worker/Process_Observer.java
src/taskmanager/worker/Process_tab.java
src/taskmanager/worker/Subject.java
src/taskmanager/worker/User_Observer.java
src/taskmanager/worker/User_tab.java 
]

SAMPLE OUTPUT:


remote12:~/Assign3/Aashay_Waghmare_assign3> ant -Darg0=read.txt -Darg1=1 run
Buildfile: /import/linux/home/awaghma1/Assign3/Aashay_Waghmare_assign3/build.xml

jar:
run:
     [java]  Result is:
     [java] Process_tab [processname=explorer, cpu_vlaue=45, memory=256, description=foros, User_Name=aashay]
     [java] Performance_tab [currentMemUsage=25, currentCpuUsage=78, totalPhysicalMemory=10000, totalCached=100]
     [java] Performance_tab [currentMemUsage=25, currentCpuUsage=78, totalPhysicalMemory=10000, totalCached=100]
     [java] Process_tab [processname=explorer, cpu_vlaue=44, memory=250, description=foros, User_Name=rishi]
     [java] Performance_tab [currentMemUsage=255, currentCpuUsage=178, totalPhysicalMemory=10000, totalCached=100]

BUILD SUCCESSFUL
Total time: 1 second
remote12:~/Assign3/Aashay_Waghmare_assign3> ant -Darg0=read.txt -Darg1=4 run
Buildfile: /import/linux/home/awaghma1/Assign3/Aashay_Waghmare_assign3/build.xml

jar:

run:
     [java] An observer Registered 
     [java] An observer Registered 
     [java] An observer Registered 

BUILD SUCCESSFUL
Total time: 1 second

TO COMPILE:

[
  Please extract the tar file, go to the directory Aashay_Waghmare_assign3 and do-> ant compile
]

TO RUN:

[
  Please go the the extracted directory
  run as: ant -Darg0=read.txt -Darg1=4 run 
]

EXTRA CREDIT:

[
  N/A
]


BIBLIOGRAPHY:

This serves as evidence that we are in no way intending Academic Dishonesty.
[
http://www.youtube.com/watch?v=wiQdrH2YpT4
]

ACKNOWLEDGEMENT:

[
]
