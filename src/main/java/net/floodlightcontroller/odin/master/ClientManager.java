package net.floodlightcontroller.odin.master;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.floodlightcontroller.odin.master.OdinClient;
import net.floodlightcontroller.util.MACAddress;

class ClientManager {
	private final Map<MACAddress, OdinClient> odinClientMap = new ConcurrentHashMap<MACAddress, OdinClient> ();
	
	/**We add a black list for the clients that should remain in 4G*/
	private final Map<MACAddress, OdinClient> odinBlackList = new ConcurrentHashMap<MACAddress, OdinClient> ();

	
	/**
	 * Add a client to the client tracker
	 * 
	 * @param hwAddress Client's hw address
	 * @param ipv4Address Client's IPv4 address
	 * @param vapBssid Client specific VAP bssid
	 * @param vapEssid Client specific VAP essid
	 */
	protected void addClient (final MACAddress clientHwAddress, final InetAddress ipv4Address, final Lvap lvap) {
		odinClientMap.put(clientHwAddress, new OdinClient (clientHwAddress, ipv4Address, lvap));
	}
	
	
	/**
	 * Add a client to the client tracker
	 * 
	 * @param hwAddress Client's hw address
	 * @param ipv4Address Client's IPv4 address
	 * @param vapBssid Client specific VAP bssid
	 * @param vapEssid Client specific VAP essid
	 */
	protected void addClient (final OdinClient oc) {
		odinClientMap.put(oc.getMacAddress(), oc);
	}
	
	
	/**
	 * Removes a client from the tracker
	 * 
	 * @param hwAddress Client's hw address
	 */
	protected void removeClient (final MACAddress clientHwAddress) {
		odinClientMap.remove(clientHwAddress);
	}
	
	
	/**
	 * Get a client by hw address
	 */
	protected OdinClient getClient (final MACAddress clientHwAddress) {
		return odinClientMap.get(clientHwAddress);
	}
	
	
	/**
	 * Get the client Map from the manager
	 * @return client map
	 */
	protected Map<MACAddress, OdinClient> getClients () {
		return odinClientMap;
	}
	
	/**
	 * Add a client to the black list
	 * 
	 * @param hwAddress Client's hw address
	 * @param ipv4Address Client's IPv4 address
	 * @param vapBssid Client specific VAP bssid
	 * @param vapEssid Client specific VAP essid
	 */
	protected void addClientBL (final MACAddress clientHwAddress, final InetAddress ipv4Address, final Lvap lvap) {
		odinBlackList.put(clientHwAddress, new OdinClient (clientHwAddress, ipv4Address, lvap));
	}
	
	
	/**
	 * Add a client to the black list
	 * 
	 * @param hwAddress Client's hw address
	 * @param ipv4Address Client's IPv4 address
	 * @param vapBssid Client specific VAP bssid
	 * @param vapEssid Client specific VAP essid
	 */
	protected void addClientBL (final OdinClient oc) {
		odinBlackList.put(oc.getMacAddress(), oc);
	}
	
	
	/**
	 * Removes a client from the black list
	 * 
	 * @param hwAddress Client's hw address
	 */
	protected void removeClientBL (final MACAddress clientHwAddress) {
		odinBlackList.remove(clientHwAddress);
	}
	
	
	/**
	 * Get a client by hw address from the black list
	 */
	protected OdinClient getClientBL (final MACAddress clientHwAddress) {
		return odinBlackList.get(clientHwAddress);
	}
	
	
	/**
	 * Get the client Map  from the black list
	 * @return client map
	 */
	protected Map<MACAddress, OdinClient> getClientsBL () {
		return odinBlackList;
	}
}
