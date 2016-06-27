package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class DCELGenerator {

	static int n_vertex = 0;
	static int n_face = 0;
	static int n_edge = 0;

	static int vertex_properties = 0;
	static int face_properties = 0;
	static int edge_properties = 0;

	static int header_lenght = 0;

	static ArrayList<String> vertexArrayString;
	static ArrayList<String> faceArrayString;
	static ArrayList<String> edgeArrayString;
	static String sort = "";

	static ArrayList<DCELVertex> vertexArray = new ArrayList<DCELVertex>();
	static ArrayList<DCELFace> faceArray = new ArrayList<DCELFace>();
	static ArrayList<DCELHalfEdge> edgeArray = new ArrayList<DCELHalfEdge>();
	static ArrayList<Integer[]> indicesForEdges = new ArrayList<Integer[]>();

	public static DCELMesh generate(DCELMesh mesh) {
		compileHeader();
		System.out.println("//////Vertex//////");
		vertexArrayString = getVertex();
		System.out.println("//////Face//////");
		faceArrayString = getFace();
		System.out.println("//////Edge//////");
		edgeArrayString = getEdge();
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
		setFaceNormal();
		setVertexNormal();

		mesh.setVertices(vertexArray);
		mesh.setEdges(edgeArray);
		mesh.setFaces(faceArray);
		mesh.setIndicesForEdges(indicesForEdges);

		//System.out.println(vertexArray.toString());

		return mesh;
	}

	private static void setVertexNormal() {


	}

	private static void setFaceNormal() {

		ArrayList<float[]> triangle = new ArrayList<float[]>();
		float[] cross1 = new float[3];
		float[] cross2 = new float[3];
		float[] vResult = new float[3];


		for (DCELFace face : faceArray) {
			triangle.add(0, face.getHalfEdge().getOrigin().getPosition());
			triangle.add(1, face.getHalfEdge().getNext().getOrigin().getPosition());
			triangle.add(2, face.getHalfEdge().getPrev().getOrigin().getPosition());
			/*System.out.println("origin: " + Arrays.toString(triangle.get(0)));
			System.out.println("next:   " + Arrays.toString(triangle.get(1)));
			System.out.println("prev:   " + Arrays.toString(triangle.get(2)));*/


			float x1 = triangle.get(0)[0];
			float y1 = triangle.get(0)[1];
			float z1 = triangle.get(0)[2];

			float x2 = triangle.get(1)[0];
			float y2 = triangle.get(1)[1];
			float z2 = triangle.get(1)[2];

			float x3 = triangle.get(2)[0];
			float y3 = triangle.get(2)[1];
			float z3 = triangle.get(2)[2];

			cross1[0] = x2 - x1;
			cross1[1] = y2 - y1;
			cross1[2] = z2 - z1;

			cross2[0] = x3 - x1;
			cross2[1] = y3 - y1;
			cross2[2] = z3 - z1;

			vResult = crossProduct(cross1, cross2, vResult);

			face.setFaceNormal(vResult);

			DCELHalfEdge currHalfEdge = face.getHalfEdge();

			System.out.println(Arrays.toString(vResult));

			do {

				//System.out.println(face.toString());
				currHalfEdge.getOrigin().addFaceNormal(vResult);

				currHalfEdge = currHalfEdge.getNext();
			} while ((face.getHalfEdge() != currHalfEdge));
		}

	}

	private static float[] crossProduct(float[] v1, float[] v2, float[] vR) {

		vR[0] = ((v1[1] * v2[2]) - (v1[2] * v2[1]));
		vR[1] = -((v1[0] * v2[2]) - (v1[2] * v2[0]));
		vR[2] = ((v1[0] * v2[1]) - (v1[1] * v2[0]));

		return vR;
	}

	private static void setRandomHalfEdgeToVertex() {
		for (int i = 0; i < vertexArray.size(); i++) {
			DCELVertex currentVertex = vertexArray.get(i);
			for (int j = 0; j < edgeArray.size(); j++) {
				DCELHalfEdge currentEdge = edgeArray.get(j);
				if (currentEdge.getOrigin().equals(currentVertex)) {
					currentVertex.setHalfEdge(currentEdge);
					break;
				}
			}
		}
	}

	private static void setTwinEdge() {
		for (int i = 0; i < edgeArray.size(); i++) {
			DCELHalfEdge currentEdge = edgeArray.get(i);
			for (int j = 0; j < edgeArray.size(); j++) {
				DCELHalfEdge testTwin = edgeArray.get(j);
				if (!testTwin.equals(currentEdge)
						&& currentEdge.getNext().getOrigin() == testTwin
						.getOrigin() && currentEdge.getOrigin() == testTwin
						.getNext().getOrigin()) {
					currentEdge.setTwin(testTwin);
					/*System.out.println(
							"Found Twin: " + currentEdge.getId() + " : "
									+ testTwin.getId());
					System.out
							.println(currentEdge.getNext().getOrigin().getId());
					System.out.println(testTwin.getOrigin().getId());*/
				} else {
					//System.out.println(testTwin.getId() + " is not twin of: " + currentEdge.getId());
				}

			}
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
				/*System.out.println("Current: " + currentEdge.getId() + " Prev: "
						+ prevInList.getId() + " (Same Face)");*/
				currentEdge.setPrev(prevInList);
			} else {
				/*System.out.println("Current: " + currentEdge.getId() + " Prev: "
						+ startOfFace.getId());*/
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
				/*System.out.println("Current: " + currentEdge.getId() + " Next: "
						+ nextInList.getId() + " (Same Face)");*/
				currentEdge.setNext(nextInList);
			} else {
				/*System.out.println("Current: " + currentEdge.getId() + " Next: "
						+ startOfFace.getId());*/
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
				/*System.out.println(
						"Added Face: " + face.getId() + " to " + cEdge.getId());*/
			}
			offset = offset + positions[0];
		}
	}

	private static void generateEdgeStringFromFace() {
		for (int i = 0; i < faceArrayString.size(); i++) {
			int[] positions = StringToIntN(faceArrayString.get(i));
			for (int j = 1; j < positions.length; j++) {
				if (j < positions.length - 1) {
					edgeArrayString.add(positions[j] + " " + positions[j + 1]);
				} else {
					edgeArrayString.add(positions[j] + " " + positions[1]);
				}
			}
		}
	}

	private static void generateEdge() {
		Map<DCELHalfEdge, int[]> tempHalfEdgeList = new HashMap<>();
		for (int i = 0; i < edgeArrayString.size(); i++) {
			int[] position = StringToIntN(edgeArrayString.get(i));
			/*System.out.println(
					"Edge " + i + " connects " + position[0] + " and "
							+ position[1])*/


			if (position[0] < position[1]) {
				Integer[] indice = new Integer[2];
				indice[0] = position[0];
				indice[1] = position[1];
				indicesForEdges.add(indice);
			}

			DCELHalfEdge currentEdge = new DCELHalfEdge(null, null, null,
					vertexArray.get(position[0]), null);
			tempHalfEdgeList.put(currentEdge, position);
			currentEdge.setId(i);
			edgeArray.add(currentEdge);
		}


		//TODO: unötig oder?:
		for (int i = 0; i < edgeArray.size(); i++) {
			DCELHalfEdge currentEdge = edgeArray.get(i);
			int[] currentPos = tempHalfEdgeList.get(currentEdge);
		}
	}

	private static void generateVertex() {
		for (int i = 0; i < vertexArrayString.size(); i++) {
			float[] position = StringToFloatN(vertexArrayString.get(i));
			Map<String, Object> customData = new HashMap<String, Object>();
			DCELVertex currentVertex = new DCELVertex(position, customData, null);
			currentVertex.setId(i);
			vertexArray.add(currentVertex);
			/*System.out.println(
					"Added Vertex @: " + postion[0] + " : " + postion[1] + " : "
							+ postion[2] + " || " + vertexArrayString.get(i));*/
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

	private static ArrayList<String> getEdge() {
		return new ArrayList<String>();
		/*
		ArrayList<String> eArray = new ArrayList<String>();
		int offset = header_lenght + 1;
		if (sort.charAt(0) == 'v') {
			offset += n_vertex;
			if (sort.charAt(1) == 'f') {
				offset += n_face;
			}
		}
		if (sort.charAt(0) == 'f') {
			offset += n_face;
			if (sort.charAt(1) == 'v') {
				offset += n_vertex;
			}
		}
		int length = offset + n_edge;
		for (int i = offset; i < length; i++) {
			String line = Loader.file.get(i);
			eArray.add(line);
			System.out.println(line);
		}
		return eArray;
		*/
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
