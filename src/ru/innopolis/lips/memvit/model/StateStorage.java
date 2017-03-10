package ru.innopolis.lips.memvit.model;

import java.util.LinkedList;
import java.util.List;

/*
 * Contains JSON objects, each object describes state of the program on one step.
 */
public class StateStorage {
	
	/*
	 * Used when user uses buttons back and forward in view.
	 * Hit back button entails shiftFromLastJson increment, 
	 * forward - decrement (uses check of boundaries)
	 * If shiftFromLastJson we on the last Json
	 * When a new event is dispatched, the view updates to last state and
	 * shiftFromLastJson becomes equal to 0
	 */
	private int shiftFromLastState = 0;
	
	
	private List<State> stateStorage = new LinkedList<State>();
	public void addState(State state) {
		shiftFromLastState = 0; // When new data is added, new data becomes current state
		stateStorage.add(state);
	}
	
	public State getLastState() {
		shiftFromLastState = 0; // Last state will be returned, reset shift pointer 
		if (stateStorage.size() == 0) return null; // If storage is empty
		return stateStorage.get(stateStorage.size() - 1);
	}
	
	public boolean isEmpty() {
		return stateStorage.size() == 0;
	}
	
	public int size() {
		return stateStorage.size();
	}
	
	private State getCurrentState() {
		return stateStorage.get(stateStorage.size() - 1 - shiftFromLastState);
	}
	
	/*
	 * Check if value of new shift will not out of boundaries
	 */
	private boolean isInBounds(int shift) {
		int index = stateStorage.size() - 1 - shift;
		return (index >= 0) && (index < stateStorage.size());
	}
	
	public State getPreviousState() {
		if (isInBounds(shiftFromLastState + 1)) { 
			shiftFromLastState++;
		}
		return getCurrentState();
	}
	
	public State getNextState() {
		if (isInBounds(shiftFromLastState - 1)) { 
			shiftFromLastState--;
		}
		return getCurrentState();
	}

}
