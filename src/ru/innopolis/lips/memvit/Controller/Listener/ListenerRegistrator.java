package ru.innopolis.lips.memvit.Controller.Listener;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.swt.widgets.Display;
import ru.innopolis.lips.memvit.CDebugger.GDBCDIDebuggerMemvit;

public class ListenerRegistrator {

	private Listener listener;
	private ICDISession cdiDebugSession;
	private boolean cdiSessionIsGot = false;

	public ListenerRegistrator() {
		
		listener = new Listener();

		// Create new thread, it will work until cdi session will be got
		// When this class is instantiated in the activator, session does not active yet
		Runnable runnable = new RunnableForThread();
		Thread thread2 = new Thread(runnable);
		thread2.start();
	}

	/*
	 * Each 100 ms try to get cdi session, break when session is got
	 */
	class RunnableForThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				cdiSessionIsGot = false;
				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				Runnable task = () -> {
					cdiSessionIsGot = getCdiSessionAndSetListener();
				};
				// Display.getDefault().asyncExec(task); 
//				if (cdiSessionIsGot)
//					break;
			}
		}
	}

	/*
	 * Try to get CDI Session. If session is changed, 
	 * update cdiDebugSession and add event listener
	 */
	private boolean getCdiSessionAndSetListener() {
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		if (session == null) {
			return false;
		}
		if (session.equals(cdiDebugSession)) {
			return false;
		}
		cdiDebugSession = session;
		cdiDebugSession.getEventManager().addEventListener(listener);
		return true;
	}
	
}
