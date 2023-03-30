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

    public synchronized void crawlIn(int thiefID, int maxDistance, int distanceToRoom) throws InterruptedException {
        thiefID = ((OrdinaryThief) Thread.currentThread()).getThiefID();
        System.out.println("Going in");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_INWARDS);

        // initialize position map
        Map<Integer, Integer> positionMap = new HashMap<>();
        positionMap.put(thiefID, 0);

        while (true) {
            boolean allThievesReachedRoom = true;
            for (int otherID : positionMap.keySet()) {
                if (positionMap.get(otherID) < distanceToRoom) {
                    allThievesReachedRoom = false;
                    break;
                }
            }
            if (allThievesReachedRoom) {
                break;
            }

            // calculate distance
            int distance = ThreadLocalRandom.current().nextInt(1, Math.min(maxDistance, distanceToRoom) + 1);

            // move the thief based on the distance
            int currentPosition = positionMap.get(thiefID);
            int newPosition = Math.min(currentPosition + distance, distanceToRoom);

            // check for collisions with other thieves
            boolean collision = false;
            for (int otherID : positionMap.keySet()) {
                if (otherID != thiefID && Math.abs(positionMap.get(otherID) - newPosition) <= 1) {
                    System.out.println("collided");
                    collision = true;
                    break;
                }
            }

            // move the thief if there are no collisions
            if (!collision) {
                positionMap.put(thiefID, newPosition);
                System.out.println("Thief_" + ((OrdinaryThief) Thread.currentThread()).getThiefID() + " " + newPosition);
            } else {
                // wait if there are collisions
                System.out.println("Thief_" + ((OrdinaryThief) Thread.currentThread()).getThiefID() + " is crawling ");
                wait();
            }
        }

        // update state of thief if in room
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.AT_A_ROOM);
        notifyAll();
    }


    public synchronized int sendAssaultParty(){
        return 0;
    }    

    public synchronized void crawlOut(){
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.CRAWLING_OUTWARDS);
        System.out.println("Ive arrived");
        ((OrdinaryThief) Thread.currentThread()).setThiefState(OrdinaryThiefStates.COLLECTION_SITE);

        while(true){ // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11
            try {
            	System.out.println("Im waitting");
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

