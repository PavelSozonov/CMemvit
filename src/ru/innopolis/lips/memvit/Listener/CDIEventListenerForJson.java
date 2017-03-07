package ru.innopolis.lips.memvit.Listener;

import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;

import ru.innopolis.lips.memvit.Controller.DataExtractor.DataExtractorObsolete;

/*
 * New listener of debug events
 * On each event invokes jsonCreator.saveStateToJson();
 */
public class CDIEventListenerForJson extends CDIEventListener {

	private DataExtractorObsolete dataExtractorObsolete;
	
	public CDIEventListenerForJson(DataExtractorObsolete dataExtractorObsolete) {
		super();
		this.dataExtractorObsolete = dataExtractorObsolete;
	}
	
	@Override
	public void handleDebugEvents(ICDIEvent[] event) {
		super.handleDebugEvents(event);
		dataExtractorObsolete.saveStateToJsonAndWriteToFile();
	}
}
