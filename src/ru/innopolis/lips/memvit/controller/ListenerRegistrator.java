package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.swt.widgets.Display;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.GDBCDIDebuggerMemvit;

/*
 * Always check session, if session is updated register listener again
 */
public class ListenerRegistrator {

	private ICDISession cdiDebugSession;

	// Reference to instance from Activator
	private Listener listener = Activator.getController().getListener();
	
	public ListenerRegistrator() {
		
		Runnable runnable = new SessionChecker();
		Thread thread2 = new Thread(runnable);
		thread2.start();
	}

	/*
	 * Each 1000 ms check if session is changed,
	 * if changed then update listener
	 */
	class SessionChecker implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1001);
				} catch (Exception e) {
				}
				Runnable task = () -> {
					getCdiSessionAndSetListener();
				};
				Display.getDefault().asyncExec(task); 
			}
		}
	}

	/*
	 * Try to get CDI Session. If session is changed, 
	 * update cdiDebugSession and add event listener
	 */
	private void getCdiSessionAndSetListener() {
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		if (session == null) {
			return;
		}
		if (session.equals(cdiDebugSession)) {
			return;
		}
		cdiDebugSession = session;
		cdiDebugSession.getEventManager().addEventListener(listener);
	}	
}
