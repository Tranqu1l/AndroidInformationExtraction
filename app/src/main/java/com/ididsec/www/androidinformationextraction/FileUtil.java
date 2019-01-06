package com.ididsec.www.androidinformationextraction;

import android.content.Context;

import java.io.File;

/**
 * Created by ZMC on 2018/12/18.
 */

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
