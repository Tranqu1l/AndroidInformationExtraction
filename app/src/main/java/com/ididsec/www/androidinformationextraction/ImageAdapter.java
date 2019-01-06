package com.ididsec.www.androidinformationextraction;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当前的图片浏览器的适配器
 *
 */
public class ImageAdapter extends BaseAdapter{

    /**
     * ctx
     */
    private Context context;
    /**
     * list
     */
    /**
     * 图片信息
     *
     *
     */
    public class ImageInfo {
        /**
         * 标题
         */
        public String title;
        /**
         * 大小
         */
        public String size;
        /**
         * 描述
         */
        public String description;
        /**
         * 图片路径
         */
        public String data;
        /**
         * 带后缀名
         */
        public String displayName;
    }
    List<ImageInfo> list = new ArrayList<ImageInfo>();

    /**
     * ctor
     */
    public ImageAdapter(Context context) {
        this.context = context;
        // 加载数据库中的图片信息
        loadImages();
    }

    /**
     * 加载图片信息
     */
    private void loadImages() {
        list.clear();
        getImages(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, list);
        getImages(MediaStore.Images.Media.INTERNAL_CONTENT_URI, list);
        Log.i("list.size(): ", list.size() + "");
    }

    /**
     * 得到list
     *
     * @param uri
     * @param list
     */
    private void getImages(Uri uri, List<ImageInfo> list) {
        Cursor externalCursor = MediaStore.Images.Media.query(
                context.getContentResolver(), uri, new String[] {
                        MediaStore.Images.Media.TITLE,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DESCRIPTION });
        if (externalCursor != null) {
            while (externalCursor.moveToNext()) {
                ImageInfo model = new ImageInfo();
                model.title = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.TITLE));
                model.displayName = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                model.description = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
                model.size = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.SIZE));
                model.data = externalCursor.getString(externalCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                list.add(model);
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView view;

        int width = (int) (getScreenWidth() / 3);
        int Height=(3*width/4);
        if (convertView != null) {
            view = (ImageView) convertView;
        } else {
            view = new ImageView(context);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,
                    Height);
            view.setLayoutParams(params);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    fileHandle(list,position);
                }
            });
        }


        view.setImageBitmap(getThumbnail(list.get(position).data, width, Height));
        return view;
    }

    private  int changetokb(String size) {
            int intsize;
            intsize=(Integer.parseInt(size)/(1024*8));
            return intsize;
    }


    private void displayToast() {
        Toast.makeText(context, "图片", Toast.LENGTH_SHORT).show();
    }
    /**
     * 获取缩略图
     *
     * @param pathName
     * @param width
     * @param height
     * @return
     */
    private Bitmap getThumbnail(String pathName, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opts);// 图片未加载进内存，但是可以读取长宽
        int oriWidth = opts.outWidth;
        int oriHeight = opts.outHeight;
        opts.inSampleSize = oriWidth / width;
        opts.inSampleSize = opts.inSampleSize > oriHeight / height ? opts.inSampleSize
                : oriHeight / height;
        opts.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(pathName, opts);// 图片加载进内存
        Bitmap result = Bitmap.createScaledBitmap(decodeFile, width, height,
                false);
        decodeFile.recycle();
        return result;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private float getScreenWidth() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private float getScreenHeight() {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    private void fileHandle(final List<ImageInfo> list,int position) {



        new AlertDialog.Builder(context).setTitle("信息摘要")
                .setMessage("标题："+list.get(position).title+'\n'+"图像路径："+list.get(position).data+ '\n'+
                "图像大小："+changetokb(list.get(position).size)+"kb"+'\n'+
                "图像描述："+list.get(position).description+'\n')
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();



    }
}