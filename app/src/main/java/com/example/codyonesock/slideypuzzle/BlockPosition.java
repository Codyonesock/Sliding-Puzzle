package com.example.codyonesock.slideypuzzle;

/* This class represents a position inside the Frame. I'm keeping track of each
 * position with X and Y that UNIQUELY represents a position a 'Block' can be placed.
 */

public class BlockPosition {
    //coordinate variables
    private final int x;
    private final int y;

    //The block that's going to be positioned somewhere
    private Block block;

    //Creating a potential position for a block
    public BlockPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //creating a position with it's coordinates for the specific block that has it's unique ID
    public BlockPosition(int x, int y, int id) {
        this(x, y);
        block = new Block(id);
    }

    //------------------------------------------get/set
    //couple gets to grab the coordinates and block
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Block getBlock() {
        return block;
    }


}
