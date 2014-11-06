package shedman.opengles;

/**
 * Created by Ross on 04/11/2014.
 */
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

/**
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Triangle
{
    private Texture mTexture;

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec2 vTextureCoord;" +
                    "varying vec2 tCoord;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 tColor;" +
                    "attribute vec3 vPosition;" +
                    "void main() {" +
                    "tCoord = vTextureCoord;" +
                    "tColor = vColor;" +
                    "  gl_Position = uMVPMatrix * vec4( vPosition, 1.0 );" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 tColor;" +
                    "uniform sampler2D sTexture;" +
                    "varying vec2 tCoord;" +
                    "void main() {" +
                    "   vec4 c = texture2D(sTexture, tCoord);"+
                    "   gl_FragColor = tColor * c;" +
                    "}";

    private Vertex[] mVertices = new Vertex[3];

    private VBO mVBO;

    private final int mProgram;
    private int mPositionHandle;
    private int mTexCoordHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private int mTextureHandle;

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Triangle()
    {
        mVertices[0] = new Vertex(new Vector3f(0.0f,  0.622008459f, 0.0f), new Vector2f(0.0f,  0.0f), new Color(1.0f, 0.0f, 0.0f, 1.0f));
        mVertices[1] = new Vertex(new Vector3f(-0.5f, -0.311004243f, 0.0f), new Vector2f(0.0f,  1.0f), new Color(0.0f, 1.0f, 0.0f, 1.0f));
        mVertices[2] = new Vertex(new Vector3f(0.5f, -0.311004243f, 0.0f), new Vector2f(1.0f,  0.0f), new Color(0.0f, 0.0f, 1.0f, 1.0f));

        mVBO = new VBO(mVertices.length);

        FloatBuffer vBuffer = CreateFlippedBuffer(mVertices);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBO.GetVbo());
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vBuffer.capacity() * 4, vBuffer, GLES20.GL_STATIC_DRAW);

        short[] indices = new short[3];
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;

        ShortBuffer iBuffer = CreateFlippedBuffer(indices);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBO.GetIbo());
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, iBuffer.capacity() * 2, iBuffer, GLES20.GL_STATIC_DRAW);

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram, "vTextureCoord");

        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");

        mTextureHandle = GLES20.glGetUniformLocation(mProgram, "sTexture");

        MyGLRenderer.checkGlError("glLinkProgram");
    }

    public void setTexture(Texture texture)
    {
        mTexture = texture;
    }

    public static ShortBuffer CreateFlippedBuffer(short... values)
    {
        ShortBuffer buffer = ByteBuffer.allocateDirect((values.length) << 1).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Vertex[] vertices)
    {
        FloatBuffer buffer = ByteBuffer.allocateDirect((vertices.length * Vertex.SIZE) << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

        for(int i = 0; i < vertices.length; i++)
        {
            buffer.put(vertices[i].GetPos().GetX());
            buffer.put(vertices[i].GetPos().GetY());
            buffer.put(vertices[i].GetPos().GetZ());
            buffer.put(vertices[i].GetTexCoord().GetX());
            buffer.put(vertices[i].GetTexCoord().GetY());
            buffer.put(vertices[i].GetColor().GetR());
            buffer.put(vertices[i].GetColor().GetG());
            buffer.put(vertices[i].GetColor().GetB());
            buffer.put(vertices[i].GetColor().GetA());
        }

        buffer.flip();

        return buffer;
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix)
    {
        MyGLRenderer.checkGlError("first draw");

        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        MyGLRenderer.checkGlError("glUseProgram");

        mVBO.bind();
        MyGLRenderer.checkGlError("bind");

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation - mat");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        MyGLRenderer.checkGlError("glEnableVertexAttribArray - pos");
        GLES20.glEnableVertexAttribArray(mTexCoordHandle);
        MyGLRenderer.checkGlError("glEnableVertexAttribArray - tex");
        GLES20.glEnableVertexAttribArray(mColorHandle);
        MyGLRenderer.checkGlError("glEnableVertexAttribArray - col");

        //if (mTexture != null)
        //{
            mTexture.bind();
            GLES20.glUniform1i(mTextureHandle, 0);
      //  }

        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, Vertex.SIZE * 4, 0);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false,Vertex.SIZE * 4, 12);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,Vertex.SIZE * 4, 20);

        MyGLRenderer.checkGlError("draw elements before");
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mVBO.GetSize(), GLES20.GL_UNSIGNED_SHORT, 0);

        MyGLRenderer.checkGlError("draw elements after");

        // Draw the triangle
       // GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);

        mVBO.unBind();
        MyGLRenderer.checkGlError("last draw");
    }

}