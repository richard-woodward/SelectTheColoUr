package fr.eseo.dis.woodward.selectthecolour;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Random rand = new Random();

    private int[] gameIntColours;
    private String[] gameTextColours;
    private double timer;
    private int score;

    private int mysteryColourIndex;
    private int mysteryTextIndex;


    private Button btnChoice1;
    private Button btnChoice2;
    private TextView tvColour;
    private TextView tvTimeLeft;
    private ImageView ivResult;
    private CountDownTimer timeOut;

    private DecimalFormat df = new DecimalFormat("0.0");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameIntColours = new int[]{
                ResourcesCompat.getColor(getResources(),R.color.guess_black,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_blue,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_brown,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_cyan,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_green,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_orange,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_pink,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_purple,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_red,null),
                ResourcesCompat.getColor(getResources(),R.color.guess_yellow,null),
        };
        gameTextColours = new String[]{
                getString(R.string.guess_black),
                getString(R.string.guess_blue),
                getString(R.string.guess_brown),
                getString(R.string.guess_cyan),
                getString(R.string.guess_green),
                getString(R.string.guess_orange),
                getString(R.string.guess_pink),
                getString(R.string.guess_purple),
                getString(R.string.guess_red),
                getString(R.string.guess_yellow),
        };

        btnChoice1 = (Button)findViewById(R.id.bn_choice1);
        btnChoice2 = (Button)findViewById(R.id.bn_choice2);
        tvTimeLeft = (TextView) findViewById(R.id.tv_timer);
        tvColour = (TextView)findViewById(R.id.tv_colour);
        ivResult = (ImageView)findViewById(R.id.iv_result);
        updateInterface();
        btnChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(1);
            }
        });
        btnChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(2);
            }
        });
        score = 0;
        timer = 60000;

        timeOut = new CountDownTimer((int)timer,100){

            @Override
            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished/1000.0;
                tvTimeLeft.setText(String.format(getString(R.string.time_left),df.format(timer)));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.GAME_SCORE,Math.max(score,0));
                setResult(RESULT_OK,intent);
                finish();

            }
        }.start();
    }

    private void buttonClicked(int buttonNumber){
        if(buttonNumber == 1){
            if(gameTextColours[mysteryColourIndex].equals(btnChoice1.getText())){
                score ++;
                ivResult.setImageDrawable(getDrawable(R.drawable.yes));
            }
            else{
                score --;
                ivResult.setImageDrawable(getDrawable(R.drawable.no));
            }
        }
        else if(buttonNumber == 2){
            if(gameTextColours[mysteryColourIndex].equals(btnChoice2.getText())){
                score ++;
                ivResult.setImageDrawable(getDrawable(R.drawable.yes));
            }
            else{
                score --;
                ivResult.setImageDrawable(getDrawable(R.drawable.no));
            }

        }
        updateInterface();
    }

    private void updateInterface(){
        mysteryColourIndex = rand.nextInt(gameIntColours.length);
        mysteryTextIndex = rand.nextInt(gameIntColours.length);
        int mysteryFalseChoice = mysteryTextIndex;
        while (mysteryFalseChoice == mysteryColourIndex){
            mysteryFalseChoice = rand.nextInt(gameIntColours.length);
        };
        int choice1=mysteryFalseChoice;
        int choice2=mysteryColourIndex;
        if(rand.nextInt(10)>=5){
            choice1=mysteryColourIndex;
            choice2=mysteryFalseChoice;
        }
        btnChoice1.setText(gameTextColours[choice1]);
        btnChoice2.setText(gameTextColours[choice2]);
        tvColour.setTextColor(gameIntColours[mysteryColourIndex]);
        tvColour.setText(gameTextColours[mysteryTextIndex]);
    }
}
