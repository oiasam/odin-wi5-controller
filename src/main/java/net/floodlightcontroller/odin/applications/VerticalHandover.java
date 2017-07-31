package net.floodlightcontroller.odin.applications;


import net.floodlightcontroller.odin.master.OdinApplication;
import net.floodlightcontroller.odin.master.OdinClient;
import net.floodlightcontroller.odin.master.IOdinAgent;
import net.floodlightcontroller.odin.master.Lvap;

import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VerticalHandover extends OdinApplication {
	
	protected static Logger log = LoggerFactory.getLogger(HandoverMultichannel.class);

	HashSet<OdinClient> clients;
	
	private final int INTERVAL = 60000; // time before running the application. This leaves you some time for starting the agents

	@Override
	public void run() {
		OdinClient currentClient;
		Lvap currentLvap;
				
		/* When the application runs, you need some time to start the agents */
		log.info("HandoverMultichannel: wait" + INTERVAL + " ms to initialize.");
		try {
			Thread.sleep(INTERVAL);
		} catch (InterruptedException e){
        		e.printStackTrace();
		}
		
		/*all the clients Odin has heared (even non-connected) */				
		clients = new HashSet<OdinClient>(getClients());
		
		Iterator it = clients.iterator();
	
		while(it.hasNext()){
			currentClient = (OdinClient) it.next();
			currentLvap = currentClient.getLvap();
			
			currentLvap.getAgent().sendDeauth(currentClient.getMacAddress(), currentLvap.getBssid(), currentLvap.getSsids());
			
		}
		
		
	}
	

}
