package sharedRegions;

import entities.*;
import genclass.GenericIO;

import main.*;
import commInfra.*;

public class ControlAndCollectionSite {
	
    private final GeneralRepos repos;	
	public ControlAndCollectionSite() {
		this.repos = null;
		
	}
    
    public synchronized void startOperations(){
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
        //System.out.println("Master is deciding what to do");
        repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());
    }
    
    /**
    *  Operation take a rest.
    *
    *  It is called by the MasterThief while waiting for thieves to arrive.
    *
    *    @return true, if his life cycle has come to an end -
    *            false, otherwise
    */
    public synchronized void takeARest(){
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.WAITING_FOR_ARRIVAL);
        repos.setMasterState(((MasterThief) Thread.currentThread()).getMasterState());
    }
    

    public synchronized int handACanvas(){
        return 0;
    }    

    public synchronized int collectACanvas(){
        return 0;
    }
    
    public synchronized void sumUpResults(){
        
    }

}
