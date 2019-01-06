package com.ididsec.www.androidinformationextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShowVideoActivity extends Activity {
    private ListView lv;
    ArrayList name;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_file);
        lv = (ListView) findViewById(R.id.lv);
        name = new ArrayList();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();// 获得SD卡路径
            File[] files = path.listFiles();// 读取
            getFileName(files);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, name, R.layout.pes, new String[] { "Name" }, new int[] { R.id.tv });
        lv.setAdapter(adapter);
    }

    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileName(file.listFiles());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".avi")||fileName.endsWith(".wma")||fileName.endsWith(".mp4")) {
                        HashMap map = new HashMap();
                        String s = fileName.substring(0, fileName.lastIndexOf(".")).toString();
                        map.put("Name", fileName .substring(0, fileName.lastIndexOf(".")));
                        name.add(map);
                    }
                }
            }
        }
    }
}
