/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Mon Apr 10 20:08:24 GMT 2017
 */

package ru.innopolis.lips.memvit.view;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class BrowserView_ESTest_scaffolding {

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private org.evosuite.runtime.thread.ThreadStopper threadStopper =  new org.evosuite.runtime.thread.ThreadStopper (org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

  @BeforeClass 
  public static void initEvoSuiteFramework() { 
    org.evosuite.runtime.RuntimeSettings.className = "ru.innopolis.lips.memvit.view.BrowserView"; 
    org.evosuite.runtime.GuiSupport.initialize(); 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    org.evosuite.runtime.RuntimeSettings.sandboxMode = org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.init(); 
    initializeClasses();
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass 
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before 
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    org.evosuite.runtime.GuiSupport.setHeadless(); 
    org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After 
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("java.vm.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.specification.version", "1.8"); 
            java.lang.System.setProperty("java.home", "/usr/lib/jvm/java-8-openjdk-amd64/jre"); 
            java.lang.System.setProperty("java.awt.headless", "true"); 
    java.lang.System.setProperty("user.home", "/home/pavel"); 
                                        java.lang.System.setProperty("user.dir", "/home/pavel/Projects/Luna/workspace-luna-git-2017/CMemvit"); 
    java.lang.System.setProperty("java.io.tmpdir", "/tmp"); 
    java.lang.System.setProperty("awt.toolkit", "sun.awt.X11.XToolkit"); 
    java.lang.System.setProperty("file.encoding", "UTF-8"); 
    java.lang.System.setProperty("file.separator", "/"); 
        java.lang.System.setProperty("java.awt.graphicsenv", "sun.awt.X11GraphicsEnvironment"); 
    java.lang.System.setProperty("java.awt.printerjob", "sun.print.PSPrinterJob"); 
    java.lang.System.setProperty("java.class.path", "/tmp/EvoSuite_pathingJar8515971254990661222.jar"); 
    java.lang.System.setProperty("java.class.version", "52.0"); 
        java.lang.System.setProperty("java.endorsed.dirs", "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/endorsed"); 
    java.lang.System.setProperty("java.ext.dirs", "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext:/usr/java/packages/lib/ext"); 
    java.lang.System.setProperty("java.library.path", "lib"); 
    java.lang.System.setProperty("java.runtime.name", "OpenJDK Runtime Environment"); 
    java.lang.System.setProperty("java.runtime.version", "1.8.0_121-8u121-b13-0ubuntu1.16.04.2-b13"); 
    java.lang.System.setProperty("java.specification.name", "Java Platform API Specification"); 
    java.lang.System.setProperty("java.specification.vendor", "Oracle Corporation"); 
        java.lang.System.setProperty("java.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.vendor.url", "http://java.oracle.com/"); 
    java.lang.System.setProperty("java.version", "1.8.0_121"); 
    java.lang.System.setProperty("java.vm.info", "mixed mode"); 
    java.lang.System.setProperty("java.vm.name", "OpenJDK 64-Bit Server VM"); 
    java.lang.System.setProperty("java.vm.specification.name", "Java Virtual Machine Specification"); 
    java.lang.System.setProperty("java.vm.specification.vendor", "Oracle Corporation"); 
    java.lang.System.setProperty("java.vm.specification.version", "1.8"); 
    java.lang.System.setProperty("java.vm.version", "25.121-b13"); 
    java.lang.System.setProperty("line.separator", "\n"); 
    java.lang.System.setProperty("os.arch", "amd64"); 
    java.lang.System.setProperty("os.name", "Linux"); 
    java.lang.System.setProperty("os.version", "4.10.0-041000-generic"); 
    java.lang.System.setProperty("path.separator", ":"); 
    java.lang.System.setProperty("user.country", "RU"); 
    java.lang.System.setProperty("user.language", "ru"); 
    java.lang.System.setProperty("user.name", "pavel"); 
    java.lang.System.setProperty("user.timezone", "Europe/Moscow"); 
      }

  private static void initializeClasses() {
    org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(BrowserView_ESTest_scaffolding.class.getClassLoader() ,
      "org.eclipse.swt.events.HelpListener",
      "ru.innopolis.lips.memvit.view.BrowserView",
      "org.eclipse.ui.IWorkbenchPartSite",
      "org.osgi.framework.BundleActivator",
      "org.eclipse.jface.action.IAction",
      "org.eclipse.ui.IWorkbenchWindow",
      "org.eclipse.jface.action.AbstractAction",
      "org.eclipse.core.runtime.CoreException",
      "org.eclipse.osgi.util.NLS$MessagesProperties",
      "org.eclipse.swt.widgets.Scrollable",
      "org.json.JSONException",
      "org.eclipse.core.runtime.IStatus",
      "org.eclipse.ui.IPropertyListener",
      "org.eclipse.jface.resource.DeviceResourceDescriptor",
      "org.eclipse.swt.widgets.Composite",
      "org.eclipse.osgi.internal.util.SupplementDebug",
      "org.eclipse.core.runtime.AssertionFailedException",
      "org.eclipse.ui.services.IServiceLocator",
      "org.eclipse.ui.IViewPart",
      "org.eclipse.swt.widgets.Widget",
      "org.eclipse.swt.widgets.Decorations",
      "org.osgi.framework.BundleException",
      "org.eclipse.core.runtime.IAdaptable",
      "org.eclipse.ui.part.WorkbenchPart",
      "org.eclipse.ui.internal.AbstractWorkingSet",
      "org.eclipse.osgi.util.NLS",
      "org.eclipse.jface.action.Action$1",
      "org.eclipse.ui.IWorkingSet",
      "org.eclipse.jface.operation.IRunnableContext",
      "org.eclipse.cdt.debug.core.cdi.CDIException",
      "org.eclipse.jface.action.LegacyActionTools",
      "org.eclipse.ui.IPageService",
      "ru.innopolis.lips.memvit.utils.HtmlBuilder",
      "org.eclipse.osgi.util.NLS$1",
      "org.eclipse.swt.internal.SWTEventListener",
      "org.eclipse.osgi.util.NLS$2",
      "org.eclipse.jface.window.IShellProvider",
      "org.eclipse.ui.part.ViewPart",
      "org.eclipse.ui.internal.util.Util",
      "org.eclipse.core.runtime.ListenerList",
      "org.eclipse.swt.widgets.Event",
      "org.eclipse.ui.part.ViewPart$1",
      "org.eclipse.ui.IWorkbenchPart",
      "org.eclipse.core.runtime.IExecutableExtension",
      "org.eclipse.ui.part.IWorkbenchPartOrientation",
      "ru.innopolis.lips.memvit.view.View",
      "ru.innopolis.lips.memvit.utils.JsonUtils",
      "org.eclipse.swt.widgets.Listener",
      "org.eclipse.core.runtime.IConfigurationElement",
      "org.eclipse.ui.plugin.AbstractUIPlugin",
      "org.eclipse.swt.widgets.Canvas",
      "org.eclipse.jface.resource.ImageDataImageDescriptor",
      "org.eclipse.swt.graphics.Drawable",
      "org.eclipse.swt.widgets.Shell",
      "org.eclipse.swt.widgets.Layout",
      "org.eclipse.ui.internal.WorkbenchMessages",
      "org.eclipse.ui.IWorkbenchSite",
      "org.eclipse.ui.IPersistableElement",
      "org.eclipse.jface.action.Action",
      "org.eclipse.ui.IWorkbenchPart3",
      "org.eclipse.ui.IPersistable",
      "org.eclipse.ui.IWorkbenchPart2",
      "org.eclipse.core.runtime.Plugin",
      "org.eclipse.jface.resource.ImageDescriptor",
      "org.eclipse.core.commands.common.EventManager",
      "org.eclipse.swt.layout.RowLayout",
      "org.eclipse.ui.IWorkingSetManager",
      "ru.innopolis.lips.memvit.Activator",
      "org.eclipse.jface.action.IMenuCreator",
      "org.eclipse.swt.widgets.Control",
      "org.eclipse.swt.layout.GridLayout"
    );
  } 

  private static void resetClasses() {
    org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(BrowserView_ESTest_scaffolding.class.getClassLoader()); 

    org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "org.eclipse.core.commands.common.EventManager",
      "org.eclipse.core.runtime.ListenerList",
      "org.eclipse.jface.action.Action",
      "org.eclipse.osgi.util.NLS",
      "org.eclipse.osgi.util.NLS$MessagesProperties",
      "org.eclipse.osgi.internal.util.SupplementDebug",
      "org.eclipse.ui.internal.WorkbenchMessages",
      "org.eclipse.jface.action.LegacyActionTools",
      "org.eclipse.ui.internal.AbstractWorkingSet",
      "org.eclipse.ui.internal.util.Util",
      "org.eclipse.core.runtime.AssertionFailedException"
    );
  }
}
