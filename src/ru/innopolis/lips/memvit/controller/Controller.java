package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.swt.widgets.Display;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateStorage;
import ru.innopolis.lips.memvit.utils.DataExtractor;
import ru.innopolis.lips.memvit.view.BrowserView;
import ru.innopolis.lips.memvit.view.View;

/*
 * Handle debug events, and backward/forward buttons' events
 */
public class Controller {

	{
		Runnable runnable = new ListenerUpdateChecker();
		Thread thread = new Thread(runnable);
		thread.start();
	}
	
	/*
	 * Each 100 ms check listener state
	 */
	class ListenerUpdateChecker implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try { Thread.sleep(100); } catch (Exception e) { }
				Runnable task = () -> { checkListener(); };
				Display.getDefault().asyncExec(task);
			}			
		}
	}
	
	/*
	 * If listener has state changed thread then handle event
	 */
	private void checkListener() {
		Listener listener = Activator.getListenerRegistrator().getListener();
		if (listener == null) return;
		if (listener.isUpdatedThread()) {
			handleEvent(listener);
			listener.setUpdatedThread(false);
		}
	}	

	// Link to the view, initialized in the class constructor
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
	
	public void handleEvent(Listener listener) {
		
		ICDIThread thread = listener.getCurrentThread();
		
		if (!listener.isUpdatedThread()) return; // Redundant?
		
		// Get link to view
		View view = getBrowserView();
				
		// Extract data
		State newState = null;
		
		newState = DataExtractor.extractData(thread);
				
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
