package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.swt.widgets.Display;

import ru.innopolis.lips.memvit.model.Model;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateStorage;
import ru.innopolis.lips.memvit.plugin.Activator;
import ru.innopolis.lips.memvit.service.DataExtractor;
import ru.innopolis.lips.memvit.view.View;

/**
 * Handle the events of the current thread changing, and the backward/forward
 * buttons' events. Instantiated by Activator
 * 
 * @author Pavel Sozonov
 */
public class Controller {

	// The unique instance of the Debug Event Listener
	private DebugEventsListener debugEventsListener = new DebugEventsListener();

	// Object which contain all the program execution related data
	private Model model = new Model();

	// Reference to the view, initialized in the Browser View class constructor
	private View cMemvitView;

	/**
	 * Constructor
	 * 
	 * Instantiate Debug Listener Registrator. Create the thread which every 100
	 * ms checks if the state of the program being debugged is changed. If the
	 * state is changed then handles it (add the new state in the states
	 * storage, and send the new state to the view).
	 */
	public Controller() {
		Runnable runnable = new ThreadUpdateChecker();
		Thread thread = new Thread(runnable);
		thread.start();
	}

	/**
	 * Each 100 ms check if state of the current thread is updated
	 * 
	 * @author Pavel Sozonov
	 */
	private class ThreadUpdateChecker implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				Runnable task = () -> {
					checkCurrentThreadState();
				};
				Display.getDefault().asyncExec(task);
			}
		}
	}

	/**
	 * The setter for the currentThead field
	 * 
	 * @param currentThread
	 *            the currentThread to set
	 */
	public void setCurrentThread(ICDIThread currentThread) {
		model.setCurrentThread(currentThread);
	}

	/**
	 * If the current thread has the updated state then handle event and reset
	 * the "updated thread" flag
	 */
	private void checkCurrentThreadState() {
		if (model.isUpdatedThread()) {
			handleEvent();
			enableStateManagementButtons();
			model.setUpdatedThread(false);
		}
	}

	private void enableStateManagementButtons() {
		Activator.getController().getModel().setStateManagementButtonsEnabled(true);
	}

	public void setCMemvitView(View cMemvitView) {
		this.cMemvitView = cMemvitView;
	}

	public View getCMemvitView() {
		return cMemvitView;
	}

	public void handleEvent() {

		// Instance of the class which is used for data extracting
		DataExtractor dataExtractor = new DataExtractor();

		// Local reference to the current thread (shortcut)
		ICDIThread thread = model.getCurrentThread();

		// Local reference to the browser view
		View view = getCMemvitView();

		// Object for storing the extracted data
		State newState;
		try {
			newState = dataExtractor.extractData(thread);
		} catch (CDIException e) {
			return;
		}

		if (newState == null) {
			System.out.println("Null state returned, break handle!");
			return;
		}

		// Put data in storage
		// Check for duplicated states
		if (!isDuplicate(newState)) {

			getStateStorage().addState(newState);

			// Update view
			if (view != null)
				view.update(newState);

		} else {
			handleDuplicatedState();
		}
	}

	/**
	 * Increase duplicated states counter, and print out the message with the
	 * total amount
	 */
	private void handleDuplicatedState() {
		int duplicatedStates = model.getDuplicatedStates() + 1;
		model.setTempDuplicatedStates(duplicatedStates);
		System.out.println("Duplicated state is found. Total: " + duplicatedStates);
	}

	/**
	 * Check if state equal to the last state in the State Storage
	 * 
	 * @param state
	 *            for duplicate checking
	 * @return true if last state in the storage equal to the state being
	 *         checked, else false
	 */
	private boolean isDuplicate(State state) {
		if (getStateStorage().isEmpty())
			return false;
		return getStateStorage().getLastState().getData().equals(state.getData());
	}

	/**
	 * Handle hits on Back button in the browser view, if previous state exists,
	 * then update the browser view to the previous state
	 */
	public void handleBackButton() {
		if (!Activator.getController().getModel().isStateManagementButtonsEnabled())
			return;
		View view = getCMemvitView();
		State state = getStateStorage().getPreviousState();
		view.update(state);
	}

	/**
	 * Handle hits on Forward button in the browser view, if next state exists,
	 * then update the browser view to the next state
	 */
	public void handleForwardButton() {
		if (!Activator.getController().getModel().isStateManagementButtonsEnabled())
			return;
		View view = getCMemvitView();
		State state = getStateStorage().getNextState();
		view.update(state);
	}

	public DebugEventsListener getDebugEventsListener() {
		return debugEventsListener;
	}

	public StateStorage getStateStorage() {
		return model.getStateStorage();
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

}
