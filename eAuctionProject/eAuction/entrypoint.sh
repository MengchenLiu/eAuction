#!/bin/sh
cd "/git/mpcs51221_eauction_teamb/eAuctionProject/eAuction/"
git pull https://eauctionteamb:teamb1234@bitbucket.org/dsimeon/mpcs51221_eauction_teamb.git
mvn clean
mvn install
java -jar "/git/mpcs51221_eauction_teamb/eAuctionProject/eAuction/target/eAuction-0.0.1-SNAPSHOT.jar"