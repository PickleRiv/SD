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

     public OrdinaryThief(String name, int thiefID, ConcentrationSite conSite, AssaultParty aParty, Museum museum, ControlAndCollectionSite ccSite){
        super (name);
    	this.thiefID =  thiefID;
    	this.maxDisp = (int) (2 + (Math.random() * (5 - 2)));
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
    	while(true) {
    		switch(thiefState) {
    		case 0:                                         // Concentration Site
            	// if not in party
    			int assignedRoom = conSite.amINeeded(thiefID);
            	// when in party
            	conSite.prepareExcursion();
            	break;
            case 1:                                         // Crawling inwards
            	// until in room
            	aParty.crawlIn(thiefID);
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
            	aParty.crawlOut(thiefID);
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
