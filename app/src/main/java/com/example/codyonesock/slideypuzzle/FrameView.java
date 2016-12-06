package com.example.codyonesock.slideypuzzle;

/* This class is the view where I'm going to generate the graphics for scalable buttons
 * the reason for this is to handle different difficulty sizes without having an excessive
 * amount of views.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class FrameView extends View {
    //access to the frame
    private Frame frame;

    //my canvas objects
    Paint background;

    //I'm going to draw with simple W * H
    private float width;
    private float height;

    //set new instance of my FrameView and give focus
    public FrameView(Context context, Frame frame) {
        super(context);
        this.frame = frame;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void canvasInit() {
        background = new Paint();
    }

    //-------------------------------------------------Button Image Generation
    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.colorAccent, ));

    }
}
