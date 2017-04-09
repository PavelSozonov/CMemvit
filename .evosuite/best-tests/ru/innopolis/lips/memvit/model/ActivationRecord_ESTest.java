/*
 * This file was automatically generated by EvoSuite
 * Sun Apr 02 21:23:20 GMT 2017
 */

package ru.innopolis.lips.memvit.model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import ru.innopolis.lips.memvit.model.ActivationRecord;
import ru.innopolis.lips.memvit.model.VarDescription;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class ActivationRecord_ESTest extends ActivationRecord_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "&amp;", "&amp;", (String) null, (String) null, "", (VarDescription[]) null, (VarDescription[]) null);
      activationRecord0.getVars();
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("&amp;", activationRecord0.getLineNumber());
      assertEquals("&amp;", activationRecord0.getFunctionName());
      assertEquals("&amp;", activationRecord0.getFileName());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("dsJC ", "", "dsJC ", "", "~{", "", varDescriptionArray0, varDescriptionArray0);
      VarDescription[] varDescriptionArray1 = activationRecord0.getVars();
      VarDescription[] varDescriptionArray2 = new VarDescription[6];
      ActivationRecord activationRecord1 = new ActivationRecord("dsJC ", "", "x}.3aYDB2*[,q#0", "", "x}.3aYDB2*[,q#0", "", varDescriptionArray1, varDescriptionArray2);
      assertEquals("", activationRecord1.getStartAddress());
      assertEquals("", activationRecord1.getStaticLink());
      assertEquals("x}.3aYDB2*[,q#0", activationRecord1.getFileName());
      assertEquals("~{", activationRecord0.getEndAddress());
      assertEquals("", activationRecord1.getFunctionName());
      assertEquals("dsJC ", activationRecord1.getLineNumber());
      assertEquals("dsJC ", activationRecord0.getFileName());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "&amp;", "&amp;", "&amp;", "&amp;", "&amp;", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getStaticLink();
      assertEquals("&amp;", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("dsJC ", "", "dsJC ", "", "~{", "", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getStaticLink();
      assertEquals("~{", activationRecord0.getEndAddress());
      assertEquals("", activationRecord0.getFunctionName());
      assertEquals("dsJC ", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("dsJC ", activationRecord0.getFileName());
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("dsJC ", "", "dsJC ", "", "~{", "", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getStartAddress();
      assertEquals("dsJC ", activationRecord0.getFileName());
      assertEquals("~{", activationRecord0.getEndAddress());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("dsJC ", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getFunctionName());
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("8XXu=WZ`sN#pJZ*9Z", " :-( ", (String) null, "xyz", " :-( ", "", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getLineNumber();
      assertNotNull(string0);
      assertEquals("8XXu=WZ`sN#pJZ*9Z", string0);
      assertEquals(" :-( ", activationRecord0.getFunctionName());
      assertEquals("xyz", activationRecord0.getStartAddress());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals(" :-( ", activationRecord0.getEndAddress());
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "", "", "", "/rZ'z?4I/HA=T>qH", "/rZ'z?4I/HA=T>qH", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getLineNumber();
      assertEquals("", string0);
      assertEquals("", activationRecord0.getFunctionName());
      assertEquals("/rZ'z?4I/HA=T>qH", activationRecord0.getEndAddress());
      assertEquals("", activationRecord0.getFileName());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("/rZ'z?4I/HA=T>qH", activationRecord0.getStaticLink());
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("3h9d>&=8", "", "", "&amp;", "3h9d>&=8", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("", activationRecord0.getFunctionName());
      
      activationRecord0.setFunctionName("3h9d>&=8");
      activationRecord0.getFunctionName();
      assertEquals("&amp;", activationRecord0.getStartAddress());
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      ActivationRecord activationRecord0 = new ActivationRecord("", "", "", "&amp;", "&amp;", "&amp;", (VarDescription[]) null, (VarDescription[]) null);
      String string0 = activationRecord0.getFunctionName();
      assertEquals("", activationRecord0.getFileName());
      assertEquals("", string0);
      assertEquals("", activationRecord0.getLineNumber());
      assertEquals("&amp;", activationRecord0.getStartAddress());
      assertEquals("&amp;", activationRecord0.getStaticLink());
      assertEquals("&amp;", activationRecord0.getEndAddress());
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "&amp;", "&amp;", "&amp;", "&amp;", "&amp;", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getFileName();
      assertEquals("&amp;", string0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      ActivationRecord activationRecord0 = new ActivationRecord("@DgO)CZ_z5CT9", "O._eLGhJ#G-5", "", "", "@DgO)CZ_z5CT9", "e3lg", (VarDescription[]) null, (VarDescription[]) null);
      String string0 = activationRecord0.getFileName();
      assertEquals("e3lg", activationRecord0.getStaticLink());
      assertEquals("@DgO)CZ_z5CT9", activationRecord0.getLineNumber());
      assertEquals("O._eLGhJ#G-5", activationRecord0.getFunctionName());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("@DgO)CZ_z5CT9", activationRecord0.getEndAddress());
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "U,t\fiDScgL", "U,t\fiDScgL", "", "", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("", activationRecord0.getEndAddress());
      
      activationRecord0.setEndAddress((String) null);
      activationRecord0.getEndAddress();
      assertEquals("U,t\fiDScgL", activationRecord0.getFileName());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("U,t\fiDScgL", activationRecord0.getFunctionName());
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "U,t\fiDScgL", "U,t\fiDScgL", "", "", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("", activationRecord0.getEndAddress());
      
      activationRecord0.setEndAddress("+TcPp8;!?oAs>S");
      activationRecord0.getEndAddress();
      assertEquals("U,t\fiDScgL", activationRecord0.getFunctionName());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "&amp;", "&amp;", (String) null, (String) null, "", (VarDescription[]) null, (VarDescription[]) null);
      activationRecord0.getArgs();
      assertEquals("&amp;", activationRecord0.getFunctionName());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("&amp;", activationRecord0.getLineNumber());
      assertEquals("&amp;", activationRecord0.getFileName());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "U,t\fiDScgL", "U,t\fiDScgL", "", "", "", varDescriptionArray0, varDescriptionArray0);
      activationRecord0.getArgs();
      assertEquals("", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("U,t\fiDScgL", activationRecord0.getFileName());
      assertEquals("", activationRecord0.getEndAddress());
      assertEquals("U,t\fiDScgL", activationRecord0.getFunctionName());
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "H2-o>P[N", "KlR", "KlR", "&amp;", "H2-o>P[N", varDescriptionArray0, varDescriptionArray0);
      activationRecord0.getArgs();
      assertEquals("H2-o>P[N", activationRecord0.getStaticLink());
      assertEquals("&amp;", activationRecord0.getLineNumber());
      assertEquals("&amp;", activationRecord0.getEndAddress());
      assertEquals("KlR", activationRecord0.getStartAddress());
      assertEquals("H2-o>P[N", activationRecord0.getFunctionName());
      assertEquals("KlR", activationRecord0.getFileName());
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("8XXu=WZ`sN#pJZ*9Z", " :-( ", (String) null, "xyz", " :-( ", "", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getFileName();
      assertEquals(" :-( ", activationRecord0.getEndAddress());
      assertNull(string0);
      assertEquals("xyz", activationRecord0.getStartAddress());
      assertEquals(" :-( ", activationRecord0.getFunctionName());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("8XXu=WZ`sN#pJZ*9Z", activationRecord0.getLineNumber());
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("8XXu=WZ`sN#pJZ*9Z", " :-( ", (String) null, "xyz", " :-( ", "", varDescriptionArray0, varDescriptionArray0);
      activationRecord0.setArgs(varDescriptionArray0);
      assertEquals("8XXu=WZ`sN#pJZ*9Z", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("xyz", activationRecord0.getStartAddress());
      assertEquals(" :-( ", activationRecord0.getFunctionName());
      assertEquals(" :-( ", activationRecord0.getEndAddress());
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "", "", (String) null, "", (String) null, varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getStaticLink();
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "H2-o>P[N", "KlR", "KlR", "&amp;", "H2-o>P[N", varDescriptionArray0, varDescriptionArray0);
      assertEquals("KlR", activationRecord0.getFileName());
      
      activationRecord0.setFileName("");
      assertEquals("&amp;", activationRecord0.getEndAddress());
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "U,t\fiDScgL", "U,t\fiDScgL", "", "", "", varDescriptionArray0, varDescriptionArray0);
      activationRecord0.getVars();
      assertEquals("U,t\fiDScgL", activationRecord0.getFileName());
      assertEquals("U,t\fiDScgL", activationRecord0.getFunctionName());
      assertEquals("", activationRecord0.getStartAddress());
      assertEquals("", activationRecord0.getEndAddress());
      assertEquals("", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStaticLink());
  }

  @Test(timeout = 4000)
  public void test21()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord((String) null, (String) null, (String) null, "54Mfp?e.my9_NwB 5$", "wbIQnAM68zd$M", "54Mfp?e.my9_NwB 5$", varDescriptionArray0, varDescriptionArray0);
      activationRecord0.setVars(varDescriptionArray0);
      assertEquals("54Mfp?e.my9_NwB 5$", activationRecord0.getStartAddress());
      assertEquals("54Mfp?e.my9_NwB 5$", activationRecord0.getStaticLink());
      assertEquals("wbIQnAM68zd$M", activationRecord0.getEndAddress());
  }

  @Test(timeout = 4000)
  public void test22()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("3h9d>&=8", "", "", "&amp;", "3h9d>&=8", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("", activationRecord0.getStaticLink());
      
      activationRecord0.setStaticLink("3h9d>&=8");
      assertEquals("&amp;", activationRecord0.getStartAddress());
  }

  @Test(timeout = 4000)
  public void test23()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("", "U,t\fiDScgL", "U,t\fiDScgL", "", "", "", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getEndAddress();
      assertEquals("", activationRecord0.getLineNumber());
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("U,t\fiDScgL", activationRecord0.getFileName());
      assertEquals("U,t\fiDScgL", activationRecord0.getFunctionName());
      assertEquals("", string0);
      assertEquals("", activationRecord0.getStartAddress());
  }

  @Test(timeout = 4000)
  public void test24()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("3h9d>&=8", "", "", "&amp;", "3h9d>&=8", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("&amp;", activationRecord0.getStartAddress());
      
      activationRecord0.setStartAddress((String) null);
      activationRecord0.getStartAddress();
      assertEquals("", activationRecord0.getStaticLink());
      assertEquals("", activationRecord0.getFunctionName());
      assertEquals("3h9d>&=8", activationRecord0.getEndAddress());
      assertEquals("", activationRecord0.getFileName());
      assertEquals("3h9d>&=8", activationRecord0.getLineNumber());
  }

  @Test(timeout = 4000)
  public void test25()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[1];
      ActivationRecord activationRecord0 = new ActivationRecord("8XXu=WZ`sN#pJZ*9Z", " :-( ", (String) null, "xyz", " :-( ", "", varDescriptionArray0, varDescriptionArray0);
      assertEquals("8XXu=WZ`sN#pJZ*9Z", activationRecord0.getLineNumber());
      
      activationRecord0.setLineNumber("&amp;");
      assertEquals("xyz", activationRecord0.getStartAddress());
  }

  @Test(timeout = 4000)
  public void test26()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[4];
      ActivationRecord activationRecord0 = new ActivationRecord((String) null, " :-( ", "yv{TfwO)o4\"", "", (String) null, (String) null, varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getLineNumber();
      assertNull(string0);
      assertEquals("yv{TfwO)o4\"", activationRecord0.getFileName());
      assertEquals(" :-( ", activationRecord0.getFunctionName());
      assertEquals("", activationRecord0.getStartAddress());
  }

  @Test(timeout = 4000)
  public void test27()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "H2-o>P[N", "KlR", "KlR", "&amp;", "H2-o>P[N", varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getStartAddress();
      assertEquals("&amp;", activationRecord0.getEndAddress());
      assertEquals("KlR", string0);
      assertEquals("H2-o>P[N", activationRecord0.getStaticLink());
      assertEquals("&amp;", activationRecord0.getLineNumber());
      assertEquals("KlR", activationRecord0.getFileName());
      assertEquals("H2-o>P[N", activationRecord0.getFunctionName());
  }

  @Test(timeout = 4000)
  public void test28()  throws Throwable  {
      VarDescription[] varDescriptionArray0 = new VarDescription[0];
      ActivationRecord activationRecord0 = new ActivationRecord((String) null, (String) null, "&lt;", "Neeu.sb-NcSz-6H|z", (String) null, (String) null, varDescriptionArray0, varDescriptionArray0);
      String string0 = activationRecord0.getFunctionName();
      assertEquals("&lt;", activationRecord0.getFileName());
      assertEquals("Neeu.sb-NcSz-6H|z", activationRecord0.getStartAddress());
      assertNull(string0);
  }
}
