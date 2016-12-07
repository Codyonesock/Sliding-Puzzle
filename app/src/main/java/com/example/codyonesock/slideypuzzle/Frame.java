package com.example.codyonesock.slideypuzzle;

/* This Class is going to handle the Frame of the puzzle where the Blocks
 * can be placed based off the coordinates.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frame {
    //variables
    private final int size;

    //all the potential places for the frame
    private final List<BlockPosition> blockPositions;

    //find all potential future frame changes and new coordinates
    private final List<PotentialPositions> potentialPositions;

    //counter for everytime a block moves
    private int blockMoveCounter;

    //random to mix blocks
    private final static Random random = new Random();

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
        //set counter to 0 on new frame
        blockMoveCounter = 0;
    }

    //------------------------------------------------------------------------------------public methods
    /*Checking to see if the block in a specific x/y position can move
     *the return gives all possible positions to check to see if they're occupied or not
     */
    public boolean canMove(BlockPosition blockPosition) {
        int x = blockPosition.getX();
        int y = blockPosition.getY();
        return emptyBlock(x - 1, y) || emptyBlock(x + 1, y) || emptyBlock(x, y - 1) || emptyBlock(x, y + 1);
    }

    /*Move the block and change the listener to understand it's change
     *It uses the blockPosition(from) to set the new tile to where it's going (to) be
     *after that it notifies the listener that something is moving
     */
    public void move(Block block) {
        for (BlockPosition blockPosition: blockPositions) {
            if (blockPosition.getBlock() == block) {
                final BlockPosition to = getBlankBlock();
                to.setBlock(block);
                blockPosition.setBlock(null);
                blockMoveCounter++;
                notificationListener(blockPosition, to, blockMoveCounter);
                if (solved()) {
                    notificationWinListener(blockMoveCounter);
                }
                return;
            }
        }
    }

    //mix the blocks up for a new game that's different
    public void mixBlocks() {
        blockMoveCounter = 0;
        for (int i = 0; i <size*size; i++) {
            mixer();
        } do {
            mixer();
        } while (!solvable() || solved());
    }

    //checking to see if the game is solved
    public boolean solved() {
        boolean result = true;
        for (BlockPosition blockPosition: blockPositions) {
            result = result && ((blockPosition.getX() == size && blockPosition.getY() == size) || (blockPosition.getBlock()) != null && blockPosition.getBlock().getId() == getIndex(blockPosition));
        }
        return result;
    }

    //-----------------------------------------------------------------------------------private methods
    //checking to see if the block at the specific x/y position is empty
    private boolean emptyBlock(int x, int y) {
        return (0 < x && x <= size) && (0 < y && y <= size) && getSpecificPosition(x,y).getBlock() == null;
    }

    /*the mixer that individually moves blocks it takes two random positions and if they aren't equal
     *move the blocks to different spots.. This only does two at a time so it's a bit slow
     */
    private void mixer() {
        BlockPosition positionOne = getSpecificPosition(random.nextInt(size) + 1, random.nextInt(size) + 1);
        BlockPosition positionTwo = getSpecificPosition(random.nextInt(size) + 1, random.nextInt(size) + 1);
        if (positionOne != positionTwo) {
            Block block = positionOne.getBlock();
            positionOne.setBlock(positionTwo.getBlock());
            positionTwo.setBlock(block);
        }
    }

    /*This bool checks to see if the puzzle is solvable. (I'm using this to compare)
     *It counts the number of inversions where an inversion is when a block precedes another
     * block with a lower number on it
     */
    private boolean solvable() {
        int inversion = 0;
        //setting the block's to equal the positions
        for (BlockPosition blockPositionOne: blockPositions) {
            Block blockOne = blockPositionOne.getBlock();
            for (BlockPosition blockPositionTwo: blockPositions) {
                Block blockTwo = blockPositionTwo.getBlock();
                //increment if position doesn't equal | blocks are null | if one block precedes another
                if (blockPositionOne != blockPositionTwo && blockOne != null && blockTwo != null && getIndex(blockPositionOne) < getIndex(blockPositionTwo) && blockOne.getId() > blockTwo.getId()) {
                    inversion++;;
                }
            }
        }
        //checking for an even size and inversion | empty on an odd row
        final boolean evenSize = size % 2 == 0;
        final boolean evenInversion = inversion % 2 == 0;
        boolean emptyOddRow = getBlankBlock().getY() % 2 == 1;
        //checking to see if the emptyOddRow is an evenSize
        emptyOddRow = evenSize != emptyOddRow;
        return (!evenSize && evenInversion) || (evenSize && emptyOddRow == evenInversion);
    }
    //-----------------------------------------------------------------------------------get/set
    //getting the size of the frame
    public int getSize() {
        return size;
    }

    //grabbing all the positions on the frame
    public Iterable<BlockPosition> blockPositions() {
        return blockPositions;
    }

    //getting the empty block by checking for the null one
    public BlockPosition getBlankBlock() {
        for (BlockPosition blockPosition: blockPositions) {
            if (blockPosition.getBlock() == null) {
                return blockPosition;
            }
        }
        return null;
    }

    //gets the block Position at specific x/y position
    public BlockPosition getSpecificPosition(int x, int y) {
        for (BlockPosition blockPosition: blockPositions) {
            if (blockPosition.getX() == x && blockPosition.getY() == y) {
                return blockPosition;
            }
        }
        return null;
    }

    //get the single index of the specific place
    public int getIndex(BlockPosition blockPosition) {
        return (blockPosition.getY() - 1) * size + blockPosition.getX();
    }
    //-----------------------------------------------------------------------------------listeners

    //this listens to all states of potential changes
    public interface PotentialPositions {
        //when a block is moved 'FROM' a spot "TO" and empty spot both change states
        void blockMoved(BlockPosition from, BlockPosition to, int blockMoveCounter);

        //when the game is completely solved it returns the final counter
        void solved(int blockMoveCounter);
    }

    //adding listeners to listen for changes on the frame
    public void frameChangeListener (PotentialPositions changeListener) {
        if (!potentialPositions.contains(changeListener)) {
            potentialPositions.add(changeListener);
        }
    }

    /*The notification Listener acts as a notifier to tell the blocks listener that somethings moving
     *"Hey you you're moving! Get ready for it and all future changes that can happen"
     */
    private void notificationListener(BlockPosition from, BlockPosition to, int blockMoveCounter) {
        for (PotentialPositions notifyListener: potentialPositions) {
            notifyListener.blockMoved(from, to, blockMoveCounter);
        }
    }

    private void notificationWinListener(int blockMoveCounter) {
        for (PotentialPositions winListener: potentialPositions) {
            winListener.solved(blockMoveCounter);
        }
    }
}
























