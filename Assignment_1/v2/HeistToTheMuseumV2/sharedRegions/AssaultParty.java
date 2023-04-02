package sharedRegions;

import main.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import commInfra.*;
import entities.*;
import genclass.GenericIO;


public class AssaultParty {
    
    private int partyID;
    private int partyMembers;
    
    private final GeneralRepos repos;
    
    public AssaultParty(int partyID){
    	this.repos = null;
		this.partyID = partyID;
        partyMembers = 0;
    }
    
    
    public int getPartyID() {
    	return partyID;
    }
    
    public synchronized void crawlIn(int thiefID,int maxDisp){
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);
    	repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
    	System.out.println("Thief_" + thiefID + " have arrived to room");
    	
    	((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);
    	repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
    }

    public synchronized void crawlOut(int thiefID,int maxDisp){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
    	System.out.println("Thief_" + thiefID + " have arrived to collectionSite");
    	
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);
        repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
    }    

    public synchronized void reverseDirection(int thiefID){
    	System.out.println("Thief_" + thiefID + " going back ");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        repos.setThiefState(((OrdinaryThief) Thread.currentThread()).getThiefID(), ((OrdinaryThief) Thread.currentThread()).getThiefState());
    } 
}

