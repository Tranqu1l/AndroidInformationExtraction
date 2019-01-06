package com.ididsec.www.androidinformationextraction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZMC on 2018/12/18.
 */

public class SearchActivity extends AppCompatActivity {
    public List<String> file_name=new ArrayList<>();
    public List<String> file_txt_path=new ArrayList<>();
    public List<String> file_size;

    public List<String> file_name_doc=new ArrayList<>();
    public List<String> file_doc_path=new ArrayList<>();

    public List<String> file_name_xls=new ArrayList<>();
    public List<String> file_xls_path=new ArrayList<>();

    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView3;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private MyDatebaseHelper helper2=new MyDatebaseHelper(this);
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    private BaseAdapter adapter;
    private ListView list2;
    private BaseAdapter adapter2;







    String sd_path1 = Environment.getDataDirectory().getAbsolutePath();

    String sd_path2 = Environment.getRootDirectory().getAbsolutePath();

    String sd_path3 = Environment.getDownloadCacheDirectory().getAbsolutePath();

    String sd_path4 = Environment.getExternalStorageDirectory().getAbsolutePath();



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);
        list2 = (ListView) findViewById(R.id.list2);

        //Log.e("路径1：",sd_path1);
        //Log.e("路径2：",sd_path2);
        //Log.e("路径3：",sd_path3);
        //Log.e("路径4：",sd_path4);

        //displayToast(str1);

        initView();

        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
                listFileTxt(sd_path4);
                //listFileDoc(sd_path4);
                //listFileXls(sd_path4);
                //String txt1=file_txt_path.get(0);
                //displayToast(txt1);
                for(int i=0;i<file_txt_path.size();i++){
                    String substr=file_name.get(i).substring(file_name.get(i).length()-4,file_name.get(i).length());
                    String substr1=" .txt";
                    Log.e(substr1,substr);
                    if(substr==substr1){
                        insertData2(file_name.get(i), "文档内容", file_txt_path.get(i));
                        Log.e("文件内容：",ReadTxtFile(file_txt_path.get(i)));
                    }else{
                        insertData2(file_name.get(i),"文档内容",file_txt_path.get(i));
                    }
                }

                //for(int i=0;i<file_doc_path.size();i++){
                    //insertData2(file_name_doc.get(i),"文档内容",file_doc_path.get(i));
                    //Log.e("文件内容：",ReadTxtFile(file_doc_path.get(i)));
                //}

                //for(int i=0;i<file_xls_path.size();i++){
                //insertData2(file_name_xls.get(i),"文档内容",file_xls_path.get(i));
                //Log.e("文件内容：",ReadTxtFile(file_xls_path.get(i)));
                 //}

            }
        });

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    //Toast.makeText(SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                    searchdb(et_search.getText().toString().trim());

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("");
                }
                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                //Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                searchdb(et_search.getText().toString().trim());


            }
        });

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 TextView textView = (TextView) view.findViewById(R.id.textView3);
                 String path = textView.getText().toString();
                 Toast.makeText(SearchActivity.this, path, Toast.LENGTH_SHORT).show();
                 openFile(path);
             }
        });


        // 插入数据，便于测试
        //Date date = new Date();
        //long time = date.getTime();
        //insertData("txt" + time);

        // 第一次进入查询所有的历史记录
        queryData("");

        new Thread(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();

                }

            }
        }).start();



    }

    private void listFileTxt(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        //Log.e("file大小：" + files.length, "");
            try {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        if (f.getName().endsWith(".txt")||f.getName().endsWith(".doc") || f.getName().endsWith(".docx")||f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {

                            file_name.add(f.getName());//文件名称
                            file_txt_path.add(f.getAbsolutePath());//文件路径
                            if (file_txt_path != null) {
                                //Log.e("路径: ", file_txt_path.get(0));
                            } else {
                                //Log.e("啥也没有", "nothing");
                            }

                        }
                    } else if (f.isDirectory()) {
                        //如果是目录，迭代进入该目录
                        listFileTxt(f.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void listFileDoc(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        //Log.e("file大小：" + files.length, "");
        try {
            for (File f : files) {
                if (!f.isDirectory()) {
                    if (f.getName().endsWith(".doc") || f.getName().endsWith(".docx")) {
                        //Log.e("文件名称：", f.getName());
                        //Log.e("文件路径：", f.getAbsolutePath());
                        //获取并计算文件大小
                        /*long size = f.length();
                        String t_size = "";
                        if (size <= 1024){
                            t_size = size + "B";
                        }else if (size > 1024 && size <= 1024 * 1024){
                            size /= 1024;
                            t_size = size + "KB";
                        }else {
                            size = size / (1024 * 1024);
                            t_size = size + "MB";
                        }*/
                        //file_size.add(t_size);//文件大小
                        file_name_doc.add(f.getName());//文件名称
                        file_doc_path.add(f.getAbsolutePath());//文件路径
                        if (file_doc_path != null) {
                            //Log.e("路径: ", file_doc_path.get(0));
                        } else {
                            //Log.e("啥也没有", "nothing");
                        }

                    }
                } else if (f.isDirectory()) {
                    //如果是目录，迭代进入该目录
                    listFileDoc(f.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listFileXls(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        Log.e("file大小：" + files.length, "");
        try {
            for (File f : files) {
                if (!f.isDirectory()) {
                    if (f.getName().endsWith(".xls") || f.getName().endsWith(".xlsx")) {
                        Log.e("文件名称：", f.getName());
                        Log.e("文件路径：", f.getAbsolutePath());
                        //获取并计算文件大小
                        /*long size = f.length();
                        String t_size = "";
                        if (size <= 1024){
                            t_size = size + "B";
                        }else if (size > 1024 && size <= 1024 * 1024){
                            size /= 1024;
                            t_size = size + "KB";
                        }else {
                            size = size / (1024 * 1024);
                            t_size = size + "MB";
                        }*/
                        //file_size.add(t_size);//文件大小
                        file_name_xls.add(f.getName());//文件名称
                        file_xls_path.add(f.getAbsolutePath());//文件路径
                        if (file_xls_path != null) {
                            //Log.e("路径: ",file_xls_path.get(0) );
                        } else {
                            //Log.e("啥也没有","nothing");
                             }

                        }
                    } else if (f.isDirectory()) {
                        //如果是目录，迭代进入该目录
                        listFileXls(f.getAbsolutePath());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

    }
    private void displayToast(String str) {
        Toast.makeText(SearchActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    public static String ReadTxtFile(String strFilePath) {
            String path = strFilePath;
            String content = ""; //文件内容字符串
            //打开文件
            File file = new File(path);
            //如果path是传递过来的参数，可以做一个非目录的判断
            if (file.isDirectory())
            {
            Log.d("TestFile", "The File doesn't not exist.");
            }
            else
            {
            try {
            InputStream instream = new FileInputStream(file);
            if (instream != null)
            {
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            //分行读取
            while (( line = buffreader.readLine()) != null) {
            content += line + "\n";
            }
            instream.close();
            }
            }
            catch (java.io.FileNotFoundException e)
            {
            Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
            Log.d("TestFile", e.getMessage());
            }
            }
            return content;
    }


    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView3.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView3 = (com.ididsec.www.androidinformationextraction.MyListView) findViewById(R.id.listView3);
        tv_clear = (TextView) findViewById(R.id.tv_clear);

        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_share);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
    }

    private void insertData2(String name,String content,String path) {
        db2 = helper2.getWritableDatabase();
        db2.execSQL("insert or replace into documents(title,content,path) values('"+name+"','"+content+"','"+path+"')");
        db2.close();
    }

    /**
     * 模糊查询数据
     */
    private void searchdb(String searchText) {
        Cursor cursor = helper2.getReadableDatabase().rawQuery(
                "select DISTINCT id as _id,title,content,path from documents where title like '%" + searchText + "%' or content like '%"+searchText+"%'", null);
        //"select id as _id,title,content from documents where title like '%" + searchText + "%' or select id as _id,title,content from documents where content like '%"+searchText+"%'"
        // 创建adapter适配器对象
        adapter2 = new SimpleCursorAdapter(this, R.layout.search, cursor, new String[] { "title","content","path"},
                new int[] { R.id.textView1,R.id.textView2,R.id.textView3 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器

        list2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }

    public static Intent openFile(String filePath){

        File file = new File(filePath);
        if(!file.exists()) return null;
		/* 取得扩展名 */
        String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
        if(end.equals("xls")){
            return getExcelFileIntent(filePath);
        }else if(end.equals("doc")){
            return getWordFileIntent(filePath);
        }else if(end.equals("txt")){
            return getTextFileIntent(filePath,true);
        }else{
            return getAllIntent(filePath);
        }
    }

    public static Intent getAllIntent( String param ) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri,"*/*");
        return intent;
    }
    public static Intent getExcelFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }
    public static Intent getWordFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
    public static Intent getTextFileIntent( String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            Log.e("路径：",param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
            Log.e("路径：",param);
        }
        return intent;
    }





}


