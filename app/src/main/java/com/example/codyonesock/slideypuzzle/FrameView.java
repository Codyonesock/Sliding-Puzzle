package com.example.codyonesock.slideypuzzle;

/* This class is the view where I'm going to generate the graphics for scalable buttons
 * the reason for this is to handle different difficulty sizes without having an excessive
 * amount of views.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.graphics.Color;

import java.util.Iterator;

public class FrameView extends View {
    //access to the frame
    private Frame frame;
    private Context context;

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
        //drawing the rectangle for each button
        Paint button = new Paint();
        button.setColor(getResources().getColor(R.color.buttonColor));
        canvas.drawRect(0, 0, getWidth(), getHeight(), button);

        //drawing the background and adding a stroke line to split buttons a bit
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.backgroundColor));
        background.setStrokeWidth(15);

        //drawing the grid lines for the background based off the size of the frame
        for (int i = 0; i < this.frame.getSize(); i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, background);
            canvas.drawLine(i * width, 0, i * width, getHeight(), background);
        }

        //drawing and setting the number properties for the buttons
        Paint numbers = new Paint(Paint.ANTI_ALIAS_FLAG);
        numbers.setColor(getResources().getColor(R.color.numberColor));
        numbers.setStyle(Paint.Style.FILL);
        numbers.setTextSize(height * 0.75f);
        numbers.setTextScaleX(width / height);
        numbers.setTextAlign(Paint.Align.CENTER);

        //setting the positive and negative values for my numbers on the buttons
        float x = width / 2;
        Paint.FontMetrics fm = numbers.getFontMetrics();
        float y = (height / 2) - (fm.ascent + fm.descent) / 2;

        /*Here I'm using an Iterator to get a collection of both values of "potential" sizes of the
         *frame then putting them together based on the elements (hasNext()) with it's specific ID's
         */
        Iterator<BlockPosition> iterator = frame.blockPositions().iterator();
        for (int i = 0; i < frame.getSize(); i++) {
            for (int k = 0; k < frame.getSize(); k++) {
                if (iterator.hasNext()) {
                    BlockPosition blockPosition = iterator.next();
                    if (blockPosition.hasBlock()) {
                        String number = Integer.toString(blockPosition.getBlock().getId());
                        canvas.drawText(number, i * width + x, k * height + y, numbers);
                    } else {
                        canvas.drawRect(i * width, k * height, i * width + width, k * height + height, background);
                    }
                }
            }
        }
    }
}






















