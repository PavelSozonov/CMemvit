package ru.innopolis.lips.memvit.Util;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.json.JSONException;
import org.json.JSONObject;

import ru.innopolis.lips.memvit.Model.ActivationRecord;
import ru.innopolis.lips.memvit.Model.DataModel;
import ru.innopolis.lips.memvit.Model.VarDescription;

public class JsonBuilder {
	
	@SuppressWarnings({ "rawtypes" })
	private static LinkedHashMap buildJsonGlobalStaticVariables(VarDescription[] globalVariableName)
			throws CDIException {

		// TODO: change using target to using cdiEventListener

		/*
		 * LinkedHashMap ret = new LinkedHashMap();
		 * 
		 * ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		 * 
		 * String file = frames[0].getLocator().getFile(); String function =
		 * frames[0].getLocator().getFunction();
		 * 
		 * ICDIGlobalVariableDescriptor globalVariableDescriptor =
		 * target.getGlobalVariableDescriptors(file, function,
		 * globalVariableName); ICDIGlobalVariable globalVariable =
		 * target.createGlobalVariable(globalVariableDescriptor);
		 * 
		 * ret.put("type", globalVariableDescriptor.getTypeName());
		 * ret.put("value", globalVariable.getValue().getValueString());
		 * ret.put("name", globalVariableName);
		 */
		return null;
	}
	
	// TODO:
	@SuppressWarnings("rawtypes")
	private static LinkedHashMap buildJsonVirtualMemory() {
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static LinkedList buildJsonStack(ActivationRecord[] records) {
		LinkedList stack = new LinkedList();

		for (ActivationRecord record : records) {
			LinkedHashMap frame = new LinkedHashMap();
			frame.put("lineNumer", record.getLineNumber());
			frame.put("functionName", record.getFunctionName());
			frame.put("fileName", record.getFileName());
			frame.put("startAddress", record.getStartAddress());
			frame.put("endAddress", record.getEndAddress());
			// frame.put("staticLink", record.getStaticLink());

			LinkedList varsList = new LinkedList();
			for (VarDescription var : record.getVars()) {
				LinkedHashMap varMap = new LinkedHashMap();
				varMap.put("address", var.getAddress());
				varMap.put("name", var.getName());
				varMap.put("type", var.getType());
				varMap.put("value", var.getValue());
				// TODO: var.getNested();
				// varMap.put("nested", "");
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
				// argMap.put("nested", "");
				argsList.add(argMap);

			}
			frame.put("args", argsList);

			stack.add(frame);

		}
		return stack;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static LinkedList buildJsonHeap(VarDescription[] vars) {
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
	 * Global variables - Name - Type - Value - Address
	 * 
	 * Stack - Frames - File - Function - Arguments - Local variables
	 * 
	 * Heap - Object name - Address - Value
	 * 
	 * Virtual memory - Allocation
	 */
	public static String buildJson(ActivationRecord[] stack, VarDescription[] heap, VarDescription[] globalStaticVariables) 
			throws JSONException, CDIException {

		if (stack == null || heap == null || globalStaticVariables == null) {
			System.out.println("Stack, heap or global static description equal null. Break json build!");
			return null;
		}
		
		JSONObject json = new JSONObject();

		json.put("stack", buildJsonStack(stack));
		json.put("heap", buildJsonHeap(heap));
		json.put("globalStaticVariables", buildJsonGlobalStaticVariables(globalStaticVariables));

		// json.put("globalVariables", buildJsonGlobalVariables("g_GLOBAL"));
		// json.put("virtualMemory", buildJsonVirtualMemory());
		// json.put("lastReturnedType", model.getEaxType());
		// json.put("lastReturnedValue", model.getEaxValue());

		return json.toString();
	}
}
