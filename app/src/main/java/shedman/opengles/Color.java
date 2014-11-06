package shedman.opengles;

/**
 * Created by Ross on 05/11/2014.
 */
public class Color
{
    private float m_r;
    private float m_g;
    private float m_b;
    private float m_a;

    public Color(float r, float g, float b, float a)
    {
        this.m_r = r;
        this.m_g = g;
        this.m_b = b;
        this.m_a = a;
    }

    public float GetR()
    {
        return m_r;
    }

    public float GetG()
    {
        return m_g;
    }

    public float GetB()
    {
        return m_b;
    }

    public float GetA()
    {
        return m_a;
    }
}
