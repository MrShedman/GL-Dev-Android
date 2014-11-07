package shedman.opengles;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Ross on 06/11/2014.
 */
public class VBO
{
    private int[] buffers = new int[2];

    private int m_size;

    public VBO(Vertex[] vertices, short[] indices)
    {
        GLES20.glGenBuffers(2, buffers, 0);
        this.m_size = vertices.length;

        FloatBuffer vBuffer = CreateFlippedBuffer(vertices);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, GetVbo());
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vBuffer.capacity() * 4, vBuffer, GLES20.GL_STATIC_DRAW);

        ShortBuffer iBuffer = CreateFlippedBuffer(indices);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, GetIbo());
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, iBuffer.capacity() * 2, iBuffer, GLES20.GL_STATIC_DRAW);
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

    private static ShortBuffer CreateFlippedBuffer(short... values)
    {
        ShortBuffer buffer = ByteBuffer.allocateDirect((values.length) << 1).order(ByteOrder.nativeOrder()).asShortBuffer();
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    private static FloatBuffer CreateFlippedBuffer(Vertex[] vertices)
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
}
