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
    private int targetRoom;

    /**
     * Reference to shared regions (Museum, AssaultParty, ConcentrationSite, CollectionSite) 
     */
    private final ConcentrationSite conSite;
    private final AssaultParty aParty;
    private final Museum museum;
    private final ControlAndCollectionSite ccSite;
    private int maxDisp;
    

    /**
	 * Thief Thread instantiation
	 * 
	 * @param thiefID Id of the thief
	 * @param cSite reference to the concentration site
	 * @param aParty reference to the Assault Party
     * @param museum reference to the Museum
     * @param ccSite reference to the Control and Collection Site
	 */

     public OrdinaryThief(String name, int thiefID, ConcentrationSite conSite, AssaultParty aParty, Museum museum, ControlAndCollectionSite ccSite, int maxDisp){
        super (name);
    	this.thiefID =  thiefID;
    	this.maxDisp = maxDisp;
        this.conSite = conSite;
        this.aParty = aParty;
        this.museum = museum;
        this.ccSite = ccSite;
        this.carryCanvas =false;
        targetRoom = -1;
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
    
    public int getThiefPartyID() {
    	return aParty.getPartyID();
    }
    

    @Override
    public void run(){
    	while(true) {
    		switch(thiefState) {
    		case 0:                                         // Concentration Site
    			conSite.amINeeded(thiefID);
                targetRoom = conSite.prepareExcursion(thiefID);
    			System.out.println("crawling");
            	break;
            case 1:                                         // Crawling inwards
            	// until in room
            	aParty.crawlIn(thiefID,maxDisp);
            	// if in room change state
            	break;
            case 2:                                         // At a room
            	// if no canvas && canvas available
            	museum.rollACanvas();
            	// if canvas or room empty and not last wait()
            	// if canvas or room empty and last
            	aParty.reverseDirection(thiefID);
            	break;
            case 3:                                         // Crawling outwards
            	// until at site
            	aParty.crawlOut(thiefID, maxDisp);
            	// if on site change state
            	break;
            case 4:											// Collection site
            	// if has canvas
            	ccSite.handACanvas();
            	// if no canvas
    			conSite.amINeeded(thiefID);
            	break;
          }
    		}
    	}
    }
