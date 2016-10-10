# Australian Twitter Analytics
This is a project which related to big data analysing, gathering twitters and processed them to uncover potential interesting information. The original project used nectar as the cloud server, you could use other cloud services such as AWS or GCE.

## Installation

Dependencies:
- [Stanford CoreNLP](http://stanfordnlp.github.io/CoreNLP/download.html)
Stanford CoreNLP now requires Java 8. If you do not have this installed you should first of all install Java 8. Probably the JDK, but the JRE will do if you are only going to be a user.
- [Twitter4J](http://twitter4j.org/en/index.html)
Twitter4J is an unofficial Java library for the Twitter API. With Twitter4J, you can easily integrate your Java application with the Twitter service. Twitter4J is an unofficial library.
- [CouchDB](http://couchdb.apache.org/)
Apache CouchDB™ lets you access your data where you need it by defining the Couch Replication Protocol that is implemented by a variety of projects and products that span every imaginable computing environment from globally distributed server-clusters, over mobile phones to web browsers.

Installing the project:

1. clone the repo
```
git clone https://github.com/Xingyuj/AustralianTwitterAnalytics.git
```
2. move into the repository directory
```
cd AustralianTwitterAnalytics
```
3. Maven install dependencies
```
mvn install
```
4. install CouchDB
(You can use HomeBrew to install CouchDB Or install it manually see to [CouchDB download](http://docs.couchdb.org/en/2.0.0/install/index.html))
```
brew install CouchDB
```

## Usage

1. Setting up CouchDB on a server or locally
2. Deploy project at a cloud server or locally
3. Write map-reduce functions that you may interested in.
The original map-reduce functions of this projects are:
```
"all": {
           "map": "function(doc) {emit( null, doc._id )}"
       },
       "count_profanity_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null' && doc.profanity == false) {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "count_by_statusID": {
           "map": "function(doc) { if (doc.statusID != 0) {emit(doc.statusID, doc._id)} }",
           "reduce": "_count"
       },
       "count_plain_emotion_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null' && doc.emotion == 2) {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "count_twitter_nember_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null') {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "find_by_statusID": {
           "map": "function(doc) { if (doc.statusID != 0) {emit(doc.statusID, doc._id)} }"
       },
       "count_politics_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null' && doc.politics == true) {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "count_positive_emotion_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null' && doc.emotion == 3) {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "count_negative_emotion_within_place": {
           "map": "function (doc) {if (doc.placeName != 'null' && doc.emotion == 1) {emit (doc.placeName, doc._id)}}",
           "reduce": "_count"
       },
       "find_recent": {
           "map": "function(doc) {if(doc.timestamp) emit(doc.timestamp, doc._id)}"
       }
```


## Documentation for libraries used
Server-side

- [Twitter4j core](https://mvnrepository.com/artifact/org.twitter4j/twitter4j-core)  A Java library for the Twitter API.
- [Twitter4j Stream](https://mvnrepository.com/artifact/org.twitter4j/twitter4j-stream)  A Java library for the Twitter API.
- [Twitter4j Media Support ](https://mvnrepository.com/artifact/org.twitter4j/twitter4j-media-support/4.0.4)Twitter4J optional component adds multimedia support.

Client-side

- [jquery](http://jquery.com/) fast, small, and feature-rich javascript library that makes DOM-traversal easy
- [Bootstrap](http://getbootstrap.com/) as a good-looking CSS framework
- [jbuilder](https://github.com/rails/jbuilder) Build JSON APIs with ease.

All other frameworks used are either part of the above, dependencies of the above or perform a function to trivial to mention.
