package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DCELGenerator {

	private static int n_vertex = 0;
	private static int n_face = 0;
	private static int n_edge = 0;

	private static int vertex_properties = 0;
	private static int face_properties = 0;
	private static int edge_properties = 0;

	private static int header_lenght = 0;

	private static ArrayList<String> vertexArrayString;
	private static ArrayList<String> faceArrayString;
	private static ArrayList<String> edgeArrayString;
	private static String sort = "";

	private static ArrayList<DCELVertex> vertexArray = new ArrayList<DCELVertex>();
	private static ArrayList<DCELFace> faceArray = new ArrayList<DCELFace>();
	private static ArrayList<DCELHalfEdge> edgeArray = new ArrayList<DCELHalfEdge>();
	private static ArrayList<Integer[]> indicesForEdges = new ArrayList<Integer[]>();

	public static DCELMesh generate(DCELMesh mesh) {
		compileHeader();
		System.out.println("//////Vertex//////");
		vertexArrayString = getVertex();
		System.out.println("//////Face//////");
		faceArrayString = getFace();
		System.out.println("//////Edge//////");
		edgeArrayString = new ArrayList<String>();
		System.out.println("//////File Info//////");
		System.out.println("Vertex: " + n_vertex);
		System.out.println("Face: " + n_face);
		System.out.println("Edge: " + n_edge);
		System.out.println("Vertex Prop: " + vertex_properties);
		System.out.println("Face Prop: " + face_properties);
		System.out.println("Edge Prop: " + edge_properties);
		System.out.println("Sort: " + sort);
		System.out.println("////////////");

		generateVertex();
		generateEdgeStringFromFace();
		generateEdge();
		generateFace();
		setNextEdge();
		setPrevEdge();
		setTwinEdge();
		setRandomHalfEdgeToVertex();

		mesh.setVertices(vertexArray);
		mesh.setEdges(edgeArray);
		mesh.setFaces(faceArray);
		mesh.setIndicesForEdges(indicesForEdges);

		return mesh;
	}

	private static void setRandomHalfEdgeToVertex() {
		vertexArray.forEach(currentVertex -> {
			for (DCELHalfEdge currentEdge : edgeArray) {
				if (currentEdge.getOrigin().equals(currentVertex)) {
					currentVertex.setHalfEdge(currentEdge);
					break;
				}
			}
		});
	}


	private static void setTwinEdge() {
		for (int i = 0; i < edgeArray.size(); i++) {
			DCELHalfEdge currentEdge = edgeArray.get(i);
			edgeArray.forEach(testTwin -> {
				if (!testTwin.equals(currentEdge)
						&& currentEdge.getNext().getOrigin() == testTwin.getOrigin()
						&& currentEdge.getOrigin() == testTwin.getNext().getOrigin()) {
					currentEdge.setTwin(testTwin);
				}
			});
		}
	}

	private static void setPrevEdge() {
		DCELHalfEdge startOfFace = null;
		for (int i = edgeArray.size() - 1; i >= 0; i--) {
			DCELHalfEdge currentEdge = edgeArray.get(i);
			DCELHalfEdge prevInList;

			try {
				prevInList = edgeArray.get(i - 1);
			} catch (Exception ex) {
				prevInList = startOfFace;
			}

			if (startOfFace == null || !startOfFace.getFace()
					.equals(currentEdge.getFace())) {
				startOfFace = currentEdge;
			}

			if (prevInList.getFace().equals(currentEdge.getFace())) {
				currentEdge.setPrev(prevInList);
			} else {
				currentEdge.setPrev(startOfFace);
			}
		}
	}

	private static void setNextEdge() {
		DCELHalfEdge startOfFace = null;
		for (int i = 0; i < edgeArray.size(); i++) {
			DCELHalfEdge currentEdge = edgeArray.get(i);
			DCELHalfEdge nextInList;

			try {
				nextInList = edgeArray.get(i + 1);
			} catch (Exception ex) {
				nextInList = startOfFace;
			}

			if (startOfFace == null || !startOfFace.getFace()
					.equals(currentEdge.getFace())) {
				startOfFace = currentEdge;
			}

			if (nextInList.getFace().equals(currentEdge.getFace())) {
				currentEdge.setNext(nextInList);
			} else {
				currentEdge.setNext(startOfFace);
			}
		}
	}

	private static void generateFace() {
		int offset = 0;

		for (int i = 0; i < faceArrayString.size(); i++) {
			int[] positions = StringToIntN(faceArrayString.get(i));
			DCELFace face = new DCELFace(edgeArray.get(positions[1]));
			face.setId(i);
			faceArray.add(face);
			for (int j = 0; j < positions[0]; j++) {
				DCELHalfEdge currentEdge = edgeArray.get(offset + j);
				currentEdge.setFace(face);
			}
			offset = offset + positions[0];
		}
	}

	private static void generateEdgeStringFromFace() {
		faceArrayString.forEach(aFaceArrayString -> {
			int[] positions = StringToIntN(aFaceArrayString);
			for (int j = 1; j < positions.length; j++) {
				if (j < positions.length - 1) {
					edgeArrayString.add(positions[j] + " " + positions[j + 1]);
				} else {
					edgeArrayString.add(positions[j] + " " + positions[1]);
				}
			}
		});
	}

	private static void generateEdge() {
		for (int i = 0; i < edgeArrayString.size(); i++) {
			int[] position = StringToIntN(edgeArrayString.get(i));
			if (position[0] < position[1]) {
				Integer[] indice = new Integer[2];
				indice[0] = position[0];
				indice[1] = position[1];
				indicesForEdges.add(indice);
			}

			DCELHalfEdge currentEdge = new DCELHalfEdge(null, null, null, vertexArray.get(position[0]), null);
			currentEdge.setId(i);
			edgeArray.add(currentEdge);
		}
	}

	private static void generateVertex() {
		for (int i = 0; i < vertexArrayString.size(); i++) {
			float[] position = StringToFloatN(vertexArrayString.get(i));
			Map<String, Object> customData = new HashMap<String, Object>();
			DCELVertex currentVertex = new DCELVertex(position, customData, null);
			currentVertex.setId(i);
			vertexArray.add(currentVertex);
		}
	}

	private static float[] StringToFloatN(String in) {
		String[] stringRet = in.split(" ");
		float[] retArray = new float[stringRet.length];
		for (int i = 0; i < stringRet.length; i++) {
			retArray[i] = Float.parseFloat(stringRet[i]);
		}
		return retArray;
	}

	private static int[] StringToIntN(String in) {
		String[] stringRet = in.split(" ");
		int[] retArray = new int[stringRet.length];
		for (int i = 0; i < stringRet.length; i++) {
			retArray[i] = Integer.parseInt(stringRet[i]);
		}
		return retArray;
	}

	private static ArrayList<String> getVertex() {
		ArrayList<String> vArray = new ArrayList<String>();
		int offset = header_lenght + 1;
		if (sort.charAt(0) == 'f') {
			offset += n_face;
			if (sort.charAt(1) == 'e') {
				offset += n_edge;
			}
		}
		if (sort.charAt(0) == 'e') {
			offset += n_edge;
			if (sort.charAt(1) == 'f') {
				offset += n_face;
			}
		}
		int length = offset + n_vertex;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
			vArray.add(line);
		}
		return vArray;
	}

	private static ArrayList<String> getFace() {
		ArrayList<String> fArray = new ArrayList<String>();
		int offset = header_lenght + 1;
		if (sort.charAt(0) == 'v') {
			offset += n_vertex;
			if (sort.charAt(1) == 'e') {
				offset += n_edge;
			}
		}
		if (sort.charAt(0) == 'e') {
			offset += n_edge;
			if (sort.charAt(1) == 'v') {
				offset += n_vertex;
			}
		}
		int length = offset + n_face;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
			fArray.add(line);
		}
		return fArray;
	}

	private static void compileHeader() {
		ArrayList<String> header = Loader.getHeader();
		header_lenght = header.size();
		boolean is_vertex_prop = false;
		boolean is_face_prop = false;
		boolean is_edge_prop = false;
		for (String line : header) {
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
