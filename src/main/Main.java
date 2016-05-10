package main;

public class Main {
	public static DCELMesh mesh;

	public static void main(String[] args) {
		System.out.println("Started Application");
		Loader.loadfile();
		mesh = DCELGenerator.generate(mesh);
	}
}
