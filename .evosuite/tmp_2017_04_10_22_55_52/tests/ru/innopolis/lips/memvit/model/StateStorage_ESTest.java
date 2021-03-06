/*
 * This file was automatically generated by EvoSuite
 * Mon Apr 10 20:07:02 GMT 2017
 */

package ru.innopolis.lips.memvit.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateImpl;
import ru.innopolis.lips.memvit.model.StateStorage;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class StateStorage_ESTest extends StateStorage_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      stateStorage0.addState((State) null);
      stateStorage0.addState((State) null);
      stateStorage0.getPreviousState();
      stateStorage0.getPreviousState();
      stateStorage0.getNextState();
      assertEquals(3, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      stateStorage0.addState((State) null);
      stateStorage0.getPreviousState();
      int int0 = stateStorage0.getNumberOfCurrentState();
      assertEquals(2, stateStorage0.getCurrentStep());
      assertEquals(1, int0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      stateStorage0.addState((State) null);
      stateStorage0.getLastState();
      assertEquals(2, stateStorage0.getNumberOfCurrentState());
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      stateStorage0.getStorageSize();
      assertEquals(1, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      StateImpl stateImpl0 = new StateImpl("");
      stateStorage0.addState(stateImpl0);
      stateStorage0.getPreviousState();
      assertEquals(1, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      StateImpl stateImpl0 = new StateImpl("");
      stateStorage0.addState(stateImpl0);
      stateStorage0.getNextState();
      assertEquals(1, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      StateImpl stateImpl0 = new StateImpl("vLwA>IY$h");
      stateStorage0.addState(stateImpl0);
      stateStorage0.getLastState();
      assertEquals(1, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      int int0 = stateStorage0.getCurrentStep();
      assertEquals(1, stateStorage0.getNumberOfCurrentState());
      assertEquals(1, int0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      stateStorage0.getAmountOfStates();
      assertEquals(1, stateStorage0.getNumberOfCurrentState());
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      // Undeclared exception!
      try { 
        stateStorage0.getPreviousState();
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      boolean boolean0 = stateStorage0.isEmpty();
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
      assertEquals(0, stateStorage0.getCurrentStep());
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.addState((State) null);
      boolean boolean0 = stateStorage0.isEmpty();
      assertEquals(1, stateStorage0.getNumberOfCurrentState());
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.getLastState();
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
      assertEquals(0, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      // Undeclared exception!
      try { 
        stateStorage0.getNextState();
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // no message in exception (getMessage() returned null)
         //
      }
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      int int0 = stateStorage0.getCurrentStep();
      assertEquals(0, int0);
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.getAmountOfStates();
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
      assertEquals(0, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      int int0 = stateStorage0.getNumberOfCurrentState();
      assertEquals(0, int0);
      assertEquals(0, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.getStorageSize();
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
      assertEquals(0, stateStorage0.getCurrentStep());
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      StateStorage stateStorage0 = new StateStorage();
      stateStorage0.resetStorage();
      assertEquals(0, stateStorage0.getCurrentStep());
      assertEquals(0, stateStorage0.getNumberOfCurrentState());
  }
}
