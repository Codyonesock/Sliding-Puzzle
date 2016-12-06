package com.example.codyonesock.slideypuzzle;

/* This class represents a position inside the Frame. I'm keeping track of each
 * position with X and Y that UNIQUELY represents a position a 'Block' can be placed.
 */

public class BlockPosition {
    //coordinate variables
    private final int x;
    private final int y;

    //The frame where a coordinate can be positioned
    private Frame frame;

    //The block that's going to be positioned somewhere
    private Block block;

    //Creating a potential position for a block
    public BlockPosition(int x, int y, Frame frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
    }

    //creating a position with it's coordinates for the specific block that has it's unique ID
    public BlockPosition(int x, int y, int id, Frame frame) {
        this(x, y, frame);
        block = new Block(id);
    }

    //-----------------------------------------------------------------get/set
    //couple gets to grab the coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //getting a block that's potentially placed
    public Block getBlock() {
        return block;
    }

    //setting a block in it's specific position
    public void setBlock(Block block) {
        this.block = block;
    }

    //------------------------------------------------ checking to see if the block is empty or not
    //checking to see if the position has a block in it's coordinates
    public boolean hasBlock() {
        return block != null;
    }
    //checking to see if the block in the coordinates can move to a new spot
    public boolean canMove() {
        return hasBlock() && frame.canMove(this);
    }
    //move the block if I can
    public void moveBlock() {
        frame.move(getBlock());
    }
}



















