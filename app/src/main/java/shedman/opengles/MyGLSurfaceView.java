package shedman.opengles;

/**
 * Created by Ross on 04/11/2014.
 */
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView
{

    private MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context)
    {
        super(context);
    }

    private final float TOUCH_SCALE_FACTOR = 100.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        if(e != null)
        {
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction())
            {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2)
                    {
                        dx *= -1;
                    }

                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2)
                    {
                        dy *= -1 ;
                    }

                    mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                    requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;
            return true;
        }
        else
        {
            return super.onTouchEvent(e);
        }
    }

    @Override
    public void setRenderer(Renderer renderer)
    {
        this.mRenderer = (MyGLRenderer)renderer;
        super.setRenderer(renderer);
    }

}
