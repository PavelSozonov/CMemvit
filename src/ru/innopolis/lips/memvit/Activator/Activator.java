package ru.innopolis.lips.memvit.Activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ru.innopolis.lips.memvit.DataExtractor.DataExtractor;

/**
 * The activator class controls the plug-in life cycle
 * Instantiates jsonCreator
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "Plugin2"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public static DataExtractor jsonCreator;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@ Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Vis: plugin Start!");
		super.start(context);
		plugin = this;
		jsonCreator = new DataExtractor();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@ Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Vis: plugin Stop!");
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		System.out.println("Vis: plugin get Default!");
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
