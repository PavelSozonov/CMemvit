package ru.innopolis.lips.memvit;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ru.innopolis.lips.memvit.Controller.Controller;
import ru.innopolis.lips.memvit.Controller.Listener.ListenerRegistrator;
import ru.innopolis.lips.memvit.View.BrowserView;

/**
 * The activator class controls the plug-in life cycle
 * Instantiates Controller and ListenerRegistrator
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "Plugin2"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	//public static DataExtractorObsolete jsonCreator;
	
	private static Controller controller = new Controller();
	private static ListenerRegistrator listenerRegistrator; 
	private static BrowserView browserView;
	
	public static void setController(Controller controller) {
		Activator.controller = controller;
	}
	
	public static Controller getController() {
		return controller;
	}
	
	public static void setBrowserView(BrowserView browserView) {
		Activator.browserView = browserView;
	}
	
	public static BrowserView getBrowserView() {
		return browserView;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@ Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		listenerRegistrator = new ListenerRegistrator();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@ Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
