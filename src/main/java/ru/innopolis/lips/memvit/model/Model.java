package ru.innopolis.lips.memvit.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;

/**
 * Contain all the plugin data (states, counters, states' flags, etc.)
 * Instantiated by the Controller
 * 
 * @author Pavel Sozonov
 */
public class Model {

	// TODO: Add extended comment
	// Updated by Debug Evetns Listener
	private ICDIThread currentThread;

	private boolean updatedThread = false;

	// Count how many states are equals
	private int duplicatedStates = 0;

	// Error counter
	private AtomicInteger errorCounter = new AtomicInteger();

	// State management buttons disable flag, when debug haven't started, using
	// of buttons is disabled
	private boolean stateManagementButtonsEnabled = false;

	// Contain all the program execution states (each debug step is a separate
	// state)
	private StateStorage stateStorage = new StateStorage();

	/**
	 * @return the errorCouner
	 */
	public int incrementAndGetErrorCounter() {
		return errorCounter.incrementAndGet();
	}

	public boolean isStateManagementButtonsEnabled() {
		return stateManagementButtonsEnabled;
	}

	public void setStateManagementButtonsEnabled(boolean value) {
		stateManagementButtonsEnabled = value;
	}

	/**
	 * @param duplicatedStates
	 *            the tempDuplicatedStates to set
	 */
	public void setTempDuplicatedStates(int duplicatedStates) {
		this.duplicatedStates = duplicatedStates;
	}

	/**
	 * @return the tempDuplicatedStates
	 */
	public int getDuplicatedStates() {
		return duplicatedStates;
	}

	/**
	 * @return the stateStorage
	 */
	public StateStorage getStateStorage() {
		return stateStorage;
	}

	/**
	 * @return the currentThread
	 */
	public ICDIThread getCurrentThread() {
		return currentThread;
	}

	/**
	 * @param currentThread
	 *            the currentThread to set
	 */
	public void setCurrentThread(ICDIThread currentThread) {
		this.currentThread = currentThread;
	}

	/**
	 * Setter for the updatedThread flag
	 * 
	 * @param value
	 *            of the updatedThread flag
	 */
	public void setUpdatedThread(boolean value) {
		updatedThread = value;
	}

	/**
	 * Getter for the updatedThread
	 * 
	 * @return updatedThread flag
	 */
	public boolean isUpdatedThread() {
		return updatedThread;
	}
}
