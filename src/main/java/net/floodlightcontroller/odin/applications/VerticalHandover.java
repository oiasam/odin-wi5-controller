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
	
	protected static Logger log = LoggerFactory.getLogger(VerticalHandover.class);

	HashSet<OdinClient> clients;
	
	private final int START_WAIT = 60000; // time before running the application. This leaves you some time for starting the agents
	private final int INTERVAL = 120000; //periodic interval to remove all clients

	@Override
	public void run() {
		OdinClient currentClient;
		//Lvap currentLvap;
		//IOdinAgent currentAgent;
				
		/* When the application runs, you need some time to start the agents */
		log.info("Wait " + START_WAIT + " ms to initialize.");
		try {
			Thread.sleep(START_WAIT);
		} catch (InterruptedException e){
        		e.printStackTrace();
		}
		while(1==1){
			log.info("Remove all clients.");
			/*all the clients Odin has heared (even non-connected) */				
			clients = new HashSet<OdinClient>(getClients());
			
			Iterator<OdinClient> it = clients.iterator();
		
			while(it.hasNext()){
				currentClient = (OdinClient) it.next();
				//currentLvap = currentClient.getLvap();
				//currentAgent = currentLvap.getAgent();
				
				//log.debug("Removing client " + currentClient.getMacAddress().toString() + " and LVAP " + currentLvap.getBssid().toString() + " from AP " + currentAgent.getIpAddress().toString());
				log.debug("Removing client " + currentClient.getMacAddress().toString());
				
				//currentAgent.sendDeauth(currentClient.getMacAddress(), currentLvap.getBssid());
				deauthClient(currentClient);
				//currentAgent.removeClientLvap(currentClient);
				
			}
			log.info("Wait " + START_WAIT + " ms to repeat removal of clients.");
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e){
	        		e.printStackTrace();
			}
		}
			
		
	}
	

}
