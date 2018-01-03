package javastory.budgetsh.stage4.server.budget.da.file.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javastory.budgetsh.stage4.server.club.da.file.io.FileConfig;
import javastory.budgetsh.stage4.server.club.util.FileDbWrapper;

public class AutoIdFile {
	//
	private FileDbWrapper autoIdFile;
	private FileDbWrapper autoIdTempFile;

	private static Map<String, Integer> keyIndexMap;

	static {
		keyIndexMap = new LinkedHashMap<String, Integer>();
		keyIndexMap.put("className", 0);
	}

	public AutoIdFile() {
		autoIdFile = new FileDbWrapper("Budget", FileConfig.getFileName("AutoId"), FileConfig.getDelimiter());
		autoIdTempFile = new FileDbWrapper("Budget", FileConfig.getFileName("AutoIdTemp"), FileConfig.getDelimiter());

		autoIdFile.setKeyIndexMap(keyIndexMap);
		autoIdTempFile.setKeyIndexMap(keyIndexMap);
	}

	public boolean exist(String className) {
		//
		boolean found = false;
		BufferedReader reader;

		String line;
		try {
			reader = autoIdFile.requestReader();
			while (!((line = reader.readLine()) == null)) {
				if (autoIdFile.hasValueOf("className", className, line)) {
					found = true;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return found;
	}

	public boolean write(AutoIdSequence autoIdSequence) {
		//
		if (this.exist(autoIdSequence.getClassName())) {
			return false;
		}
		PrintWriter writer;
		try {
			writer = autoIdFile.requestPrintWriter();
			writer.write(this.convertToLine(autoIdSequence));
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public AutoIdSequence read(String className) {
		//
		AutoIdSequence foundIdSequence = null;
		BufferedReader reader;

		String line;

		try {
			reader = autoIdFile.requestReader();
			while (!((line = reader.readLine()) == null)) {
				if (autoIdFile.hasValueOf("className", className, line)) {
					foundIdSequence = this.convertToObject(line);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		return foundIdSequence;
	}

	public void delete(String className) {
		if (!this.exist(className)) {
			return;
		}
		BufferedReader reader;
		PrintWriter writer;
		String line;
		try {
			reader = autoIdFile.requestReader();
			writer = autoIdTempFile.requestPrintWriter();

			while (!((line = reader.readLine()) == null)) {
				if (autoIdFile.hasValueOf("className", className, line)) {
					continue;
				}
				writer.write(line);
				writer.write("\r\n");
			}
			writer.close();
			reader.close();

		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	public void modify(AutoIdSequence idSequence) {
		//
		if (!this.exist(idSequence.getClassName())) {
			return;
		}
		BufferedReader reader;
		PrintWriter writer;
		String line;
		try {
			reader = autoIdFile.requestReader();
			writer = autoIdTempFile.requestPrintWriter();

			while (!((line = reader.readLine()) == null)) {
				if (autoIdFile.hasValueOf("className", idSequence.getClassName(), line)) {
					writer.write(this.convertToLine(idSequence));
					writer.write("\r\n");
				} else {
					writer.write(line);
					writer.write("\r\n");
				}
			}
			writer.close();
			reader.close();

		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		if(!autoIdFile.delete()) {
			System.out.println("failure to delete");
		}
		
		if(!autoIdTempFile.renameTo(autoIdFile)) {
			System.out.println("failure to reName");
		}
	}

	private AutoIdSequence convertToObject(String line) {
		return (AutoIdSequence) autoIdFile.convertTo(line, AutoIdSequence.class);
	}

	private String convertToLine(Object object) {
		return autoIdFile.convertFrom(object);
	}

}
