package seoulnightmarket.seoulnightmarket.etc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cjw94 on 2017-10-22.
 */

public class HttpTask
{
    private static HttpTask instance = null;
    Bitmap bitmap;

    public static synchronized HttpTask getInstance()
    {
        if (instance == null) {
            instance = new HttpTask();
        }
        return instance;
    }


    public static String GET(String url, String type)
    {
        InputStream is = null;
        String result = "";

        try
        {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();

            if(httpCon != null)
            {
                httpCon.setRequestMethod("GET");
                httpCon.setRequestProperty("Accept-Charset", "UTF-8");
                httpCon.setRequestProperty("Accept", "application/json");
                httpCon.setRequestProperty("Content-type", "application/json");
                httpCon.setConnectTimeout(10000);
            }

            is = httpCon.getInputStream();

            StringBuilder builder = new StringBuilder(); //문자열을 담기 위한 객체
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8")); //문자열 셋 세팅
            String line=null;

            while ((line = reader.readLine()) != null)
            {
                builder.append(line+ "\n");
            }

            result = builder.toString();

            reader.close();
            httpCon.disconnect();

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");

            switch(type)
            {
                case "소개":
                    break;
                case "야시장":
                    Singleton.getInstance().initStoreList();

                    for (int i = 0; i < posts.length(); i++)
                    {
                        String storeName = posts.optJSONObject(i).getString("Store_Name");
                        String imageSource = posts.optJSONObject(i).getString("Image_Source");

                        Singleton.getInstance().addStoreList(storeName,imageSource);
                    }

                    Singleton.getInstance().setServerRequest(true);
                    break;
                case "공연":
                    break;
                case "오시는길":
                    break;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public static String getURLEncode(String content)
    {
        try
        {
            return URLEncoder.encode(content, "utf-8");   // UTF-8
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String regionTranslate(String region)
    {
        String translated ="";

        switch(region)
        {
            case "Yeouido":
                translated = "여의도";
                break;
            case "DDP":
                translated = "DDP";
                break;
            case "Banpo":
                translated = "반포";
                break;
            case "Cheonggyecheon":
                translated = "청계천";
                break;
            case "CheonggyePlaza":
                translated = "청계광장";
                break;
        }

        return translated;
    }

    public Bitmap translateBitmap(String url)
    {
        final String de = url;

        Thread mThread = new Thread()
        {
            @Override
            public void run(){
                try{
                    URL url = new URL(de);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return bitmap;
    }

}