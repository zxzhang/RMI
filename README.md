RMI
===

CMU 15-440 lab2: Design and Implementation of a RMI Facility for Java
===

Authors:

San-Chuan Hung(sanchuah@andrew.cmu.edu)

Zhengxiong Zhang(zhengxiz@andrew.cmu.edu)

===

#How to compile the code?

    type “make compile” 

#How to set up servers and clients?
    ##Launch Simple Registry Server
    type “make registry”

    ## Launch RMI Server
    type “make rmi_server”

    ## Run client
    type “make client”

#How to test the code?

    You need to open three terminal screens to test the code. In the first screen, type ”make registry”, which will launch Registry Server on port 8888. In the second screen, type “make rmi_server”, which will launch a RMI Server on port 10000. Last, type “make client,” which will invoke ZipCodeServer.initialise(), ZipCodeServer.find(), ZipCodeServer.findAll(), and ZipCodeServer.printAll() and show the results.
