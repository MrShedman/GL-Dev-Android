package shedman.opengles;

/**
 * Created by Ross on 04/11/2014.
 */

import android.opengl.GLES20;

/**
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Triangle
{
    private VBO mVBO;

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Triangle()
    {
        Vertex[] vertices = new Vertex[3];

        vertices[0] = new Vertex(new Vector3f(0.0f,  0.622008459f, 0.0f), new Vector2f(0.0f,  0.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f));
        vertices[1] = new Vertex(new Vector3f(-0.5f, -0.311004243f, 0.0f), new Vector2f(0.0f,  1.0f), new Color(0.0f, 1.0f, 0.0f, 1.0f));
        vertices[2] = new Vertex(new Vector3f(0.5f, -0.311004243f, 0.0f), new Vector2f(1.0f,  0.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f));

        short[] indices = new short[3];
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;

        mVBO = new VBO(vertices, indices);
    }

     /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix, Texture texture, Shader shader)
    {
        shader.useProgram();

        mVBO.bind();

        shader.setMVPMatrix(mvpMatrix);

        shader.EnableVertexAttribArray();

        if (texture != null)
        {
            texture.bind();
        }

        shader.setVertexPointers();

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mVBO.GetSize(), GLES20.GL_UNSIGNED_SHORT, 0);

        shader.DisableVertexAttribArray();

        mVBO.unBind();
    }

}