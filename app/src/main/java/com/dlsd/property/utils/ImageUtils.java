package com.dlsd.property.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LSH ON 2019/10/9
 * Describe:
 */
public class ImageUtils {
    /**
     * 压缩图片到指定的位置
     *
     * a，图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片
     * b，宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280
     * c，宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至
     * d，宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片
     *
     * @param filePath 返回压缩后图片的路径
     * @return
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("finally")
    public static String compressUpImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) return null;
        //旋转角度
        int degree = readPictureDegree(filePath);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPurgeable = true;// 同时设置才会有效
        opt.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opt);
        opt.inSampleSize = calculateInSampleSize(opt, 1028, 1028);
        opt.inJustDecodeBounds = false;
        Bitmap cbitmap = BitmapFactory.decodeFile(filePath, opt);
        Log.i("==image==","压缩前的尺寸：  宽： " + opt.outWidth + " ----  高：" + opt.outHeight);
        Bitmap image = null;
        Bitmap upImage = null;
        int quality = 100;
        if (Math.abs(degree) > 0) {
            try {
                image = rotaingImage(degree, cbitmap);
            } catch (Exception e) {
                e.printStackTrace();
                image = cbitmap;
            }
        } else {
            image = cbitmap;
        }
        if(image==null) return "";
        int width = image.getWidth();
        int height = image.getHeight();
        File tempFile = null;
        if (width <= 1028 && height <= 1028) {
            upImage = image;
            if(upImage!=null&&upImage.getByteCount()>204800) {
                quality = 60;
            }
            Log.i("==image==","图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片");
        } else if ((width > 1028 || height > 1028) && width / height <= 2) {
            Log.i("==image==","宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280");
            int newWidth = 1028;
            int newHeight = 1028;
            if (width <= height) {
                newWidth = (int) ((width * 1028f) / height);
            } else {
                newHeight = (int) ((height * 1028f) / width);
            }
            upImage = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
            if (image != null && !image.isRecycled()) {
                image.recycle();
            }
            quality = 60;
        } else if (width > 1028 && height > 1028 && width / height > 2) {
            Log.i("==image==","宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至1280");
            int newWidth = 1028;
            int newHeight = 1028;
            if (width >= height) {
                newWidth = (int) ((width * 1028f) / height);
            } else {
                newHeight = (int) ((height * 1028f) / width);
            }
            upImage = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
            if (image != null && !image.isRecycled()) {
                image.recycle();
            }
            quality = 60;
        } else if ((width > 1028 && height < 1028) || (width < 1028 && height > 1028) && width / height > 2) {
            Log.i("==image==","宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片");
            upImage = image;
            quality = 60;
        } else {
            upImage = image;
            quality = 60;
        }
        Log.i("==image==","压缩后的尺寸：  宽：" + upImage.getWidth() + " ---  高： " + upImage.getHeight());
        FileOutputStream out = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile("upImage", ".jpg");
            out = new FileOutputStream(tempFile);
            upImage.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (tempFile == null) {
                return null;
            } else {
                return tempFile.getAbsolutePath();
            }
        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     *计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImage(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Log.i("==image==","angle=" + angle);
        // 创建新的图片
        Bitmap returnBm = null;
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (returnBm == null) {
                returnBm = bitmap;
            }
            if (bitmap != returnBm) {
                bitmap.recycle();
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return returnBm;
    }
}
