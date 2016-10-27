# Teacher Tone Analyzer

Three ways to deploy this application:

Option 1: Click the button below
[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy)
================================================================================

Option 2: Download the source, compile using ant and push the war to Bluemix
```
git clone https://github.com/IBM-Bluemix/tone-analysis.git
cd talent-manager
ant
cd output
cf login -a https://api.ng.bluemix.net
cf create-service cloudantToneAnalysisDB Shared tone-analysis-db
cf create-service personality_insights tiered personality-insights-talent-manager
cf push aUniqueAppName
```

Replace aUniqueAppName with the name of your app (ex. talent-manager123)

Option 3: Import and deploy to Bluemix using [Eclipse for Java EE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr1) with the [Bluemix plugin](https://marketplace.eclipse.org/content/ibm-eclipse-tools-bluemix).


#Architecture Diagram
![alt tag](https://raw.github.com/IBM-Bluemix/talent-manager/master/talent-manager-architecutre-diagram.png)


# About
## Meet Ivy
* She's a talent manager at a growing tech startup. 
* She's having trouble finding the right candidate based on:
..* technical skills
..* personal compatibility


# Technologies
## Watson APIs
Tone Analyzer API
* Retrieve all emotional and behavior tones

## Cloudant NoSQL Database
* Store personality profiles

## Bluemix
* [Liberty for Java Profile](https://ace.ng.bluemix.net/#/store/cloudOEPaneId=store&appTemplateGuid=javawebstarter)

## Other
* Angular.js
* external file manager for reading text files

# Pre-req's for local development
* [Install the Eclipse EE](https://eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplersr2).
* [Install Java 1.7 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).
* [Install the cf command-line tool](https://www.ng.bluemix.net/docs/#starters/install_cli.html).
* [Sign up for an IBM Bluemix account](http://bluemix.net).

