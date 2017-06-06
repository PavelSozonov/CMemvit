package ru.innopolis.lips.memvit.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIArgument;
import org.eclipse.cdt.debug.core.cdi.model.ICDIArgumentDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDILocalVariable;
import org.eclipse.cdt.debug.core.cdi.model.ICDILocalVariableDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRegister;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRegisterDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRegisterGroup;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.cdi.model.ICDIValue;
import org.eclipse.cdt.debug.core.cdi.model.ICDIVariable;
import org.eclipse.cdt.debug.core.cdi.model.ICDIVariableDescriptor;
import org.eclipse.cdt.debug.mi.core.cdi.model.Variable;

import ru.innopolis.lips.memvit.model.ActivationRecord;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateImpl;
import ru.innopolis.lips.memvit.model.VarDescription;

/**
 * Take thread and return all useful data as a State object
 * 
 * @author Pavel Sozonov
 */
public class DataExtractor {

	private List<VarDescription> heapVars = new ArrayList<>();
	private ActivationRecord[] activationRecords;

	/**
	 * Base method, extract all the information from the thread, and form the
	 * result as a State
	 * 
	 * @param thread
	 *            from which will be extracted information about the current
	 *            state
	 * @return state object which contain all the information about the current
	 *         state
	 * @throws CDIException
	 */
	public State extractData(ICDIThread thread) throws CDIException {

		// Instance of the class for managing Json
		JsonUtils jsonUtils = new JsonUtils();

		fillHeapVarsAndActivationRecordsLists(thread);

		// Convert list to array
		VarDescription[] heap = getHeapVariables();

		// Not implemented
		VarDescription[] globalStaticVariables = getGlobalStaticVariables();

		// Build all data in one json
		String json = jsonUtils.buildJson(activationRecords, heap, globalStaticVariables);
		if (json == null)
			return null;

		State state = new StateImpl(json);
		return state;
	}

	/**
	 * Extract data from the thread and fill heapVars and activationRecords
	 * arrays
	 * 
	 * @param thread
	 *            from which will be extracted information about the current
	 *            state
	 * @throws CDIException
	 */
	private void fillHeapVarsAndActivationRecordsLists(ICDIThread thread) throws CDIException {

		heapVars = new ArrayList<>();

		ICDIStackFrame[] frames = getStackFrames(thread);

		ActivationRecord[] records;
		records = extractActivationRecordsFromFrames(frames);
		activationRecords = records;
	}

	private ICDIStackFrame[] getStackFrames(ICDIThread thread) throws CDIException {
		if (thread == null) {
			return null;
		}
		ICDIStackFrame[] frames = new ICDIStackFrame[0];
		try {
			frames = thread.getStackFrames();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		if (frames == null)
			throw new CDIException();
		return frames;
	}

	/*
	 * TODO: not implemented
	 */
	private VarDescription[] getGlobalStaticVariables() {
		VarDescription[] globalStaticVariables = new VarDescription[0];
		return globalStaticVariables;
	}

	/*
	 * Reads frame by frame, extract data and form array of activation records
	 */
	private ActivationRecord[] extractActivationRecordsFromFrames(ICDIStackFrame[] frames) throws CDIException {

		ActivationRecord[] records = new ActivationRecord[frames.length];

		int recordCounter = 0;
		for (ICDIStackFrame frame : frames) {

			String functionName = getFunctionName(frame);
			String fileName = getFileName(frame);
			String startAddress = getStartAddress(frame);
			String endAddress = getEndAddress(frame);

			ICDILocalVariableDescriptor[] localVariables = getLocalVariablesFromFrame(frame);
			VarDescription[] vars;
			vars = localVariablesProcessing(localVariables, startAddress, endAddress);

			ICDIArgumentDescriptor[] argDescriptors = getArgumentsFromFrame(frame);

			// extra space for return value
			VarDescription[] args = argsProcessing(argDescriptors);

			String curLineNumber = String.valueOf(frame.getLocator().getLineNumber());

			records[recordCounter++] = new ActivationRecord(curLineNumber, functionName, fileName, startAddress,
					endAddress, vars, args);
		}

		return records;
	}

	/**
	 * Convert local variables descriptions in useful format
	 * 
	 * @param localVariables
	 * @param startAddress
	 * @param endAddress
	 * @return
	 * @throws CDIException
	 */

	private VarDescription[] localVariablesProcessing(ICDILocalVariableDescriptor[] localVariables,
			String startAddress, String endAddress) throws CDIException {

		ArrayList<VarDescription> convertedLocalVariablesList = new ArrayList<>();

		for (ICDILocalVariableDescriptor localVariable : localVariables) {

			ICDILocalVariable iCdiLocalVariable = getLocalVariable(localVariable);
			String hexAddress = getHexAddress((Variable) iCdiLocalVariable);
			String typeName = getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue cdiValue = getLocalVariableValue(iCdiLocalVariable);
			String valueString = getValueString(cdiValue);
			String qualifiedName = getQualifiedName(iCdiLocalVariable);

			VarDescription variable = new VarDescription(hexAddress, typeName, valueString, qualifiedName);

			separateStackHeapVariables(startAddress, endAddress, hexAddress, convertedLocalVariablesList, variable);
			fillVariableDescriptors(variable, cdiValue, startAddress, endAddress);
		}

		// Convert list to array
		VarDescription[] convertedLocalVariablesArray = new VarDescription[convertedLocalVariablesList.size()];
		convertedLocalVariablesList.toArray(convertedLocalVariablesArray);

		return convertedLocalVariablesArray;
	}

	/**
	 * Convert list to array
	 * 
	 * @return a list as an array
	 */
	private VarDescription[] getHeapVariables() {
		VarDescription[] array = new VarDescription[heapVars.size()];
		heapVars.toArray(array);
		return array;
	}

	private void fillVariableDescriptors(VarDescription var, ICDIValue cdiValue, String startAddress, String endAddress)
			throws CDIException {
		ICDIVariable[] subVariables = getLocalVariablesFromValue(cdiValue);
		if (subVariables == null) {
			return;
		}

		List<VarDescription> subVarsList = new ArrayList<>();
		for (int k = 0; k < subVariables.length; k++) {
			ICDIVariable iCdiLocalVariable = subVariables[k];
			String hexAddress;
			hexAddress = getHexAddress((Variable) iCdiLocalVariable);
			String typeName = getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue subCDIValue = getLocalVariableValue(iCdiLocalVariable);
			String valueString = getValueString(subCDIValue);
			String qualifiedName = getQualifiedName(iCdiLocalVariable);
			VarDescription varToAdd = new VarDescription(hexAddress, typeName, valueString, qualifiedName);

			separateStackHeapVariables(startAddress, endAddress, hexAddress, subVarsList, var, varToAdd);

			fillVariableDescriptors(varToAdd, subCDIValue, startAddress, endAddress);
		}

		// extra space for return value
		VarDescription[] subVarsArray = new VarDescription[subVarsList.size()];
		subVarsList.toArray(subVarsArray);
	}

	private void separateStackHeapVariables(String startAddress, String endAddress, String hexAddress,
			List<VarDescription> stackVars, VarDescription varToAdd) {
		separateStackHeapVariables(startAddress, endAddress, hexAddress, stackVars, null, varToAdd);
	}

	private void separateStackHeapVariables(String startAddress, String endAddress, String hexAddress,
			List<VarDescription> stackVars, VarDescription var, VarDescription varToAdd) {
		// If it is the last frame, there is problem with end address, compiler
		// does not set rsp register properly

		if (startAddress.equals(endAddress)) {
			Long start;
			Long end;
			try {
				start = Long.parseLong(startAddress.substring(2), 16);
				end = Long.parseLong(endAddress.substring(2), 16);
			} catch (StringIndexOutOfBoundsException e) {
				return;
			}
			if (Math.abs(start - end) <= 10000) {
				stackVars.add(varToAdd);
				if (var != null)
					var.addNested(varToAdd);
			} else {
				heapVars.add(varToAdd);
			}
		} else {
			if (hexAddress.compareTo(endAddress) >= 0 && hexAddress.compareTo(startAddress) <= 0) {
				stackVars.add(varToAdd);
				if (var != null)
					var.addNested(varToAdd);
			} else {
				heapVars.add(varToAdd);
			}
		}
	}

	private String getHexAddress(Variable variable) throws CDIException {
		String hexAddress = "";
		hexAddress = variable.getHexAddress();
		return hexAddress;
	}

	private ICDIValue getLocalVariableValue(ICDIVariable variable) {
		ICDIValue value = null;
		try {
			value = variable.getValue();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return value;
	}

	private ICDILocalVariableDescriptor[] getLocalVariablesFromFrame(ICDIStackFrame frame) {
		ICDILocalVariableDescriptor[] descriptor = new ICDILocalVariableDescriptor[0];
		try {
			descriptor = frame.getLocalVariableDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}

	private ICDIArgumentDescriptor[] getArgumentsFromFrame(ICDIStackFrame frame) {
		ICDIArgumentDescriptor[] descriptor = new ICDIArgumentDescriptor[0];
		try {
			descriptor = frame.getArgumentDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}

	private String getLocalVariableTypeName(ICDIVariable variable) {
		String typeName = null;
		try {
			typeName = variable.getTypeName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	private String getQualifiedName(ICDIVariableDescriptor variable) {
		String QualifiedName = null;
		try {
			QualifiedName = variable.getQualifiedName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return QualifiedName;
	}

	private ICDIVariable[] getLocalVariablesFromValue(ICDIValue value) {
		ICDIVariable[] variables = null;
		try {
			variables = value.getVariables();
		} catch (CDIException e) {
			System.out.println(String.format("",
					"Debug: Target is not suspended[]. Class DataExtractor, getLocalVariablesFromValue(...);"));
			// e.printStackTrace();
		}
		return variables;
	}

	private String getValueString(ICDIValue value) {
		String valuestring = "";
		if (value == null) {
			return valuestring;
		}
		try {
			valuestring = value.getValueString();
		} catch (CDIException e) {
			System.out.println("Debug: Target is not suspended[]. Class DataExtractor, getValueString(...);");
			// e.printStackTrace();
		}
		return valuestring;
	}

	private String getFileName(ICDIStackFrame frame) {
		return frame.getLocator().getFile();
	}

	private String getFunctionName(ICDIStackFrame frame) {
		return frame.getLocator().getFunction();
	}

	private String getStartAddress(ICDIStackFrame frame) {
		ICDIValue registerBasePointer = findRegisterValueByQualifiedName(frame, "$rbp");
		String startAddress = getValueString(registerBasePointer);
		if (startAddress.length() == 0) {
			registerBasePointer = findRegisterValueByQualifiedName(frame, "$ebp");
			startAddress = getValueString(registerBasePointer);
		}
		return startAddress;
	}

	private String getEndAddress(ICDIStackFrame frame) {
		ICDIValue registerStackPointer = findRegisterValueByQualifiedName(frame, "$rsp");
		String endAddress = getValueString(registerStackPointer);
		if (endAddress.length() == 0) {
			registerStackPointer = findRegisterValueByQualifiedName(frame, "$esp");
			endAddress = getValueString(registerStackPointer);
		}
		return endAddress;
	}

	private ICDIValue findRegisterValueByQualifiedName(ICDIStackFrame frame, String qualifedName) {
		ICDIValue value = null;
		ICDIRegisterGroup[] registerGroups = getICDIRegisterGroups(frame);
		for (ICDIRegisterGroup registerGroup : registerGroups) {
			ICDIRegisterDescriptor[] regDescriptors = getICDIRegisterDescriptors(registerGroup);
			for (ICDIRegisterDescriptor regDescriptor : regDescriptors) {
				ICDIRegister cdiRegister = createICDIRegister(frame, regDescriptor);
				String qName = getQualifiedName(cdiRegister);
				if (qName.equals(qualifedName)) {
					value = getRegisterValue(frame, cdiRegister);
				}
			}
		}
		return value;
	}

	private ICDIRegisterGroup[] getICDIRegisterGroups(ICDIStackFrame frame) {
		ICDIRegisterGroup[] registerGroup = new ICDIRegisterGroup[0];
		try {
			registerGroup = frame.getThread().getTarget().getRegisterGroups();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return registerGroup;
	}

	private ICDIRegisterDescriptor[] getICDIRegisterDescriptors(ICDIRegisterGroup registerGroup) {
		ICDIRegisterDescriptor[] regDescriptors = new ICDIRegisterDescriptor[0];
		try {
			regDescriptors = registerGroup.getRegisterDescriptors();
		} catch (CDIException e) {
			System.out
					.println("Debug: Target is not suspended[]. Class DataExtractor, getICDIRegisterDescriptors(...);");
			// e.printStackTrace();
		}
		return regDescriptors;
	}

	private ICDIRegister createICDIRegister(ICDIStackFrame frame, ICDIRegisterDescriptor regDescriptor) {
		ICDIRegister register = null;
		try {
			register = frame.getTarget().createRegister(regDescriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return register;
	}

	private ICDIValue getRegisterValue(ICDIStackFrame frame, ICDIRegister register) {
		ICDIValue value = null;
		try {
			value = register.getValue(frame);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return value;
	}

	private ICDILocalVariable getLocalVariable(ICDILocalVariableDescriptor descriptor) {
		ICDILocalVariable variable = null;
		try {
			variable = descriptor.getStackFrame().createLocalVariable(descriptor);
		} catch (CDIException e) {
			System.out.println("Debug: Target is not suspended[]. Class DataExtractor, getLocalVariable(...);");
			// e.printStackTrace();
		}
		return variable;
	}

	/*
	 * Converts stack arguments description to VarDescription[]
	 */
	private VarDescription[] argsProcessing(ICDIArgumentDescriptor[] argDescriptors) throws CDIException {
		int argsCounter = 0;

		// extra space for return value
		VarDescription[] args = new VarDescription[argDescriptors.length];

		for (ICDIArgumentDescriptor argDescriptor : argDescriptors) {
			ICDILocalVariable iCdiLocalVariable = getArgument(argDescriptor);
			String hexAddress;
			hexAddress = getHexAddress((Variable) iCdiLocalVariable);
			String typeName = getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue icdValue = getLocalVariableValue(iCdiLocalVariable);
			String valueString = getValueString(icdValue);
			String qualifiedName = getQualifiedName(iCdiLocalVariable);
			args[argsCounter++] = new VarDescription(hexAddress, typeName, valueString, qualifiedName);
		}
		return args;
	}

	private ICDIArgument getArgument(ICDIArgumentDescriptor descriptor) {
		ICDIArgument argument = null;
		try {
			argument = descriptor.getStackFrame().createArgument(descriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return argument;
	}

	/*
	 * private String getValueTypeName(ICDIValue value) { String valueTypeName =
	 * ""; if (value == null) { return valueTypeName; } try { valueTypeName =
	 * value.getTypeName(); } catch (CDIException e) { e.printStackTrace(); }
	 * return valueTypeName; }
	 */
}
