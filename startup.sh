#!/bin/sh

cd /home/ec2-user
aws s3 cp s3://bucket-shoppet-api/shoppet-0.0.1-SNAPSHOT.jar . 
java -jar shoppet-0.0.1-SNAPSHOT.jar
