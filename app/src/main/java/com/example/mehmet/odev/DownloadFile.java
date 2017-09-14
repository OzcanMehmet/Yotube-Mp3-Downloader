package com.example.mehmet.odev;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class DownloadFile extends AsyncTask<Void, String, File> {

    /**
     * Before starting background thread Show Progress Bar Dialog
     * */
    Context cnt;
    public DownloadFile(Context contex,String url)
    {

        cnt=contex;
        Url = "http://www.youtubeinmp3.com/fetch/?video="+url;
    }
    ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //showProgressDialog();
        dialog=new ProgressDialog(cnt);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.show();
    }
    String Url = "";
    /**
     * Downloading file in background thread
     * */
    @Override
    protected File doInBackground(Void... params) {
        int count;
        File file=null;

        try {
            URL url = new URL(Url);
            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();

            InputStream input = conection.getInputStream();

            File SDCardRoot = Environment.getExternalStorageDirectory();

            File folder = new File(SDCardRoot, "downloadmusic");
            if (!folder.exists())
                folder.mkdir();
            file = new File(folder,new Date().getTime()+"music.mp3");


            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;

                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (Exception e) {
            Toast.makeText(cnt,"Yükleme başarısız oldu",Toast.LENGTH_SHORT).show();
        }
        return file;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        dialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(File file) {
        // dismiss the dialog after the file was downloaded
        //dismissProgressDialog();
        dialog.hide();
    }

}