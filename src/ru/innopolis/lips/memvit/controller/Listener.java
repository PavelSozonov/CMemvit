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
	
	@Override
	public void handleDebugEvents(ICDIEvent[] events) {
		
		// TODO: add Data Extractor invocation
		
		for (ICDIEvent event : events) {
			ICDIObject source = event.getSource();	
			if (source == null) {
				currentThread = null;
				return;
			}
			
			ICDITarget target = source.getTarget();
			if (target.isTerminated()) {
				currentThread = null;
				return;	
			}
			try {
				ICDIThread thread = target.getCurrentThread();
				currentThread = thread;
			} catch (CDIException e) {}
		}
		
		// Check, handle event only once or each event in cycle
		Activator.getController().handleEvent(currentThread);		
	}

}
