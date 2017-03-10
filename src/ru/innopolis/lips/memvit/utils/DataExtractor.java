package ru.innopolis.lips.memvit.utils;

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
import org.json.JSONException;

import ru.innopolis.lips.memvit.model.ActivationRecord;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateImpl;
import ru.innopolis.lips.memvit.model.VarDescription;

/*
 * Takes thread and return all useful data as State object
 */
public class DataExtractor {

	private static List<VarDescription> heapVars = new ArrayList<>();
	@SuppressWarnings("unused")
	private static String eaxValueType;
	@SuppressWarnings("unused")
	private static String eaxValue;

	/*
	 * Base method, extract all information from thread, and forms result
	 */
	public static State extractData(ICDIThread thread) throws JSONException, CDIException {
		
		ActivationRecord[] stack = getActivationRecords(thread);
		VarDescription[] heap = getHeapVariables();
		VarDescription[] globalStaticVariables = getGlobalStaticVariables();
		
		String json = JsonUtils.buildJson(stack, heap, globalStaticVariables);
		if (json == null) return null;
		
		// String json = "Stub " + new Date();

		State result = new StateImpl(json);
		
		return result;
	}

	/*
	 * Takes frames from current thread, read data from each frame and return
	 * activation records array with data about all frames. Sets status of
	 * thread to not updated.
	 * 
	 * TODO: this method also finds heap variables, need to create separate method
	 */
	private static ActivationRecord[] getActivationRecords(ICDIThread thread) {

		heapVars = new ArrayList<>(); // TODO move to findHeapVariables();
		
		ICDIStackFrame[] frames = getStackFrames(thread);
		if (frames == null) return null;
		
		ActivationRecord[] records = processingFrames(frames);
		return records;
	}
	
	// Move functionality from getActivationRecords() and its submethods
	@SuppressWarnings("unused")
	private static void findHeapVariables() {
		heapVars = new ArrayList<>();
	}

	private static ICDIStackFrame[] getStackFrames(ICDIThread thread) {
		if (thread == null) {
			return null;
		}
		ICDIStackFrame[] frames = new ICDIStackFrame[0];
		try {
			frames = thread.getStackFrames();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return frames;
	}
	
	/*
	 * TODO: not implemented
	 */
	private static VarDescription[] getGlobalStaticVariables() {
		VarDescription[] globalStaticVariables = new VarDescription[0];  
		return globalStaticVariables;
	}

	/*
	 * Reads frame by frame, extract data and form array of activation records
	 * On each step updates fields eaxValue and eaxValueType
	 */
	private static ActivationRecord[] processingFrames(ICDIStackFrame[] frames) {
		
		ActivationRecord[] records = new ActivationRecord[frames.length];

		int recordCounter = 0;
		for (ICDIStackFrame frame : frames) {

			String functionName = getFunctionName(frame);
			String fileName = getFileName(frame);
			String startAddress = getStartAddress(frame);
			String endAddress = getEndAddress(frame);

			ICDILocalVariableDescriptor[] localVariables = GetStackFrameLocalVariableDescriptors(frame);
			VarDescription[] vars = localVariablesProcessing(localVariables, startAddress, endAddress);

			ICDIArgumentDescriptor[] argDescriptors = getStackFrameArgumentDescriptors(frame);
			VarDescription[] args = argsProcessing(argDescriptors); // extra
																	// space for
																	// return
																	// value

			updateEaxValueAndType(frame);

			String curLineNumber = String.valueOf(frame.getLocator()
					.getLineNumber());

			records[recordCounter++] = new ActivationRecord(curLineNumber,
					functionName, fileName, startAddress, endAddress,
					"Unknown (not implemented)"/* String staticLink */, vars,
					args);
		}
		
		return records;
	}
	
	/*
	 * Convert local variables descriptions in useful format
	 * 
	 * TODO: also finds heap variables, move functionality in separate method findHeapVariables()
	 */
	private static VarDescription[] localVariablesProcessing(ICDILocalVariableDescriptor[] localVariables, String startAddress, String endAddress) {

		ArrayList<VarDescription> convertedLocalVariables = new ArrayList<>();

		for (ICDILocalVariableDescriptor localVariable : localVariables) {

			ICDILocalVariable iCdiLocalVariable = getLocalVariable(localVariable);
			String hexAddress = getHexAddress((Variable) iCdiLocalVariable);
			String typeName = getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue cdiValue = getLocalVariableValue(iCdiLocalVariable);
			String valueString = getValueString(cdiValue);
			String qualifiedName = getQualifiedName(iCdiLocalVariable);

			VarDescription addedVar = new VarDescription(hexAddress, typeName, valueString, qualifiedName);
			
			if (hexAddress.compareTo(endAddress) >= 0 // TODO may be it make sense to change this check
					&& hexAddress.compareTo(startAddress) <= 0) {
				convertedLocalVariables.add(addedVar);
			} else {
				heapVars.add(addedVar); // TODO check is it work well? Move functionality in findHeapVariables()
			}
			
			fillVariableDescriptors(addedVar, cdiValue, startAddress, endAddress);
		}
		
		VarDescription[] result = new VarDescription[convertedLocalVariables.size()];
		convertedLocalVariables.toArray(result);
		
		return result;
	}
	
	private static VarDescription[] getHeapVariables() {
		VarDescription[] array = new VarDescription[heapVars.size()];
		heapVars.toArray(array);
		return array;
	}

	private static void fillVariableDescriptors(VarDescription var, ICDIValue cdivalue, String startAddress, String endAddress) {
		ICDIVariable[] subVariables = getLocalVariablesFromValue(cdivalue);
		if (subVariables == null) {
			return;
		}

		ArrayList<VarDescription> tempSubVars = new ArrayList<>();
		for (int k = 0; k < subVariables.length; k++) {
			ICDIVariable iCdiLocalVariable = subVariables[k];
			String hexAddress = getHexAddress((Variable) iCdiLocalVariable);
			String typeName = getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue subCDIValue = getLocalVariableValue(iCdiLocalVariable);
			String valueString = getValueString(subCDIValue);
			String qualifiedName = getQualifiedName(iCdiLocalVariable);
			VarDescription addedVar = new VarDescription(hexAddress, typeName,
					valueString, qualifiedName);
			if (hexAddress.compareTo(endAddress) >= 0
					&& hexAddress.compareTo(startAddress) <= 0) {
				tempSubVars.add(addedVar);
				var.addNested(addedVar);
			} else {
				heapVars.add(addedVar);
			}
			fillVariableDescriptors(addedVar, subCDIValue, startAddress,
					endAddress);
		}
		VarDescription[] subVars = new VarDescription[tempSubVars.size()]; // extra
																			// space
																			// for
																			// return
																			// value
		tempSubVars.toArray(subVars);
	}

	private static String getHexAddress(Variable variable) {
		String hexAddress = "";
		try {
			hexAddress = variable.getHexAddress();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return hexAddress;
	}

	private static ICDIValue getLocalVariableValue(ICDIVariable variable) {
		ICDIValue value = null;
		try {
			value = variable.getValue();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return value;
	}

	private static ICDILocalVariableDescriptor[] GetStackFrameLocalVariableDescriptors(
			ICDIStackFrame frame) {
		ICDILocalVariableDescriptor[] descriptor = new ICDILocalVariableDescriptor[0];
		try {
			descriptor = frame.getLocalVariableDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}

	private static ICDIArgumentDescriptor[] getStackFrameArgumentDescriptors(
			ICDIStackFrame frame) {
		ICDIArgumentDescriptor[] descriptor = new ICDIArgumentDescriptor[0];
		try {
			descriptor = frame.getArgumentDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}
	
	private static String getLocalVariableTypeName(ICDIVariable variable) {
		String typeName = null;
		try {
			typeName = variable.getTypeName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return typeName;
	}

	private static String getQualifiedName(ICDIVariableDescriptor variable) {
		String QualifiedName = null;
		try {
			QualifiedName = variable.getQualifiedName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return QualifiedName;
	}
	
	private static ICDIVariable[] getLocalVariablesFromValue(ICDIValue value) {
		ICDIVariable[] variables = null;
		try {
			variables = value.getVariables();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return variables;
	}

	private static String getValueString(ICDIValue value) {
		String valuestring = "";
		if (value == null) {
			return valuestring;
		}
		try {
			valuestring = value.getValueString();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return valuestring;
	}

	private static String getFileName(ICDIStackFrame frame) {
		return frame.getLocator().getFile();
	}

	private static String getFunctionName(ICDIStackFrame frame) {
		return frame.getLocator().getFunction();
	}

	private static String getStartAddress(ICDIStackFrame frame) {
		ICDIValue registerBasePointer = findRegisterValueByQualifiedName(frame, "$rbp");
		String startAddress = getValueString(registerBasePointer);
		if (startAddress.length() == 0) {
			registerBasePointer = findRegisterValueByQualifiedName(frame, "$ebp");
			startAddress = getValueString(registerBasePointer);
		}
		return startAddress;
	}

	private static String getEndAddress(ICDIStackFrame frame) {
		ICDIValue registerStackPointer = findRegisterValueByQualifiedName(frame, "$rsp");
		String endAddress = getValueString(registerStackPointer);
		if (endAddress.length() == 0) {
			registerStackPointer = findRegisterValueByQualifiedName(frame, "$esp");
			endAddress = getValueString(registerStackPointer);
		}
		return endAddress;
	}

	private static ICDIValue findRegisterValueByQualifiedName(
			ICDIStackFrame frame, String qualifedName) {
		ICDIValue value = null;
		ICDIRegisterGroup[] registerGroups = getICDIRegisterGroups(frame);
		for (ICDIRegisterGroup registerGroup : registerGroups) {
			ICDIRegisterDescriptor[] regDescriptors = getICDIRegisterDescriptors(registerGroup);
			for (ICDIRegisterDescriptor regDescriptor : regDescriptors) {
				ICDIRegister cdiRegister = createICDIRegister(frame,
						regDescriptor);
				String qName = getQualifiedName(cdiRegister);
				if (qName.equals(qualifedName)) {
					value = getRegisterValue(frame, cdiRegister);
				}
			}
		}
		return value;
	}

	private static ICDIRegisterGroup[] getICDIRegisterGroups(ICDIStackFrame frame) {
		ICDIRegisterGroup[] registerGroup = new ICDIRegisterGroup[0];
		try {
			registerGroup = frame.getThread().getTarget().getRegisterGroups();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return registerGroup;
	}

	private static ICDIRegisterDescriptor[] getICDIRegisterDescriptors(
			ICDIRegisterGroup registerGroup) {
		ICDIRegisterDescriptor[] regDescriptors = new ICDIRegisterDescriptor[0];
		try {
			regDescriptors = registerGroup.getRegisterDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return regDescriptors;
	}

	private static ICDIRegister createICDIRegister(ICDIStackFrame frame, ICDIRegisterDescriptor regDescriptor) {
		ICDIRegister register = null;
		try {
			register = frame.getTarget().createRegister(regDescriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return register;
	}

	private static ICDIValue getRegisterValue(ICDIStackFrame frame, ICDIRegister register) {
		ICDIValue value = null;
		try {
			value = register.getValue(frame);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return value;
	}

	private static ICDILocalVariable getLocalVariable(ICDILocalVariableDescriptor descriptor) {
		ICDILocalVariable variable = null;
		try {
			variable = descriptor.getStackFrame().createLocalVariable(
					descriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return variable;
	}

	/*
	 * Converts stack arguments description to VarDescription[]
	 */
	private static VarDescription[] argsProcessing(
			ICDIArgumentDescriptor[] argDescriptors) {
		int argsCounter = 0;
		VarDescription[] args = new VarDescription[argDescriptors.length]; // extra
																			// space
																			// for
																			// return
																			// value
		for (ICDIArgumentDescriptor argDescriptor : argDescriptors) {
			ICDILocalVariable icdilovalvariable = getArgument(argDescriptor);
			String hexAddress = getHexAddress((Variable) icdilovalvariable);
			String typeName = getLocalVariableTypeName(icdilovalvariable);
			ICDIValue icdValue = getLocalVariableValue(icdilovalvariable);
			String valueString = getValueString(icdValue);
			String qualifiedName = getQualifiedName(icdilovalvariable);
			args[argsCounter++] = new VarDescription(hexAddress, typeName,
					valueString, qualifiedName);
		}
		return args;
	}

	private static ICDIArgument getArgument(ICDIArgumentDescriptor descriptor) {
		ICDIArgument argument = null;
		try {
			argument = descriptor.getStackFrame().createArgument(descriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return argument;
	}

	/*
	 * Updates returned value and type
	 */
	private static void updateEaxValueAndType(ICDIStackFrame frame) {
		ICDIValue registerReturnValue = findRegisterValueByQualifiedName(frame, "$eax");
		eaxValue = getValueString(registerReturnValue);
		eaxValueType = getValueTypeName(registerReturnValue);
	}
	
	private static String getValueTypeName(ICDIValue value) {
		String valueTypeName = "";
		if (value == null) {
			return valueTypeName;
		}
		try {
			valueTypeName = value.getTypeName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return valueTypeName;
	}
}
