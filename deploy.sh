#!/bin/bash

scp -i /home/anton/safe/giskeys.pem -r /home/anton/NetBeansProjects/GISParking/dist/* ubuntu@ec2-23-23-34-187.compute-1.amazonaws.com:/home/ubuntu/glassfish3/glassfish/domains/domain1/autodeploy/


