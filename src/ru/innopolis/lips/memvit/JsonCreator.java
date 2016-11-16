package ru.innopolis.lips.memvit;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.swt.widgets.Display;
import org.json.JSONObject;

public class JsonCreator {
	
	//	private ICDITarget target = null;
	
	private CDIEventListenerForJson cdiEventListenerForJson;
	private ICDISession cdiDebugSession;
	private boolean cdiSessionGot = false;

	public JsonCreator() {
		cdiEventListenerForJson = new CDIEventListenerForJson(this);
		tryGetCdiSession();
		
		// Create new thread, it will work until cdi session will got
		Runnable runnable = new RunnableForThread();
		Thread thread2 = new Thread(runnable);
		thread2.start();
	
	}
	
	/*
	 * Each 100 ms try to got cdi session, when it got, break
	 */
	class RunnableForThread implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				cdiSessionGot = false;
				try { Thread.sleep(100); } catch (Exception e) { }
				Runnable task = () -> { 
					cdiSessionGot = tryGetCdiSession();
				};
				Display.getDefault().asyncExec(task);
				if (cdiSessionGot) break;
			}			
		}
	}
	
	/*
	 * Get CDI session, if thread is updated, save new state to json
	 */
	public void saveStateToJson() {		
		tryGetCdiSession();
		if (cdiEventListenerForJson == null) { return; }
		if (!cdiEventListenerForJson.isItUpdatedThread()) { return; }
			
		String jsonContent = buildJson();
		
		// write json to file
		writeJson(jsonContent);
	}	
	
	
	/*
	 * If it is new debug session, add event listener
	 */
	private boolean tryGetCdiSession() {	
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		
		if (session == null) { return false; }
		if (session.equals(cdiDebugSession)) { return false; }
		cdiDebugSession = session;
		cdiDebugSession.getEventManager().addEventListener(cdiEventListenerForJson);
		return true;
	}
	
	private void writeJson(String json) {
		FileWriter2.writeJson(json);
	}

	@SuppressWarnings({ "unused", "rawtypes" })
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
	
	@SuppressWarnings("unused")
	private void setTarget(ICDITarget target) {
		//this.target = target;
	}
	
	@SuppressWarnings("unused")
	private Integer frameCount() throws CDIException {
		//return target.getCurrentThread().getStackFrames().length;
		return null;
	}
	
	// TODO:
	@SuppressWarnings("unused")
	private String frames() throws CDIException {
		/*
		ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		for (ICDIStackFrame frame : frames) {
		}
		*/
		return "";
	}
	
	// TODO:
	@SuppressWarnings("rawtypes")
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	LinkedList buildJsonHeap(VarDescription[] vars) {
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
		return varsList;
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
	public String buildJson() {
		
		JSONObject json = new JSONObject();

		json.put("stack", buildJsonStack(cdiEventListenerForJson.getActivationRecords()));
		json.put("heap", buildJsonHeap(cdiEventListenerForJson.getHeapVars()));
		//json.put("globalVariables", buildJsonGlobalVariables("g_GLOBAL"));
		json.put("virtualMemory", buildJsonVirtualMemory());
		json.put("lastReturnedType", cdiEventListenerForJson.getEaxType());
		json.put("lastReturnedValue", cdiEventListenerForJson.getEaxValue());
		
		return json.toString();
	}
}
