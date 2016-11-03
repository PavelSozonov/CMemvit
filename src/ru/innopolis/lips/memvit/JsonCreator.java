package ru.innopolis.lips.memvit;

import java.util.LinkedHashMap;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariable;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariableDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.json.JSONObject;

public class JsonCreator {
	
	private ICDITarget target = null;

	private void writeJson(String json) {
		FileWriter2.writeJson(json);
	}

	private LinkedHashMap globalVariable(String globalVariableName) throws CDIException {
		
		LinkedHashMap ret = new LinkedHashMap();
		
		ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		
		String file = frames[0].getLocator().getFile();
		String function = frames[0].getLocator().getFunction();

		ICDIGlobalVariableDescriptor globalVariableDescriptor = 
				target.getGlobalVariableDescriptors(file, function, globalVariableName);
		ICDIGlobalVariable globalVariable = target.createGlobalVariable(globalVariableDescriptor);

		ret.put("type", globalVariableDescriptor.getTypeName());
		ret.put("value", globalVariable.getValue().getValueString());
		ret.put("name", globalVariableName);
		return ret;
	}
	
	private void setTarget(ICDITarget target) {
		this.target = target;
	}
	
	private Integer frameCount() throws CDIException {
		return target.getCurrentThread().getStackFrames().length;
	}
	
	// TODO:
	private String frames() throws CDIException {
		ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		for (ICDIStackFrame frame : frames) {
		}
		return "";
	}
	
	// TODO:
	private String threadNames() throws CDIException {
		ICDIThread[] threads = target.getThreads();
		String ret = ""; 
		for (ICDIThread thread : threads) {
			//ret += thread.
		}
		return "";
	}
	
	// TODO:
	private LinkedHashMap heap() {
		return null;
	}
	
	// TODO:
	private LinkedHashMap virtualMemory() {
		return null;
	}

	/*
	 * Structure:
	 * 
	 * Global variables
	 *  - Name
	 *  - Type
	 *  - Value
	 *  - Address
	 * 
	 * Stack
	 *  - Frames
	 * 	   - File
	 *     - Function
	 *     - Arguments
	 *     - Local variables
	 * 
	 * Heap
	 *  - Object name
	 *  - Address
	 *  - Value
	 * 
	 * Virtual memory
	 *  - Allocation
	 */
	public void targetProcessing(ICDITarget target) throws CDIException {
		
		//setTarget(target);
		/*
		JSONObject json = new JSONObject();
		json.put("frameCount", frameCount());
		
		json.put("globalVariable", globalVariable("g_GLOBAL"));
		json.put("stack", "");
		json.put("heap", "");
		json.put("virtualMemory", "");

		writeJson(json.toString());*/
	}
}
