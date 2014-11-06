package shedman.opengles;

import java.util.Vector;

/**
 * Created by Ross on 05/11/2014.
 */
public class Vertex
{
    public static final int SIZE = 9;

    private Vector3f m_pos;
    private Vector2f m_texCoord;
    private Color m_color;


    public Vertex(Vector3f pos)
    {
        this(pos, new Vector2f(0,0), new Color(1, 1, 1, 1));
    }

    public Vertex(Vector3f pos, Vector2f texCoord)
    {
        this(pos, texCoord, new Color(1, 1, 1, 1));
    }

    public Vertex(Vector3f pos, Vector2f texCoord, Color color)
    {
        this.m_pos = pos;
        this.m_texCoord = texCoord;
        this.m_color = color;
    }

    public Vector3f GetPos()
    {
        return m_pos;
    }

    public void SetPos(Vector3f pos)
    {
        this.m_pos = pos;
    }

    public Vector2f GetTexCoord()
    {
        return m_texCoord;
    }

    public void SetTexCoord(Vector2f texCoord)
    {
        this.m_texCoord = texCoord;
    }

    public Color GetColor()
    {
        return m_color;
    }

    public void SetColor(Color color)
    {
        this.m_color = color;
    }
}
