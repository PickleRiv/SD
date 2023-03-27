package sharedRegions;

import main.*;
import commInfra.*;
import entities.*;


public class AssaultParty {
    
    private final AssaultParty [] AP;
    private MemFIFO<Integer> thieves;

    public AssaultParty(){

        AP = new AssaultParty [SimulationParameters.M];
    
        for(int i = 0; i < SimulationParameters.M; i++){
            AP[i] = null;
        }
        try{
            thieves = new MemFIFO<>(new Integer [SimulationParameters.M]);
        }
        catch (MemException e){
	        System.out.println("Instantiation of waiting FIFO failed: " + e.getMessage ());
	        thieves = null;
	        System.exit (1);
      }
    }

    public synchronized void prepareAssaultParty(){

        int masterID, thiefID;
        masterID = ((MasterThief) Thread.currentThread()).getMasterID();
        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.ASSEMBLING_A_GROUP);
        notifyAll();
        
        try{
            thiefID = thieves.read();
            if((thiefID < 0) || !(thieves.full()))
                throw new MemException ("illegal thief id!");
        }
        catch (MemException e){
            System.out.println("Retrieval of customer id from waiting FIFO failed: " + e.getMessage ());
            thiefID = -1;
            System.exit(1);
        }   
    }

    public synchronized void crawlIn(){
    	
    	System.out.println("Going in");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);

        // if() 
        System.out.println("Ive arrived");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);
    }    

    public synchronized int sendAssaultParty(){
        return 0;
    }    

    public synchronized void crawlOut(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        System.out.println("Ive arrived");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);

        while(){ // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
            try {
            wait();
            } 
            catch (InterruptedException e) {
            e.printStackTrace();
            }   
        }
    }    

    public synchronized void reverseDirection(){
    	System.out.println("Going Out");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    } 
}

