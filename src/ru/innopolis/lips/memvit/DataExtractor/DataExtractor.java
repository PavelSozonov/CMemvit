package ru.innopolis.lips.memvit.DataExtractor;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.swt.widgets.Display;
import ru.innopolis.lips.memvit.CDebugger.GDBCDIDebuggerMemvit;
import ru.innopolis.lips.memvit.Listener.CDIEventListenerForJson;
import ru.innopolis.lips.memvit.Util.FileWriter2;
import ru.innopolis.lips.memvit.Util.JsonBuilder;

/*
 * Instantiated by Activator
 * Creates listener, which on each debug event writes new json file
 * by invoking function saveStateToJsonAndWriteToFile of this class 
 */
public class DataExtractor {

	private CDIEventListenerForJson cdiEventListenerForJson;
	private ICDISession cdiDebugSession;
	private boolean cdiSessionIsGot = false;

	public DataExtractor() {
		cdiEventListenerForJson = new CDIEventListenerForJson(this);

		// Create new thread, it will work until cdi session will be got
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
					cdiSessionIsGot = getCdiSessionSetListener();
				};
				Display.getDefault().asyncExec(task);
				if (cdiSessionIsGot)
					break;
			}
		}
	}

	/*
	 * Try to get CDI Session. If session is changed, 
	 * update cdiDebugSession and add event listener
	 */
	private boolean getCdiSessionSetListener() {
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		if (session == null) {
			return false;
		}
		if (session.equals(cdiDebugSession)) {
			return false;
		}
		cdiDebugSession = session;
		cdiDebugSession.getEventManager().addEventListener(cdiEventListenerForJson);
		return true;
	}
	
	/*
	 * Get CDI session, if thread is updated, 
	 * save new state to json
	 */
	public void saveStateToJsonAndWriteToFile() {
		getCdiSessionSetListener();
		if (cdiEventListenerForJson == null) {
			return;
		}
		if (!cdiEventListenerForJson.isItUpdatedThread()) {
			return;
		}

		String jsonContent = JsonBuilder.buildJson(cdiEventListenerForJson.getDataModel());

		// Write json to file
		FileWriter2.writeJson(jsonContent);
	}
}
