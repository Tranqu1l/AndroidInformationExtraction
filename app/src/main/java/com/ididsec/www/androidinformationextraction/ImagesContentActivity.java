package com.ididsec.www.androidinformationextraction;





/**
 * Created by ZMC on 2018/11/23.
 */

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.util.Date;

import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;


public class ImagesContentActivity extends Activity {
    private ListView lv_images;
    private Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_images);
        lv_images = (ListView) findViewById(R.id.lv_images);
        adapter = new ImageAdapter(this);
        lv_images.setAdapter((ListAdapter) adapter);


        //lv_images.setOnItemClickListener(new AdapterView.OnItemClickListener(){

         //   @Override
         //   public void onItemClick(AdapterView<?>
          //                                  arg0, View arg1, int arg2,
          //                          long arg3){
                //displayToast();

         //   }
        //});

    }


    private void displayToast() {
        Toast.makeText(ImagesContentActivity.this, "图片信息", Toast.LENGTH_SHORT).show();
    }
}