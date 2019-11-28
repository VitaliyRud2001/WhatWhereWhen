package com.example.whatwherewhen;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import PackageStructure.Codes;
import PackageStructure.packageToSend;

public class PostRequest extends AsyncTask<packageToSend,String, packageToSend>
{
Context context;
URL url;
HttpURLConnection urlConnection;
OutputStream outputStream;
BufferedReader bufferedReader;
String serverAnswer;

   public PostRequest(Context context)
   {
       this.context = context;
   }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected packageToSend doInBackground(packageToSend... packageToSends) {
       packageToSend packageToSend_ = packageToSends[0];
       try{
           url = new URL("https://script.google.com/macros/s/AKfycbxsHk7CkDOzMJ6oaw9m8UjqFhivpol5zD76oAJGuNRNgZTvq10O/exec");
           urlConnection = (HttpURLConnection)url.openConnection();
           urlConnection.setRequestMethod("POST");
           urlConnection.setDoOutput(true);
           outputStream = urlConnection.getOutputStream();

           String request = packageToSend_.CreateRequest();

           outputStream.write(request.getBytes());
           bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
           serverAnswer = bufferedReader.readLine();
           if(serverAnswer.equals("Error"))
           {
               packageToSend_.ResponseCode= Codes.ERROR_ON_SERVER;
           }
           else
           {
             if(packageToSend_.code==Codes.GET_TEAMS_COUNT)
             {
                 packageToSend_.sheetInfo.CountOfTeams=Integer.parseInt(serverAnswer);
                 packageToSend_.ResponseCode=Codes.SERVER_OK;
             }
             else if(packageToSend_.code==Codes.ANSWER_BTN_CLICKED)
             {
                 if(serverAnswer.equals("DISABLED"))
                 {
                     packageToSend_.ResponseCode=Codes.ANSWER_CANCLED;
                 }
                 else if(serverAnswer.equals("OK")){
                     packageToSend_.ResponseCode=Codes.ANSWER_SUBMITED;
                 }

             }

           }

       }catch (Exception e)
       {
           Toast.makeText(context,"Lol",Toast.LENGTH_LONG).show();
         return null;
       }


        return packageToSend_;
    }


    @Override
    protected void onPostExecute(packageToSend result)
    {
    if(result==null)
    {
        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
    }
    else
    {
        if(result.ResponseCode==Codes.SERVER_OK) {
            if (result.code == Codes.GET_TEAMS_COUNT) {
                Intent intent = new Intent(context, Rounds.class);
                intent.putExtra("SheetInfo", result.sheetInfo);
                context.startActivity(intent);
            }
        }
        else if(result.ResponseCode==Codes.ANSWER_CANCLED)
        {
        result.button.setBackgroundResource(R.drawable.btn_bg);
        }else if(result.ResponseCode==Codes.ANSWER_SUBMITED)
        {
            result.button.setBackgroundResource(R.drawable.btn_pressed_bg);
        }
    }
    }
}
