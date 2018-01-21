#!/bin/sh
cd "/git/mpcs51221_eauction_teamb/eAuctionProject/eAuctionLogging/"
git pull https://eauctionteamb:teamb1234@bitbucket.org/dsimeon/mpcs51221_eauction_teamb.git
mvn clean install
java -jar "/git/mpcs51221_eauction_teamb/eAuctionProject/eAuctionLogging/target/eAuctionLogging-0.0.1-SNAPSHOT.jar"