package ru.innopolis.lips.memvit;

import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;

public class CDIEventListenerForJson extends CDIEventListener {

	private JsonCreator jsonCreator;
	
	public CDIEventListenerForJson(JsonCreator jsonCreator) {
		super();
		this.jsonCreator = jsonCreator;
	}
	
	@Override
	public void handleDebugEvents(ICDIEvent[] event) {
		super.handleDebugEvents(event);
		jsonCreator.saveStateToJson();
	}
}
