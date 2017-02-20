package ru.innopolis.lips.memvit.Listener;

import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;

import ru.innopolis.lips.memvit.DataExtractor.DataExtractor;

/*
 * New listener of debug events
 * On each event invokes jsonCreator.saveStateToJson();
 */
public class CDIEventListenerForJson extends CDIEventListener {

	private DataExtractor dataExtractor;
	
	public CDIEventListenerForJson(DataExtractor dataExtractor) {
		super();
		this.dataExtractor = dataExtractor;
	}
	
	@Override
	public void handleDebugEvents(ICDIEvent[] event) {
		super.handleDebugEvents(event);
		dataExtractor.saveStateToJsonAndWriteToFile();
	}
}
