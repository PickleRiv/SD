package sharedRegions;

import entities.*;
import main.*;
import commInfra.*;

public class ControlAndCollectionSite {
    //private final GeneralRepos repos;
    
    /**
	 * Queue of pending Requests
	 */
	private MemFIFO<Integer> thiefQueue;
	
	private int nThieves;
	
	public ControlAndCollectionSite() {
		
	}
    
    public synchronized void startOperations(){
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
        //repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());

        try{
            wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    
    /**
    *  Operation take a rest.
    *
    *  It is called by the MasterThief while waiting for thieves to arrive.
    *
    *    @return true, if his life cycle has come to an end -
    *            false, otherwise
    */
    public synchronized boolean takeARest(){
        while(nThieves == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                return true;
            }
        }

        if (nThieves>0){
            nThieves-=1;
        }

        return false; 
    }
    
    
    public synchronized void appraiseSit() {
    	while(true) {
    		try{
    			System.out.println("Master is waiting");
                wait();
            } catch(InterruptedException e){
                e.printStackTrace();
            }	
    	}
    }
    		

    public synchronized int handACanvas(){
        return 0;
    }    

    public synchronized int collectACanvas(){
        return 0;
    }

}
