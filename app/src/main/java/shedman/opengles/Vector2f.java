package shedman.opengles;

/**
 * Created by Ross on 05/11/2014.
 */
public class Vector2f
{
    private float m_x;
    private float m_y;

    public Vector2f(float x, float y)
    {
        this.m_x = x;
        this.m_y = y;
    }

    public float Length()
    {
        return (float)Math.sqrt(m_x * m_x + m_y * m_y);
    }

    public float Max()
    {
        return Math.max(m_x, m_y);
    }

    public float Dot(Vector2f r)
    {
        return m_x * r.GetX() + m_y * r.GetY();
    }

    public Vector2f Normalized()
    {
        float length = Length();

        return new Vector2f(m_x / length, m_y / length);
    }

    public float Cross(Vector2f r)
    {
        return m_x * r.GetY() - m_y * r.GetX();
    }

    public Vector2f Lerp(Vector2f dest, float lerpFactor)
    {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    public Vector2f Rotate(float angle)
    {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float)(m_x * cos - m_y * sin),(float)(m_x * sin + m_y * cos));
    }

    public Vector2f Add(Vector2f r)
    {
        return new Vector2f(m_x + r.GetX(), m_y + r.GetY());
    }

    public Vector2f Add(float r)
    {
        return new Vector2f(m_x + r, m_y + r);
    }

    public Vector2f Sub(Vector2f r)
    {
        return new Vector2f(m_x - r.GetX(), m_y - r.GetY());
    }

    public Vector2f Sub(float r)
    {
        return new Vector2f(m_x - r, m_y - r);
    }

    public Vector2f Mul(Vector2f r)
    {
        return new Vector2f(m_x * r.GetX(), m_y * r.GetY());
    }

    public Vector2f Mul(float r)
    {
        return new Vector2f(m_x * r, m_y * r);
    }

    public Vector2f Div(Vector2f r)
    {
        return new Vector2f(m_x / r.GetX(), m_y / r.GetY());
    }

    public Vector2f Div(float r)
    {
        return new Vector2f(m_x / r, m_y / r);
    }

    public Vector2f Abs()
    {
        return new Vector2f(Math.abs(m_x), Math.abs(m_y));
    }

    public String toString()
    {
        return "(" + m_x + " " + m_y + ")";
    }

    public Vector2f Set(float x, float y) { this.m_x = x; this.m_y = y; return this; }
    public Vector2f Set(Vector2f r) { Set(r.GetX(), r.GetY()); return this; }

    public float GetX()
    {
        return m_x;
    }

    public void SetX(float x)
    {
        this.m_x = x;
    }

    public float GetY()
    {
        return m_y;
    }

    public void SetY(float y)
    {
        this.m_y = y;
    }

    public boolean equals(Vector2f r)
    {
        return m_x == r.GetX() && m_y == r.GetY();
    }
}