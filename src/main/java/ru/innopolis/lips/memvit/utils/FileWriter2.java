package ru.innopolis.lips.memvit.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileWriter2 {

	private String file;

	public FileWriter2() {
		file = "~/CMemvitLog.txt";
	}

	public FileWriter2(String file) {
		this.file = file;
	}

	public void writeLog(String message) {
		writeLog(0, message);
	}

	public void writeLog(int nestingLevel, String message) {
		String indent = "";
		if (nestingLevel > 0) {
			for (int i = 0; i < nestingLevel; i++) {
				indent += "    ";
			}
		}
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(indent + message);
			fw.append('\n');
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String dateTimeToString() {
		long currentTime = System.currentTimeMillis();
		String curStringDate = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss-SSS").format(currentTime);
		return curStringDate;
	}

	public void writeJson(String json) {
		try {
			FileWriter fileWriter = new FileWriter(file, false);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
