package ru.innopolis.lips.memvit.View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import ru.innopolis.lips.memvit.Activator;
import ru.innopolis.lips.memvit.Model.State;
import ru.innopolis.lips.memvit.View.HtmlUtils.HtmlBuilder;

public class BrowserView extends ViewPart implements View {

	private Browser browser;
	private String content;
	private boolean isItChanged;
	
	public BrowserView() {
		Activator.setBrowserView(this);
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
	
	private void renderBrowser() {		
		if (content != null && isItChanged) {
			browser.setText(content);
			isItChanged = false;
		}
	}	

	@Override
	public void createPartControl(Composite parent) {
		initializeBrowser(parent);
		
		Runnable runnable = new RunnableForThread();
		Thread thread = new Thread(runnable);
		thread.start();		

	}
	
	@Override
	public void update(State state) {
		
		if (state != null) {
			content = HtmlBuilder.composeBrowserView(state);
			isItChanged = true;
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
		System.out.println("+ Browser is initialized!");
		browser.setText("<html><body>Here will appear stack- and heap-related debug information </body></html>");
	}

}
