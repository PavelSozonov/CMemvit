package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;

import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateStorage;
import ru.innopolis.lips.memvit.utils.DataExtractor;
import ru.innopolis.lips.memvit.view.BrowserView;
import ru.innopolis.lips.memvit.view.View;

/*
 * Handle debug events, and backward/forward buttons' events
 */
public class Controller {

	private static BrowserView browserView;

	public static void setBrowserView(BrowserView browserView) {
		Controller.browserView = browserView;
	}
	
	public static BrowserView getBrowserView() {
		return browserView;
	}
	
	// Count how many states are equals
	private static int tempDublicatedStates = 0;
	
	private StateStorage stateStorage = new StateStorage();
	
	public void handleEvent(ICDIThread thread) {
		
		// Get link to view
		View view = getBrowserView();
				
		// Extract data
		State newState = null;
		try {
			newState = DataExtractor.extractData(thread);
		} catch (CDIException e) {
			e.printStackTrace();
		}
				
		if (newState == null) {
			System.out.println("Null state returned, break handle!");
			return;
		}
		
		// Put data in storage
		// Check for duplicated states
		if (stateStorage.isEmpty() || !stateStorage.getLastState().getData().equals(newState.getData())) {
			stateStorage.addState(newState);
			// Update view
			if (view != null) view.update(newState);
		} else {
			// Debug info
			System.out.println("- Duplicated state: " + ++tempDublicatedStates);
		}
		
	}
	
	public void handleBackButton() {
		View view = getBrowserView();
		State state = stateStorage.getPreviousState();
		view.update(state);
	}
	
	public void handleForwardButton() {
		View view = getBrowserView();
		State state = stateStorage.getNextState();
		view.update(state);
	}
}
