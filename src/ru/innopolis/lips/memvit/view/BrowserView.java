package ru.innopolis.lips.memvit.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.controller.Controller;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateStorage;
import ru.innopolis.lips.memvit.utils.HtmlBuilder;

/*
 * Used as extension point
 */
public class BrowserView extends ViewPart implements View {

	private Browser browser = null;
	private String content = null;
	private boolean contentChanged = false;;
	private Label stepNumber;
	
	public BrowserView() {
		Controller.setBrowserView(this);
	}
	
	/*
	 * Each 100 ms updates the browser view content
	 */
	class BrowserUpdater implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try { Thread.sleep(100); } catch (Exception e) { }
				Runnable task = () -> { renderBrowser(); };
				Display.getDefault().asyncExec(task);
			}			
		}
	}
	
	/*
	 * Update content of the browser view if there is new state
	 */
	private void renderBrowser() {		
		if (content != null && contentChanged && browser != null) {
			StateStorage stateStorage = Activator.getController().getStateStorage();
			setStepLabel(stateStorage.getCurrentStep(), stateStorage.getStorageSize());
			browser.setText(content);
			contentChanged = false;
		}
		//if (!contentChanged) System.out.println("!contentChanged");
	}	

	/*
	 * Initialize the browser view
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		initializeBrowser(parent);
		
		Runnable runnable = new BrowserUpdater();
		Thread thread = new Thread(runnable);
		thread.start();		

	}
	
	/*
	 * Update the content field to new state, set flag "changed"
	 * 
	 * (non-Javadoc)
	 * @see ru.innopolis.lips.memvit.view.View#update(ru.innopolis.lips.memvit.model.State)
	 */
	@Override
	public void update(State state) {
		
		if (state != null) {
			content = HtmlBuilder.composeBrowserView(state);
			contentChanged = true;
		}
	}

	@Override
	public void setFocus() {
		if (browser != null) browser.setFocus();
	}
	
	/*
	 * Initialize browser
	 */
	private void initializeBrowser(Composite parent) {

//		browser = new Browser(parent, SWT.NONE);
//		browser.setText("<html><body>Here will appear stack- and heap-related debug information </body></html>");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);	
		
		Composite buttonsLayout = new Composite(parent, SWT.NONE);
		
		
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = false;
		rowLayout.pack = true;
		rowLayout.type = SWT.HORIZONTAL;
		buttonsLayout.setLayout(rowLayout);
		
		Button backButton = new Button(buttonsLayout, SWT.PUSH | SWT.TOP);
		backButton.setText(" ← ");
		backButton.setEnabled(true);
		
		Button forwardButton = new Button(buttonsLayout, SWT.PUSH | SWT.TOP);
		forwardButton.setText(" → ");
		forwardButton.setEnabled(true);
		
		stepNumber = new Label(buttonsLayout, SWT.PUSH | SWT.MEDIUM);
		stepNumber.setText("");
		stepNumber.setEnabled(true);
		
		browser = new Browser(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 100;
		gridData.verticalSpan = 100;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		
		browser.setLayoutData(gridData);
		browser.setText("<html><body>Here will appear stack- and heap-related debug information </body></html>");	
	}
	
	public void setStepLabel(int step, int of) {
		stepNumber.setText("Step " + step + " / " + of);
	}

}
