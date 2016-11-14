package ru.innopolis.lips.memvit;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariable;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariableDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.swt.widgets.Display;
import org.json.JSONException;
import org.json.JSONObject;

import ru.innopolis.lips.memvit.Stack.RunnableForThread2;

public class JsonCreator {
	
	//	private ICDITarget target = null;
	
	private CDIEventListener cdiEventListener = null;
	private ICDISession cdiDebugSession = null;

	public JsonCreator() {
		cdiEventListener = new CDIEventListener();
		tryGetCdiSession();
		
		// Create new thread to regular save state to json
		Runnable runnable = new RunnableForThread2();
		Thread thread2 = new Thread(runnable);
		thread2.start();
	}
	
	/*
	 * Each 100 ms check if thread state updated, save it to json
	 */
	class RunnableForThread2 implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try { Thread.sleep(100); } catch (Exception e) { }
				Runnable task = () -> { saveStateToJson(); };
				Display.getDefault().asyncExec(task);
			}			
		}
	}
	
	/*
	 * Get CDI session, if thread is updated, save new state to json
	 */
	private void saveStateToJson() {		
		tryGetCdiSession();
		if (cdiEventListener == null) { return; }
		if (!cdiEventListener.isItUpdatedThread()) { return; }
			
		String jsonContent = buildJson(
				cdiEventListener.getActivationRecords(),
				cdiEventListener.getEaxType(),
				cdiEventListener.getEaxValue(),
				cdiEventListener.getHeapVars()
				);
		
		// write json to file
		writeJson(jsonContent);
	}	
	
	
	/*
	 * If it is new debug session, add event listener
	 */
	private void tryGetCdiSession() {	
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		
		if (session == null) { return; }
		if (session.equals(this.cdiDebugSession)) { return; }
		else {
			this.cdiDebugSession = session;
			if (this.cdiDebugSession != null) { 
				this.cdiDebugSession.getEventManager().addEventListener(this.cdiEventListener); 
			}	
		}
	}
	
	private void writeJson(String json) {
		FileWriter2.writeJson(json);
	}

	private LinkedHashMap buildJsonGlobalVariables(String globalVariableName) throws CDIException {
		
		// TODO: change using target to using cdiEventListener
		
		/*
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
		*/
		return null;
	}
	
	private void setTarget(ICDITarget target) {
		//this.target = target;
	}
	
	private Integer frameCount() throws CDIException {
		//return target.getCurrentThread().getStackFrames().length;
		return null;
	}
	
	// TODO:
	private String frames() throws CDIException {
		/*
		ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		for (ICDIStackFrame frame : frames) {
		}
		*/
		return "";
	}
	
	// TODO:
	private LinkedHashMap buildJsonHeap() {
		return null;
	}
	
	// TODO:
	private LinkedHashMap buildJsonVirtualMemory() {
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LinkedList buildJsonStack(ActivationRecord[] records) {
		LinkedList stack = new LinkedList();
		
		for (ActivationRecord record : records) {
			LinkedHashMap frame = new LinkedHashMap();
			frame.put("lineNumer", record.getLineNumber());
			frame.put("functionName", record.getFunctionName());
			frame.put("fileName", record.getFileName());
			frame.put("startAddress", record.getStartAddress());
			frame.put("endAddress", record.getEndAddress());
			//frame.put("staticLink", record.getStaticLink());
			
			LinkedList varsList = new LinkedList();
			for (VarDescription var : record.getVars()) {
				LinkedHashMap varMap = new LinkedHashMap();
				varMap.put("address", var.getAddress());
				varMap.put("name", var.getName());
				varMap.put("type", var.getType());
				varMap.put("value", var.getValue());
				// TODO: var.getNested();
				//varMap.put("nested", "");
				varsList.add(varMap);
			}
			frame.put("vars", varsList);

			LinkedList argsList = new LinkedList();
			for (VarDescription arg : record.getArgs()) {
				LinkedHashMap argMap = new LinkedHashMap();
				argMap.put("address", arg.getAddress());
				argMap.put("name", arg.getName());
				argMap.put("type", arg.getType());
				argMap.put("value", arg.getValue());
				// TODO: var.getNested();
				//argMap.put("nested", "");
				argsList.add(argMap);
				
			}
			frame.put("args", argsList);

			stack.add(frame);			
			
		}
		return stack;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String buildJson(ActivationRecord[] records, String eaxType, String eaxValue, VarDescription[] vars) {
		
		JSONObject json = new JSONObject();

		json.put("stack", buildJsonStack(records));
		json.put("lastReturnedType", eaxType);
		json.put("lastReturnedValue", eaxValue);

		LinkedList varsList = new LinkedList();
		for (VarDescription var : vars) {
			LinkedHashMap varMap = new LinkedHashMap();
			varMap.put("address", var.getAddress());
			varMap.put("name", var.getName());
			varMap.put("type", var.getType());
			varMap.put("value", var.getValue());
			// TODO: var.getNested();
			varMap.put("nested", "");
			varsList.add(varMap);
		}
		json.put("variables", varsList);
		//json.put("globalVariables", buildJsonGlobalVariables("g_GLOBAL"));
		json.put("heap", buildJsonHeap());
		json.put("virtualMemory", buildJsonVirtualMemory());


		return json.toString();
	}
}
