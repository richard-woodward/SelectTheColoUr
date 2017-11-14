package fr.eseo.dis.woodward.selectthecolour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int GAME_OVER = 1;
    public static final String GAME_SCORE = "GAME_SCORE";

    private Button btnPlay;
    private TextView tvScore;
    private TextView tvHigh;
    private int score;
    private int high;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button)findViewById(R.id.bn_start_game);
        tvScore = (TextView)findViewById(R.id.tv_score);
        tvHigh = (TextView)findViewById(R.id.tv_high);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intent, GAME_OVER);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GAME_OVER){
            if(resultCode == RESULT_OK){
                score = data.getIntExtra(GAME_SCORE,0);
                if(score > high){
                    high = score;
                }
                tvScore.setText(String.valueOf(score));
                tvHigh.setText(String.valueOf(high));
            }
        }
    }
}
