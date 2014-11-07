package shedman.opengles;

import android.opengl.GLES20;

/**
 * Created by Ross on 06/11/2014.
 */
public class Shader
{
    private int mProgram;

    private int mPositionLocation;
    private int mTexCoordLocation;
    private int mColorLocation;
    private int mMVPMatrixLocation;

    public Shader(final String vertCode, final String fragCode)
    {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        mPositionLocation = getAttribLocation("vPosition");

        mTexCoordLocation = getAttribLocation("vTextureCoord");

        mColorLocation = getAttribLocation("vColor");

        mMVPMatrixLocation = getUniformLocation("uMVPMatrix");

        GLES20.glDetachShader(mProgram, vertexShader);
        GLES20.glDetachShader(mProgram, fragmentShader);
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);
    }

    public void useProgram()
    {
        GLES20.glUseProgram(mProgram);
    }

    public void EnableVertexAttribArray()
    {
        GLES20.glEnableVertexAttribArray(mPositionLocation);
        GLES20.glEnableVertexAttribArray(mTexCoordLocation);
        GLES20.glEnableVertexAttribArray(mColorLocation);
    }

    public void DisableVertexAttribArray()
    {
        GLES20.glDisableVertexAttribArray(mPositionLocation);
        GLES20.glDisableVertexAttribArray(mTexCoordLocation);
        GLES20.glDisableVertexAttribArray(mColorLocation);
    }

    public void setMVPMatrix(float [] mvpMatrix)
    {
        GLES20.glUniformMatrix4fv(mMVPMatrixLocation, 1, false, mvpMatrix, 0);
    }

    public void setVertexPointers()
    {
        GLES20.glVertexAttribPointer(mPositionLocation, 3, GLES20.GL_FLOAT, false, Vertex.SIZE * 4, 0);
        GLES20.glVertexAttribPointer(mTexCoordLocation, 2, GLES20.GL_FLOAT, false,Vertex.SIZE * 4, 12);
        GLES20.glVertexAttribPointer(mColorLocation, 4, GLES20.GL_FLOAT, false,Vertex.SIZE * 4, 20);
    }

    public int getAttribLocation(final String name)
    {
       return GLES20.glGetAttribLocation(mProgram, name);
    }

    public int getUniformLocation(final String name)
    {
        return GLES20.glGetUniformLocation(mProgram, name);
    }

    private int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        MyGLRenderer.checkGlError("glCompileShader");

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0)
        {
            throw new RuntimeException(GLES20.glGetShaderInfoLog(shader));
        }

        return shader;
    }

    @Override
    protected void finalize()
    {
        GLES20.glDeleteProgram(mProgram);
    }

}
