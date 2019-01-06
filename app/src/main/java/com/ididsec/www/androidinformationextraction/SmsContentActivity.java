package com.ididsec.www.androidinformationextraction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by ZMC on 2018/11/23.
 */

public class SmsContentActivity extends AppCompatActivity{
    private ListView listView1;
    private SimpleAdapter sa;
    private List<Map<String, Object>> data;
    public static final int REQ_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sms);
        //得到ListView
        listView1 = (ListView) findViewById(R.id.listView1);

        data = new ArrayList<Map<String, Object>>();

        sa = new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,new String[]{"names","message"},new int[]{android.R.id
                .text1,android.R.id.text2});
        listView1.setAdapter(sa);
        readSMS();
    }


    public void readSMS() {
        //读取所有短信
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "body", "date", "type"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String body;
            String date;
            int type;
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                _id = cursor.getInt(0);
                address = cursor.getString(1);
                body = cursor.getString(2);
                date = cursor.getString(3);
                type = cursor.getInt(4);

                map.put("names", "对方："+address);
                map.put("message",body);

                Log.i("test", "_id=" + _id + " address=" + address + " body=" + body + " date=" + date + " type=" + type);
                data.add(map);
                //通知适配器发生改变
                sa.notifyDataSetChanged();
            }
        }
    }
    //搜索功能的实现



}
