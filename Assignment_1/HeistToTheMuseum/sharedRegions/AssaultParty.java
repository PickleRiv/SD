package sharedRegions;

import main.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import commInfra.*;
import entities.*;


public class AssaultParty {
    
    private final AssaultParty [] AP;
    private MemFIFO<Integer> thieves;
    public int inPartyPos;
    public int [] positions;
    private int partyID;
    private int [] roomsDist;

    public AssaultParty(int partyID, int [] roomsDist){
    	this.partyID = partyID;
    	this.roomsDist = roomsDist;
    	
        AP = new AssaultParty [SimulationParameters.M];
    
        for(int i = 0; i < SimulationParameters.M; i++){
            AP[i] = null;
        }

        try{
            thieves = new MemFIFO<>(new Integer [3]);
        }
        catch (MemException e){
	        System.out.println("Instantiation of waiting FIFO failed: " + e.getMessage ());
	        thieves = null;
	        System.exit (1);
      }
    }

//    public synchronized void prepareAssaultParty(){
//
//        int masterID, thiefID;
//        masterID = ((MasterThief) Thread.currentThread()).getMasterID();
//        ((MasterThief) Thread.currentThread()).setMasterState(MasterThiefStates.ASSEMBLING_A_GROUP);
//        notifyAll();
//        
//        try{
//            thiefID = thieves.read();
//            if((thiefID < 0) || !(thieves.full()))
//                throw new MemException ("illegal thief id!");
//        }
//        catch (MemException e){
//            System.out.println("Retrieval of customer id from waiting FIFO failed: " + e.getMessage ());
//            thiefID = -1;
//            System.exit(1);
//        }   
//    }

    public synchronized void crawlIn(int thiefID){
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    	System.out.println("Thief_" + thiefID + " have arrived to room");
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);

    }


//    public synchronized int sendAssaultParty(){
//        return 0;
//    }    

    public synchronized void crawlOut(int thiefID){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    	System.out.println("Thief_" + thiefID + " have arrived to collectionSite");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);
    }    

    public synchronized void reverseDirection(int thiefID){
    	System.out.println("Thief_" + thiefID + " going back ");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    } 
}

