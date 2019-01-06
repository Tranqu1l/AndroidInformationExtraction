package com.ididsec.www.androidinformationextraction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
public class CategoryActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_category);

        ImageButton button_text = (ImageButton) findViewById(R.id.button_txt);
        button_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentimg = new Intent(CategoryActivity.this, ShowTextActivity.class);
                startActivity(intentimg);
            }
        }
        );

        ImageButton button_doc = (ImageButton) findViewById(R.id.button_doc);
        button_doc.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               Intent intentimg = new Intent(CategoryActivity.this, ShowDocActivity.class);
                                               startActivity(intentimg);
                                           }
                                       }
        );

        ImageButton button_excel = (ImageButton) findViewById(R.id.button_excel);
        button_excel.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent intentimg = new Intent(CategoryActivity.this, ShowExcelActivity.class);
                                                startActivity(intentimg);
                                            }
                                        }
        );

        ImageButton button_pdf = (ImageButton) findViewById(R.id.button_pdf);
        button_pdf.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent intentimg = new Intent(CategoryActivity.this, ShowPdfActivity.class);
                                                startActivity(intentimg);
                                            }
                                        }
        );

        ImageButton button_pic = (ImageButton) findViewById(R.id.button_pic);
        button_pic.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent intentimg = new Intent(CategoryActivity.this, ImagesContentActivity.class);
                                                startActivity(intentimg);
                                            }
                                        }
        );

        ImageButton button_video = (ImageButton) findViewById(R.id.button_video);
        button_video.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                Intent intentimg = new Intent(CategoryActivity.this, ShowVideoActivity.class);
                                                startActivity(intentimg);
                                            }
                                        }
        );
    }





}