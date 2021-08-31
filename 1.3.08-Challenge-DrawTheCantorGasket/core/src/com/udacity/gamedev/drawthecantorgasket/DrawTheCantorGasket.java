package com.udacity.gamedev.drawthecantorgasket;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/*

TODO: Start here

The Cantor gasket is a fractal where we start with a white square. We divide that square up into a 3x3 grid of smaller squares, then remove the middle square. Finally, we repeat the process on each of the remaining 8 squares.

 */

public class DrawTheCantorGasket extends ApplicationAdapter {

    ShapeRenderer shapeRenderer;
    // TODO: Set a constant for how many recursions to draw. 5 is a good place to start
    private static final int RECURSONS = 5;

    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Finds a good place to draw our fractal
        // Rectangle has members x,y for the lower left corner, and width and height
        Rectangle bounds = findLargestSquare();

        // TODO: Begin a filled shapeRenderer batch
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // TODO: Draw a white square matching the bounds
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(bounds.x, bounds.y,bounds.width, bounds.height);
        //System.out.println("largest square: \nx: " + bounds.x + "\ny: " + bounds.y + "\nwidth: " + bounds.width + "\nheight: " + bounds.height);

        // TODO: Set the working color to black, and call punchCantorGasket with the bounds
        shapeRenderer.setColor(Color.BLACK);
        punchCantorGasket(bounds.x, bounds.y, bounds.getWidth(), RECURSONS, Color.BLACK);

        // TODO: End the batch
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        shapeRenderer = new ShapeRenderer();
    }


    private void punchCantorGasket(float x, float y, float size, int recursions, Color color){
        // Note that size means the height and width of the square
        // TODO: Base case, if recursions = 0, return

        if(recursions == 0){
            return;
        }
        float centerSquareX = x + size/3;
        float centerSquareY = y + size/3;
        float centerSquareSize = size/3;
        //System.out.println("center square: \nx: " + centerSquareX + "\ny: " + centerSquareY + "\nwidth: " + centerSquareSize + "\nheight: " + centerSquareSize);

        // TODO: Draw a black square in the middle square
        switch (recursions){
            case 5:
                color = Color.MAGENTA;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.CYAN;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 1:
                color = Color.RED;
                break;
        }
        shapeRenderer.setColor(color);
        shapeRenderer.rect(centerSquareX, centerSquareY, centerSquareSize, centerSquareSize);
        recursions--;
        // TODO: Call punchCantorGasket on all 8 other squares
        punchCantorGasket(centerSquareX - centerSquareSize, centerSquareY - centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX, centerSquareY - centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX + centerSquareSize, centerSquareY - centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX + centerSquareSize, centerSquareY, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX + centerSquareSize, centerSquareY + centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX, centerSquareY + centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX - centerSquareSize, centerSquareY + centerSquareSize, centerSquareSize, recursions, color);
        punchCantorGasket(centerSquareX - centerSquareSize, centerSquareY, centerSquareSize, recursions, color);

    }

    private Rectangle findLargestSquare(){
        Rectangle largestSquare = new Rectangle();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        if (screenWidth > screenHeight){
            largestSquare.x = (screenWidth - screenHeight)/2;
            largestSquare.y = 0;
            largestSquare.width = screenHeight;
            largestSquare.height = screenHeight;
        } else {
            largestSquare.x = 0;
            largestSquare.y = (screenHeight - screenWidth)/2;
            largestSquare.width = screenWidth;
            largestSquare.height = screenWidth;
        }
        return largestSquare;
    }
}
