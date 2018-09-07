package pinetree.myapplication.shape;

import android.opengl.GLES30;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by shisk on 2018/9/7.
 */

public class Triangle {
//
//    private String vertex = "#version 300 es\n" +
//            "uniform mat4 uMVPMatrix; //总变换矩阵\n" +
//            "layout (location = 0) in vec3 aPosition;  //顶点位置\n" +
//            "layout (location = 1) in vec4 aColor;    //顶点颜色\n" +
//            "out  vec4 vColor;  //用于传递给片元着色器的变量\n" +
//            "\n" +
//            "void main()\n" +
//            "{\n" +
//            "   gl_Position = uMVPMatrix * vec4(aPosition,1); //根据总变换矩阵计算此次绘制此顶点位置\n" +
//            "   vColor = aColor;//将接收的颜色传递给片元着色器 \n" +
//            "}";
//    private String fragment = "#version 300 es\n" +
//            "precision mediump float;\n" +
//            "in  vec4 vColor; //接收从顶点着色器过来的参数\n" +
//            "out vec4 fragColor;//输出到的片元颜色\n" +
//            "void main()                         \n" +
//            "{                       \n" +
//            "   fragColor = vColor;//给此片元颜色值\n" +
//            "}";

    private String vertex = "#version 300 es\n" +
            "uniform mat4 uMVPMatrix; \n" +
            "layout (location = 0) in vec3 aPosition;  \n" +
            "layout (location = 1) in vec4 aColor;    \n" +
            "out  vec4 vColor;  \n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "   gl_Position = uMVPMatrix * vec4(aPosition,1); \n" +
            "   vColor = aColor; \n" +
            "}";
    private String fragment = "#version 300 es\n" +
            "precision mediump float;\n" +
            "in  vec4 vColor; \n" +
            "out vec4 fragColor;\n" +
            "void main()                         \n" +
            "{                       \n" +
            "   fragColor = vColor;\n" +
            "}";



    public static float[] mProjMatrix = new float[16];// 投影矩阵
    public static float[] mVMatrix = new float[16];//摄像机位置朝向的参数矩阵
    public static float[] mMVPMatrix = new float[16];//最后起作用的总变换矩阵

    private int program;//自定义渲染管线ID
    private int muMvpMatrixHandle;//总变换矩阵引用
    private int maPositionHandle;//顶点位置属性引用
    private int maColorHandle;// 顶点颜色属性引用

    static float[] mMatrix = new float[16];//具体物体移动的旋转矩阵，旋转 平移 缩放

    FloatBuffer verTexBuffer;//顶点数据缓冲
    FloatBuffer colorBurref;//顶点颜色数据缓冲

    int vCount = 0;
    public  float xAngle = 0;//绕着x轴旋转的角度

    public Triangle() {
        initVertex();
        initShader();
    }

    /**
     * 初始化顶点数据和颜色数据
     */
    private void initVertex() {
        vCount = 3;
        float unit_size = 0.2f;
        float vertices[] = new float[]{
                -4 * unit_size, 0, 0,
                0, -4 * unit_size, 0,
                0, 0, -4 * unit_size
        };

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());// 设置字节顺序为本地操作系统顺序
        verTexBuffer = byteBuffer.asFloatBuffer();//转换为float 型缓冲
        verTexBuffer.put(vertices);// 在缓冲区内写入数据
        verTexBuffer.position(0);// 设置缓冲区起始位置

        float colors[] = new float[]{
                1, 1, 1, 0,// white
                0, 0, 1, 0,//blue
                0, 1, 1, 0//green
        };

        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuffer1.order(ByteOrder.nativeOrder());
        colorBurref = byteBuffer1.asFloatBuffer();
        colorBurref.put(colors);
        colorBurref.position(0);
    }

    private void initShader() {
        //基于顶点着色器和片元着色器创建 程序
        program = GLShaderUtils.createProgram(vertex, fragment);
        // 获取程序中顶点位置属性的引用
        maPositionHandle = GLES30.glGetAttribLocation(program, "aPosition");
        // 获取程序中顶点颜色属性的引用
        maColorHandle = GLES30.glGetAttribLocation(program, "aColor");
        //获取程序中总变换矩阵
        muMvpMatrixHandle = GLES30.glGetUniformLocation(program, "uMVPMatrix");
    }

    public void draw() {
        // 指定使用哪个程序
        GLES30.glUseProgram(program);
        //初始化 变换矩阵
        Matrix.setRotateM(mMatrix, 0, 0, 0, 1, 0);
        //沿着Y轴正方向位移1
        Matrix.translateM(mMatrix, 0, 0, 0, 1);
        //沿着X轴旋转
        Matrix.rotateM(mMatrix, 0, xAngle, 1, 0, 0);
        //将变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMvpMatrixHandle, 1, false, getFianlMatrix(mMatrix), 0);
        //将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer(maPositionHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, verTexBuffer);
        //将顶点颜色数据传入渲染管线
        GLES30.glVertexAttribPointer(maColorHandle, 4, GLES30.GL_FLOAT, false, 4 * 4, colorBurref);
        //启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        //启用顶点着色数据
        GLES30.glEnableVertexAttribArray(maColorHandle);
        //绘制三角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }


    public static float[] getFianlMatrix(float[] spec) {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

}
