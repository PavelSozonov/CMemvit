/**
 * 
 */
package ru.innopolis.lips.memvit.mocks;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDILocation;
import org.eclipse.cdt.debug.core.cdi.model.ICDISignal;
import org.eclipse.cdt.debug.core.cdi.model.ICDIStackFrame;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThreadStorage;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThreadStorageDescriptor;

/**
 * 
 * @author Pavel Sozonov
 */
public class MockICDIThread implements ICDIThread {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteStep#stepOver(int)
	 */
	@Override
	public void stepOver(int count) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteStep#stepOverInstruction
	 * (int)
	 */
	@Override
	public void stepOverInstruction(int count) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteStep#stepInto(int)
	 */
	@Override
	public void stepInto(int count) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteStep#stepIntoInstruction
	 * (int)
	 */
	@Override
	public void stepIntoInstruction(int count) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteStep#stepUntil(org.eclipse
	 * .cdt.debug.core.cdi.ICDILocation)
	 */
	@Override
	public void stepUntil(ICDILocation location) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteResume#resume(boolean)
	 */
	@Override
	public void resume(boolean passSignal) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteResume#resume(org.eclipse
	 * .cdt.debug.core.cdi.ICDILocation)
	 */
	@Override
	public void resume(ICDILocation location) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIExecuteResume#resume(org.eclipse
	 * .cdt.debug.core.cdi.model.ICDISignal)
	 */
	@Override
	public void resume(ICDISignal signal) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDISuspend#suspend()
	 */
	@Override
	public void suspend() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDISuspend#isSuspended()
	 */
	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIObject#getTarget()
	 */
	@Override
	public ICDITarget getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#getStackFrames()
	 */
	@Override
	public ICDIStackFrame[] getStackFrames() throws CDIException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#getStackFrames(int,
	 * int)
	 */
	@Override
	public ICDIStackFrame[] getStackFrames(int lowFrame, int highFrame) throws CDIException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#getStackFrameCount()
	 */
	@Override
	public int getStackFrameCount() throws CDIException {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#getThreadStorageDescriptors
	 * ()
	 */
	@Override
	public ICDIThreadStorageDescriptor[] getThreadStorageDescriptors() throws CDIException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#createThreadStorage(org
	 * .eclipse.cdt.debug.core.cdi.model.ICDIThreadStorageDescriptor)
	 */
	@Override
	public ICDIThreadStorage createThreadStorage(ICDIThreadStorageDescriptor varDesc) throws CDIException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#resume()
	 */
	@Override
	public void resume() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepOver()
	 */
	@Override
	public void stepOver() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepInto()
	 */
	@Override
	public void stepInto() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepOverInstruction()
	 */
	@Override
	public void stepOverInstruction() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepIntoInstruction()
	 */
	@Override
	public void stepIntoInstruction() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#stepReturn()
	 */
	@Override
	public void stepReturn() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#runUntil(org.eclipse.
	 * cdt.debug.core.cdi.ICDILocation)
	 */
	@Override
	public void runUntil(ICDILocation location) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#jump(org.eclipse.cdt.
	 * debug.core.cdi.ICDILocation)
	 */
	@Override
	public void jump(ICDILocation location) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.debug.core.cdi.model.ICDIThread#signal()
	 */
	@Override
	public void signal() throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#signal(org.eclipse.cdt
	 * .debug.core.cdi.model.ICDISignal)
	 */
	@Override
	public void signal(ICDISignal signal) throws CDIException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.debug.core.cdi.model.ICDIThread#equals(org.eclipse.cdt
	 * .debug.core.cdi.model.ICDIThread)
	 */
	@Override
	public boolean equals(ICDIThread thead) {
		// TODO Auto-generated method stub
		return false;
	}

}
