package com.example.codyonesock.slideypuzzle;

/* This Class Represents EACH block in the puzzle. Here I'm giving each 'specific' block an ID
* so I can reference it from wherever I want at any time. I'm using a unique ID to keep track
* of what position the block is in so I can handle it in coordination.
*/

public class Block {

    //this is the UNIQUE ID of each block
    private final int id;

    //This is where I make the block that has the unique ID
    public Block(int number) {
        this.id = number;
    }

    //getting the ID of whichever block
    public int getId() {
        return id;
    }
}
