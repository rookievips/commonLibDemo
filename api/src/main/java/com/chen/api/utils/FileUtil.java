package com.chen.api.utils;

import android.os.Environment;
import android.util.Base64;
import com.chen.api.base.BaseApplication;
import com.chen.api.security.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件处理util
 */
public class FileUtil {

    /**
     * 获取缓存路径，存储临时文件，可被一键清理和卸载清理
     * 可以看到，当SD卡存在或者SD卡不可被移除的时候
     * 就调用getExternalCacheDir()方法来获取缓存路径
     * 否则就调用getCacheDir()方法来获取缓存路径
     * 前者获取到的就是/storage/emulated/0/Android/data/<application package>/cache 这个路径
     * 而后者获取到的是 /data/data/<application package>/cache 这个路径
     */
    public static File getDiskCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            return BaseApplication.getBaseAppContext().getExternalCacheDir();
        } else {
            return BaseApplication.getBaseAppContext().getCacheDir();
        }
    }

    /**
     * 用于存放一些图片文件
     *
     * @return
     */
    public static String getImageCacheDir() {
        String imageCachePath = getDiskCacheDir() + File.separator + "images";

        File file = new File(imageCachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return imageCachePath;
    }

    /**
     * 文件转Base64
     *
     * @param filePath /storage/emulated/0/Android/data/<application package>/cache/images/avatar.jpg
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String filePath) throws Exception {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        fis.read(buffer);
        fis.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 文件转Base64
     *
     * @param filePath /storage/emulated/0/Android/data/<application package>/cache/images/avatar.jpg
     * @return
     * @throws Exception
     */
    public static String encodeBase64FileNoWrap(String filePath) throws Exception {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        fis.read(buffer);
        fis.close();
        return Base64.encodeToString(buffer, Base64.NO_WRAP);
    }

    /**
     * Base64转文件
     *
     * @param base64Str
     * @param savePath  文件保存路径
     * @throws Exception
     */
    public static void decodeBase64File(String base64Str, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Str, Base64.DEFAULT);
        FileOutputStream fos = new FileOutputStream(savePath);
        fos.write(buffer);
        fos.close();
    }

    /**
     * 获取 FileProvider
     *
     * @return
     */
    public static String getFileProvider() {
        return BaseApplication.getBaseAppContext().getPackageName() + ".fileProvider";
    }

    public static String generateFileName(String userId) {
        String name = userId + System.currentTimeMillis();
        name = MD5.getMD5(name);
        return name + userId;
    }

    /**
     * 判断sd卡可用
     *
     * @return
     */
    public static boolean hasSDCard() {
        String state = Environment.getExternalStorageState();
        return state != null && state.equals(Environment.MEDIA_MOUNTED);
    }
}
