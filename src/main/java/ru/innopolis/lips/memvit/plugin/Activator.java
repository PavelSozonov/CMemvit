package ru.innopolis.lips.memvit.plugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ru.innopolis.lips.memvit.controller.Controller;
import ru.innopolis.lips.memvit.controller.DebugEventsListenerRegistrator;

/**
 * The activator class controls the plug-in life cycle Instantiates Controller
 * and ListenerRegistrator
 * 
 * @author Pavel Sozonov
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "CMemvitPlugin"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	// Controller for managing the browser view
	private static Controller controller = new Controller();

	/**
	 * Getter for the controller class field
	 * 
	 * @return reference to the controller class field
	 */
	public static Controller getController() {
		return controller;
	}

	// Register listener, if session changed update registration
	private DebugEventsListenerRegistrator debugEventsListenerRegistrator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		debugEventsListenerRegistrator = new DebugEventsListenerRegistrator();
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
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
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
