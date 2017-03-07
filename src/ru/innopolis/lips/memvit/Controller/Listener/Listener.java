package ru.innopolis.lips.memvit.Controller.Listener;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;

import ru.innopolis.lips.memvit.Activator;

public class Listener implements ICDIEventListener {

	private ICDIThread currentThread = null; 
	//private boolean itIsUpdatedThread = false;
	
	@Override
	public void handleDebugEvents(ICDIEvent[] events) {
		
		// TODO: add Data Extractor invocation
		
		for (ICDIEvent event : events) {
			ICDIObject source = event.getSource();	
			if (source == null) { // When button stop pressed, fires ~4 times, after isTerminated
				currentThread = null;
				//setItIsUpdatedThread(false);
				return;
			}
			
			ICDITarget target = source.getTarget();
			if (target.isTerminated()) { // When button stop pressed, fires ~2 times, before source == null
				currentThread = null;
				//setItIsUpdatedThread(false);
				return;	
			}
			try {
				ICDIThread thread = target.getCurrentThread();
				currentThread = thread;
				//setItIsUpdatedThread(true);	
			} catch (CDIException e) {}
		}
		
		// Check, handle event only once or each event in cycle
		Activator.getController().handleEvent(currentThread);		
	}
	
	//	private void setItIsUpdatedThread(boolean value) {
	//		itIsUpdatedThread = value;
	//	}

}
