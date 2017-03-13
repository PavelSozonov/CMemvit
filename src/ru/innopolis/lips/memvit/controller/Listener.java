package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;

import ru.innopolis.lips.memvit.Activator;

public class Listener implements ICDIEventListener {

	private ICDIThread currentThread = null; 
	private boolean updatedThread = false;
	
	@Override
	public void handleDebugEvents(ICDIEvent[] events) {
		
		for (ICDIEvent event : events) {
			ICDIObject source = event.getSource();	
			if (source == null) {
				currentThread = null;
				updatedThread = false;
				return;
			}
			
			ICDITarget target = source.getTarget();
			if (target.isTerminated()) {
				currentThread = null;
				updatedThread = false;
				return;	
			}
			try {
				ICDIThread thread = target.getCurrentThread();
				currentThread = thread;
				updatedThread = true;
			} catch (CDIException e) {}
		}
		
		// Check, handle event only once or each event in cycle
		// Data extractor invocation
		//Activator.getController().handleEvent(this);		
	}
	
	public ICDIThread getCurrentThread() {
		return currentThread;
	}
	
	public boolean isUpdatedThread() {
		return updatedThread;
	}
	
	public void setUpdatedThread(boolean updatedThread) {
		this.updatedThread = updatedThread;
	}
}
