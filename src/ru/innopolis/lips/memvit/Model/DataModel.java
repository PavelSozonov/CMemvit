package ru.innopolis.lips.memvit.Model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIArgument;
import org.eclipse.cdt.debug.core.cdi.model.ICDIArgumentDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIInstruction;
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
import ru.innopolis.lips.memvit.Listener.CDIEventListener;

/* 
 * Is used by class Stack,
 * main methods:
 * getActivationRecords()
 * getEaxType()
 * getEaxValue()
 * getHeapVars()
 */
public class DataModel {
	
	private List<VarDescription> heapVars = new ArrayList<>();
	private String eaxValueType;
	private String eaxValue;
	private CDIEventListener cdiEventListener;
	
	/*
	 * Constructor
	 */
	public DataModel(CDIEventListener cdiEventListener) {
		this.cdiEventListener = cdiEventListener;
	}
	
	public VarDescription[] getHeapVars() {
		VarDescription[] array = new VarDescription[heapVars.size()];
		heapVars.toArray(array);
		return array;
	}
	
	public String getEaxType() {
		return eaxValueType;
	}
	
	public String getEaxValue() {
		return eaxValue;
	}
	
	public void setEaxType(String eaxType) {
		this.eaxValueType = eaxType;
	}
	
	public void setEaxValue(String eaxValue) {
		this.eaxValue = eaxValue;
	}

	/*
	 * Takes frames from current thread, read data from each frame
	 * and return activation records array with data about all frames.
	 * Set status of thread to not updated.
	 */
	public ActivationRecord[] getActivationRecords() {
		
		heapVars = new ArrayList<>();	
		ICDIStackFrame[] frames = DataModel.getStackFrames(cdiEventListener.getCurrentThread());
		ActivationRecord[] records = new ActivationRecord[frames.length];
		processingFrames(frames, records);
		cdiEventListener.setItIsUpdatedThread(false);
		return records;
	}

	/*
	 * Reads frame by frame, extract data and form array of activation records
	 * On each step updates fields eaxValue and eaxValueType
	 */
	private void processingFrames(ICDIStackFrame[] frames, ActivationRecord[] records) {
		
		int recordCounter = 0;
		
		for (ICDIStackFrame frame : frames) {
			
			String functionName = getFunctionName(frame);
			String fileName = getFileName(frame);
			String startAddress = getStartAddress(frame);
			String endAddress = getEndAddress(frame);
			
			ICDILocalVariableDescriptor[] varDescriptors = DataModel.GetStackFrameLocalVariableDescriptors(frame);
			
			ArrayList<VarDescription> tempVars = new ArrayList<>();
			
			for (ICDILocalVariableDescriptor varDescriptor : varDescriptors) {
				
				ICDILocalVariable iCdiLocalVariable = DataModel.getLocalVariable(varDescriptor);
				String hexAddress = DataModel.getHexAddress((Variable)iCdiLocalVariable);
				String typeName = DataModel.getLocalVariableTypeName(iCdiLocalVariable);
				ICDIValue cdiValue = DataModel.getLocalVariableValue(iCdiLocalVariable);
				String valueString = DataModel.getValueString(cdiValue);
				String qualifiedName = DataModel.getQualifiedName(iCdiLocalVariable);
				
				VarDescription addedVar = new VarDescription(hexAddress, typeName, valueString, qualifiedName);
				if (hexAddress.compareTo(endAddress) >=0  && hexAddress.compareTo(startAddress) <=0 ) {
					tempVars.add(addedVar);
				} else {
					heapVars.add(addedVar);
				}
				fillVariableDescriptors(addedVar, cdiValue, startAddress, endAddress);
			}
			VarDescription[] vars = new VarDescription[tempVars.size()];
			tempVars.toArray(vars); // TODO: missed left part
			
			ICDIArgumentDescriptor[] argDescriptors = DataModel.getStackFrameArgumentDescriptors(frame);
			VarDescription[] args = argsProcessing(argDescriptors); //extra space for return value
			

			updateEaxValueAndType(frame);	
			
			String curLineNumber = String.valueOf(frame.getLocator().getLineNumber());
			
			records[recordCounter++] = new ActivationRecord(curLineNumber, functionName, fileName, startAddress, endAddress, 
					"Unknown (not implemented)"/* String staticLink */, 
					vars, args);
		}
	}
	
	/*
	 * Converts stack arguments description to VarDescription[]
	 */
	private VarDescription[] argsProcessing(ICDIArgumentDescriptor[] argDescriptors) {
		int argsCounter = 0;
		VarDescription[] args = new VarDescription[argDescriptors.length]; //extra space for return value
		for (ICDIArgumentDescriptor argDescriptor : argDescriptors) {
			ICDILocalVariable icdilovalvariable =  DataModel.getArgument(argDescriptor);
			String hexAddress = DataModel.getHexAddress((Variable)icdilovalvariable);
			String typeName = DataModel.getLocalVariableTypeName(icdilovalvariable);
			ICDIValue icdValue = DataModel.getLocalVariableValue(icdilovalvariable);
			String valueString = DataModel.getValueString(icdValue);
			String qualifiedName = DataModel.getQualifiedName(icdilovalvariable);
			args[argsCounter++] = new VarDescription(hexAddress, typeName, valueString, qualifiedName);
		}
		return args;
	}
	
	private String getFileName(ICDIStackFrame frame) {
		return frame.getLocator().getFile();
	}
	
	private String getFunctionName(ICDIStackFrame frame) {
		return frame.getLocator().getFunction();
	}
	
	private String getStartAddress(ICDIStackFrame frame) {
		ICDIValue registerBasePointer = DataModel.findRegisterValueByQualifiedName(frame, "$rbp");
		String startAddress = DataModel.getValueString(registerBasePointer);
		if (startAddress.length() == 0) {
			registerBasePointer = DataModel.findRegisterValueByQualifiedName(frame, "$ebp");
			startAddress = DataModel.getValueString(registerBasePointer);
		}
		return startAddress;
	}
	
	private String getEndAddress(ICDIStackFrame frame) {
		ICDIValue registerStackPointer = DataModel.findRegisterValueByQualifiedName(frame, "$rsp");
		String endAddress = DataModel.getValueString(registerStackPointer);
		if (endAddress.length() == 0) {
			registerStackPointer = DataModel.findRegisterValueByQualifiedName(frame, "$esp");
			endAddress = DataModel.getValueString(registerStackPointer);
		}
		return endAddress;
	}
	
	/*
	 * Updates returned value and type
	 */
	private void updateEaxValueAndType(ICDIStackFrame frame) {
		ICDIValue registerReturnValue = DataModel.findRegisterValueByQualifiedName(frame, "$eax");
		eaxValue = DataModel.getValueString(registerReturnValue);	
		eaxValueType = DataModel.getValueTypeName(registerReturnValue);
	}
	
	public static ICDIStackFrame[] getStackFrames(ICDIThread thread) {
		if (thread == null) { return null; }
		ICDIStackFrame[] Frames = new ICDIStackFrame[0];
		try {
			Frames = thread.getStackFrames();
		} catch (CDIException e) { e.printStackTrace(); }
		return Frames;
	}
	
	public static ICDIStackFrame getTopStackFrame(ICDIThread thread) {
		if (thread == null) { return null; }
		ICDIStackFrame Frame = null;
		try {
			Frame = thread.getStackFrames()[0];
		} catch (CDIException e) {}		
		return Frame;
	}
	
	private void fillVariableDescriptors(VarDescription var, ICDIValue cdivalue, String startAddress, String endAddress) {
		ICDIVariable[] subVariables =  DataModel.getLocalVariablesFromValue(cdivalue);
		if (subVariables == null) { return; }
		
		ArrayList<VarDescription> tempSubVars = new ArrayList<>();
		for (int k = 0; k < subVariables.length; k++) {
			ICDIVariable iCdiLocalVariable =  subVariables[k];
			String hexAddress = DataModel.getHexAddress((Variable)iCdiLocalVariable);
			String typeName = DataModel.getLocalVariableTypeName(iCdiLocalVariable);
			ICDIValue subCDIValue = DataModel.getLocalVariableValue(iCdiLocalVariable);
			String valueString = DataModel.getValueString(subCDIValue);
			String qualifiedName = DataModel.getQualifiedName(iCdiLocalVariable);
			VarDescription addedVar = new VarDescription(hexAddress, typeName, valueString, qualifiedName);
			if (hexAddress.compareTo(endAddress) >= 0  && hexAddress.compareTo(startAddress) <= 0) {
				tempSubVars.add(addedVar);
				var.addNested(addedVar);
			} else {
				heapVars.add(addedVar);
			}
			fillVariableDescriptors(addedVar, subCDIValue, startAddress, endAddress);
		}
		VarDescription[] subVars = new VarDescription[tempSubVars.size()]; //extra space for return value
		tempSubVars.toArray(subVars);
	}
	
	public static ICDILocalVariableDescriptor[] GetStackFrameLocalVariableDescriptors(ICDIStackFrame frame) {
		ICDILocalVariableDescriptor[] descriptor = new ICDILocalVariableDescriptor[0];
		try {
			descriptor = frame.getLocalVariableDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}
	
	public static ICDIArgumentDescriptor[] getStackFrameArgumentDescriptors(ICDIStackFrame frame) {
		ICDIArgumentDescriptor[] descriptor = new ICDIArgumentDescriptor[0];
		try {
			descriptor = frame.getArgumentDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return descriptor;
	}
	
	public static ICDIValue getLocalVariableValue(ICDIVariable variable) {
		ICDIValue value = null;
		try {
			value = variable.getValue();
		} catch (CDIException e) {
			e.printStackTrace();
		}	
		return value;
	}
	
	public static String getLocalVariableTypeName(ICDIVariable variable) {
		String typeName = null;
		try {
			typeName = variable.getTypeName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return typeName;
	}
	
	public static String getQualifiedName(ICDIVariableDescriptor variable) {
		String QualifiedName = null;
		try {
			QualifiedName = variable.getQualifiedName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return QualifiedName;
	}
	
	public static String getValueString(ICDIValue value) {
		String valuestring = "";
		if (value == null) { return valuestring; }
		try {
			valuestring = value.getValueString();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return valuestring;
	}
	
	public static String getValueTypeName(ICDIValue value) {
		String valueTypeName = "";
		if (value == null) { return valueTypeName; }
		try {
			valueTypeName = value.getTypeName();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return valueTypeName;
	}
	
	public static ICDIVariable[] getLocalVariablesFromValue(ICDIValue value) {
		ICDIVariable[] variables = null;
		try {
			variables = value.getVariables();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return variables;
	}
	
	public static ICDILocalVariable getLocalVariable(ICDILocalVariableDescriptor descriptor) {
		ICDILocalVariable variable = null;
		try {
			 variable = descriptor.getStackFrame().createLocalVariable(descriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}		
		return variable;
	}
	
	public static ICDIArgument getArgument(ICDIArgumentDescriptor descriptor) {
		ICDIArgument argument = null;
		try {
			argument = descriptor.getStackFrame().createArgument(descriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}		
		return argument;
	}
	
	public static String getHexAddress (Variable variable) {
		String hexAddress = "";
		try {
			hexAddress = variable.getHexAddress();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return hexAddress;
	}
	
	public static ICDIRegisterGroup[]  getICDIRegisterGroups (ICDIStackFrame frame) {
		ICDIRegisterGroup[] registerGroup = new ICDIRegisterGroup[0];
		try {
			registerGroup = frame.getThread().getTarget().getRegisterGroups();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return registerGroup;
	}
	
	public static ICDIRegisterDescriptor[] getICDIRegisterDescriptors(ICDIRegisterGroup registerGroup) {
		ICDIRegisterDescriptor[] regDescriptors = new ICDIRegisterDescriptor[0];
		try {
			regDescriptors = registerGroup.getRegisterDescriptors();
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return regDescriptors;
	}
	
	public static ICDIRegister createICDIRegister(ICDIStackFrame frame, ICDIRegisterDescriptor regDescriptor) {
		ICDIRegister register = null;
		try {
			register = frame.getTarget().createRegister(regDescriptor);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return register;
	}
	
	public static ICDIValue getRegisterValue(ICDIStackFrame frame, ICDIRegister register) {
		ICDIValue value = null;
		try {
			value = register.getValue(frame);
		} catch (CDIException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static ICDIValue findRegisterValueByQualifiedName(ICDIStackFrame frame, String qualifedName) {
		ICDIValue value = null;
		ICDIRegisterGroup[] registerGroups = DataModel.getICDIRegisterGroups(frame);
		for (ICDIRegisterGroup registerGroup : registerGroups) {
			ICDIRegisterDescriptor[] regDescriptors = DataModel.getICDIRegisterDescriptors(registerGroup);
			for (ICDIRegisterDescriptor regDescriptor : regDescriptors) {
				ICDIRegister cdiRegister = DataModel.createICDIRegister(frame, regDescriptor);
				String qName = DataModel.getQualifiedName(cdiRegister);
				if (qName.equals(qualifedName)) {
					value = DataModel.getRegisterValue(frame, cdiRegister);
				}
			}
		}		
		return value;
	}
	
	public static ICDIInstruction[] getInstructions(ICDIStackFrame frame) {
		ICDIInstruction[] instructions = new ICDIInstruction[0];
		try {
			instructions = frame.getTarget().getInstructions(
					frame.getLocator().getFile(), frame.getLocator().getLineNumber());
		}
		catch (CDIException e) { e.printStackTrace(); }
		return instructions;
	}

}
