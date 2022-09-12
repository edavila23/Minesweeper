package com.davila;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameBoard {

    private int[][] board;
    private boolean firstClick = true;

    //Textures
    private Texture emptyTile;
    private Texture questionTile;
    private Texture bombTile;
    private Texture emptyFloor;
    private Texture bomb;
    private Texture oneTile, twoTile, threeTile, fourTile, fiveTile, sixTile, sevenTile, eightTile;

    private static final int BOMB = 9, EMPTYTILE = 10, FLAGGEDTILE = 20, QUESTIONTILE = 30;

    public GameBoard() {
        board = new int[16][30];
        initEmptyBoard();

        //load all textures
        emptyTile = new Texture("img.png");
        bomb = new Texture("img_1.png");
        oneTile = new Texture("img_2.png");
        twoTile = new Texture("img_3.png");
        threeTile = new Texture("img_4.png");
        fourTile = new Texture("img_5.png");
        fiveTile = new Texture("img_6.png");
        sixTile = new Texture("img_7.png");
        sevenTile = new Texture("img_8.png");
        eightTile = new Texture("img_9.png");
    }

    public boolean isValidLoc(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    public void handleClick(int x, int y) {
        //change windows (x,y) coordinate to 2D array loc
        int rowClicked = (y-10)/25;
        int colClicked = (x-10)/25;

        if(isValidLoc(rowClicked, colClicked)) {
            board[rowClicked][colClicked] = board[rowClicked][colClicked] % 10;
            if(firstClick) {
                firstClick = false;
                placeBombs(rowClicked, colClicked);
                initBoardNumbers();
            }
            //if tile pressed on is empty (clear large space)
            if(board[rowClicked][colClicked] == 0) {
                clearLargeSpace(rowClicked, colClicked);
            }
        }
    }

    private void clearLargeSpace(int row, int col) {
        //top
        if(isValidLoc(row-1, col)) {
            if(board[row-1][col] == 10) {
                System.out.println("cleared above");
                board[row-1][col] = 0;
                clearLargeSpace(row-1, col);
            }
            else {
                System.out.println("num above");
                board[row-1][col] = bombsAroundLoc(row-1, col) % 10;
            }
        }
        //right
        if(isValidLoc(row, col+1)) {
            if(board[row][col+1] == 10) {
                board[row][col+1] = 0;
                clearLargeSpace(row, col+1);
            }
            else {
                board[row][col+1] = bombsAroundLoc(row, col+1) % 10;
            }
        }
        //bottom
        if(isValidLoc(row+1, col)) {
            if(board[row+1][col] == 10) {
                board[row+1][col] = 0;
                clearLargeSpace(row+1, col);
            }
            else {
                board[row+1][col] = bombsAroundLoc(row+1, col) % 10;
            }
        }
        //left
        if(isValidLoc(row, col-1)) {
            if(board[row][col-1] == 10) {
                board[row][col-1] = 0;
                clearLargeSpace(row, col-1);
            }
            else {
                board[row][col-1] = bombsAroundLoc(row, col-1) % 10;
            }
        }
    }

    private int bombsAroundLoc(int row, int col) {
        ArrayList<Location> locs = getNeighbors(row, col);
        int count = 0;
        for(Location temp: locs) {
            if(board[temp.getRow()][temp.getCol()] % 10 == BOMB) {
                count++;
            }
        }
        return count;
    }

    private void initBoardNumbers() {
        for(int row=0; row < board.length; row++) {
            for(int col=0; col < board[0].length; col++) {
                if(board[row][col] % 10 != BOMB) {
                    int numOfBombs = bombsAroundLoc(row, col);
                    board[row][col] = numOfBombs + 10;
                }
            }
        }
    }

    private void placeBombs(int rowClicked, int colClicked) {
        int bombCount = 0;
        while(bombCount < 99) {
            int randomRow = (int)(Math.random() * board.length);
            int randomCol = (int)(Math.random() * board[0].length);

            //if random location is not equal to the first click
            if(randomRow != rowClicked && randomCol != colClicked) {
                if(board[randomRow][randomCol] == EMPTYTILE) {
                    board[randomRow][randomCol] = BOMB+10;
                    bombCount++;
                }
            }
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        for(int row=0; row < board.length; row++) {
            for(int col=0; col < board[row].length; col++) {
                //if we have an empty tile
                if (board[row][col] >= EMPTYTILE && board[row][col] < FLAGGEDTILE) {
                    spriteBatch.draw(emptyTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == BOMB) {
                    spriteBatch.draw(bomb, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 1) {
                    spriteBatch.draw(oneTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 2) {
                    spriteBatch.draw(twoTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 3) {
                    spriteBatch.draw(threeTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 4) {
                    spriteBatch.draw(fourTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 5) {
                    spriteBatch.draw(fiveTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 6) {
                    spriteBatch.draw(sixTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 7) {
                    spriteBatch.draw(sevenTile, (10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == 8) {
                    spriteBatch.draw(eightTile, (10) + (col*25), (600-35) - (row * 25));
                }
            }
        }
    }

    private void initEmptyBoard() {
        for(int i=0; i < board.length; i++) {
            for(int j=0; j< board[i].length; j++) {
                board[i][j] = 10;
            }
        }
    }

    private ArrayList<Location> getNeighbors(int row, int col) {
        ArrayList<Location> locs = new ArrayList<>();
            //top
            if(isValidLoc(row-1, col)) {
                locs.add(new Location(row-1, col));
            }
            //top right
            if(isValidLoc(row-1, col+1)) {
                locs.add(new Location(row-1, col+1));
            }
            //right
            if(isValidLoc(row, col+1)) {
                locs.add(new Location(row, col+1));
            }
            //bottom right
            if(isValidLoc(row+1, col+1)) {
                locs.add(new Location(row+1, col+1));
            }
            //bottom
            if(isValidLoc(row+1, col)) {
                locs.add(new Location(row+1, col));
            }
            //bottom left
            if(isValidLoc(row+1, col-1)) {
                locs.add(new Location(row+1, col-1));
            }
            //left
            if(isValidLoc(row, col-1)) {
                locs.add(new Location(row, col-1));
            }
            //top left
            if(isValidLoc(row-1, col-1)) {
                locs.add(new Location(row-1, col-1));
            }
        return locs;
    }
}
