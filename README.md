**Note**. You will find detailed information in this wiki: https://github.com/Wi5/odin-wi5/wiki

Odin Controller
===============

The Odin Controller is implemented as an application on top of the Floodlight OpenFlow controller. It uses an inband control channel to invoke commands on the agents. In its current form, Odin commands can add and remove LVAPs and query for statistics. The master, through Floodlight, uses the OpenFlow protocol to update forwarding tables on the AP and switches. Odin applications (i.e. Mobility Manager and Load Balancer) execute as a thread on top of the Odin Controller. Applications can view the statistics exposed by the Controller in a key-value format.

References
----------
 
Floodlight
An Apache licensed, Java based OpenFlow controller

Floodlight is a Java based OpenFlow controller originally written by David Erickson at Stanford
University. It is available under the Apache 2.0 license.

For documentation, forums, issue tracking and more visit:

http://www.openflowhub.org/display/Floodlight/Floodlight+Home

Installation
------------

Clone the repo

`git clone https://github.com/Wi5/odin-wi5-controller`

To build the Odin Controller, run the following:

  `sudo apt install default-jdk`
  
  `cd odin-wi5-controller`
  
  `ant`
  
  After you configure the poolFile, run the controller as follows:
  
  `~/odin-controller# java -jar ./target/floodlight.jar -cf ./src/main/resources/floodlightdefault.properties`
