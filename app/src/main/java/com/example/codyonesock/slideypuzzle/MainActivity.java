package com.example.codyonesock.slideypuzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    public int frameSize = 3;

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
    //-------------------------------------------------------------------------public methods
    //this adds my menu.xml to the bar at the top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //change the difficulty(size)
    public void difficulty(int difficultySize) {
        if (difficultySize != this.frameSize) {
            this.frameSize = difficultySize;
            this.restart();
            frameView.invalidate();
        }
    }

    /*Here's all the drop down items inside the bar that I'm using a menu for,
     *I'm just going to do a case statements to handle each setting
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //make a new game
            case R.id.newGame:
                new AlertDialog.Builder(this).setTitle("New Game").setMessage("Click to start a new game")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                frame.mixBlocks();
                                counter.setText("Number of Movements: 0");
                                frameView.invalidate();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            //the difficulty settings to change the game size
            case R.id.difficulty:
                FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment(this.frameSize);
                settingsDialogFragment.show(fragmentManager, "fragment_settings");
                break;
            case R.id.instructions:
                new AlertDialog.Builder(this).setTitle("Instructions").setMessage("Slide the Blocks to make the numbers in order starting from the top left")
                        .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------------------------------------------------------------private methods
    /* When the game restarts make a new frame size, new listener, mix everything,
     * remove the old view, add a new view and reset the counter
     */
    private void restart() {
        this.frame = new Frame(this.frameSize);
        this.frame.frameChangeListener(potentialPositions);
        this.frame.mixBlocks();
        this.viewGroup.removeView(frameView);
        this.frameView = new FrameView(this, frame);
        this.viewGroup.addView(frameView);
        this.counter.setText("Number of movements: 0");
    }

    //the frame change listener for all potential positions at any point
    private Frame.PotentialPositions potentialPositions = new Frame.PotentialPositions() {
        public void blockMoved(BlockPosition from, BlockPosition to, int blockMoveCouner) {
            counter.setText("Number of movements: " + Integer.toString(blockMoveCouner));
        }

        @Override
        public void solved(int blockMoveCounter) {
            counter.setText("Solved In " + Integer.toString(blockMoveCounter) + " moves!");
            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();
        }
    };
    //---------------------------------------------------------------------------difficulty class
    //I'm going to handle the difficulty with a dialog since there isn't much going on
    public class SettingsDialogFragment extends DialogFragment {

        //holds the difficulty size of whatevers picked
        private int size;

        //Instantiates a new settings dialog fragment.
        public SettingsDialogFragment(int size) {
            this.size = size;
        }

        //----------------------------------------------------------------------get/set
        int getSize() {
            return this.size;
        }
        void setSize(int size) {
            this.size = size;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set the dialog title
            builder.setTitle("Choose your difficulty")
                    .setSingleChoiceItems(R.array.difficulty, this.size - 2, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setSize(which + 2);
                                }
                            })
                    //ok button
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    ((MainActivity) getActivity()).difficulty(getSize());
                                }
                            })
                    //cancel button
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            return builder.create();
        }
    }
}















