package main;

import java.nio.FloatBuffer;
import java.util.Arrays;

/**
 * Klasse zum Halten der Kamerainformation
 */
public class Camera {

    FloatBuffer mypmatrix;

    @Override
    public String toString() {
        return "Camera{" +
                "mymvmatrix=" + Arrays.toString(mymvmatrix.array()) +
                '}';
    }

    FloatBuffer mymvmatrix;

    Camera(float moveX, float moveY, float moveZ) {
        // Anlegen der aktuellen Matrizen fuer die Anzeige
        mypmatrix = FloatBuffer.wrap(
                new float[]{
                        0.5f, 0.0f, 0.0f, 0.0f,
                        0.0f, 0.5f, 0.0f, 0.0f,
                        0.0f, 0.03f, 0.2f, 0.2f,
                        0.0f, 0.0f, 1.0f, 1.0f
                });


        mymvmatrix = FloatBuffer.wrap(
                new float[]{1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, -1.0f, 0.0f,
                        moveX, moveY, moveZ, 0.0f
                });
    }


    /**
     * Gibt die aktuelle Model View Matrix zurück
     *
     * @return die aktuelle Model View Matrix
     */
    public FloatBuffer getModelViewMatrix() {
        return mymvmatrix;
    }

    public void setModelViewMatrix(float moveX, float moveY, float moveZ) {
        this.mymvmatrix = FloatBuffer.wrap(
                new float[]{1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, -1.0f, 0.0f,
                        moveX, moveY, moveZ, 0.0f
                });
    }

    /**
     * Gibt die aktuelle Projektionsmatrix zurück
     *
     * @return die aktuelle Projektionsmatrix
     */
    public FloatBuffer getProjectionMatrix() {
        return mypmatrix;
    }
}
