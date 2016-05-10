package main;

import java.io.*;
import java.util.ArrayList;


public class Loader {
	private static ArrayList<String> file = new ArrayList<>();
	private static ArrayList<String> header = new ArrayList<>();
	private static ArrayList<String> data = new ArrayList<>();

	public static void loadfile() {
		readFile();
		verifyFile();
		sortFile();
		System.out.println("Loaded File");
	}

	private static void sortFile() {
		boolean head = true;
		for (int i = 0; i < file.size(); i++) {
			String line = file.get(i);
			if (head) {
				header.add(line);
			} else {
				data.add(line);
			}

			if (line.contains("end_header")) {
				head = false;
			}
		}
	}

	private static void verifyFile() {
		ArrayList<String> tmpfile = new ArrayList<>();
		for (int i = 0; i < file.size(); i++) {
			boolean keep = true;
			String line = file.get(i);
			if (line.contains("format")) {
				if (!line.equals("format ascii 1.0")) {
					System.err.println(".ply has to be Ascii :");
					System.exit(-1);
				} else {
					keep = false;
				}

			}
			if (line.contains("comment")) {
				keep = false;
			}
			if (line.contains("ply")) {
				keep = false;
			}

			if (keep) {
				tmpfile.add(file.get(i));
			}
		}
		file = tmpfile;
	}

	private static void readFile() {
		System.out.println("Please insert File Name");
		try {
			BufferedReader inputConsole = new BufferedReader(
					new InputStreamReader(System.in));
			String input = inputConsole.readLine();
			inputConsole.close();
			if (!input.contains(".ply")) {
				input += ".ply";
			}

			try {
				//BufferedReader fileReader = new BufferedReader(new FileReader(".\\resource\\" + input));
				BufferedReader fileReader = new BufferedReader(
						new FileReader("resource\\" + input));
				String line = fileReader.readLine();
				while (line != null) {
					line = line.replace("\n", "").replace("\r", "")
							.replace("\0", "");
					file.add(line);
					line = fileReader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getHeader() {
		return header;
	}

	public static ArrayList<String> getData() {
		return data;
	}

}
