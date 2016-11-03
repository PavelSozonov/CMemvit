package ru.innopolis.lips.memvit;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDICondition;
import org.eclipse.cdt.debug.core.cdi.ICDILocator;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDIBreakpoint;
import org.eclipse.cdt.debug.core.cdi.model.ICDIExpression;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariable;
import org.eclipse.cdt.debug.core.cdi.model.ICDIGlobalVariableDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIInstruction;
import org.eclipse.cdt.debug.core.cdi.model.ICDILocalVariableDescriptor;
import org.eclipse.cdt.debug.core.cdi.model.ICDIMemoryBlock;
import org.eclipse.cdt.debug.core.cdi.model.ICDIMixedInstruction;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRegisterGroup;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRuntimeOptions;
import org.eclipse.cdt.debug.core.cdi.model.ICDISharedLibrary;
import org.eclipse.cdt.debug.core.cdi.model.ICDISignal;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThreadStorageDescriptor;

public class Analyzer {

	private void log(int nestingLevel, String message) {
		FileWriter2.writeLog(nestingLevel, message);
	}
	
	private void log(String message) {
		FileWriter2.writeLog(message);
	}

	private void log() {
		FileWriter2.writeLog("");
	}

	public void targetExploration(ICDITarget target) throws CDIException {
		log("EVENT");
		log("");
		//target.getCurrentThread().getStackFrames()[0].get
		
		//ICDIVariableDescriptor var = target.getGlobalVariableDescriptors(null, "main", null);
		//log("$$$ Global var name: " + var.getName());
		//log("$$$ Global var qalified name: " + var.getQualifiedName());
		//ICDIType type1 = var.getType();
		//if (type1 != null) {
		//	log("$$$ Global var type: " + type1.getTypeName());
		//}
		//log("");
		
		// Begin breakpoints
		log(1, "BREAKPOINTS");
		int breakpointsCount = 0;
		ICDIBreakpoint[] breakpoints = target.getBreakpoints();
		log(2, "ICDIBreakpoint[] breakpoints = target.getBreakpoints();\n");
		for (ICDIBreakpoint breakpoint : breakpoints) {
			log(2, "breakpoints[" + breakpointsCount + "]");
			
			// Begin condition
			log(3, "CONDITION");
			ICDICondition condition = breakpoint.getCondition();
			log(4, "ICDICondition condition = breakpoints[" + breakpointsCount + "].getCondition();");
			String expression = breakpoint.getCondition().getExpression();
			if (expression.equals("")) { expression = "\"\""; }
			log(4, "condition.getExpression() == " + expression);
			log(4, "condition.getCondition().getIgnoreCount() == " + breakpoint.getCondition().getIgnoreCount());
			String[] threadIds = breakpoint.getCondition().getThreadIds();
			log(4, "String[] threadIds = condition.getThreadIds();\n");
			int threadIdCount = 0;
			for (String threadId : threadIds) {
				log(5, "threadIds[" + threadIdCount++ + "] == " + threadId);
			}
			// End condition
			breakpointsCount++;
		}
		// End breakpoints

		// Begin target configuration
		log(1, "TARGET CONFIGURATION");
		ICDITargetConfiguration targetConfiguration = target.getConfiguration();
		log(2, "ICDITargetConfiguration targetConfiguration = target.getConfiguration();\n");
		targetConfiguration.supportsBreakpoints();
		log(2, "targetConfiguration.supportsBreakpoints() == " + targetConfiguration.supportsBreakpoints());
		targetConfiguration.supportsDisconnect();
		log(2, "targetConfiguration.supportsDisconnect() == " + targetConfiguration.supportsDisconnect());
		targetConfiguration.supportsExpressionEvaluation();
		log(2, "targetConfiguration.supportsExpressionEvaluation() == " + targetConfiguration.supportsExpressionEvaluation());
		targetConfiguration.supportsInstructionStepping();
		log(2, "targetConfiguration.supportsInstructionStepping() == " + targetConfiguration.supportsInstructionStepping());
		targetConfiguration.supportsMemoryModification();
		log(2, "targetConfiguration.supportsMemoryModification() == " + targetConfiguration.supportsMemoryModification());
		targetConfiguration.supportsMemoryRetrieval();
		log(2, "targetConfiguration.supportsMemoryRetrieval() == " + targetConfiguration.supportsMemoryRetrieval());
		targetConfiguration.supportsRegisterModification();
		log(2, "targetConfiguration.supportsRegisterModification() == " + targetConfiguration.supportsRegisterModification());
		targetConfiguration.supportsRegisters();
		log(2, "targetConfiguration.supportsRegisters() == " + targetConfiguration.supportsRegisters());
		targetConfiguration.supportsRestart();
		log(2, "targetConfiguration.supportsRestart() == " + targetConfiguration.supportsRestart());
		targetConfiguration.supportsResume();
		log(2, "targetConfiguration.supportsResume() == " + targetConfiguration.supportsResume());
		targetConfiguration.supportsSharedLibrary();
		log(2, "targetConfiguration.supportsSharedLibrary() == " + targetConfiguration.supportsSharedLibrary());
		targetConfiguration.supportsStepping();
		log(2, "targetConfiguration.supportsStepping() == " + targetConfiguration.supportsStepping());
		targetConfiguration.supportsSuspend();
		log(2, "targetConfiguration.supportsSuspend() == " + targetConfiguration.supportsSuspend());
		targetConfiguration.supportsTerminate();
		log(2, "targetConfiguration.supportsTerminate() == " + targetConfiguration.supportsTerminate());
		log();
		//End target configuration
		
		// Begin expressions
		log(1, "EXPRESSIONS");
		ICDIExpression[] expressions = target.getExpressions();
		log(2, "ICDIExpression[] expressions = target.getExpressions();");
		int expressionCount = 0;
		for (ICDIExpression expression : expressions) {
			log(2, "expressions[" + expressionCount + "].getExpressionText() == " + expression.getExpressionText());
			log(2, "$$$$$$");
			log(2, expression.getValue(target.getCurrentThread().getStackFrames()[0]).getValueString());
			//ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
			// expression.getValue(context);
			expressionCount++;
		}
		log();
		// End expressions
		
		// Begin global variable descriptor
		log(1, "GLOBAL VARIABLE DESCRIPTOR");
		log(2, "ICDIGlobalVariableDescriptor globalVariableDescriptor = target.getGlobalVariableDescriptors(filename, function, name);");
		log(2, "^^^");
		ICDIStackFrame[] framesGlob = target.getCurrentThread().getStackFrames();		
		ICDIGlobalVariableDescriptor globalVariableDescriptor = target.getGlobalVariableDescriptors(framesGlob[0].getLocator().getFile(), 
																									framesGlob[0].getLocator().getFunction(), 
																									"g_GLOBAL");
		ICDIGlobalVariable gv = target.createGlobalVariable(globalVariableDescriptor);
		log(2, "Type name: " + globalVariableDescriptor.getTypeName());
		log(2, "Expressions length: " + target.getExpressions().length);
		log(2, "Qualified name: " + globalVariableDescriptor.getQualifiedName());
		log(2, "gv: " + gv.getValue().getValueString());
		
		// End global variable descriptor
		
		// Begin instructions
		log(1, "INSTRUCTIONS");
		//ICDIInstruction[] instructions1 = target.getInstructions(startAddress, endAddress);
		//ICDIInstruction[] instructions2 = target.getInstructions(filename, linenum);
		//ICDIInstruction[] instructions3 = target.getInstructions(filename, linenum, lines);
		//getInstructions(
		//		frame.getLocator().getFile(), frame.getLocator().getLineNumber());
		ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();
		log(2, "ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();");
		int frameCount = 0;
		for (ICDIStackFrame instructionFrame : frames) {
			ICDIInstruction[] instructions = target.getInstructions(
												instructionFrame.getLocator().getFile(), 
												instructionFrame.getLocator().getLineNumber()
												);
			log(3, "frameCount == " + frameCount);
			log(3, "ICDIInstruction[] instructions = target.getInstructions(frame.getLocator().getFile(), frame.getLocator().getLineNumber());\n");

			int instructionCount = 0;
			for (ICDIInstruction instruction : instructions) {
				log(4, "instructionCount == " + instructionCount);
				log(4, "instruction.getAdress() == " + instruction.getAdress());
				log(4, "instruction.getArgs() == " + instruction.getArgs());
				log(4, "instruction.getFuntionName() == " + instruction.getFuntionName());
				log(4, "instruction.getInstruction() == " + instruction.getInstruction());
				log(4, "instruction.getOffset() == " + instruction.getOffset());
				log(4, "instruction.getOpcode() == " + instruction.getOpcode());
				log();
				instructionCount++;
			}
			frameCount++;
		}
		// End instructions
		
		// Begin memory blocks
		log(1, "MEMORY BLOCKS");
		ICDIMemoryBlock[] memoryBlocks = target.getMemoryBlocks();
		log(2, "ICDIMemoryBlock[] memoryBlocks = target.getMemoryBlocks();\n");
		log(2, "memoryBlocks.length == " + memoryBlocks.length);
		log();
		// End memory blocks

		// Begin mixed instructions
		log(1, "MIXED INSTRUCTIONS");
		//ICDIMixedInstruction[] mixedInstructions1 = target.getMixedInstructions(startAddress, endAddress);
		//ICDIMixedInstruction[] mixedInstructions2 = target.getMixedInstructions(filename, linenum);
		//ICDIMixedInstruction[] mixedInstructions3 = target.getMixedInstructions(filename, linenum, lines);
		ICDIStackFrame[] mixedFrames = target.getCurrentThread().getStackFrames();
		log(2, "ICDIStackFrame[] frames = target.getCurrentThread().getStackFrames();");
		int mixedFrameCount = 0;
		for (ICDIStackFrame mixedFrame : mixedFrames) {
			ICDIMixedInstruction[] instructionsMixed = target.getMixedInstructions(
												mixedFrame.getLocator().getFile(), 
												mixedFrame.getLocator().getLineNumber()
												);
			log(2, "mixedFrameCount == " + mixedFrameCount);
			log(2, "ICDIInstruction[] instructions = target.getInstructions(frame.getLocator().getFile(), frame.getLocator().getLineNumber());\n");

			int mixedInstructionCount = 0;
			for (ICDIMixedInstruction instructionMixed : instructionsMixed) {
				log(3, "mixedInstructionCount == " + mixedInstructionCount);
				log(3, "instructionMixed.getFileName() == " + instructionMixed.getFileName());
				log(3, "instructionMixed.getLineNumber() == " + instructionMixed.getLineNumber());
				log(3, "instructionMixed.getInstructions() // Omitted");
				log();
				mixedInstructionCount++;
			}
			mixedFrameCount++;
		}
		// End mixed instructions
		
		// Begin process
		log(1, "PROCESS");
		Process process = target.getProcess();
		log(2, "Process process = target.getProcess();\n");
		log(2, "isAlive(); getOutputStream(); getInputStream(); getErrorStream(); destroy(); exitValue();\n");
		// End process
		
		// Begin register group
		log(1, "REGISTER GROUP");
		ICDIRegisterGroup[] registerGroups = target.getRegisterGroups();
		log(2, "ICDIRegisterGroup[] registerGroups = target.getRegisterGroups();\n");
		int registerGroupCounter = 0;
		for (ICDIRegisterGroup registerGroup : registerGroups) {
			log(3, "registerGroupCounter == " + registerGroupCounter);
			log(3, "registerGroup.getName() == " + registerGroup.getName());
			log(3, "registerGroup.getRegisterDescriptors()");
			log(3, "registerGroup.hasRegisters() == " + registerGroup.hasRegisters()); 
			log();
			registerGroupCounter++;
		}
		// End register group
		
		// Begin runtime options
		log(1, "RUNTIME OPTIONS");
		ICDIRuntimeOptions runtimeOptions = target.getRuntimeOptions();
		log(2, "ICDIRuntimeOptions runtimeOptions = target.getRuntimeOptions();");
		log(2, "Ommited\n");
		// End runtime options
		
		// Begin session
		log(1, "SESSION");
		ICDISession session = target.getSession();
		log(2, "session.getAttribute(key); session.getConfiguration(); session.getEventManager(); session.getSessionProcess();\n");
		// End session
		
		// Begin shared libraries 
		log(1, "SHARED LIBRARIES");
		ICDISharedLibrary[] sharedLibraries = target.getSharedLibraries();
		log(2, "ICDISharedLibrary[] sharedLibraries = target.getSharedLibraries();\n");
		int sharedLibrariesCount = 0;
		for (ICDISharedLibrary sharedLibrary : sharedLibraries) {
			log(2, "sharedLibrariesCount == " + sharedLibrariesCount);
			log(2, "sharedLibrary.areSymbolsLoaded() == " + sharedLibrary.areSymbolsLoaded());
			log(2, "sharedLibrary.getEndAddress() == " + sharedLibrary.getEndAddress()); 
			log(2, "sharedLibrary.getFileName() == " + sharedLibrary.getFileName());
			log(2, "sharedLibrary.getStartAddress() == " + sharedLibrary.getStartAddress());
			sharedLibrariesCount++;
			log();
		}
		// End shared libraries

		// Begin signals
		log(1, "SIGNALS");
		ICDISignal[] signals = target.getSignals();
		log(2, "ICDISignal[] signals = target.getSignals();\n");
		int signalCount = 0;
		for (ICDISignal signal : signals) {
			log(2, "signalCount == " + signalCount);
			log(2, "signal.getDescription() == " + signal.getDescription());
			log(2, "signal.getName() == " + signal.getName()); 
			log(2, "signal.isIgnore() == " + signal.isIgnore());
			log(2, "signal.isStopSet() == " + signal.isStopSet());
			signalCount++;
			log();
		}
		// End signals

		// Begin paths
		log(1, "PATHS");
		String[] paths = target.getSourcePaths();
		log(2, "String[] paths = target.getSourcePaths();\n");
		int pathCount = 0;
		for (String path : paths) {
			log(2, "pathCount == " + pathCount);
			log(2, "path");
			pathCount++;
			log();
		}
		// End paths

		// Begin threads
		log(1, "THREADS");
		ICDIThread[] threads = target.getThreads();
		log(2, "ICDIThread[] threads = target.getThreads();");
		log(2, "See current thread (next)\n");
		// End threads
		
		// Begin current thread
		log(1, "CURRENT THREAD");
		ICDIThread currentThread = target.getCurrentThread();
		log(2, "ICDIThread currentThread = target.getCurrentThread();\n");
		log(2, "currentThread.getStackFrameCount() == " + currentThread.getStackFrameCount());
		log();
		
		// Begin stack frames
		log(2, "STACK FRAMES");
		ICDIStackFrame[] stackFrames = currentThread.getStackFrames();
		log(3, "ICDIStackFrame[] stackFrames = currentThread.getStackFrames();");
		int stackFrameCount = 0;
		for (ICDIStackFrame strackFrame : stackFrames) {
			log(4, "stackFrameCount == " + stackFrameCount);
			log(4, "strackFrame.getLevel() == " + strackFrame.getLevel());
			log();
			
			log(4, "LOCAL VARIABLE DESCRIPTORS");
			ICDILocalVariableDescriptor[] localVariableDescriptors = strackFrame.getLocalVariableDescriptors();
			log(5, "ICDILocalVariableDescriptor[] localVariableDescriptors = strackFrame.getLocalVariableDescriptors();\n");
			
			int localVariableDescriptorCount = 0;
			for (ICDILocalVariableDescriptor localVariableDescriptor : localVariableDescriptors) {
				log(5, "localVariableDescriptorCount == " + localVariableDescriptorCount);
				log(5, "localVariableDescriptor.getName() == " + localVariableDescriptor.getName());
				log(5, "localVariableDescriptor.getQualifiedName() == " + localVariableDescriptor.getQualifiedName());
				log(5, "localVariableDescriptor.getStackFrame()");
				log(5, "localVariableDescriptor.getTypeName() == " + localVariableDescriptor.getTypeName());
				log(5, "localVariableDescriptor.sizeof() == " + localVariableDescriptor.sizeof());
				localVariableDescriptorCount++;
				log();
			}
			
			// Begin locator
			log(4, "LOCATOR");
			ICDILocator locator = strackFrame.getLocator();
			log(5, "ICDILocator locator = strackFrame.getLocator();\n");
			log(5, "locator.getAddress() == " + locator.getAddress());
			log(5, "locator.getFile() == " + locator.getFile());
			log(5, "locator.getFunction() == " + locator.getFunction());
			log(5, "locator.getLineNumber() == " + locator.getLineNumber());
			// End locator
			log(4, "strackFrame.getThread()\n");
			
			stackFrameCount++;
		}
		
		log(2, "THREAD STORAGE DESCRIPTOR");
		ICDIThreadStorageDescriptor[] threadStorageDescriptors = currentThread.getThreadStorageDescriptors();
		log(3, "ICDIThreadStorageDescriptor[] threadStorageDescriptors = currentThread.getThreadStorageDescriptors();\n");
		int threadStorageDescriptorCount = 0;
		for (ICDIThreadStorageDescriptor threadStorageDescriptor : threadStorageDescriptors) {
			log(3, "threadStorageDescriptorCount == " + threadStorageDescriptorCount);
			log(3, "threadStorageDescriptor.getName() == " + threadStorageDescriptor.getName());
			log(3, "threadStorageDescriptor.getQualifiedName() == " + threadStorageDescriptor.getQualifiedName()); 
			log(3, "threadStorageDescriptor.getTypeName() == " + threadStorageDescriptor.getTypeName()); 
			log(3, "threadStorageDescriptor.sizeof() == " + threadStorageDescriptor.sizeof()); 
			threadStorageDescriptorCount++;
		}
		// End current thread
		
		log("\n\n"); // End event
	}

}
