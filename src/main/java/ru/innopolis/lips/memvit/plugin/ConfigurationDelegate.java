package ru.innopolis.lips.memvit.plugin;

import org.eclipse.cdt.launch.internal.LocalCDILaunchDelegate;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

/**
 * Used by the plugin extension point
 * 
 * @author Pavel Sozonov
 */
public class ConfigurationDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		LocalCDILaunchDelegate localCDI = new LocalCDILaunchDelegate();
		localCDI.launch(configuration, mode, launch, monitor);
	}

}
