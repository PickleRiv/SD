package sharedRegions;

import entities.*;
import main.SimulationParameters;
import genclass.GenericIO;

public class ConcentrationSite {

	private int[] parties;
	private int[] busyParties;
	private boolean masterBool;
	private int currentParty;
	private int targettedRoom;
	/**
	 * Reference to the thief threads
	 */
	private final OrdinaryThief[] thieves;
	private final GeneralRepos repos;	

	/**
	 * Concentration instantiation
	 * 
	 * @param repos reference to general repository
	 */
	public ConcentrationSite() {

		parties = new int[SimulationParameters.G];
		busyParties = new int[SimulationParameters.G];
		masterBool = false;
		currentParty = -1;
		targettedRoom = -1;
		
		
		
		
		// Initialisation of students thread
		thieves = new OrdinaryThief[SimulationParameters.M];
		for (int i = 0; i < SimulationParameters.M; i++)
			thieves[i] = null;
		this.repos = null;
	}

	public synchronized void amINeeded(int thiefID) {
		thieves[thiefID] = ((OrdinaryThief) Thread.currentThread());
		thieves[thiefID].setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
		repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
		int partyID=thieves[thiefID].getThiefPartyID();
		System.out.println("Thief_" + thiefID + " joined Party_" + thieves[thiefID].getThiefPartyID());
		parties[partyID]++;
		notifyAll();
		while (!masterBool || partyID != currentParty) {
			try {
				wait();
			} catch (InterruptedException e) {
				GenericIO.writelnString ("YUPPPPPPPPPPPPPPPPPPPPPPPPPp: " + e.getMessage ());
		        System.exit (1);
				e.printStackTrace();
			}
		}
		notifyAll();
		System.out.println("Thief_" + thiefID + " on it");
	}
	
    public boolean isBusy() {
    	int count =0;
    	for(int  i : busyParties) {
    		if(i!=0) {
    			count++; 
    		}
    	}
    	return (count==busyParties.length ? true : false);
    }

	public void masterBool() {
		masterBool = masterBool ? true : false;
	}

	public synchronized int appraiseSit() {
		// Set the state of the MasterThief to "deciding what to do"
		((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
	    repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());
		// Check if there are any thieves in the assault parties
		int party = fullParty();
		while (party == -1) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			party = fullParty();
		}
		currentParty = party;
		return party;
	}

	public int fullParty() {
		for (int i = 0; i < parties.length; i++) {
			
			if (parties[i]==3 && busyParties[i]!=1) {
				return i;
			}
		}
		return -1;
	}

	public synchronized void prepareAssaultParty() {
		((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.ASSEMBLING_A_GROUP);
	    repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());
		parties[currentParty]=0;
		busyParties[currentParty]=1;
	}

	public synchronized void sendAssaultParty(int targetRoom) {
		((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
	    repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());
		masterBool();
		targettedRoom = targetRoom;
		notifyAll();
	}

	public synchronized int prepareExcursion(int thiefID) {
		thieves[thiefID].setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
		repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
		return targettedRoom;
	}
}
