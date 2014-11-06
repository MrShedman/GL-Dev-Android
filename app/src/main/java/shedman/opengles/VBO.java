package shedman.opengles;

import android.opengl.GLES20;

/**
 * Created by Ross on 06/11/2014.
 */
public class VBO
{
    private int[] buffers = new int[2];

    private int m_size;

    public VBO(int size)
    {
        GLES20.glGenBuffers(2, buffers, 0);
        this.m_size = size;
    }

    public void bind()
    {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, GetVbo());
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, GetIbo());
    }

    public void unBind()
    {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    protected void finalize()
    {
        GLES20.glDeleteBuffers(2, buffers, 0);
    }

    public int GetVbo()  { return buffers[0]; }
    public int GetIbo()  { return buffers[1]; }
    public int GetSize() { return m_size; }
}
