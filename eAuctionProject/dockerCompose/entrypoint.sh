#!/bin/sh
service mysql start
cd "/git/mpcs51221_eauction_teamb/eAuctionProject/$1/"
git pull https://eauctionteamb:teamb1234@bitbucket.org/dsimeon/mpcs51221_eauction_teamb.git
mysql --user=root --password=password -t < db_init.sql
mvn clean install
java -jar "/git/mpcs51221_eauction_teamb/eAuctionProject/$1/target/$1-0.0.1-SNAPSHOT.jar"