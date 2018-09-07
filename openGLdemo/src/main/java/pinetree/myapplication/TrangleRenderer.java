package pinetree.myapplication;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.myapplication.shape.Triangle;

/**
 * Created by shisk on 2018/9/7.
 */

public class TrangleRenderer implements GLSurfaceView.Renderer {
    private Triangle triangle;
    private float ANGLE_SPAN = 0.375f;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        triangle = new Triangle();
        // 设置屏幕背景色
        GLES30.glClearColor(0, 0, 0, 0);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        new RorateThread().start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
        //计算GLSurfaceView 的宽高比
        float ratio=(float)width/height;
        //调用此方法计算产生透视投影矩阵
        Matrix.frustumM(Triangle.mProjMatrix,0,-ratio,ratio,-1,1,1,10);
        //调用此方法产生摄像机9参数位矩阵
        Matrix.setLookAtM(Triangle.mVMatrix,0,0,0,3,0f,0f,0f,1f,0f,0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
       //清楚深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);
        triangle.draw();
    }

    public class RorateThread extends Thread {
        @Override
        public void run() {
            super.run();
            triangle.xAngle += ANGLE_SPAN;
        }
    }
}
