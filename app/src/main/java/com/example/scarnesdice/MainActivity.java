package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int humanTotalScore;
    private int computerTotalScore;
    private int humanCurrentScore;
    private int computerCurrentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set the initial scores
        setScore(0,0,0,0);



    }

    public int rollDice()
    {
        // get the roll of the dice value
        Random rand = new Random();
        int nextRoll = rand.nextInt(6) + 1;
        int diceId=0;

        Log.d("rollResult", Integer.toString(nextRoll));

        // decide the dice image depending on what the roll value was
        switch(nextRoll)
        {
            case 1:
                diceId = R.drawable.dice1;
                break;
            case 2:
                diceId = R.drawable.dice2;
                break;
            case 3:
                diceId = R.drawable.dice3;
                break;
            case 4:
                diceId = R.drawable.dice4;
                break;
            case 5:
                diceId = R.drawable.dice5;
                break;
            case 6:
                diceId = R.drawable.dice6;
                break;
            default:
                break;
        }

        // update the dice image
        ImageView imgView = (ImageView)findViewById(R.id.diceView);
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(), diceId);
        imgView.setImageBitmap(bMap);

        // return the dice value
        return nextRoll;
    }

    public boolean rollButton(View view)
    {
        Log.d("defaultButton.pressed", "rollbutton pressed");

        int i = rollDice();

        Log.d("humanDiceRoll",Integer.toString(i));

        if(i!=1)
            setScore(humanCurrentScore + i, humanTotalScore, computerCurrentScore, computerTotalScore);
        else
        {
            setScore(0, humanTotalScore, computerCurrentScore, computerTotalScore);
            computerTurn();
        }
        return true;
    }

    public boolean holdButton(View view)
    {
        Log.d("defaultButton.pressed", "holdbutton pressed");

        // store the current score in total, then let the computer have its turn
        setScore(0,humanTotalScore+humanCurrentScore, computerCurrentScore, computerTotalScore);

        computerTurn();

        return true;
    }

    public void buttonCtrl(boolean value)
    {
        Button roll = (Button)findViewById(R.id.rollButton);
        Button hold = (Button)findViewById(R.id.holdButton);
        roll.setEnabled(value);
        hold.setEnabled(value);
    }

    public void computerTurn()
    {
        int croll = 0;

        //disable roll and hold buttons
        buttonCtrl(false);

        while(computerCurrentScore < 20 && croll!=1)
        {
            croll = rollDice();
            Log.d("compRoll", "computer rolled a "+gInt(croll));
            if(croll != 1) {
                setScore(humanCurrentScore, humanTotalScore, computerCurrentScore + croll, computerTotalScore);
//                try {
//                    Thread.sleep(500);
//                } catch (Exception e) {
//                    Log.d("sleepexception", "sleep exception caught: " + e);
//                }
            }
        }


        setScore(humanCurrentScore, humanTotalScore, 0, computerTotalScore+computerCurrentScore);

        // re-enabled the roll and hold buttons
        buttonCtrl(true);
    }

    public boolean resetButton(View view)
    {
        Log.d("defaultButton.pressed", "resetbutton pressed");
        // reset the scores
        setScore(0,0,0,0);

        return true;
    }

    public void setScore(int hCScore, int hTScore, int cCScore, int cTScore)
    {
        humanTotalScore = hTScore;
        humanCurrentScore = hCScore;
        computerTotalScore = cTScore;
        computerCurrentScore = cCScore;

        String report = "("+gInt(humanTotalScore)+":";
        report = report + gInt(humanCurrentScore)+":";
        report = report + gInt(computerTotalScore)+":";
        report = report + gInt(computerCurrentScore)+")";

        TextView displayHTS, displayHCS, displayCTS, displayCCS;

        displayHCS = (TextView)findViewById(R.id.humanCurrent);
        displayHTS = (TextView)findViewById(R.id.humanScore);
        displayCCS = (TextView)findViewById(R.id.computerCurrent);
        displayCTS = (TextView)findViewById(R.id.computerScore);

        displayHCS.setText(gInt(humanCurrentScore));
        displayHTS.setText(gInt(humanTotalScore));
        displayCCS.setText(gInt(computerCurrentScore));
        displayCTS.setText(gInt(computerTotalScore));

        Log.d("current_score", report );
    }

    public String gInt(int a){
        return Integer.toString(a);
    }

}
