package com.example.whatwherewhen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import PackageStructure.AnswerPackage;
import PackageStructure.Codes;
import PackageStructure.SheetInfo;
import PackageStructure.packageToSend;

public class Rounds extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds);


        Intent i = getIntent();
        final SheetInfo info = (SheetInfo) i.getSerializableExtra("SheetInfo");
        final int roundNum = i.getIntExtra("RoundNum",1);
        final int questionNum = i.getIntExtra("QuestionNum",1);
        final char Columm = i.getCharExtra("Column",'B');
        setTitle("Раунд "+String.valueOf(roundNum)+" вопрос "+String.valueOf(questionNum));
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.button_layout);
        linearLayout.setGravity(Gravity.CENTER);
        TextView viewRound = (TextView)findViewById(R.id.RoundNum_status);
        viewRound.setText(String.format("Раунд %d\nВопрос %d",roundNum,questionNum));


            for(int a = 1;a<=info.CountOfTeams;a++)
            {

                Button button = new Button(this);
                button.setPressed(false);
                LinearLayout.LayoutParams r_button = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                r_button.setMargins(0,20,0,0);
                button.setLayoutParams(r_button);
                button.setBackgroundResource(R.drawable.btn_bg);
                StringBuilder builder = new StringBuilder();
                button.setPadding(0,10,0,10);
                builder.append(Columm).append(a+1);
                button.setText(String.format("Команда №%d",a));
                linearLayout.addView(button);
                button.setOnClickListener(new AnswerButtonClicked(button,info,builder.toString()));
            }


       if(!(roundNum==3 && questionNum==12)) {
           Button button = (Button)findViewById(R.id.nxtQuestin_btn);
           button.setVisibility(View.VISIBLE);
           button.setOnClickListener(
                   new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(getApplicationContext(), Rounds.class);
                           intent.putExtra("SheetInfo", info);
                           if (questionNum != 12) {
                               intent.putExtra("RoundNum", roundNum);
                               intent.putExtra("QuestionNum", questionNum + 1);
                               intent.putExtra("Column", (char) ((int) Columm + 1));
                           } else if (questionNum == 12) {
                               intent.putExtra("RoundNum", roundNum + 1);
                           }
                           getApplicationContext().startActivity(intent);
                       }
                   }
           );
       }
    }

    class AnswerButtonClicked implements  View.OnClickListener
    {
        String Range;
        SheetInfo sheetInfo;
        Button button;

        public AnswerButtonClicked(Button button, SheetInfo info,String range)
        {
            this.Range = range;
            this.sheetInfo = info;
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            new PostRequest(getApplicationContext()).execute(CreatePackage());
        }

        public packageToSend CreatePackage()
        {
            packageToSend PackageToSend = new packageToSend();
            PackageToSend.button = this.button;
            PackageToSend.sheetInfo = this.sheetInfo;
            PackageToSend.code= Codes.ANSWER_BTN_CLICKED;
            AnswerPackage answerPackage = new AnswerPackage();
            answerPackage.Range = this.Range;
            answerPackage.Round = 1;
            PackageToSend.answer = answerPackage;
            return PackageToSend;
        }


    }




}
