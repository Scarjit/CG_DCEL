package main;

import java.util.ArrayList;


public class DCELGenerator {

	static int n_vertex = 0;
	static int n_face = 0;
	static int n_edge = 0;

	static int vertex_properties = 0;
	static int face_properties = 0;
	static int edge_properties = 0;

	static String sort = "";

	public static DCELMesh generate(DCELMesh mesh) {
		compileHeader();
		System.out.println("////////////");
		System.out.println("Vertex: "+n_vertex);
		System.out.println("Face: "+n_face);
		System.out.println("Edge: "+n_edge);
		System.out.println("Vertex Prop: "+vertex_properties);
		System.out.println("Face Prop: "+face_properties);
		System.out.println("Edge Prop: "+edge_properties);
		return null;
	}

	private static void compileHeader() {
		ArrayList<String> header = Loader.getHeader();
		boolean is_vertex_prop = false;
		boolean is_face_prop = false;
		boolean is_edge_prop = false;
		for (int i = 0; i < header.size(); i++) {
			String line = header.get(i);
			if (line.contains("element vertex")) {
				line = line.substring("element vertex ".length());
				n_vertex = Integer.parseInt(line);
				sort = sort + "v";
				is_vertex_prop = true;
				is_face_prop = false;
				is_edge_prop = false;
			}
			if (line.contains("element face")) {
				line = line.substring("element face ".length());
				n_face = Integer.parseInt(line);
				sort = sort + "f";
				is_vertex_prop = false;
				is_face_prop = true;
				is_edge_prop = false;
			}
			if (line.contains("element edge")) {
				line = line.substring("element edge ".length());
				n_edge = Integer.parseInt(line);
				sort = sort + "e";
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
