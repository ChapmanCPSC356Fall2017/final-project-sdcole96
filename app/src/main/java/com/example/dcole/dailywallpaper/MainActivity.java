package com.example.dcole.dailywallpaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.WallpaperManager;
import java.util.concurrent.ExecutionException;
import java.io.IOException;
import android.widget.TextView;
import android.util.Log;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
import java.net.URL;
import android.widget.ImageView;
import android.graphics.Bitmap;
import java.io.File;
import java.io.FileInputStream;
import android.graphics.BitmapFactory;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.FileOutputStream;
import android.net.Uri;
import java.util.ArrayList;
import android.widget.Toast;
import java.lang.String;
import android.widget.EditText;

import static com.example.dcole.dailywallpaper.R.mipmap.metoo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Button button;
    public TextView text;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public ImageView img4;
    public ImageView img5;
    public ImageView img6;
    public ImageView img7;
    public ImageView img8;
    public ImageView img9;
    public EditText searchbar;
    public boolean toggle = true;


    public Drawable thisGuy;
    public String result;
    private static String TAG = "hey";
    public ArrayList<String> codes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.changewallpaper);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle = !toggle;
                if(toggle)
                {
                    theHardPart("https://api.instagram.com/v1/users/566550263/media/recent/?access_token=222809106.465d814.c3eca269f13047649edde4cc7a0b8ca4");
                }
                else
                {
                    theHardPart("https://api.instagram.com/v1/users/self/media/recent/?access_token=222809106.465d814.c3eca269f13047649edde4cc7a0b8ca4");
                }
            }
        });

        img1 = (ImageView)findViewById(R.id.img1);
        img1.setOnClickListener(this);

        img2 = (ImageView)findViewById(R.id.img2);
        img2.setOnClickListener(this);

        img3 = (ImageView)findViewById(R.id.img3);
        img3.setOnClickListener(this);

        img4 = (ImageView)findViewById(R.id.img4);
        img4.setOnClickListener(this);

        img5 = (ImageView)findViewById(R.id.img5);
        img5.setOnClickListener(this);

        img6 = (ImageView)findViewById(R.id.img6);
        img6.setOnClickListener(this);

        img7 = (ImageView)findViewById(R.id.img7);
        img7.setOnClickListener(this);

        img8 = (ImageView)findViewById(R.id.img8);
        img8.setOnClickListener(this);

        img9 = (ImageView)findViewById(R.id.img9);
        img9.setOnClickListener(this);

        //Some url endpoint that you may have
        String myUrl = "https://api.instagram.com/v1/users/566550263/media/recent/?access_token=222809106.465d814.c3eca269f13047649edde4cc7a0b8ca4";
        //String to place our result in
        theHardPart(myUrl);
    }

    public void theHardPart(String url)
    {
        String myUrl = url;
        //Perform the doInBackground method, passing in our url
        try {
            HttpGetRequest getRequest = new HttpGetRequest();
            result = getRequest.execute(myUrl).get();
            ArrayList<String> arr = new ArrayList<>();
            int j = 0;
            for(int i = 0; i <=35; i++)
            {
                j++;
                int s = result.indexOf("url");
                result = result.substring(s + 7);
                int end = result.indexOf("\"");

                if(j == 3)
                {
                    arr.add(result.substring(0,end));
                    j = 0;
                }
                result = result.substring(end);
            }

            new DownloadImageTask(img1).execute(arr.get(0));
            new DownloadImageTask(img2).execute(arr.get(1));
            new DownloadImageTask(img3).execute(arr.get(2));
            new DownloadImageTask(img4).execute(arr.get(3));
            new DownloadImageTask(img5).execute(arr.get(5));
            new DownloadImageTask(img6).execute(arr.get(6));
            new DownloadImageTask(img7).execute(arr.get(7));
            new DownloadImageTask(img8).execute(arr.get(8));
            new DownloadImageTask(img9).execute(arr.get(9));

        }
        catch (InterruptedException e)
        {

        }
        catch (ExecutionException e)
        {
            System.out.println("YOOOO");
        }
    }

    public void updateCodes(ArrayList<String> arr)
    {
        codes = arr;
    }

    @Override
    public void onClick(View v)
    {
            v.buildDrawingCache();
            Bitmap bmap = v.getDrawingCache();
            int x;
            if (bmap.getWidth() > 100) {
                x = 50;
            } else {
                x = 0;
            }
            Bitmap croppedBmp = Bitmap.createBitmap(bmap, bmap.getWidth() / 4, 0, bmap.getWidth() / 2, bmap.getHeight());

            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                myWallpaperManager.setBitmap(croppedBmp);
                Toast.makeText(getApplicationContext(), "Wallpaper successfully changed.",
                        Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
