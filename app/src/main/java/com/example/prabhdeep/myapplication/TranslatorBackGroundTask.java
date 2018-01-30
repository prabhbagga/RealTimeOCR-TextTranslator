package com.example.prabhdeep.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TranslatorBackGroundTask extends AsyncTask<String, Void, String> {

    //Declare Context
    public interface MyAsyncTaskListener{
        void onPostExecute(String s);
    }
    public static String resultString;
    Context ctx;
    //Set Context
    TranslatorBackGroundTask(MyAsyncTaskListener listener){
        this.listener=listener;
    }
    private MyAsyncTaskListener listener;
    @Override
    protected String doInBackground(String... params) {
        //String variables
        String textToBeTranslated = params[0];
        String languagePair = params[1];
        String jsonString;

        try {
            //Set up the translation call URL
            String yandexKey = "trnsl.1.1.20180113T102250Z.941f2a23f7d88084.0e9f4357f3500975fac5d2cbb11c2a946f72faa1";
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;
            URL yandexTranslateURL = new URL(yandexUrl);

            //Set Http Conncection, Input Stream, and Buffered Reader
            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb= new StringBuilder();
            String buf = bufferedReader.readLine();
            while(buf!=null){
                sb.append(buf);
                buf=bufferedReader.readLine();
            }
            String data=sb.toString();
            JSONObject jObj= new JSONObject(data);
            String string = jObj.getString("text");
            resultString=string.substring(string.indexOf('[')+2,string.indexOf(']')-1);
            return resultString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if(listener!=null){
            listener.onPostExecute(result);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}