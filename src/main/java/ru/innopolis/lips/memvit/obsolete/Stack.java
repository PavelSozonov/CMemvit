package ru.innopolis.lips.memvit.obsolete;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.SWT;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.ui.part.ViewPart;

import ru.innopolis.lips.memvit.GDBCDIDebuggerMemvit;
import ru.innopolis.lips.memvit.utils.HtmlBuilder;

/*
 * Each second requests data from data model,
 * and draw it in the browser
 */
public class Stack extends ViewPart {
	
	private CDIEventListener cdiEventListener = null;
	private ICDISession cdiDebugSession = null;
	private Browser browser;

	/*
	 * Each second updates the stack view (browser) content
	 */
	class RunnableForThread2 implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try { Thread.sleep(1000); } catch (Exception e) { }
				Runnable task = () -> { visualizeStackCpp(); };
				Display.getDefault().asyncExec(task);
			}			
		}
	}
	
	/*
	 * Initialize listener and borwser.
	 * Create new thread for updating stack view (browser),
	 * set initial content to stack view
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		initializeCDIEventListener();
		initializeBrowser(parent);
		
//		Runnable runnable = new RunnableForThread2();
//		Thread thread2 = new Thread(runnable);
//		thread2.start();		
	}	
	
	/*
	 * Initialize cdiEventListener
	 */
	public void initializeCDIEventListener() {
		cdiEventListener = new CDIEventListener();
		getCdiSessionSetListener();
	}
	
	/*
	 * Initialize browser
	 */
	public void initializeBrowser(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setText("<html><body>Here will appear stack- and heap-related debug information</body></html>");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}	
	
	private boolean getCdiSessionSetListener() {
		ICDISession session = GDBCDIDebuggerMemvit.getSession();
		if (session == null) {
			return false;
		}
		if (session.equals(cdiDebugSession)) {
			return false;
		}
		cdiDebugSession = session;
		cdiDebugSession.getEventManager().addEventListener(cdiEventListener);
		return true;
	}
	
	/*
	 * Get CDI session, if thread is updated, draw new data in browser.
	 */
	private void visualizeStackCpp() {		
		getCdiSessionSetListener();
		if (cdiEventListener == null) { return; }
		if (!cdiEventListener.isItUpdatedThread()) { return; }
			
		String tabContent = HtmlBuilder.composeStackTab(
				cdiEventListener.getDataModel().getActivationRecords(),
				cdiEventListener.getDataModel().getEaxType(),
				cdiEventListener.getDataModel().getEaxValue(),
				cdiEventListener.getDataModel().getHeapVars()
				);
		
		browser.setText(tabContent);
	}	
	
	
	
	
	
	
	
	
	/*
	private boolean isExist(ArrayList<ICDIVariable> varlist, ICDIVariable variable) {
		Variable v = (Variable)variable;
		String hexAddress1 = CDIEventListener.getHexAddress(v);
		for (ICDIVariable var : varlist) {
			Variable variab = (Variable)var;
			String hexAddress2 = CDIEventListener.getHexAddress(variab);
			if (hexAddress1.equals(hexAddress2)) { return true; }	
		}
		return false;
	}
	*/
	
	/*
	private void fillVarList(ArrayList<ICDIVariable> varlist, ICDIVariable[] variables) {		
		for (ICDIVariable variable : variables) {
			if (!isExist(varlist, variable)) {varlist.add(variable);}
			ICDIValue value 			= CDIEventListener.getLocalVariableValue(variable);
			ICDIVariable[] subvariables = CDIEventListener.getLocalVariablesFromValue(value);
			fillVarList(varlist, subvariables);
		}
	}
	*/
	
	/*
	private void visualizeCdiVariables(TreeItem item, ICDIVariable[] variables) {
		if (variables == null) { return; }
		for (ICDIVariable lvariable : variables) {
			Variable variable = (Variable)lvariable;	
			
			ICDIValue value = CDIEventListener.getLocalVariableValue(lvariable);
			String valuestring = CDIEventListener.getValueString(value);
			String typename	= CDIEventListener.getLocalVariableTypeName(lvariable);
			String QualifiedName = CDIEventListener.getQualifiedName(lvariable);
			TreeItem subItem = new TreeItem(item, SWT.LEFT);
			String hexAddress = CDIEventListener.getHexAddress(variable);
			
			subItem.setText(0,typename + " " + QualifiedName + " : " + valuestring + " address: " + hexAddress);
			visualizeCdiVariables(subItem, CDIEventListener.getLocalVariablesFromValue(value));		
		}
	}
	*/
}