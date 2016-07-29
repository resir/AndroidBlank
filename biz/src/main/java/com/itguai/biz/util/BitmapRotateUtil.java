package com.itguai.biz.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class BitmapRotateUtil {
    private static BitmapRotateUtil bitmapRotateUtils;
    private BitmapRotateUtil(){};
    public static BitmapRotateUtil getInstance()
    {
        if(null == bitmapRotateUtils)
        {
            bitmapRotateUtils = new BitmapRotateUtil();
        }
        return bitmapRotateUtils;
    }

    /**
     *�õ� ͼƬ��ת �Ľ�??
     *@param filepath
     *@return
     **/
    private int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
            if (exif != null)
            {
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                if (orientation != -1) {
                    switch (orientation)
                    {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            degree = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            degree = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            degree = 270;
                            break;
                    }
                }
            }
        } catch (IOException ex) { }
        return degree;
    }


    /**
     * @param angle ͼƬ������ת�Ľ�??
     * @param bitmap Ҫ��ת��bitmap
     * @return
     */
    private Bitmap rotateBitmap(int angle, Bitmap bitmap)
    {
        try {
            if(angle!=0)
            {  //�����Ƭ����??��ת ��ô �͸����ת��??
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                bitmap = Bitmap.createBitmap(bitmap,0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
        } catch (Exception e) {}
        return bitmap;
    }

    /**
     * ????bitmap�Ƕȷ���任�������仯����У�飬��ֹͼƬ��??
     * @param filePath
     * @param bitmap
     * @return
     */
    public Bitmap checkBitmapAngleToAdjust(String filePath, Bitmap bitmap)
    {
        int angle = getExifOrientation(filePath);
        return rotateBitmap(angle, bitmap);
    }

}
