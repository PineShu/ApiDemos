package pinetree.myapplication.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shisk on 2018/8/30.
 */

public class Square {
    //顶点
    private float vertices[] = {
            -1f, 1f, 0,//top left
            -1, -1, 0,//bottom left
            1, -1, 0,//bottom right
            1f, 1f, 0//top right
    };
    //顶点的链接顺序
    private short[] indices = {0, 1, 2, 0, 2, 3};
    //顶点数组
    private FloatBuffer vertexBuffer;
    //连接点数组
    private ShortBuffer indexBuffer;

    public Square() {
        //vertices[]  float 占4 byte,所以*4
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        // indeces is short , short is two byte
        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

    /**
     * draws our squaer on screen
     *
     * @param gl10
     */
    public void draw(GL10 gl10) {
        //cull  剔除  ccw  逆时针方向
        //正面的顶点绘制顺序是按照逆时针方向的,反面按顺时针方向
        gl10.glFrontFace(GL10.GL_CCW);
        //打开忽略面
        gl10.glEnable(GL10.GL_CULL_FACE);
        //指定忽略后面
        gl10.glCullFace(GL10.GL_BACK);
        // Enabled the vertices buffer for writing
        //and to be used during
        // rendering.
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of
        //an array of vertex
        // coordinates to use when rendering.
        //gl10.glTranslatef(1,0,0);// 平移的是当前图形的一倍 是1,2 是两倍。所以平移在缩放，和缩放后在平移是不同的
        gl10.glScalef(0.5f, 0.5f, 0.5f);
        gl10.glRotatef(45,0,0,0.5f);
        /**
         * size:

         每个顶点有几个数指描述。必须是2，3  ，4 之一，初始值是4.

         type:
         数组中每个顶点的坐标类型。取值：GL_BYTE, GL_SHORT , GL_FIXED , GL_FLOAT,   初始值 GL_FLOAT

         stride：

         数组中每个顶点间的间隔，步长（字节位移）。取值若为0，表示数组是连续的   初始值为0

         pointer

         It's your array ,存储着每个顶点的坐标值。初始值为0

         size：指定了每个顶点对应的坐标个数，只能是2,3,4中的一个，默认值是4
         type：指定了数组中每个顶点坐标的数据类型，可取常量:GL_BYTE, GL_SHORT,GL_FIXED,GL_FLOAT;
         stride:指定了连续顶点间的字节排列方式，如果为0，数组中的顶点就会被认为是按照紧凑方式排列的，默认值为0
         pointer:制订了数组中第一个顶点的首地址，默认值为0，对于我们的android，大家可以不用去管什么地址的，一般给一个IntBuffer就可以了。
         */
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        /**
         * mode 指定你要绘制何种图元， opengl 中的图元就这几个: GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, GL_TRIANGLES.

         first 在已制定的数组中的开始位置(索引位置)

         count 点的绘制次数， 比如我们绘制一个三角形，就是绘制三个顶点，即此参数为 3

         gl10.glDrawArrays();

         矩阵操作，单位矩阵
         在进行平移，旋转，缩放变换时，所有的变换都是针对当前的矩阵（与当前矩阵相乘），如果需要将当前矩阵回复最初的无变换的矩阵，可以使用单位矩阵（无平移，缩放，旋转）。

         public abstract void glLoadIdentity()。
         在栈中保存当前矩阵和从栈中恢复所存矩阵，可以使用

         public abstract void glPushMatrix()
         和

         public abstract void glPopMatrix()。
         */


        gl10.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // Disable the vertices buffer.
        gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl10.glDisable(GL10.GL_CULL_FACE);


        /**
         * 方法 public abstract void glTranslatef (float x, float y, float z) 用于坐标平移变换。

         在上个例子中我们把需要显示的正方形后移了4个单位，就是使用的坐标的平移变换，可以进行多次平移变换，
         其结果为多个平移矩阵的累计结果，矩阵的顺序不重要，可以互换。


         方法 public abstract void glRotatef(float angle, float x, float y, float z)
         用来实现选择坐标变换，单位为角度。(x,y,z)定义旋转的参照矢量方向。多次旋转的顺序非常重要。
         如果说x,y,z表达的是一个坐标(x,y,z),那么这个函数就说明了当前几何图形围着这个坐标点旋转,
         但往哪个方向旋转呢?所以很明显,x,y,z表达的意思并不是坐标点,而是要围绕哪个坐标轴旋转.即其实这里的x,y,z
         值相当于一个布尔值，0.0表示假，而非零参数则表示真。所以如果你想让当前的几何图形围绕着z轴旋转，那么x和y都设为0，而z设为非零值即可。
         如果这里的x,y,z的值都设置为0.0，那么将围绕着x轴旋转。还有一点需要注意，
         如果设置的旋转值（x,y,z的值）为正数，那么旋转的方向是逆时针的，如果旋转值是负数，那么旋转的方向是顺时针的。

         旋转变换 glRotatef(angle, -x, -y, -z) 和 glRotatef(-angle, x, y, z) 是等价的，
         但选择变换的顺序直接影响最终坐标变换的结果。 角度为正时表示逆时针方向。

         方法 public abstract void glScalef (float x, float y, float z)用于缩放变换。

         下图为使用 gl.glScalef(2f, 2f, 2f) 变换后的基本，相当于把每个坐标值都乘以2.
         */

    }

}
