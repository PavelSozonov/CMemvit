/*
 * This file was automatically generated by EvoSuite
 * Sun Apr 02 21:19:03 GMT 2017
 */

package ru.innopolis.lips.memvit.utils;

import static org.evosuite.runtime.EvoAssertions.assertThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.innopolis.lips.memvit.model.ActivationRecord;
import ru.innopolis.lips.memvit.model.State;
import ru.innopolis.lips.memvit.model.StateImpl;
import ru.innopolis.lips.memvit.model.VarDescription;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true)
public class HtmlBuilder_ESTest extends HtmlBuilder_ESTest_scaffolding {

	@Test(timeout = 4000)
	public void test0() throws Throwable {
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[0];
		VarDescription[] varDescriptionArray0 = new VarDescription[1];
		// Undeclared exception!
		try {
			HtmlBuilder.composeStackTab(activationRecordArray0, "?-:)ZeTkt$q{fF5au", "?-:)ZeTkt$q{fF5au",
					varDescriptionArray0);
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			//
			// no message in exception (getMessage() returned null)
			//
			assertThrownBy("ru.innopolis.lips.memvit.utils.HtmlBuilder", e);
		}
	}

	@Test(timeout = 4000)
	public void test1() throws Throwable {
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder0 = new HtmlBuilder();
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[1];
		VarDescription[] varDescriptionArray0 = new VarDescription[1];
		VarDescription varDescription0 = new VarDescription(
				"<div class=\"ar collapsibleList\"><div class=\"ar_title\">Activation Record</div><table class=\"ar_info\"> <tr> <td class=\"n\">Function</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">File</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">Line</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">Static Link</td><td class=\"v\">%s</td></tr><tr> <td colspan=\"2\"> <table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Name</td><td>Value</td></tr></thead> <tbody> <tr> <td>%s</td><td colspan=\"3\" class=\"gr\">end address</td></tr>%s</tbody> </table> </td></tr></table></div>",
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
				"");
		VarDescription varDescription1 = new VarDescription("", "UmKq'<o SJka^S", "", "91Ux7P]7Wr%;+h+,9");
		varDescription0.addNested(varDescription1);
		varDescriptionArray0[0] = varDescription0;
		ActivationRecord activationRecord0 = new ActivationRecord(
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
				"",
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
				"", "UmKq'<o SJka^S", "", varDescriptionArray0, varDescriptionArray0);
		varDescription0.getName();
		activationRecordArray0[0] = activationRecord0;
		activationRecord0.setStaticLink("&Vey,kFK{O3H*~");
		activationRecord0.setFunctionName((String) null);
		// Undeclared exception!
		try {
			HtmlBuilder
					.composeStackTab(
							activationRecordArray0,
							"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
							(String) null, varDescriptionArray0);
			fail("Expecting exception: UnknownFormatConversionException");

		} catch (UnknownFormatConversionException e) {
			//
			// Conversion = ';'
			//
			assertThrownBy("java.util.Formatter", e);
		}
	}

	@Test(timeout = 4000)
	public void test2() throws Throwable {
		// Undeclared exception!
		try {
			HtmlBuilder.composeBrowserView((State) null);
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			//
			// no message in exception (getMessage() returned null)
			//
			assertThrownBy("ru.innopolis.lips.memvit.utils.HtmlBuilder", e);
		}
	}

	@Test(timeout = 4000)
	public void test3() throws Throwable {
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[1];
		VarDescription[] varDescriptionArray0 = new VarDescription[1];
		VarDescription varDescription0 = new VarDescription("d5Rc}T:`5:\"", "d5Rc}T:`5:\"", "d5Rc}T:`5:\"", "<");
		varDescription0.setAddress("k(%z");
		varDescriptionArray0[0] = varDescription0;
		VarDescription[] varDescriptionArray1 = new VarDescription[0];
		ActivationRecord activationRecord0 = new ActivationRecord("<", "<", "<", "d5Rc}T:`5:\"", "d5Rc}T:`5:\"", "<",
				varDescriptionArray0, varDescriptionArray1);
		activationRecord0.setArgs(varDescriptionArray1);
		activationRecordArray0[0] = activationRecord0;
		// Undeclared exception!
		try {
			HtmlBuilder.composeStackTab(activationRecordArray0, "d5Rc}T:`5:\"", "<", (VarDescription[]) null);
			fail("Expecting exception: UnknownFormatConversionException");

		} catch (UnknownFormatConversionException e) {
			//
			// Conversion = 'z'
			//
			assertThrownBy("java.util.Formatter$FormatSpecifier", e);
		}
	}

	@Test(timeout = 4000)
	public void test4() throws Throwable {
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[2];
		ActivationRecord activationRecord0 = new ActivationRecord("&amp;", "&amp;", "&amp;", "&amp;", "&amp;", "&amp;",
				(VarDescription[]) null, (VarDescription[]) null);
		activationRecordArray0[0] = activationRecord0;
		ActivationRecord activationRecord1 = new ActivationRecord(
				"&amp;",
				(String) null,
				"&amp;",
				"<table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Name</td><td>Value</td></tr></thead> <tbody>",
				"&amp;", "", (VarDescription[]) null, (VarDescription[]) null);
		activationRecordArray0[1] = activationRecord1;
		activationRecord1.setLineNumber("{Buq*B");
		String string0 = HtmlBuilder
				.composeStackTab(
						activationRecordArray0,
						"<table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Name</td><td>Value</td></tr></thead> <tbody>",
						"<table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Name</td><td>Value</td></tr></thead> <tbody>",
						(VarDescription[]) null);
		assertNotNull(string0);
	}

	@Test(timeout = 4000)
	public void test5() throws Throwable {
		StateImpl stateImpl0 = new StateImpl("oAcPCDx");
		stateImpl0.setData("&amp;");
		stateImpl0.setData("&amp;");
		stateImpl0.setData((String) null);
		HtmlBuilder.composeBrowserView(stateImpl0);
		stateImpl0.setData((String) null);
		HtmlBuilder.composeBrowserView(stateImpl0);
		VarDescription[] varDescriptionArray0 = new VarDescription[1];
		VarDescription varDescription0 = new VarDescription("oAcPCDx", "5k8v$al3G", "&amp;", "5k8v$al3G");
		varDescriptionArray0[0] = varDescription0;
		String string0 = HtmlBuilder
				.composeStackTab((ActivationRecord[]) null, "", (String) null, varDescriptionArray0);
		String string1 = HtmlBuilder.composeStackTab((ActivationRecord[]) null, "5k8v$al3G", ",n|F{2'l>$n]KQ",
				varDescriptionArray0);
		assertFalse(string1.equals(string0));
	}

	@Test(timeout = 4000)
	public void test7() throws Throwable {
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder0 = new HtmlBuilder();
		StateImpl stateImpl0 = new StateImpl((String) null);
		HtmlBuilder.composeBrowserView(stateImpl0);
		@SuppressWarnings("unused")
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[6];
		VarDescription[] varDescriptionArray0 = new VarDescription[6];
		VarDescription varDescription0 = new VarDescription("z* TjKB# Kccn!9z", "", " :-( ", "_ps0&y");
		varDescriptionArray0[0] = varDescription0;
		VarDescription varDescription1 = new VarDescription("", "", "", "_ps0&y");
		varDescriptionArray0[1] = varDescription1;
		@SuppressWarnings("unused")
		VarDescription varDescription2 = null;
		try {
			varDescription2 = new VarDescription((String) null, "", "", "_ps0&y");
			fail("Expecting exception: NullPointerException");

		} catch (NullPointerException e) {
			//
			// no message in exception (getMessage() returned null)
			//
			assertThrownBy("ru.innopolis.lips.memvit.model.VarDescription", e);
		}
	}

	@Test(timeout = 4000)
	public void test8() throws Throwable {
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder0 = new HtmlBuilder();
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder1 = new HtmlBuilder();
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[0];
		String string0 = "<";
		VarDescription[] varDescriptionArray0 = new VarDescription[1];
		VarDescription varDescription0 = new VarDescription("@$c$$i{VV2dIyCHz", "<", "&lt;", "<");
		VarDescription varDescription1 = new VarDescription("@$c$$i{VV2dIyCHz", "&lt;", "@$c$$i{VV2dIyCHz", "<");
		varDescription0.addNested(varDescription1);
		varDescriptionArray0[0] = varDescription0;
		varDescription0.setName("");
		varDescription0.addNested(varDescription0);
		// Undeclared exception!
		try {
			HtmlBuilder.composeStackTab(activationRecordArray0, string0, string0, varDescriptionArray0);
			fail("Expecting exception: StackOverflowError");

		} catch (StackOverflowError e) {
		}
	}

	@Test(timeout = 4000)
	public void test9() throws Throwable {
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder0 = new HtmlBuilder();
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder1 = new HtmlBuilder();
		@SuppressWarnings("unused")
		HtmlBuilder htmlBuilder2 = new HtmlBuilder();
		ActivationRecord[] activationRecordArray0 = new ActivationRecord[2];
		VarDescription[] varDescriptionArray0 = new VarDescription[2];
		VarDescription varDescription0 = new VarDescription("", "t@,/>W-C|", "qZ7WS}", "-%HlwruZ@>5HcL");
		varDescriptionArray0[0] = varDescription0;
		VarDescription varDescription1 = new VarDescription(
				"",
				"&",
				"",
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>");
		varDescriptionArray0[1] = varDescription1;
		VarDescription[] varDescriptionArray1 = new VarDescription[5];
		varDescriptionArray1[0] = varDescription0;
		varDescriptionArray1[1] = varDescription0;
		varDescriptionArray1[2] = varDescription0;
		varDescriptionArray1[3] = varDescription1;
		varDescriptionArray1[4] = varDescription0;
		ActivationRecord activationRecord0 = new ActivationRecord("63p=p6QX+", "-%HlwruZ@>5HcL", "3'8/N_t?x",
				"W\".U$U?}k&*<rTn", "", "3'8/N_t?x", varDescriptionArray0, varDescriptionArray1);
		activationRecordArray0[0] = activationRecord0;
		ActivationRecord activationRecord1 = new ActivationRecord(
				"",
				"<tr> <td class=\"c_addr\">%s</td><td class=\"c_type\">%s</td><td class=\"c_name\">%s</td><td class=\"c_value\"><label for=\"mln%s\">%s</label></td></tr>",
				"<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_name arg\">%s</td><td class=\"c_value arg\">%s</td></tr>",
				"&amp;", "&amp;", "3'8/N_t?x", varDescriptionArray0, varDescriptionArray0);
		activationRecordArray0[1] = activationRecord1;
		// Undeclared exception!
		try {
			HtmlBuilder.composeStackTab(activationRecordArray0, "W\".U$U?}k&*<rTn", "-%HlwruZ@>5HcL",
					varDescriptionArray0);
			fail("Expecting exception: MissingFormatArgumentException");

		} catch (MissingFormatArgumentException e) {
			//
			// Format specifier '%s'
			//
			assertThrownBy("java.util.Formatter", e);
		}
	}
}
