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
	
	private final int START_WAIT = 120000; // ms time before running the application. This leaves you some time for starting the agents
	private final int INTERVAL = 120000; // ms periodic interval to remove all clients
	private final long BLACKLIST = 30000; // ms time to blacklist client before it may reconnect

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

				long _now = System.nanoTime();
				long _blacklist = _now + BLACKLIST*1000000;
				log.debug("Removing client " + currentClient.getMacAddress().toString()+", blacklisting from "+_now+" ns until "+_blacklist+" ns");

				deauthClient(currentClient, _blacklist);
			}

			log.info("Wait " + INTERVAL + " ms to repeat removal of clients.");
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e){
	        		e.printStackTrace();
			}
		}
	}
}
