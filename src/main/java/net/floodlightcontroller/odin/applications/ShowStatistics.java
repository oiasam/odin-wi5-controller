package net.floodlightcontroller.odin.applications;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.floodlightcontroller.odin.master.OdinApplication;
import net.floodlightcontroller.odin.master.OdinClient;
import net.floodlightcontroller.util.MACAddress;

public class ShowStatistics extends OdinApplication {

  private final int INTERVAL = 10000;
  //private final int SIGNAL_THRESHOLD = 160;

  HashSet<OdinClient> clients;
  //Map<MACAddress, Set<InetAddress>> hearingMap = new HashMap<MACAddress, Set<InetAddress>> ();
  //Map<InetAddress, Integer> newMapping = new HashMap<InetAddress, Integer> ();
	
	
  @Override
  public void run() {
    while (true) {
      try {
        Thread.sleep(INTERVAL);
          clients = new HashSet<OdinClient>(getClients());
          /*
	  * Probe each AP to get the list of MAC addresses that it can "hear".
	  * We define "able to hear" as "signal strength > SIGNAL_THRESHOLD".
	  * 
	  *  We then build the hearing table.
	  */
				 
	  // for each Agent
	  for (InetAddress agentAddr: getAgents()) {
	    System.out.println("\nAgent: " + agentAddr);
	    
	    // Reception statistics
	    Map<MACAddress, Map<String, String>> vals_rx = getRxStatsFromAgent(agentAddr);


            // for each STA associated to the Agent
	    for (Entry<MACAddress, Map<String, String>> vals_entry_rx: vals_rx.entrySet()) {
	      MACAddress staHwAddr = vals_entry_rx.getKey();
	      
	      for (OdinClient oc_rx: clients) {
		if (oc_rx.getMacAddress().equals(staHwAddr) && oc_rx.getIpAddress() != null && !oc_rx.getIpAddress().getHostAddress().equals("0.0.0.0")) {
	          System.out.println("\tStation MAC: " + staHwAddr + " IP: " + oc_rx.getIpAddress().getHostAddress());
	          System.out.println("\t\tnum packets: " + vals_entry_rx.getValue().get("packets"));
	          System.out.println("\t\tavg rate: " + vals_entry_rx.getValue().get("avg_rate") + " kbps");
	          System.out.println("\t\tavg signal: " + vals_entry_rx.getValue().get("avg_signal") + " dBm");
	          System.out.println("\t\tavg length: " + vals_entry_rx.getValue().get("avg_len_pkt") + " bytes");
	          System.out.println("\t\tair time: " + vals_entry_rx.getValue().get("air_time") + "ms");						
	          System.out.println("\t\tinit time: " + vals_entry_rx.getValue().get("first_received") + " sec");
	          System.out.println("\t\tend time: " + vals_entry_rx.getValue().get("last_received") + " sec");
	          System.out.println("");
		}
	      }
	    }
	    
	    // Transmission statistics
	    Map<MACAddress, Map<String, String>> vals_tx = getTxStatsFromAgent(agentAddr);


            // for each STA associated to the Agent
	    for (Entry<MACAddress, Map<String, String>> vals_entry_tx: vals_tx.entrySet()) {
	      MACAddress staHwAddr = vals_entry_tx.getKey();
	      
	      for (OdinClient oc_tx: clients) {
		if (oc_tx.getMacAddress().equals(staHwAddr) && oc_tx.getIpAddress() != null && !oc_tx.getIpAddress().getHostAddress().equals("0.0.0.0")) {
	          System.out.println("\tStation MAC: " + staHwAddr + " IP: " + oc_tx.getIpAddress().getHostAddress());
	          System.out.println("\t\tnum packets: " + vals_entry_tx.getValue().get("packets"));
	          System.out.println("\t\tavg rate: " + vals_entry_tx.getValue().get("avg_rate") + " kbps");
	          System.out.println("\t\tavg signal: " + vals_entry_tx.getValue().get("avg_signal") + " dBm");
	          System.out.println("\t\tavg length: " + vals_entry_tx.getValue().get("avg_len_pkt") + " bytes");
	          System.out.println("\t\tair time: " + vals_entry_tx.getValue().get("air_time") + " ms");						
	          System.out.println("\t\tinit time: " + vals_entry_tx.getValue().get("first_received") + " sec");
	          System.out.println("\t\tend time: " + vals_entry_tx.getValue().get("last_received") + " sec");
	          System.out.println("");
		}
	      }
	    }	    
	    
	    
	  }
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
      }
    }
}
