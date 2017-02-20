/*******************************************************************************
 * Copyright (c) 2015.
 * This file is part of Memvit.
 
 * Memvit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * Memvit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package ru.innopolis.lips.memvit.Listener;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import ru.innopolis.lips.memvit.Model.DataModel;

/*
 * Handles each event, when event occurs sets currentThread,
 * and sets flag itIsUpdatedThread
 */
public class CDIEventListener implements ICDIEventListener {

	private ICDIThread currentThread = null; 
	private boolean itIsUpdatedThread = false;
	private DataModel dataModel = new DataModel(this);

	@Override
	public void handleDebugEvents(ICDIEvent[] events) {

		for (ICDIEvent event : events) {
			ICDIObject source = event.getSource();	
			if (source == null) { // when button stop pressed, fires ~4 times, after isTerminated
				setCurrentThread(null);
				setItIsUpdatedThread(false);
				return;
			}
			
			ICDITarget target = source.getTarget();
			if (target.isTerminated()) { // when button stop pressed, fires ~2 times, before source == null
				setCurrentThread(null);
				setItIsUpdatedThread(false);
				return;	
			}
			try {
				ICDIThread thread = target.getCurrentThread();
				setCurrentThread(thread);
				setItIsUpdatedThread(true);	
			} catch (CDIException e) {}
		}	
	}
	
	public DataModel getDataModel() {
		return dataModel;
	}
	
	public void setCurrentThread(ICDIThread thread) {
		currentThread = thread;
	}
	
	public void setItIsUpdatedThread(boolean value) {
		itIsUpdatedThread = value;
	}
	
	public ICDIThread getCurrentThread() {
		setItIsUpdatedThread(false);
		return currentThread;		
	}	
	
	public boolean isItUpdatedThread() {
		return itIsUpdatedThread;
	}
	
}
