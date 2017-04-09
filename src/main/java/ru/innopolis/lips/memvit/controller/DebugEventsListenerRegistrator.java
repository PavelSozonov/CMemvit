package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.swt.widgets.Display;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.GDBCDIDebuggerMemvit;

/**
 * Every 1 second check session, 
 * if session is updated register the debug event listener again.
 * Instantiated by Activator
 * 
 * @author Pavel Sozonov
 */
public class DebugEventsListenerRegistrator {
	
	// Reference to the current CDI Debug Session
	private ICDISession cdiDebugSession;

	// Reference to the unique instance of debug events listener from the Activator
	private DebugEventsListener debugEventsListener = Activator.getController().getDebugEventsListener();
	
	/**
	 * Constructor.
	 * Create the thread for managing the CDI Debug Session changing and updating the debug event listener
	 */
	public DebugEventsListenerRegistrator(Controller controller) {
		debugEventsListener = controller.getDebugEventsListener();
		Runnable runnable = new SessionChecker();
		Thread thread2 = new Thread(runnable);
		thread2.start();
	}

	/**
	 * Each 1 s check if the CDI Debug Session is changed,
	 * then update the local reference to it and add the debug events listener again
	 *  
	 * @author Pavel Sozonov
	 */
	private class SessionChecker implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1001);
				} catch (Exception e) {
				}
				Runnable task = () -> {
					ICDISession session = GDBCDIDebuggerMemvit.getSession();
					if (isSessionUpdated(session)) {
						updateSession(session);
						updateListener();
						resetStateStorage();
					}
				};
				Display.getDefault().asyncExec(task); 
			}
		}
	}
	
	/**
	 * If session is updated, 
	 * the states from the previous session should be deleted from the storage,
	 * and the storage counters should be reseted.   
	 */
	private void resetStateStorage() {
		Activator.getController().getStateStorage().resetStorage();
	}
	
	/**
	 * Check if the CDI Debug Session is updated
	 * 
	 * @param session - reference to the actual current CDI Debug Session (from GDBCDIDebuggerMemvit.getSession())
	 */
	private boolean isSessionUpdated(ICDISession session) {
		if (session == null || session.equals(cdiDebugSession)) return false;
		return true;
	}
	
	/**
	 * Update the local reference to the current CDI Debug Session
	 * 
	 * @param session - reference to the actual current CDI Debug Session (from GDBCDIDebuggerMemvit.getSession())
	 */
	private void updateSession(ICDISession session) {
		cdiDebugSession = session;
	}
	
	/**
	 * Add the debug events listener to the current CDI Debug Session
	 */
	private void updateListener() {
		cdiDebugSession.getEventManager().addEventListener(debugEventsListener);
	}

}
