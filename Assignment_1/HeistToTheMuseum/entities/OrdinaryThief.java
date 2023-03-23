package entities;

import sharedRegions.*;
import main.*;

public class OrdinaryThief extends Thread {
    /**
     * ThiefID 
    */
    private int thiefID;

    /**
     * Thief State
     */
    private int thiefState;
    
    private boolean carryCanvas;

    /**
     * Reference to shared regions (Museum, AssaultParty, ConcentrationSite, CollectionSite) 
     */
    private final ConcentrationSite conSite;
    private final AssaultParty aParty;
    private final Museum museum;
    private final ControlAndCollectionSite ccSite;
    

    /**
	 * Thief Thread instantiation
	 * 
	 * @param thiefID Id of the thief
	 * @param cSite reference to the concentration site
	 * @param aParty reference to the Assault Party
     * @param museum reference to the Museum
     * @param ccSite reference to the Control and Collection Site
	 */

     public OrdinaryThief(String name, int thiefID, ConcentrationSite conSite, AssaultParty aParty, Museum museum, ControlAndCollectionSite ccSite){
        super (name);
    	this.thiefID =  thiefID;
        this.conSite = conSite;
        this.aParty = aParty;
        this.museum = museum;
        this.ccSite = ccSite;
        this.carryCanvas =false;
        thiefState = OrdinaryThiefStates.CONCENTRATION_SITE;
        
     }

     public void setThiefID (int id){
        thiefID = id;
    }

    public int getThiefID(){
        return thiefID;
    }

    public void setThiefState(int state){
        thiefState = state;
    }

    public int getThiefState(){
        return thiefState;
    }
    

    @Override
    public void run(){
    	int distance = 0;
    	while(true) {
    		if(conSite.amINeeded()) { 			
    			conSite.prepareExcursion();
    			while(thiefState == OrdinaryThiefStates.CRAWLING_INWARDS) {
    				aParty.crawlIn();
    				if(thiefState == OrdinaryThiefStates.AT_A_ROOM) break;
    			}
    			
    			while(museum.hasCanvas()) {museum.rollACanvas();}
    			
    			aParty.reverseDirection();
    			while(thiefState == OrdinaryThiefStates.CRAWLING_OUTWARDS) {
    				aParty.crawlOut();
    				if(thiefState == OrdinaryThiefStates.COLLECTION_SITE) break;
    			}
    			ccSite.handACanvas();
    		}
    		break;
    	}
    }
}
