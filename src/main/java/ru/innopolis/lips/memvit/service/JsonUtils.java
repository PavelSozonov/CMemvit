package ru.innopolis.lips.memvit.service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import ru.innopolis.lips.memvit.model.ActivationRecord;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.VarDescription;

public class JsonUtils {

	/*
	 * Structure: Global variables (Name - Type - Value - Address);
	 * 
	 * Stack (Frames - File - Function - Arguments - Local variables);
	 * 
	 * Heap (Object name - Address - Value);
	 */
	public String buildJson(ActivationRecord[] stack, VarDescription[] heap, VarDescription[] globalStaticVariables) {

		if (stack == null || heap == null || globalStaticVariables == null) {
			System.out.println("Stack, heap or global description equal to null. Break json build!");
			return null;
		}

		JSONObject json = null;
		try {
			json = new JSONObject();

			json.put("stack", buildJsonStack(stack));
			json.put("heap", buildJsonHeap(heap));
			json.put("globalStaticVariables", buildJsonGlobalStaticVariables(globalStaticVariables));

		} catch (JSONException | CDIException e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	public ActivationRecord[] getStackFromJson(State state) {

		JSONTokener tokener = new JSONTokener(state.getData());
		JSONObject json = new JSONObject(tokener);
		JSONArray frames = json.getJSONArray("stack");

		ActivationRecord[] records = new ActivationRecord[frames.length()];

		int recordsCount = 0;
		for (Object frame : frames) {
			JSONObject frameAsJson = (JSONObject) frame;
			records[recordsCount++] = jsonFrameProcessing(frameAsJson);
		}

		return records;
	}

	public VarDescription[] getHeapFromJson(State state) {

		JSONTokener tokener = new JSONTokener(state.getData());
		JSONObject json = new JSONObject(tokener);
		JSONArray heapVariables = json.getJSONArray("heap");

		VarDescription[] heap = new VarDescription[heapVariables.length()];

		int heapCount = 0;
		for (Object heapVariable : heapVariables) {
			JSONObject heapVariableAsJson = (JSONObject) heapVariable;
			heap[heapCount++] = varProcessing(heapVariableAsJson);
		}

		return heap;
	}

	public VarDescription[] getGlobalStaticFromJson(State state) {

		JSONTokener tokener = new JSONTokener(state.getData());
		JSONObject json = new JSONObject(tokener);
		JSONArray globalStaticVariables = json.getJSONArray("globalStatic");

		VarDescription[] globalStatic = new VarDescription[globalStaticVariables.length()];

		int heapCount = 0;
		for (Object globalStaticVariable : globalStaticVariables) {
			JSONObject globalStaticVariableAsJson = (JSONObject) globalStaticVariable;
			globalStatic[heapCount++] = varProcessing(globalStaticVariableAsJson);
		}

		return globalStatic;
	}

	@SuppressWarnings({ "rawtypes" })
	private LinkedHashMap buildJsonGlobalStaticVariables(VarDescription[] globalVariableName) throws CDIException {

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
	@SuppressWarnings({ "rawtypes", "unused" })
	private LinkedHashMap buildJsonVirtualMemory() {
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private LinkedList buildJsonStack(ActivationRecord[] records) {
		LinkedList stack = new LinkedList();

		for (ActivationRecord record : records) {
			LinkedHashMap frame = new LinkedHashMap();
			frame.put("lineNumber", record.getLineNumber());
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
	private LinkedList buildJsonHeap(VarDescription[] vars) {
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

	private ActivationRecord jsonFrameProcessing(JSONObject frame) {

		ActivationRecord record = new ActivationRecord(frame.getString("lineNumber"), frame.getString("functionName"),
				frame.getString("fileName"), frame.getString("startAddress"), frame.getString("endAddress"),
				processingVars(frame.getJSONArray("vars")), processingVars(frame.getJSONArray("args")));

		return record;
	}

	private VarDescription[] processingVars(JSONArray vars) {

		VarDescription[] variables = new VarDescription[vars.length()];

		int varCount = 0;
		for (Object var : vars) {
			JSONObject varAsJson = (JSONObject) var;
			variables[varCount++] = varProcessing(varAsJson);

		}
		return variables;
	}

	private VarDescription varProcessing(JSONObject var) {
		VarDescription varDescription = new VarDescription(var.getString("address"), var.getString("type"),
				var.getString("value"), var.getString("name"));
		return varDescription;
	}

}
