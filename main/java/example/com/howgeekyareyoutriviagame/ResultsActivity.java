/*
    Cole Howell, Manoj Bompada, Justin Le
    ResultsActivity.java
    ITCS 4180
 */

package example.com.howgeekyareyoutriviagame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    Button quit,tryAgain;
    int results = 0;
    ImageView imageView;
    TextView description, title;
    ArrayList<Questions> qslst = new ArrayList<Questions>();
    public static String RESULT_OK = "okok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Results");

        quit = (Button) findViewById(R.id.quit);
        tryAgain = (Button) findViewById(R.id.tryAgain);
        imageView = (ImageView) findViewById(R.id.geekResultsImage);
        description = (TextView) findViewById(R.id.resultsDescription);
        title = (TextView) findViewById(R.id.geekTitle);

        if(getIntent().getExtras() !=null){
            qslst  = getIntent().getExtras().getParcelableArrayList(QuizActivity.QUESTIONS);
            results = getIntent().getExtras().getInt(QuizActivity.QUIZTOTAL);

            if(results >= 0 && results <= 10){
                imageView.setImageResource(R.drawable.non_geek);
                imageView.getLayoutParams().height = 500;
                imageView.getLayoutParams().width = 500;
                description.setText("There isn't a single geeky bone in your body. You perfer to party rather than study, and have someone else fix your computer, if need be. You're just too cool for this. You probably don't even wear glasses!");
                title.setText("Non-Geek");
            } else if(results >= 11 && results <= 50){
                imageView.setImageResource(R.drawable.semi_geek);
                imageView.getLayoutParams().height = 500;
                imageView.getLayoutParams().width = 500;
                description.setText("Maybe you're just influenced by the trend, or maybe you just got it all perfectly balanced. You have some geeky traits, but they aren't as 'hardcore' and they don't take over your life. You like some geeky things, but aren't nearly as obsessive about them as the uber-geeks. You actually get to enjoy both worlds.");
                title.setText("Semi-Geek");
            } else{
                imageView.setImageResource(R.drawable.uber_geek);
                imageView.getLayoutParams().height = 500;
                imageView.getLayoutParams().width = 500;
                description.setText("You are the geek supreme! You are likely to be interested in technology, science, gaming and geeky media such as Sci-Fi and fantasy. All the mean kids that used to laugh at you in high school are now begging you for a job. Be proud of your geeky nature, for geeks shall inherit the Earth!");
                title.setText("Uber-Geek");
            }

        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, WelcomeActivity.class);
                startActivity(intent);
                ResultsActivity.this.finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this,QuizActivity.class);
                intent.putParcelableArrayListExtra(QuizActivity.QUESTIONS, qslst);
                intent.putExtra(RESULT_OK, "ok");
                startActivity(intent);
                ResultsActivity.this.finish();
            }
        });
    }
}
