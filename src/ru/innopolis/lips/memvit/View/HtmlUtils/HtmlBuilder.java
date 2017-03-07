package ru.innopolis.lips.memvit.View.HtmlUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import ru.innopolis.lips.memvit.Model.ActivationRecord;
import ru.innopolis.lips.memvit.Model.State;
import ru.innopolis.lips.memvit.Model.VarDescription;

public class HtmlBuilder {
	
	private static String htmlHeader = "<html><head><title>Stack</title><style type=\"text/css\">body{background-color: white;}*{font-family: monospace; font-size:10pt;}div.ar{background-color: #FDF1FA; padding: 6px; margin-bottom: 12px; border: 1px solid #bbb;}div.ar_title{font-size: small; color: #669999;}.ar_info, .ar_info td{border: 1px solid #FDF1FA; border-collapse: collapse; padding: 4px;}.ar_vars, .ar_vars td{border: 1px solid #ccc; border-collapse: collapse; padding: 6px;}.ar_info .n, .ar_vars .title td{font-size: 10pt; color: #669999;}.ar_info{font-size: small; border-color: #FDF1FA;}.gr{color: grey; font-size: 8pt;} td.arg { background-color: #d9ffb3; } .collapsibleList li > input + *{display: none;}.collapsibleList li > input:checked + *{display: block;}.collapsibleList{list-style-type: none;}.collapsibleList li > input{display: none;}.collapsibleList label{cursor: pointer; text-decoration: underline;}.fixed{position: fixed; top: 0; left: 6;}.container{width: 100%; margin: auto;}.stack{width: 48%; float: left; overflow-y: auto;}.heap{margin-left: 2%; width: 46%; float: left; padding: 2px; overflow-y: auto;} .heap table { width: 100%; background-color: #FDF1FA; } .clear{clear: both;} b { margin-bottom: 10px; display: block; } </style></head><body><div class=\"container\"><div class=\"stack\"><b>Stack:</b>";
	
	private static String htmlSplitter = "</div><div class=\"heap\"><b>Heap:</b>";
	
	private static String htmlFooter = "</div><div class=\"clear\"></div></div></body><script>window.onload=function(){var avatarElem=document.getElementById('ff'); var avatarSourceBottom=avatarElem.getBoundingClientRect().bottom + window.pageYOffset; window.onscroll=function(){if (avatarElem.classList.contains('fixed') && window.pageYOffset < avatarSourceBottom){avatarElem.classList.remove('fixed');}else if (window.pageYOffset > avatarSourceBottom){avatarElem.classList.add('fixed');}};};</script></html>";

	// Template's params list: function, file, line, Static link, end address, args, rows, start address
	// address, return value type, return value
	private static String activationRecordTemplate = "<div class=\"ar collapsibleList\"><div class=\"ar_title\">Activation Record</div><table class=\"ar_info\"> <tr> <td class=\"n\">Function</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">File</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">Line</td><td class=\"v\">%s</td></tr><tr> <td class=\"n\">Static Link</td><td class=\"v\">%s</td></tr><tr> <td colspan=\"2\"> <table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Value</td><td>Name</td></tr></thead> <tbody> <tr> <td>%s</td><td colspan=\"3\" class=\"gr\">end address</td></tr>%s</tbody> </table> </td></tr></table></div>";

	// Template's params list: addr, type, value, name
	private static String varsRowTemplate = "<tr> <td class=\"c_addr\">%s</td><td class=\"c_type\">%s</td><td class=\"c_value\">%s</td><td class=\"c_name\">%s</td></tr>";
	private static String argsRowTemplate = "<tr> <td class=\"c_addr arg\">%s</td><td class=\"c_type arg\">%s</td><td class=\"c_value arg\">%s</td><td class=\"c_name arg\">%s</td></tr>";

	// Template's params list: addr, type, UNIQUE ID ,value, name
	private static String varsRowWithNestedTemplate = "<tr> <td class=\"c_addr\">%s</td><td class=\"c_type\">%s</td><td class=\"c_value\"><label for=\"mln%s\">%s</label></td><td class=\"c_name\">%s</td></tr>";
	
	private static String varsTableHeader = "<table class=\"ar_vars\"> <thead> <tr class=\"title\"> <td>Address</td><td>Type</td><td>Value</td><td>Name</td></tr></thead> <tbody>";
	private static String varsTableFooter = "</tbody></table>";
	
	private static int idCounter;
	
	{
		idCounter = 0;
	}
	
	public static String composeBrowserView(State state) {

		String html = state.getData();
		
		// TODO:
		
		if (html == null) return null;
		
		html = "<html><body><pre>" + html + "</pre></body></html>";
		
		ActivationRecord[] frames = getStackFromJson(state);
		String eaxType = "eaxType";
		String eaxValue = "eaxValue";
		VarDescription[] heapVars = getHeapFromJson(state);
		
		html = composeStackTab(frames, eaxType, eaxValue, heapVars);
		return html;
	}
	
	private static ActivationRecord[] getStackFromJson(State state) {

		JSONTokener tokener = new JSONTokener(state.toString());
		JSONObject json = new JSONObject(tokener);
		
		List frames = (LinkedList) json.get("stack");
		
		List<ActivationRecord> records = new LinkedList<ActivationRecord>();
		
		ActivationRecord record;
		
		for (Object frame : frames) {
			Map frameMap = (LinkedHashMap)frame;
			
			// TODO
			VarDescription[] args = new VarDescription[0];
			String staticLink = "Not implemented";
			
			List varsList = (LinkedList) frameMap.get("vars");
			VarDescription[] vars = new VarDescription[varsList.size()];
			int varsCount = 0;
			for (Object var : varsList) {
				Map varMap = (LinkedHashMap) var;
				
				vars[varsCount++] = new VarDescription(
						(String) varMap.get("address"), 
						(String) varMap.get("type"), 
						(String) varMap.get("value"), 
						(String) varMap.get("name")
						);
			}
			
//			LinkedList varsList = new LinkedList();
//			for (VarDescription var : record.getVars()) {
//				LinkedHashMap varMap = new LinkedHashMap();
//				varMap.put("address", var.getAddress());
//				varMap.put("name", var.getName());
//				varMap.put("type", var.getType());
//				varMap.put("value", var.getValue());
//				// TODO: var.getNested();
//				// varMap.put("nested", "");
//				varsList.add(varMap);
//			}
//			frame.put("vars", varsList);
			
			record = new ActivationRecord(
					(String) frameMap.get("lineNumber"), 
					(String) frameMap.get("functionName"), 
					(String) frameMap.get("fileName"), 
					(String) frameMap.get("startAddress"),
					(String) frameMap.get("endAddress"), 
					(String) staticLink, 
					vars, 
					args);
			
		}

		/*LinkedHashMap frame = new LinkedHashMap();
		frame.put("lineNumer", record.getLineNumber());
		frame.put("functionName", record.getFunctionName());
		frame.put("fileName", record.getFileName());
		frame.put("startAddress", record.getStartAddress());
		frame.put("endAddress", record.getEndAddress());
		String staticLink, 
			VarDescription[] vars, VarDescription[] args
		*/
		
		
		return null;
	}
	
	private static VarDescription[] getHeapFromJson(State state) {
		
		return null;
	}

	private static String getUniqueId(boolean nextPlease) {
		if (nextPlease) {
			idCounter++;
		}
		return Integer.toString(idCounter);
	}
	
	public static String composeStackTab(ActivationRecord[] frames, String eaxType, String eaxValue, VarDescription[] heapVars) {
		StringBuilder html = new StringBuilder();

		html.append(htmlHeader);
		html.append(String.format("<div class=\"ar\" id=\"ff\">EAX: (%s) %s<br>Program Counter: %s</div>", eaxType, eaxValue, getCurrentLineNumber(frames)));
		if (frames != null) {
			for (ActivationRecord frame : frames) {

				String varsTable = String.format(composeVarsTable(frame.getArgs(), frame.getVars(), true), frame.getStartAddress());

				String activationRecord = String.format(activationRecordTemplate, frame.getFunctionName(),
						frame.getFileName(), frame.getLineNumber(), frame.getStaticLink(), frame.getEndAddress(), varsTable);

				html.append(activationRecord);
			}
		}
		
		html.append(htmlSplitter);
		String heap = composeVarsTable(null, heapVars, false);
		html.append(heap);

		html.append(htmlFooter);

		return html.toString();
	}
	
	private static String getCurrentLineNumber(ActivationRecord[] recs) {
		if (recs != null && recs.length > 0) {
			return recs[0].getLineNumber();
		}
		return "Unknown";
	}
	
	private static String composeVarsTable(VarDescription[] args, VarDescription[] vars, boolean firstTime) {
		StringBuilder builder = new StringBuilder();
		
		if (!firstTime) {
			builder.append(varsTableHeader);
		}
		
		VarDescription[] nested;
		if (vars != null) {
			Arrays.sort(vars);
			for (VarDescription var : vars) {
				if ((nested = var.getNested()) != null && nested.length > 0) {
					builder.append(String.format(varsRowWithNestedTemplate, var.getAddress(), var.getType(),
							getUniqueId(true), var.getValue(), var.getName()));
					builder.append(String.format("<tr><td colspan=\"4\"><li><input type=\"checkbox\" id=\"mln%s\" /><div>", getUniqueId(false)));
					
					builder.append(composeVarsTable(null, nested, false));
					
					builder.append("</div></li></td></tr>");
				} else {
					builder.append(String.format(varsRowTemplate, var.getAddress(), var.getType(), var.getValue(),
							var.getName()));
				}
			}
		}
		
		if (firstTime) {
			builder.append("<tr><td>%s</td><td colspan=\"3\" class=\"gr\">start address</td></tr>");
		}
		
		if (args != null) {
			Arrays.sort(args);
			for (VarDescription arg : args) {
				builder.append(
						String.format(argsRowTemplate, arg.getAddress(), arg.getType(), arg.getValue(), arg.getName()));
			}
		}

		if (!firstTime) {
			builder.append(varsTableFooter);
		}
		return builder.toString();
	}
}
