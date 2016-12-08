package com.example.codyonesock.slideypuzzle;

/* This class is the view where I'm going to generate the graphics for scalable buttons
 * the reason for this is to handle different difficulty sizes without having an excessive
 * amount of views.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;

public class FrameView extends View {
    //access to the frame
    private Frame frame;

    //my canvas objects
    private Paint button;
    private Paint background;
    private Paint numbers;

    //Values for the width and height
    private float width;
    private float height;

    //----------------------------------------------------------------constructor method
    public FrameView(Context context) {
        super(context);
    }

    public FrameView(Context context, Frame frame) {
        super(context);
        this.frame = frame;
        setFocusable(true);
        setFocusableInTouchMode(true);
        button = new Paint();
        background = new Paint();
        numbers = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    //getting all the new and old sizes that are changed
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w / this.frame.getSize();
        this.height = h / this.frame.getSize();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //-------------------------------------------------------------------public methods
    //handling the touch events to move the blocks "IF" it has the ability to move
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(motionEvent);
        BlockPosition blockPosition = getPosition(motionEvent.getX(), motionEvent.getY());
        if (blockPosition != null && blockPosition.canMove() && !frame.solved()) {
            blockPosition.moveBlock();
            invalidate();
        }
        return true;
    }

    //-------------------------------------------------------------------private methods
    //locate the position at x/y
    private BlockPosition getPosition(float x, float y) {
        int fx = (int) (x / width);
        int fy = (int) (y / height);

        return frame.getSpecificPosition(fx + 1, fy + 1);
    }

    //---------------------------------------------------------------Button Image Generation
    @Override
    protected void onDraw(Canvas canvas) {
        //drawing the rectangle for each button
        button.setColor(ContextCompat.getColor(getContext(), R.color.buttonColor));
        canvas.drawRect(0, 0, getWidth(), getHeight(), button);

        //drawing the background and adding a stroke line to split buttons a bit
        background.setColor(ContextCompat.getColor(getContext(), R.color.backgroundColor));
        background.setStrokeWidth(15);

        //drawing the grid lines for the background based off the size of the frame
        for (int i = 0; i < this.frame.getSize(); i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, background);
            canvas.drawLine(i * width, 0, i * width, getHeight(), background);
        }

        //drawing and setting the number properties for the buttons
        numbers.setColor(ContextCompat.getColor(getContext(), R.color.numberColor));
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

    //-------------------------------------------------------------------------orientation handling
    //saving the current game state
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("draw", super.onSaveInstanceState());
        return bundle;
    }

    //restoring the game state from the saved instance
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable("draw"));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}






















