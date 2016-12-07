package com.example.codyonesock.slideypuzzle;

/* This Class is going to handle the Frame of the puzzle where the Blocks
 * can be placed based off the coordinates.
 */

import java.util.ArrayList;
import java.util.List;

public class Frame {
    //variables
    private final int size;

    //all the potential places for the frame
    private final List<BlockPosition> blockPositions;

    //find all potential future frame changes and new coordinates
    private final List<PotentialPositions> potentialPositions;

    //Creating a new Frame and giving it's dimensions of the initial puzzle from the start
    public Frame(int size) {
        potentialPositions = new ArrayList<PotentialPositions>();
        this.size = size;
        blockPositions = new ArrayList<BlockPosition>(size * size);
        for (int x = 1; x <= size; x++) {
            for (int y = 1; y <= size; y++) {
                blockPositions.add(x == size && y == size ? new BlockPosition(x, y, this) : new BlockPosition(x, y, (y - 1) * size + x, this));
            }
        }
    }


    public boolean canMove(BlockPosition blockPosition) {
        return true;
    }

    public void move(Block block) {
    }

    //---------------------------get/set
    public int getSize() {
        return size;
    }

    public Iterable<BlockPosition> blockPositions() {
        return blockPositions;
    }

    //-----------------------------------------------------------listeners

    //this listens to all states of potential changes
    public interface PotentialPositions {
        //when a block is moved 'FROM' a spot "TO" and empty spot both change states
        void blockMoved(BlockPosition from, BlockPosition to);
    }

    //adding listeners to listen for changes on the frame
    public void frameChangeListener (PotentialPositions changeListener) {
        if (!potentialPositions.contains(changeListener)) {
            potentialPositions.add(changeListener);
        }
    }


}
























