package main;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Beispiele für andere OpenGL Profile:
// import com.jogamp.opengl.GL4ES2;
// import com.jogamp.opengl.GL;


/**
 * Hauptprogramm um eine einfache Szene anzuzeigen
 *
 * @author hlawit
 * @version 2016-05-20
 */
public class SimpleScene {

    static DCELMesh mesh;
    static Geometry myGeometry;
    static Shader myShader;
    static Camera myCamera;

    /**
     * Hilfsfunkton zum Testen und Ausgeben von OpenGL-Fehlern
     *
     * @param gl    Das OpenGL Profil
     * @param where Information über die position im Quelltext, die bei einem Fehler mit ausgegeben wird
     */
    public static void checkGLError(GL4 gl, String where) {
        int error;
        while ((error = gl.glGetError()) != GL4.GL_NO_ERROR) {
            // Ausgabe einer Fehlermeldung
            System.err.println("OpenGL Error at " + where + ": " + error);
            // Alternative: Werfen einer Ausnahme und beenden des Programms bei einem Fehler
            //throw new RuntimeException(where + ": glError " + error);
        }
    }


    /**
     * Hauptprogramm, initialisiert die Fenster
     *
     * @param args ignoriert
     */
    public static void main(String[] args) {

        System.out.println("Started Application");
        Loader.loadfile();
        mesh = new DCELMesh(Loader.filename);
        mesh = DCELGenerator.generate(mesh);

        // Create the geometry object
        myGeometry = new Geometry(mesh);

        // create the camera

        float largestCoordNum = 0;

        for (float vertex : myGeometry.getVertices().array()) {
            if (vertex > largestCoordNum) {
                largestCoordNum = vertex;
            }
        }

        myCamera = new Camera(-largestCoordNum / 2, -largestCoordNum / 2, largestCoordNum / 2);

        myShader = new Shader();


        // Anlaegen eines OpenGL4 Profils, sodass auf OpenGL4 Funktionalitaet zugegriffen werden kann
        GLProfile glp = GLProfile.get(GLProfile.GL4);
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas glcanvas = new GLCanvas(caps);

        // Erstellen des Hauptfensters
        Frame frame = new Frame("HTWK Computergrafik");

        frame.setSize(500, 500);

        // OpenGL-Canvas hinzufuegen
        frame.add(glcanvas);
        frame.setVisible(true);

        // Listener fuer das Bearbeiten von Zeichenereignissen hinzufuegen
        glcanvas.addGLEventListener(new GLEventListener() {

            /**
             *  Reshape wird beim Aendern der Groesse des Fensters aufgerufen und
             *  passt die Größe des Viewports an das aktuelle Fenster an
             */
            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                GL4 gl2 = glautodrawable.getGL().getGL4();
                gl2.glViewport(0, 0, width, height);
                checkGLError(gl2, "end of reshape");

            }


            /**
             *  Initialisierung, wird einmal beim Erstellen des Fensters aufgerufen und erzeugt die 
             *  Puffer bzw. initialisiert alle OpenGL Datenstrukturen
             */
            @Override
            public void init(GLAutoDrawable glautodrawable) {
                GL4 gl2 = glautodrawable.getGL().getGL4();

                checkGLError(gl2, "start of init");

                myShader.initShader(gl2);
                myGeometry.initBuffers(gl2);

                checkGLError(gl2, "end of init");
            }


            @Override
            public void dispose(GLAutoDrawable glautodrawable) {
            }


            /**
             * Displayfunktion wird bei jedem Neuzeichnen des Fensters aufgerufen
             * Aus Effizienzgründen sollten hier nur bereits angelegte Puffer verwendet werden
             * Das Anlegen der Puffer muss entweder in init, reshape oder display erfolgen, da nur
             * dort der OpenGL-Kontext gesetzt ist.
             */
            @Override
            public void display(GLAutoDrawable glautodrawable) {
                GL4 gl2 = glautodrawable.getGL().getGL4();

                checkGLError(gl2, "start of display");

                gl2.glClearColor(0.04f, 0.04f, 0.04f, 0.0f);
                gl2.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
                checkGLError(gl2, "display 2");


                myShader.useProgram(gl2);

                // setzen der Matrizen im Shaderprogramm
                myShader.setModelViewMatrix(gl2, myCamera.getModelViewMatrix());
                myShader.setProjectionMatrix(gl2, myCamera.getProjectionMatrix());

                checkGLError(gl2, "after matrices");


                // zeichnet die eigene Geometrie
                myGeometry.display(gl2);

                checkGLError(gl2, "end of display");
            }
        });

        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.remove(glcanvas);
                frame.dispose();
                System.exit(0);
            }
        });


        float finalLargestCoordNum = largestCoordNum;
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode();

                float moveX = myCamera.getModelViewMatrix().array()[12];
                float moveY = myCamera.getModelViewMatrix().array()[13];
                float moveZ = myCamera.getModelViewMatrix().array()[14];

                float moveStep = finalLargestCoordNum / 100;

                //System.out.println(moveStep);

                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        if (e.isControlDown()) {
                            myCamera.setModelViewMatrix(moveX, moveY, moveZ - moveStep);
                        } else {
                            myCamera.setModelViewMatrix(moveX, moveY - moveStep, moveZ);
                        }
                        glcanvas.display();
                        break;
                    case KeyEvent.VK_DOWN:
                        if (e.isControlDown()) {
                            myCamera.setModelViewMatrix(moveX, moveY, moveZ + moveStep);
                        } else {
                            myCamera.setModelViewMatrix(moveX, moveY + moveStep, moveZ);
                        }
                        glcanvas.display();
                        break;
                    case KeyEvent.VK_LEFT:
                        myCamera.setModelViewMatrix(moveX + moveStep, moveY, moveZ);
                        glcanvas.display();
                        break;
                    case KeyEvent.VK_RIGHT:
                        myCamera.setModelViewMatrix(moveX - moveStep, moveY, moveZ);
                        glcanvas.display();
                        break;
                }

                //System.out.println(Arrays.toString(myCamera.getModelViewMatrix().array()));
            }
        });

    }
}
