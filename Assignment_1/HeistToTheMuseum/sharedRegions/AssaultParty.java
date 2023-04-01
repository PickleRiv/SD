package sharedRegions;

import main.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import commInfra.*;
import entities.*;


public class AssaultParty {
    
    private int partyID;
    private int partyMembers;

    
    public AssaultParty(int partyID){
    	this.partyID = partyID;
        partyMembers = 0;
    }
    
    
    public int getPartyID() {
    	return partyID;
    }
    
    public synchronized void crawlIn(int thiefID,int maxDisp){
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    	System.out.println("Thief_" + thiefID + " have arrived to room");
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);

    }

    public synchronized void crawlOut(int thiefID,int maxDisp){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    	System.out.println("Thief_" + thiefID + " have arrived to collectionSite");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);
    }    

    public synchronized void reverseDirection(int thiefID){
    	System.out.println("Thief_" + thiefID + " going back ");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
    } 
}

