/*
    Cole Howell, Manoj Bompada, Justin Le
    QuizActivity.java
    ITCS 4180
 */

package example.com.howgeekyareyoutriviagame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    Button quit, next;
    ArrayList<Questions> qslst = new ArrayList<Questions>();
    ImageView questimg;
    TextView questxt,quesno;
    ProgressBar imageProgressBar;
    RadioGroup radioGroup;
    int listindex = 1;
    RadioButton btn1,btn2,btn3,btn4,btn5;
    int optvalue=0,total=0;
    static String QUIZTOTAL="total";
    public static final int REQ_CODE = 100;
    public static String QUESTIONS= "question list";
    List<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");

        quit = (Button) findViewById(R.id.quit);
        next = (Button) findViewById(R.id.next);
        questimg  = (ImageView) findViewById(R.id.qimg);
        questxt = (TextView) findViewById(R.id.qtxt);
        quesno= (TextView) findViewById(R.id.qno);
        imageProgressBar = (ProgressBar) findViewById(R.id.imageProgressBar);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rellayout);

        questimg.getLayoutParams().width = 400;
        questimg.getLayoutParams().height = 400;

        if(getIntent().getExtras() !=null){
            if(getIntent().getExtras().containsKey(ResultsActivity.RESULT_OK)) {
                qslst = getIntent().getExtras().getParcelableArrayList(QuizActivity.QUESTIONS);
                String OK = getIntent().getExtras().getString(ResultsActivity.RESULT_OK);
                Log.d("Demo", "From Result ACT  --------------- ");
            }
        }

        if(getIntent().getExtras() !=null){
            if(getIntent().getExtras().containsKey(WelcomeActivity.WELCOMEKEY)) {
                qslst = getIntent().getExtras().getParcelableArrayList(WelcomeActivity.QSTLIST);
                for (Questions x : qslst) {
                    Log.d("demo", "questions in quizact**********************: " + x.toString());
                }
            }
        }

        Collections.shuffle(qslst);

        if(!qslst.isEmpty()){
            questxt.setText(qslst.get(0).questtxt);
            quesno.setText("Q1");

            createRadioGroup();
            radioGroup.setPadding(0, 60, 0, 0);
            btn1 = new RadioButton(this);
            btn1.setText(qslst.get(0).opttxt1);
            btn1.setId(View.generateViewId());
//            radioGroup.addView(btn1);
            btn2 = new RadioButton(this);
//            radioGroup.addView(btn2);
            btn2.setId(View.generateViewId());
            btn2.setText(qslst.get(0).opttxt2);
            btn3 = new RadioButton(this);
//            radioGroup.addView(btn3);
            btn3.setId(View.generateViewId());
            btn3.setText(qslst.get(0).opttxt3);
            buttons.add(btn1);
            buttons.add(btn2);
            buttons.add(btn3);
            Log.d("demo", "opt4 txt in quizact: " + qslst.get(0).opttxt4);
            if(!qslst.get(0).opttxt4.toString().isEmpty()){
                btn4 = new RadioButton(this);
//                radioGroup.addView(btn4);
                btn4.setId(View.generateViewId());
                btn4.setText(qslst.get(0).opttxt4);
                buttons.add(btn4);
            }

            if(!qslst.get(0).opttxt5.toString().isEmpty()){
                btn5 = new RadioButton(this);
//                radioGroup.addView(btn5);
                btn5.setId(View.generateViewId());
                btn5.setText(qslst.get(0).opttxt5);
                buttons.add(btn5);
            }

            Collections.shuffle(buttons);

            for (int i = 0; i < buttons.size(); i++){
                radioGroup.addView(buttons.get(i));
            }

            relativeLayout.addView(radioGroup);

            if(!qslst.get(0).questimgUrl.isEmpty()){
                imageProgressBar.setVisibility(View.VISIBLE);
                new getUrlImage().execute(qslst.get(0).questimgUrl);
            }else{
                imageProgressBar.setVisibility(View.INVISIBLE);
                questimg.setImageResource(0);
            }
            groupButtonListener(0);
        }
        else{
            Toast.makeText(QuizActivity.this,"Question list is empty", Toast.LENGTH_SHORT).show();
        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, WelcomeActivity.class);
                startActivity(intent);
                QuizActivity.this.finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttons.clear();
                total += optvalue;

                Log.d("demo", "Total value: " + total);

                if(radioGroup.getCheckedRadioButtonId()!=-1){
                    int length = qslst.size();
                    if(listindex < length){
                        relativeLayout.removeView(radioGroup);
                        createRadioGroup();
                        radioGroup.setPadding(0, 60, 0, 0);

                        questxt.setText(qslst.get(listindex).questtxt);
                        quesno.setText("Q" + (listindex + 1));

                        btn1 = new RadioButton(QuizActivity.this);
                        btn1.setText(qslst.get(listindex).opttxt1);
                        btn1.setId(View.generateViewId());
                        //radioGroup.addView(btn1);
                        btn2 = new RadioButton(QuizActivity.this);
                        //radioGroup.addView(btn2);
                        btn2.setId(View.generateViewId());
                        btn2.setText(qslst.get(listindex).opttxt2);
                        btn3 = new RadioButton(QuizActivity.this);
                        //radioGroup.addView(btn3);
                        btn3.setId(View.generateViewId());
                        btn3.setText(qslst.get(listindex).opttxt3);
                        buttons.add(btn1);
                        buttons.add(btn2);
                        buttons.add(btn3);
                        Log.d("demo", "opt4 txt in quizact: " + qslst.get(listindex).opttxt4);
                        if(!qslst.get(listindex).opttxt4.isEmpty()){
                            btn4 = new RadioButton(QuizActivity.this);
                           // radioGroup.addView(btn4);
                            btn4.setId(View.generateViewId());
                            btn4.setText(qslst.get(listindex).opttxt4);
                            buttons.add(btn4);
                        }

                        if(!qslst.get(listindex).opttxt5.isEmpty()){
                            btn5 = new RadioButton(QuizActivity.this);
                            //radioGroup.addView(btn5);
                            btn5.setId(View.generateViewId());
                            btn5.setText(qslst.get(listindex).opttxt5);
                            buttons.add(btn5);
                        }

                        Collections.shuffle(buttons);

                        for (int i = 0; i < buttons.size(); i++){
                            radioGroup.addView(buttons.get(i));
                        }

                        relativeLayout.addView(radioGroup);

                        if(!qslst.get(listindex).questimgUrl.isEmpty()){
                            imageProgressBar.setVisibility(View.VISIBLE);
                            new getUrlImage().execute(qslst.get(listindex).questimgUrl);
                        }else{
                            imageProgressBar.setVisibility(View.INVISIBLE);
                            questimg.setImageResource(0);
                        }

                        for(Questions x: qslst){
                            Log.d("demo","questions in next:"+listindex+" "+x.toString());
                        }

                        int clickindex = listindex;
                        groupButtonListener(clickindex);
                        listindex++;
                    }
                    else{
                        Toast.makeText(QuizActivity.this,"You finished the survey",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
                        intent.putExtra(QUIZTOTAL, total);
                        intent.putParcelableArrayListExtra(QUESTIONS, qslst);
                        startActivityForResult(intent, REQ_CODE);
                        QuizActivity.this.finish();
                    }
                }
                else{
                    Toast.makeText(QuizActivity.this,"Please select ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class getUrlImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            URL url;

            try {
                url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageProgressBar.setVisibility(View.INVISIBLE);
            questimg.setImageBitmap(bitmap);
        }
    }

    private void createRadioGroup(){

        radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setId(View.generateViewId());
        RelativeLayout.LayoutParams groupLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        groupLayoutParams.addRule(RelativeLayout.BELOW, questxt.getId());
        groupLayoutParams.setMargins(0,-150,0,0);
        radioGroup.setLayoutParams(groupLayoutParams);

    }

    private void groupButtonListener(final int clkind){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                final int ind = clkind;

                if( checkedId == btn1.getId()){
                    optvalue = Integer.parseInt(qslst.get(ind).opttxt1score);
                }
                else if(checkedId == btn2.getId()){
                    optvalue = Integer.parseInt(qslst.get(ind).opttxt2score);
                }
                else if(checkedId == btn3.getId()){
                    optvalue = Integer.parseInt(qslst.get(ind).opttxt3score);
                }
                else if(checkedId == btn4.getId()){
                    optvalue = Integer.parseInt(qslst.get(ind).opttxt4score);
                }
                else if(checkedId == btn5.getId()){
                    optvalue = Integer.parseInt(qslst.get(ind).opttxt5score);
                }
            }
        });
    }

}
