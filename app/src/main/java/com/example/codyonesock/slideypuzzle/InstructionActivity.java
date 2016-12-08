package com.example.codyonesock.slideypuzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//random class that shows instructions and a counter (activity reserved for high scores)
public class InstructionActivity extends Activity {

    TextView txtCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);

        //references the view
        txtCounter = (TextView) findViewById(R.id.txtCounter);

        //grabbing the intent and block counter | set the counter
        Intent intent = getIntent();
        int counterValue = intent.getIntExtra("counter", 0);

        //setting the text
        txtCounter.setText(Integer.toString(counterValue));
    }
}

