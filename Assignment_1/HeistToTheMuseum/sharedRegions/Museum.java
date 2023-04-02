package sharedRegions;

import main.*;
import genclass.GenericIO;

public class Museum {
    
    private int [][] rooms;
    
    public Museum(int [][] rooms) {
    	this.rooms = rooms;
    }
    
    public int getDistToRoom(int roomID) {
    	return 0;}
    
    public int getAvailableRoom() {
    	for (int i=0;i<rooms.length;i++) {
    		if (rooms[i][2]==1 && rooms[i][3]!=1) {
    			return i;
    		}
    	}
    	if(isLooted()) {
    		return -2;
    	}
    	return -1;
    }
    
    
    public int numAvail() {
    	int count=0;
    	for (int i=0;i<rooms.length;i++) {
    		if (rooms[i][2]==1 && rooms[i][3]!=1) {
    			count++;
    		}
    	}
    	return count;
    }
    
    
    public boolean isLooted() {
    	for (int i=0;i<rooms.length;i++) {
    		if (rooms[i][3]==1) {
    			return true;
    		}
    	}
    	return false;

    }

    
   
	public synchronized void rollACanvas(){
    }
}
