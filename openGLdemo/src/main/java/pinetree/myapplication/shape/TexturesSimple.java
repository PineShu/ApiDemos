package pinetree.myapplication.shape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import javax.microedition.khronos.opengles.GL10;

import pinetree.myapplication.R;

/**
 * Created by shisk on 2018/9/4.
 */

public class TexturesSimple {


    public TexturesSimple(Context context) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.timg);


    }

    public void draw(GL10 gles10) {
        int[] texttures = new int[1];

        gles10.glGenTextures(1, texttures, 0);

        gles10.glBindTexture(GL10.GL_TEXTURE_2D, texttures[0]);
        gles10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gles10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
//
//        常用的两种模式为 GL10.GL_LINEAR 和 GL10.GL_NEAREST。
//
//        需要比较清晰的图像使用 GL10.GL_NEAREST：
    }

}
