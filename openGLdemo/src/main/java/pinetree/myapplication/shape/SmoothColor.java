package pinetree.myapplication.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shisk on 2018/8/31
 * 当给一个顶点定义颜色时，OpenGl 自动为不同顶点的颜色之间生成中间色（渐变色）
 */

public class SmoothColor extends Square {

    private FloatBuffer bufferColor;

    public SmoothColor() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        bufferColor = byteBuffer.asFloatBuffer();
        bufferColor.put(colors);
        bufferColor.position(0);
    }

    private float[] colors = {
            1f, 0f, 0f, 1f,// vertex 0 red
            0f, 1f, 0f, 1f,// vertex 1 blue
            0, 0, 1, 1,// vertex2
            1, 0, 1, 1// vertex 3
    };

    @Override
    public void draw(GL10 gl10) {
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, bufferColor);
        super.draw(gl10);
        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
