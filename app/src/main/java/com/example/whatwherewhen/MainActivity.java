package com.example.whatwherewhen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import PackageStructure.Codes;
import PackageStructure.SheetInfo;
import PackageStructure.packageToSend;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_submit = (Button)findViewById(R.id.submit_btn);
        final EditText url = (EditText)findViewById(R.id.url);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String Url = url.getText().toString();
                SheetInfo info = new SheetInfo(Url);
                if(info.ParseID()==true)
                {
                    packageToSend Package = new packageToSend();
                    Package.code= Codes.GET_TEAMS_COUNT;
                    Package.sheetInfo = info;
                    new PostRequest(getApplicationContext()).execute(Package);
                }
                else{
                    showErrorMessage();
                }
            }

            private void showErrorMessage()
            {
                Toast.makeText(getApplicationContext(),"Invalid url!",Toast.LENGTH_LONG).show();
            }

        });


    }
}
