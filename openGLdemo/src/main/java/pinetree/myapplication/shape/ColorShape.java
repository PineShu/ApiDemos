package pinetree.myapplication.shape;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shisk on 2018/8/31.
 */

public class ColorShape extends Square {
    /**
     * 是通知 OpenGL 使用单一的颜色来渲染，OpenGL 将一直使用指定的颜色来渲染直到你指定其它的颜色。
     * <p>
     * 指定颜色的方法为
     * <p>
     * public abstract void glColor4f(float red, float green, float blue, float alpha)。
     * 缺省的 red,green,blue 为 1，代表白色。这也是为什么前面显示的正方形都是白色的缘故。
     *
     * @param gl10
     */
    @Override
    public void draw(GL10 gl10) {
        gl10.glColor4f(0.5f, 1, 0.f, 1);
        super.draw(gl10);
    }
}
