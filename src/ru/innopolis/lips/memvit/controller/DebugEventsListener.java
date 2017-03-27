package ru.innopolis.lips.memvit.controller;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import ru.innopolis.lips.memvit.Activator;

/**
 * Check if state of the current thread is changed then
 * update reference to this thread, and set "updated thread" flag
 * 
 * @author Pavel Sozonov
 */
public class DebugEventsListener implements ICDIEventListener {

	/**
	 * Handle each debug event, if the current thread is updated,
	 * update reference to it, and set "updated state" flag
	 */
	@Override
	public void handleDebugEvents(ICDIEvent[] events) {
		
		for (ICDIEvent event : events) {
			ICDIObject source = event.getSource();	
			if (source == null) {
				setCurrentThread(null);
				setIsUpdatedThread(false);
				return;
			}
			
			ICDITarget target = source.getTarget();
			if (target.isTerminated()) {
				setCurrentThread(null);
				setIsUpdatedThread(false);
				return;	
			}
			try {
				ICDIThread thread = target.getCurrentThread();
				setCurrentThread(thread);
				setIsUpdatedThread(true);
			} catch (CDIException e) {}
		}
	}
	
	/**
	 * The setter for the currentThead field
	 * 
	 * @param currentThread the currentThread to set
	 */
	private void setCurrentThread(ICDIThread currentThread) {
		Activator.getController().setCurrentThread(currentThread);
	}
	
	/**
	 * The setter for the "updated state" flag
	 * 
	 * @param value
	 */
	private void setIsUpdatedThread(boolean value) {
		Activator.getController().getModel().setUpdatedThread(value);
	}
}
