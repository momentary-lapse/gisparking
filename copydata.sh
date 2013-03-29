#!/bin/bash

scp -i /home/anton/safe/giskeys.pem -r /home/anton/glassfish-3.1.2.2/javadb/.netbeans-derby/* ubuntu@ec2-23-23-34-187.compute-1.amazonaws.com:/home/ubuntu/glassfish3/glassfish/databases/
scp -i /home/anton/safe/giskeys.pem -r /home/anton/glassfish-3.1.2.2/glassfish/domains/images/* ubuntu@ec2-23-23-34-187.compute-1.amazonaws.com:/home/ubuntu/glassfish3/glassfish/domains/domain1/images/

