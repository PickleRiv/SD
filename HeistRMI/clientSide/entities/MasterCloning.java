package clientSide.entities;

/**
 * Master Thief cloning.
 *
 * It specifies his own attributes. Implementation of a client-server model of
 * type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */
public class MasterCloning {

	/**
	 * Set master id
	 * 
	 * @param state id of the master
	 */
	public void setMasterState(int state);

	/**
	 * Get master state
	 * 
	 * @return state of the master
	 */
	public int getMasterState();
}
