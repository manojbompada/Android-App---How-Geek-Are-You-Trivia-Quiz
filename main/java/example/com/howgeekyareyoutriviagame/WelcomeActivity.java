/*
    Cole Howell, Manoj Bompada, Justin Le
    WelcomeActivity.java
    ITCS 4180
 */

package example.com.howgeekyareyoutriviagame;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    Questions qs;
    ArrayList<Questions> qList = new ArrayList<>();
    ProgressBar qpgress;
    TextView ptxt;
    Button start;
    public static String QSTLIST = "question list";
    public static String WELCOMEKEY = "welcome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Welcome");

        Button exit = (Button) findViewById(R.id.exit);
        start = (Button) findViewById(R.id.quizStart);
        qpgress = (ProgressBar) findViewById(R.id.qprogressBar);
        ptxt = (TextView) findViewById(R.id.pgtxt);

        new DoWork().execute("http://dev.theappsdr.com/apis/spring_2016/hw3/index.php?qid=");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, QuizActivity.class);
                intent.putParcelableArrayListExtra(QSTLIST, qList);
                intent.putExtra(WELCOMEKEY, "welcome");
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    class DoWork extends AsyncTask<String, Void, ArrayList<Questions>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            start.setEnabled(false);
        }

        @Override
        protected ArrayList<Questions> doInBackground(String... params) {
            BufferedReader reader = null;

            for (int x = 0; x < 7; x++) {
                String indexedUrl = params[0] + x;
                Log.d("demo", "indexURL :" + indexedUrl);
                URL url;
                try {
                    url = new URL(indexedUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    String[] quest;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        Log.d("demo", "URL generated: " + sb.toString());
                        quest = line.split(";");
                        String quesid = quest[0];
                        String qtxt = quest[1];
                        String otxt1 = quest[2];
                        String otxt1score = quest[3];
                        String otxt2 = quest[4];
                        String otxt2score = quest[5];
                        String otxt3 = quest[6];
                        String otxt3score = quest[7];
                        String otxt4="",otxt4score="",otxt5="",otxt5score="",quesurl="";
                        if(quest.length >8 ){
                            if(quest[8].contains(".jpg")){
                                quesurl = quest[8];
                            }
                            else
                            {
                                otxt4 = quest[8];
                                otxt4score = quest[9];
                                if(quest.length >10){
                                    if(quest[10].contains(".jpg")){
                                        quesurl = quest[10];
                                    }
                                    else{
                                        otxt5=quest[10];
                                        otxt5score=quest[11];
                                    }
                                }
                                else{
                                    quesurl = "";
                                }
                            }
                        }
                        else{

                            otxt4 = "";
                            otxt4score = "";
                            quesurl = "";
                        }
                        qs = new Questions(quesid, qtxt, otxt1, otxt1score, otxt2, otxt2score, otxt3, otxt3score, otxt4, otxt4score,otxt5, otxt5score, quesurl);
                        qList.add(qs);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            return qList;
        }

        @Override
        protected void onPostExecute(ArrayList<Questions> qlst) {
            qpgress.setVisibility(View.INVISIBLE);
            ptxt.setVisibility(View.INVISIBLE);
            start.setEnabled(true);
            for(Questions x: qlst){
                Log.d("demo","questions in welcome: "+x.toString());
            }
        }
    }
}
