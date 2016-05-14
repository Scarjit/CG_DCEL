package main;

import java.util.ArrayList;


public class DCELGenerator {

	static int n_vertex = 0;
	static int n_face = 0;
	static int n_edge = 0;

	static int vertex_properties = 0;
	static int face_properties = 0;
	static int edge_properties = 0;

	static int header_lenght = 0;

	static ArrayList<String> vertexArray;
	static ArrayList<String> faceArray;
	static ArrayList<String> edgeArray;
	static String sort = "";

	public static DCELMesh generate(DCELMesh mesh) {
		compileHeader();
		System.out.println("//////Vertex//////");
		vertexArray = getVertex();
		System.out.println("//////Face//////");
		faceArray = getFace();
		System.out.println("//////Edge//////");
		edgeArray = getEdge();
		System.out.println("//////File Info//////");
		System.out.println("Vertex: "+n_vertex);
		System.out.println("Face: "+n_face);
		System.out.println("Edge: "+n_edge);
		System.out.println("Vertex Prop: "+vertex_properties);
		System.out.println("Face Prop: "+face_properties);
		System.out.println("Edge Prop: "+edge_properties);
		System.out.println("Sort: "+sort);
		System.out.println("////////////");

		return null;
	}

	private static int[] StringToInt3(String in){
		int[] ret = new int[3];
		ret[0] = Integer.parseInt(""+in.charAt(0));
		ret[1] = Integer.parseInt(""+in.charAt(2));
		ret[2] = Integer.parseInt(""+in.charAt(4));
		return ret;
	}

	private static ArrayList<String> getVertex() {
		ArrayList<String> vArray = new ArrayList<String>();
		int offset = header_lenght+1;
		if(sort.charAt(0) == 'f'){
			offset += n_face;
			if(sort.charAt(1) == 'e'){
				offset += n_edge;
			}
		}
		if(sort.charAt(0) == 'e'){
			offset += n_edge;
			if(sort.charAt(1) == 'f'){
				offset += n_face;
			}
		}
		int length = offset+n_vertex;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
            vArray.add(line);
            System.out.println(line);
		}
		return vArray;
	}

	private static ArrayList<String> getFace() {
		ArrayList<String> fArray = new ArrayList<String>();
		int offset = header_lenght+1;
		if(sort.charAt(0) == 'v'){
			offset += n_vertex;
			if(sort.charAt(1) == 'e'){
				offset += n_edge;
			}
		}
		if(sort.charAt(0) == 'e'){
			offset += n_edge;
			if(sort.charAt(1) == 'v'){
				offset += n_vertex;
			}
		}
		int length = offset+n_face;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
            fArray.add(line);
            System.out.println(line);
		}
		return fArray;
	}

	private static ArrayList<String> getEdge() {
		ArrayList<String> eArray = new ArrayList<String>();
		int offset = header_lenght+1;
		if(sort.charAt(0) == 'v'){
			offset += n_vertex;
			if(sort.charAt(1) == 'f'){
				offset += n_face;
			}
		}
		if(sort.charAt(0) == 'f'){
			offset += n_face;
			if(sort.charAt(1) == 'v'){
				offset += n_vertex;
			}
		}
		int length = offset+n_edge;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
            eArray.add(line);
            System.out.println(line);
		}
		return eArray;
	}

	private static void compileHeader() {
		ArrayList<String> header = Loader.getHeader();
		header_lenght = header.size();
		boolean is_vertex_prop = false;
		boolean is_face_prop = false;
		boolean is_edge_prop = false;
		for (int i = 0; i < header.size(); i++) {
			String line = header.get(i);
			if (line.contains("element vertex")) {
				line = line.substring("element vertex ".length());
				n_vertex = Integer.parseInt(line);
				sort += "v";
				is_vertex_prop = true;
				is_face_prop = false;
				is_edge_prop = false;
			}
			if (line.contains("element face")) {
				line = line.substring("element face ".length());
				n_face = Integer.parseInt(line);
				sort += "f";
				is_vertex_prop = false;
				is_face_prop = true;
				is_edge_prop = false;
			}
			if (line.contains("element edge")) {
				line = line.substring("element edge ".length());
				n_edge = Integer.parseInt(line);
				sort += "e";
				is_vertex_prop = false;
				is_face_prop = false;
				is_edge_prop = true;
			}
			if (line.contains("property")) {
				if (is_vertex_prop) {
					vertex_properties += 1;
				}
				if (is_face_prop) {
					face_properties += 1;
				}
				if (is_edge_prop) {
					edge_properties += 1;
				}
			}
		}
	}
}
