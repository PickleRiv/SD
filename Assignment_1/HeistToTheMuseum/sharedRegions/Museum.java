package sharedRegions;

import main.*;

public class Museum {
    
    private int firstToArrive;
    
    private int lastToArrive;
    
    private int lastToRoll;
    
    private boolean carryCanvas;
    private int [] roomsPaints;
    
    public Museum(int [] roomsPaints) {
    	this.roomsPaints = roomsPaints;
		
    	this.firstToArrive = -1;
    	this.lastToArrive = -1;
    	this.lastToRoll = -1;
    	this.carryCanvas = false;
    }
    
    /**
	 * Obtain id of the last thief to arrive
	 * @return id of the last thief to arrive at the room
	 */
	public int getLastToArrive() {return lastToArrive;}
	
	/**
	 * Set id of the last thief to arrive
	 * @param lastToArrive if of the last thief to arrive to the room
	 */
	public void setLastToArrive(int lastToArrive) {this.lastToArrive = lastToArrive;}
    
	/**
	 * Obtain id of the last thief to roll a canvas
	 * @return id of the last thief to roll a canvas
	 */
	public int getLastToRoll() {return lastToRoll;}
	
	/**
	 * Set id of the last thief to roll a canvas
	 * @param lastToArrive if of the last thief to roll a canvas
	 */
	public void setLastToRoll(int lastToRoll) {this.lastToRoll = lastToRoll;}
	
    
	/**
	 * Operation to roll a canvas
	 * 
	 * It is called by a thief whenever they are at a room and there are canvas
	 * Thief rolls a canvas if there are any and if isn't carrying one
	 * Thief waits if is carrying a canvas and if it was not the last to roll one
	 */
	public synchronized void rollACanvas(){
//		if(lastToArrive > -1 && !this.carryCanvas) {
//			canvas -= 1;
//			this.carryCanvas = true;
//			notifyAll();
//			System.out.println("I've taken a Canvas");
//		}
//
//		while(this.carryCanvas || lastToArrive == -1) {
//			try {
//				System.out.println("I'm waiting");
//				wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}    
//		}
    }
}
