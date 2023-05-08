package clientSide.entities;

import genclass.GenericIO;
import serverSide.main.*;
import clientSide.stubs.*;
import clientSide.entities.*;

/**
 * Represents an Ordinary thief thread
 */
public class OrdinaryThief extends Thread {

	private boolean readyToLeave;
	private final int thiefId;
	private ThiefStates thiefState;
	private AssaultPartyStub aParty;
	private int aPartyId;
	private final CocentrationStub conSite;
	private final CollectionStub ccSite;
	private boolean isHoldingCanvas;
	private final int maxThiefDisp;
	private final MuseumStub museum;
	private int position;
	private final AssaultPartyStub[] parties;
	private boolean inParty;
	private final GeneralReposStub repos;

	/**
	 * Creates an Ordinary thief object and initializes variables/flags
	 */
	public OrdinaryThief(String name, int id, CocentrationStub conSite, CollectionStub ccSite,
			AssaultPartyStub[] parties, MuseumStub museum, GeneralReposStub repos, int maxThiefDisp) {
		super(name);
		this.thiefId = id;
		this.conSite = conSite;
		this.thiefState = ThiefStates.CONCENTRATION_SITE;
		this.parties = new AssaultPartyStub[SimulPar.nParties];
		for (int i = 0; i < SimulPar.nParties; i++) {
			this.parties[i] = parties[i];
		}
		this.ccSite = ccSite;
		this.museum = museum;
		isHoldingCanvas = false;
		this.maxThiefDisp = maxThiefDisp;
		position = 0;
		readyToLeave = false;
		aPartyId = -1;
		inParty = false;
		this.repos = repos;
		repos.setOt_mdj(thiefId, maxThiefDisp);
	}

	/**
	 * Check if the inParty flag that represents if the thief is in a party
	 * 
	 * @return a boolean that represents the state of thief in a party
	 */
	public boolean isInParty() {
		return inParty;
	}

	/**
	 * Set the thief in party given the value
	 * 
	 * @param inParty A boolean stating the readiness of the thief
	 */
	public void setInParty(boolean inParty) {
		this.inParty = inParty;
	}

	/**
	 * Set the readyToLeave flag that represents if the thief is ready to start
	 * crawling out
	 * 
	 * @param readyToLeave A boolean stating the readiness of the thief
	 */
	public void setReadyToLeave(boolean readyToLeave) {
		this.readyToLeave = readyToLeave;
	}

	/**
	 * Checks if the thief is ready to leave the room
	 * 
	 * @return a boolean that represents the readiness of the thief
	 */
	public boolean isReadyToLeave() {
		return readyToLeave;
	}

	/**
	 * Get the current position of the thief
	 * 
	 * @return an integer that represents the current position of the ordinary thief
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set the ordinary thief position
	 * 
	 * @param position an integer that represents the position of the ordinary thief
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Get the thief id
	 * 
	 * @return thief id
	 */
	public int thiefId() {
		return thiefId;
	}

	/**
	 * Get the current state of the thief
	 * 
	 * @return thief state
	 */
	public ThiefStates getThiefState() {
		return thiefState;
	}

	/**
	 * Set the ordinary thief state
	 * 
	 * @param thief state
	 */
	public void setThiefstate(ThiefStates thiefState) {
		this.thiefState = thiefState;
	}

	/**
	 * Get the ordinary thief's assault party
	 * 
	 * @return an Assault party object containing the assault party information
	 */
	public AssaultPartyStub getParty() {
		return aParty;
	}

	/**
	 * Checks if the ordinary thief is holding a canvas or not
	 * 
	 * @return a boolean that represents if the hands of thief are busy or not
	 */
	public boolean isHoldingCanvas() {
		return isHoldingCanvas;
	}

	/**
	 * Main run() method; Starts executing when thread.start() is called
	 */
	@Override
	public void run() {
		while (true) {
			int ret = conSite.amINeeded();
			if (ret == 1) {
				break;
			}
				aPartyId = conSite.prepareExcursion();
				aParty = parties[aPartyId];
				aParty.join();
				aParty.crawlIn();
				if (getPosition() == aParty.getDistance()) {
					aParty.next();
					setThiefState(ThiefStates.AT_A_ROOM);
					//gp.setOT_states(getOT_id(), OrdinaryThievesStates.AT_A_ROOM);
					//GenericIO.writeString("Position -> " + getPosition() + " Thief " + getOT_id() + " in a room!\n");
				}
				isHoldingCanvas = museum.rollACanvas(getParty().getRoom_assigned());
				//gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
				aParty.reverseDirection();
				aParty.crawlOut();
				if (getPosition() == aParty.getDistance()) {
					aParty.next();
					setThiefState(ThiefStates.COLLECTION_SITE);
					//gp.setOT_states(getOT_id(), OrdinaryThievesStates.COLLECTION_SITE);
					//GenericIO.writeString(
						//	"Position -> " + getPosition() + " Thief " + getOT_id() + " in concentration site!\n");
				}
				ccSite.handACanvas();
				isHoldingCanvas = false;
				//gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
				setPosition(0);
				setInParty(false);
				//gp.setIsInParty(getOT_id(), isInParty());
				//gp.setOT_states(getOT_id(), OrdinaryThievesStates.CONCENTRATION_SITE);
			}
		}
	}
}
