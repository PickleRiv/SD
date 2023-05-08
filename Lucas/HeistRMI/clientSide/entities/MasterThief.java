package clientSide.entities;

import comminfra.Room;
import genclass.GenericIO;
import serverSide.main.*;
import clientSide.stubs.*;

import java.util.ArrayList;

/**
 * Represents the Master thief thread
 */
public class MasterThief extends Thread{

    private MasterThiefStates masterState;

    private final MasterThiefCCSStub ccSite;

    private final OrdinaryThiefCSStub conSite;

    private final MuseumStub museum;

    private boolean isOver;

    private final AssaultPartyStub[] parties;

    private GeneralReposStub repos;

    /**
     * Creates a master thief object and initializes variables and flags needed for the operation of the thread
     */
    public MasterThief(String name, MasterThiefCCSStub ccSite, OrdinaryThiefCSStub conSite, AssaultPartyStub[] parties, MuseumStub museum, GeneralReposStub repos){
        super(name);
        this.ccSite = ccSite;
        this.conSite = conSite;
        this.masterState = MasterStates.PLANNING_THE_HEIST;
        this.museum = museum;
        isOver = false;
        this.parties = new AssaultPartyStub[SimulPar.nParties];
        for (int i = 0; i < SimulPar.nParties; i++) {
            this.parties[i] = parties[i];
        }
        this.repos = repos;
    }

    /**
     * Checks if the heist is over
     * @return a boolean that represents the state of the heist
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Sets the master thief state given the parameter
     * @param masterState is an enumerate that represents a master thief state
     */
    public void setMasterState(MasterThiefStates masterState) {
        this.masterState = masterState;
    }

    /**
     * Gets the current master thief state
     * @return an enumerate that represents the master thief state
     */
    public MasterThiefStates getMasterState() {
        return masterState;
    }

    /**
     * Get the first room in the available rooms of the museum that could be stolen
     * @return an integer that represents the index of the room
     */
    public int getRoomToBeStolen(){
        for (Room r : museum.getRooms()) {
            if(!r.isAssigned() && r.isEmpty()) return r.getId();
        }
        return SimulPar.N - 1;
    }

    /**
     * Function that complements isOver() that checks if all the rooms in the museum are empty
     * @return a boolean that represents the emptiness of the museum
     */
    public boolean isHeistOver(){
        for (Room r : museum.getRooms()) {
            if(r.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    /**
     * The main run method; Starts executing when thread.start() is called
     */
    @Override
    public void run () {
        while(!isOver()){
            switch(getMasterState()){
                case PLANNING_THE_HEIST :
                    ccSite.startOperations();
                    break;
                case DECIDING_WHAT_TO_DO :
                    repos.setMasterState(MasterThiefStates.DECIDING_WHAT_TO_DO);
                    ccSite.appraiseSit(isHeistOver());
                    break;
                case ASSEMBLING_A_GROUP :
                    repos.setMasterState(MasterThiefStates.ASSEMBLING_A_GROUP);
                    conSite.prepareAssaultParty();
                    for(int i = 0; i < SimulPar.nParties; i++){
                        int roomToSteal= getRoomToBeStolen();
                        GenericIO.writeString("Assigning room "+roomToSteal+" to party "+parties[i].getId()+"\n");
                        int distance = museum.getRooms()[roomToSteal].getDistance();
                        museum.getRooms()[roomToSteal].setAssigned(true);
                        parties[i].setRoom_assigned(roomToSteal);
                        parties[i].setDistance(distance);
                        gp.setAPRid(i, roomToSteal);
                    }
                    ccSite.sendAssaultParty();
                    break;
                case WAITING_FOR_GROUP_ARRIVAL:
                    repos.setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
                    cc_site.takeARest();
                    cc_site.collectACanvas();
                    for (int i = 0; i < Simul_Par.N_Parties; i++) {
                        museum.getRooms()[parties[i].getRoom_assigned()].setAssigned(false);
                        if(museum.getRooms()[parties[i].getRoom_assigned()].getN_canvas() == 0){
                            museum.getRooms()[parties[i].getRoom_assigned()].setEmpty(true);
                        }
                        parties[i].setAP(new ArrayList<>(SimulPar.K));
                        parties[i].setNext_inLine(0);
                    }
                    break;
                case PRESENTING_THE_REPORT:
                    repos.setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
                    int results = ccSite.sumUpResults();
                    isOver = true;
                    conSite.endOfHeist();
                    GenericIO.writeString("Heist over, collected "+results+" canvas!\n");
                    //gp.setCanvas(results);
                    //gp.printSumUp();
            }
        }
    }
}

