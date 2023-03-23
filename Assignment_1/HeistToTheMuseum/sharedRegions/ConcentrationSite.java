package sharedRegions;

import entities.*;

public class ConcentrationSite {
	/**
	 *	Number of thieves available
	 */
	private int numberOfThievesReady;
	
	/**
	 *	Number of thieves sent in a party
	 */
	private int numberOfThievesUnavailable;
	
	 /**
     * Concentration  instantiation
     * 
     * @param repos reference to general repository
     */
	public ConcentrationSite()
	{
		this.numberOfThievesReady = 0;
		this.numberOfThievesUnavailable = 0;

	}

    public synchronized boolean amINeeded(){
    	notifyAll();
		((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CONCENTRATION_SITE);
    	if(numberOfThievesReady < 3 && numberOfThievesUnavailable < 3) {
    		System.out.println("Im ready!");
    		numberOfThievesReady += 1;
    		return false;
    	}
    	System.out.println("Joined AP!");
    	numberOfThievesUnavailable +=1;
    	return true;
    	}

    public synchronized void prepareExcursion(){
    	System.out.println("Sneaky beaky like!");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    }

    public synchronized void sumUpResults(){
        
    }
}
