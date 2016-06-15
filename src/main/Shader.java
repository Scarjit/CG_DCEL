package main;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.glsl.ShaderUtil;

import java.nio.FloatBuffer;

public class Shader {

    /**
     * GLSL-Quellcode fuer den Vertex-Shader
     */
    final static String vShaderCode = new String(
            "#version 400\n" +
                    "layout (location = 0) in vec3 aVertexPosition;" +
                    "layout (location = 1) in vec3 aVertexColor;" +

                    "uniform mat4 uMVMatrix;" +
                    "uniform mat4 uPMatrix;" +
                    "out vec4 Color;" +

                    "void main(void) {" +
                    "    gl_Position = uPMatrix * uMVMatrix * vec4(aVertexPosition, 1.0);" +
                    "    Color = vec4( aVertexColor, 1.0);" +
                    "}"
    );

    /**
     * GLSL-Quellcode fuer den Fragment-Shader
     */
    final static String fShaderCode = new String(
            "#version 400\n" +
                    //"precision mediump float;" +
                    //"" +
                    "in vec4 Color;" +
                    "layout ( location = 0 ) out vec4 outColor;" +
                    "void main(void) {" +
                    "    outColor = vec4(1.0,0.0,0.0,1.0);" +
                    "}"
    );


    // Attribute zum Speichern der Positionen wesentlicher Variablen

    int vertexPositionAttribute;
    int vertexColorAttribute;

    private int pMatrixUniform;
    private int mvMatrixUniform;

    private int myProgram;


    // Hilfsfunkton zum Testen und Ausgeben von OpenGL-Fehlern
    public void checkGLError(GL4 gl, String where) {
        int error;
        while ((error = gl.glGetError()) != GL4.GL_NO_ERROR) {
            // log.info(error);
            throw new RuntimeException("Shader: " + where + ": glError " + error);
        }
    }

    /**
     * Erzeugt und initialisiert das Shaderprogramm
     *
     * @param gl
     */
    public void initShader(GL4 gl) {
        // Anlagen der verwendeten Shaderprogramme
        int fshader = gl.glCreateShader(GL4.GL_FRAGMENT_SHADER);

        String ff[] = new String[1];
        ff[0] = fShaderCode;
        gl.glShaderSource(fshader, 1, ff, null, 0);

        gl.glCompileShader(fshader);

        System.out.println("Fragment Shader Info Log: " + ShaderUtil.getShaderInfoLog(gl, fshader));

        checkGLError(gl, "compile fs");

        int vshader = gl.glCreateShader(GL4.GL_VERTEX_SHADER);
        String vf[] = new String[1];
        vf[0] = vShaderCode;
        gl.glShaderSource(vshader, 1, vf, null, 0);
        gl.glCompileShader(vshader);

        System.out.println("Vertex Shader Info Log: " + ShaderUtil.getShaderInfoLog(gl, fshader));
        checkGLError(gl, "compile vs");

        myProgram = gl.glCreateProgram();


        gl.glAttachShader(myProgram, vshader);
        gl.glAttachShader(myProgram, fshader);
        gl.glLinkProgram(myProgram);

        checkGLError(gl, "link");

        gl.glValidateProgram(myProgram);

        System.out.println("Program Info Log: " + ShaderUtil.getProgramInfoLog(gl, myProgram));

        checkGLError(gl, "validate");

        gl.glUseProgram(myProgram);
        checkGLError(gl, "use");

        // holen der "location" der Attribute (falls diese später benötigt werden)
        vertexPositionAttribute = gl.glGetAttribLocation(myProgram, "aVertexPosition");
        checkGLError(gl, "attrib");

        vertexColorAttribute = gl.glGetAttribLocation(myProgram, "aVertexColor");
        checkGLError(gl, "attrib");


        pMatrixUniform = gl.glGetUniformLocation(myProgram, "uPMatrix");
        checkGLError(gl, "uPMatrix");

        mvMatrixUniform = gl.glGetUniformLocation(myProgram, "uMVMatrix");
        checkGLError(gl, "uMVMatrix");
    }

    public void useProgram(GL4 gl) {
        gl.glUseProgram(myProgram);
        checkGLError(gl, "useProgram");
    }

    public void setProjectionMatrix(GL4 gl, FloatBuffer mypmatrix) {
        gl.glUniformMatrix4fv(pMatrixUniform, 1, false, mypmatrix);
        checkGLError(gl, "mypmatrix");
    }

    public void setModelViewMatrix(GL4 gl, FloatBuffer mymvmatrix) {
        gl.glUniformMatrix4fv(mvMatrixUniform, 1, false, mymvmatrix);
        checkGLError(gl, "mymvmatrix");
    }
}
