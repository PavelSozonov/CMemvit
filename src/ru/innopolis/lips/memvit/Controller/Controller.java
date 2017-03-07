package ru.innopolis.lips.memvit.Controller;

import java.util.Date;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.json.JSONException;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.Controller.DataExtractor.DataExtractor;
import ru.innopolis.lips.memvit.Model.State;
import ru.innopolis.lips.memvit.Model.StateStorage;
import ru.innopolis.lips.memvit.View.BrowserView;
import ru.innopolis.lips.memvit.View.View;

public class Controller {

	private StateStorage stateStorage = new StateStorage();
	
	public void handleEvent(ICDIThread thread) {
		
		View view = Activator.getBrowserView();
				
		// Extract data
		State newState = null;
		try {
			newState = DataExtractor.extractData(thread);
		} catch (CDIException e) {
			e.printStackTrace();
		}
				
		if (newState == null) {
			System.out.println("Null state returned, break handle!");
			return;
		}
		
		// Put data in storage
		// TODO: Add check for duplicated states
		stateStorage.addState(newState);
		
		// Update view
		if (view != null) view.update(newState);
		
	}
	
	public void handleBackButton() {
		View view = Activator.getBrowserView();
		State state = stateStorage.getPreviousState();
		view.update(state);
	}
	
	public void handleForwardButton() {
		View view = Activator.getBrowserView();
		State state = stateStorage.getNextState();
		view.update(state);
	}
}
