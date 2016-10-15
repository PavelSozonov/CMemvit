package ru.innopolis.lips.memvit;

import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {

	public static void write(String message) {
		write(0, message);
	}

	public static void write(int nestingLevel, String message) {
		String indent = "";
		if (nestingLevel > 0) {
			for (int i = 0; i < nestingLevel; i++) { indent += "    "; }
		}
		String filePath = "/home/pavel/Projects/CMemvitLog.txt";
		try {
			FileWriter fw = new FileWriter(filePath, true);
	        fw.write(indent + message);
            fw.append('\n');  
            fw.flush();
            fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
