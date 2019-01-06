package com.ididsec.www.androidinformationextraction;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ididsec.www.androidinformationextraction.bean.FileBean;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by ZMC on 2018/11/24.
 */

public class DocumentsActivity extends ListActivity {
    private static final String ROOT_PATH = "/";
    private static final String ROOT_path="/storage/emulated/0";
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private View view;
    private EditText editText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_documents);
        //显示文件列表
        showFileDir(ROOT_path);
    }
    private void showFileDir(String path){
        names = new ArrayList<String>();
        paths = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
        //如果当前目录不是根目录
        if (!ROOT_path.equals(path)){
            names.add("@1");
            paths.add(ROOT_PATH);
            names.add("@2");
            paths.add(file.getParent());
        }
        //添加所有文件
        if(files!=null) {
            for (File f : files) {
                names.add(f.getName());
                paths.add(f.getPath());
            }
        }
        else
        {
            Toast.makeText(this,"好像出了点错",Toast.LENGTH_SHORT).show();
        }
        this.setListAdapter(new MyAdapter(this,names, paths));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String path = paths.get(position);
        File file = new File(path);
        // 文件存在并可读
        if (file.exists() && file.canRead()){
            if (file.isDirectory()){
                //显示子目录及文件
                showFileDir(path);
            }
            else{
                //处理文件
                fileHandle(file);
            }
        }
        //没有权限
        else{
            Resources res = getResources();
            new AlertDialog.Builder(this).setTitle("Message")
                    .setMessage(res.getString(R.string.no_permission))
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        super.onListItemClick(l, v, position, id);
    }
    //对文件进行增删改
    private void fileHandle(final File file) {
        String name = file.getName();//获取文件名称
        String absPath = file.getAbsolutePath();//获取文件路径，是绝对路径。
        String path = file.getPath();//获取文件路径，是相对路径。
        long len = file.length();
        long time = file.lastModified();//获取文件修改时间
        Date date = new Date(time);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
        String str_time = dateFormat.format(date);

        new AlertDialog.Builder(this).setTitle("信息摘要")
                .setMessage("文件名："+name+'\n'+
                        "路径："+absPath+'\n'+"大小："+len+'\n'+"创建日期："+str_time+'\n'+"最近修改时间："+time+'\n')
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();



    }








        }

