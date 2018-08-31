package pinetree.myapplication;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.myapplication.shape.Square;

public class OpenGlDemo extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    private Square square;

    private Square squareB;

    private int angle = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl_demo);
        glSurfaceView = findViewById(R.id.glsurfaceview);
        glSurfaceView.setRenderer(new MRander());

    }


    private class MRander implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(1, 0.5f, 0.5f, 0);
            //
            gl.glShadeModel(GL10.GL_SMOOTH);
            // Depth buffer setup.
            gl.glClearDepthf(1.0f);// OpenGL docs.
            // Enables depth testing.
            gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
            gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
            // Really nice perspective calculations.
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                    GL10.GL_NICEST);
            square = new Square();
            squareB = new Square();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
            // Select the projection matrix
            gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
            // Reset the projection matrix
            gl.glLoadIdentity();// OpenGL docs.
            // Calculate the aspect ratio of the window
            GLU.gluPerspective(gl, 45.0f,
                    (float) width / (float) height,
                    0.1f, 100.0f);
            // Select the modelview matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
            // Reset the modelview matrix
            gl.glLoadIdentity();// OpenGL docs.
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                    GL10.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity();
            //沿着Z 轴 平移 -10
            gl.glTranslatef(0, 0, -10);
            //保存当前矩阵
            gl.glPushMatrix();
            // 以Z轴为中心z>0&&angle>0逆时针旋转,z>0&&angle<0,顺时针旋转
            gl.glRotatef(angle, 0, 0, 1);
            square.draw(gl);

            //恢复保存的矩阵状态
            gl.glPopMatrix();

            //squareB
            gl.glPushMatrix();
            //顺时针旋转
            gl.glRotatef(-angle,0,0,1);
            //沿着X轴方向平移两倍
            gl.glTranslatef(2,0,0);
            //缩小一半
            gl.glScalef(0.5f,0.5f,0.5f);
            //draw square b
            square.draw(gl);
            // source c
            gl.glPushMatrix();

            // Make the rotation around B
            gl.glRotatef(-angle, 0, 0, 1);
            gl.glTranslatef(2, 0, 0);
            // Scale it to 50% of square B
            gl.glScalef(.5f, .5f, .5f);
            // Rotate around it's own center.
            gl.glRotatef(angle*10, 0, 0, 1);
            // Draw square C.
            //square.draw(gl);
            // Restore to the matrix as it was before C.
            gl.glPopMatrix();
            // Restore to the matrix as it was before B.
            gl.glPopMatrix();
            // Increse the angle.
            angle++;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null)
            glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null)
            glSurfaceView.onPause();
    }
}
