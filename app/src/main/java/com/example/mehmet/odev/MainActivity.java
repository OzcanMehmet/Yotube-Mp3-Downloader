package com.example.mehmet.odev;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    MediaPlayer player;
    public  String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent uri=getIntent();
        final MainActivity actvy=this;

        if(uri!=null)
        {
            url=uri.getStringExtra(Intent.EXTRA_TEXT);
            EditText text=(EditText)findViewById(R.id.url);
            text.setText(url);
        }

        Button cal=(Button)findViewById(R.id.cal);
        cal.setOnClickListener(this);
        Button durdur=(Button)findViewById(R.id.durdur);
        durdur.setOnClickListener(this);
        final Button indir=(Button)findViewById(R.id.indir);
        indir.setOnClickListener(this);

    }
    void cal()
    {
        if(player!=null)
        player.start();
    }
    void yukle() {
        if (!url.trim().isEmpty())
        {
            Uri myUri = Uri.parse("http://www.youtubeinmp3.com/fetch/?video=" + url);
            player = MediaPlayer.create(this, myUri);
            cal();
        }
        else
            Toast.makeText(getApplicationContext(),"Url boş.",Toast.LENGTH_LONG).show();

    }
    void indir()
    {
        if(!url.trim().isEmpty())
            new DownloadFile(this,url).execute();
        else
            Toast.makeText(getApplicationContext(),"Url boş.",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.indir:
                try {
                    EditText text=(EditText)findViewById(R.id.url);
                    url= text.getText().toString();
                    indir();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"Yükleme işlemi başarısız oldu.",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.durdur:
                if(player!=null)
                    player.pause();
                break;
            case R.id.cal:
                try {
                    EditText text=(EditText)findViewById(R.id.url);
                    url= text.getText().toString();
                    if (player != null)
                        cal();
                    else
                        yukle();
                }
                catch (Exception ex)
                {
                    player=null;
                    Toast.makeText(getApplicationContext(),"Çalma işlemi başarısız oldu.",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
