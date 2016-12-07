package com.example.codyonesock.slideypuzzle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

/* Here Is the MainActivity Class which I'm going to use to put everything together from the
 * Frame and the FrameView to make the physical activity that the user sees
 */

public class MainActivity extends AppCompatActivity {

    //this is going to represent the main view
    private ViewGroup viewGroup;

    //access to the Frame and FrameView
    private Frame frame;
    private FrameView frameView;

    //the textview where I'm going to have a counter
    private TextView counter;

    //the default size of the game
    private int frameSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        viewGroup = (ViewGroup) findViewById(R.id.mainView);
        counter = (TextView) findViewById(R.id.txtCounter);
        counter.setTextSize(20);
        counter.setTextColor(Color.WHITE);
        this.restart();
    }

    //when the game restarts
    private void restart() {
        this.frame = new Frame(this.frameSize);
        this.frame.frameChangeListener();
    }

    //the frame change listener for all potential positions at any point
    private Frame.PotentialPositions potentialPositions = new Frame.PotentialPositions() {
        public void blockMoved(BlockPosition from, BlockPosition to) {
            
        }
    }
}
