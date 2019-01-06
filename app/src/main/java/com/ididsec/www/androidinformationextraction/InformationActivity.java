package com.ididsec.www.androidinformationextraction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DeviceInformationUtil;

/**
 * Created by ZMC on 2018/11/23.
 */

public class InformationActivity extends AppCompatActivity {

    private ListView listView2;
    private SimpleAdapter sb;
    private List<Map<String, Object>> Informationdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super. onCreate(savedInstanceState);
        setContentView(R.layout.content_information);
        //得到ListView
        listView2 = (ListView) findViewById(R.id.listView2);

        Informationdata = new ArrayList<Map<String, Object>>();

        sb = new SimpleAdapter(this, Informationdata, android.R.layout.simple_list_item_2, new String[]{"type", "message"}, new int[]{android.R.id
                .text1, android.R.id.text2});
        listView2.setAdapter(sb);

        getInformation();
    }

    public void getInformation(){

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("type","品牌");
        map1.put("message",DeviceInformationUtil.getBrand());
        Informationdata.add(map1);
        sb.notifyDataSetChanged();

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("type","机型");
        map2.put("message",DeviceInformationUtil.getDeviceName());
        Informationdata.add(map2);
        sb.notifyDataSetChanged();

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("type","AndroidID");
        map3.put("message",DeviceInformationUtil.getAndroidId(this));
        Informationdata.add(map3);
        sb.notifyDataSetChanged();

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("type","EI_SI");
        map4.put("message",DeviceInformationUtil.getEI_SI("EI_SI",this));
        Informationdata.add(map4);
        sb.notifyDataSetChanged();

        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("type","当前手机号");
        map5.put("message",DeviceInformationUtil.getCard1Number(this));
        Informationdata.add(map5);
        sb.notifyDataSetChanged();


    }
}
