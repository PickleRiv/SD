package clientSide.entities;

/**
 * Thief cloning.
 *
 * It specifies his own attributes. Implementation of a client-server model of
 * type 2 (server replication). Communication is based on a communication
 * channel under the TCP protocol.
 */
public interface ThiefCloning {

	/**
	 * Set Thief id
	 * 
	 * @param id id of the Thief
	 */
	public void setThiefId(int id);

	/**
	 * Get Thief id
	 * 
	 * @return id of the Thief
	 */
	public int getThiefId();

	/**
	 * Set Thief state
	 * 
	 * @param state new state of the Thief
	 */
	public void setThiefState(int state);

	/**
	 * Get Thief state
	 * 
	 * @return Thief state
	 */
	public int getThiefState();

}