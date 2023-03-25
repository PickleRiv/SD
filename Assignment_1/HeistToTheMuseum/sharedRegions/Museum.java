package sharedRegions;
import java.util.Enumeration;
import java.util.Hashtable;
import main.*;

public class Museum {
    
    private int canvas;
    
    private int firstToArrive;
    
    private int lastToArrive;
    
    private int lastToRoll;
    
    private boolean carryCanvas;
    
 //   private int [][] distToRoom;
 	Hashtable<Integer, Integer> roomInfo = new Hashtable<>();
    
    public Museum() {

		// define the ranges
		int maxDist = 30;
		int minDist = 15;
		int rangeDist = maxDist - minDist;
		
		int maxPaints = 16;
		int minPaints = 8;
		int rangePaints = maxPaints - minPaints;
		

		for(int i = 0; i < SimulationParameters.N; i++){
			roomInfo.put((int)(Math.random() * rangeDist) + minDist, (int)(Math.random() * rangePaints) + minPaints);
		}
		System.out.println(roomInfo);
		
    	//distToRoom = new int [SimulationParameters.N];

		// // define the range
        // int maxDist = 30;
        // int minDist = 15;
        // int rangeDist = maxDist - minDist;
 
		// for(int i = 0; i < SimulationParameters.N; i++){
		// 	distToRoom[i] = (int)(Math.random() * rangeDist) + minDist;
		// }
		// for(int i =0; i < 3;i++) {
		// 	System.out.println(distToRoom[i]);
		// }
		
    	this.canvas = 4;
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
		if(lastToArrive > -1 && !this.carryCanvas) {
			canvas -= 1;
			this.carryCanvas = true;
			notifyAll();
			System.out.println("I've taken a Canvas");
		}

		while(this.carryCanvas || lastToArrive == -1) {
			try {
				System.out.println("I'm waiting");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
    }
	
	/**
	 * Obtain availability of canvas in the room
	 * @return true if there are canvas or false if there aren't any
	 */
	public boolean hasCanvas() {return canvas > 0 ? true : false;}
}
