package main;

import java.io.*;
import java.util.ArrayList;


public class Loader {
	public static ArrayList<String> file = new ArrayList<>();

	public static void loadfile() {
		readFile();
	}

	private static void readFile() {
		System.out.println("Please insert File Name");
		try{
			BufferedReader inputConsole = new BufferedReader(new InputStreamReader(System.in));
			String input = inputConsole.readLine();
			inputConsole.close();
			if(!input.contains(".ply")){
				input+=".ply";
			}

			try {
				BufferedReader fileReader = new BufferedReader(new FileReader("./resource/"+input));
				String line = fileReader.readLine();
				while (line != null){
					file.add(line);
					line = fileReader.readLine();
				}
			}catch (IOException e){
				e.printStackTrace();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getHeader() {
		ArrayList<String> header = new ArrayList<>();
		for (int i = 0; i < file.size(); i++) {
			if (file.get(i).contains("end_header")){
				break;
			}else{
				header.add(file.get(i));
			}
		}
		return header;
	}
}
