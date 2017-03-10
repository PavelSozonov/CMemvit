package ru.innopolis.lips.memvit.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import ru.innopolis.lips.memvit.controller.Controller;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.utils.HtmlBuilder;

/*
 * Used as extension point
 */
public class BrowserView extends ViewPart implements View {

	private Browser browser;
	private String content;
	private boolean isChanged;
	
	public BrowserView() {
		Controller.setBrowserView(this);
	}
	
	/*
	 * Each second updates the browser view content
	 */
	class RunnableForThread implements Runnable {
		
		@Override
		public void run() {
			while (true) {
				try { Thread.sleep(1000); } catch (Exception e) { }
				Runnable task = () -> { renderBrowser(); };
				Display.getDefault().asyncExec(task);
			}			
		}
	}
	
	/*
	 * Update content of the browser view if there is new state
	 */
	private void renderBrowser() {		
		if (content != null && isChanged) {
			browser.setText(content);
			isChanged = false;
		}
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
		
		Runnable runnable = new RunnableForThread();
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
			isChanged = true;
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
		browser = new Browser(parent, SWT.NONE);
		browser.setText("<html><body>Here will appear stack- and heap-related debug information </body></html>");
	}

}
