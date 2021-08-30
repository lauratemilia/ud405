package com.udacity.gamedev.drawaspiral;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * TODO: Start here
 *
 * In this exercise we have a project that draws a number of concentric rectangles as specified in
 * the COILS constant. The space between the rectangles is given by xStep and yStep.
 *
 * The rectangles are drawn using four lines between five points. Your task is to adjust the first
 * and last point such that each rectangle turns into a coil that meets up with the neighboring
 * coils inside and outside of it.
 */

public class DrawASpiral extends ApplicationAdapter {

    // How many rectangles/coils to draw
    private static final int COILS = 10;
    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Line);

        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int xStep = screenWidth / 2 / COILS;
        int yStep = screenHeight / 2 / COILS;

        for (int i = 0; i < COILS; i++) {
            //System.out.printf("i is: " + i + "\n");
            int xOffset = xStep * i;
            int yOffset = yStep * i;
            int delta = 20;
           /* if(i != 0){
                delta = (COILS * 10)/i;
            }*/
           /* System.out.println("xOffset: "  + xOffset);
            System.out.println("yOffset: "  + yOffset);

            System.out.println("delta is: " + delta);*/
            truncateCornersWithGradient(screenWidth, screenHeight, xStep, yStep, i, xOffset, yOffset, delta);


        }
        shapeRenderer.end();
    }

    private void truncateCorners(int screenWidth, int screenHeight, int xStep, int yStep, int i, int xOffset, int yOffset, int delta) {
        // Make this coil reach back to the outer coil
        Vector2 point1 = new Vector2(xOffset - xStep + delta, yOffset - delta);
        Vector2 point2 = new Vector2(screenWidth - xOffset - delta, yOffset - delta);
        Vector2 point2a = new Vector2(screenWidth - xOffset + delta, yOffset + delta);
        Vector2 point3 = new Vector2(screenWidth - xOffset + delta, screenHeight - yOffset - delta);
        Vector2 point3a = new Vector2(screenWidth - xOffset - delta, screenHeight - yOffset + delta);
        Vector2 point4 = new Vector2(xOffset + delta, screenHeight - yOffset + delta);
        Vector2 point4a = new Vector2(xOffset - delta, screenHeight - yOffset - delta);
        Vector2 point5 = new Vector2(xOffset - delta, yOffset + yStep + delta);
        Vector2 point5a = new Vector2(xOffset + delta, yOffset + yStep - delta);

        //Vector2 point5a = new Vector2(xOffset + delta - Math.round(delta/++i), yOffset + yStep - delta + Math.round(delta/++i));

        /*System.out.println("point1: " + point1.x + ", " + point1.y);
        System.out.println("point5a: " + point5a.x + ", " + point5a.y);*/


        //this works well with a delta that is constant
        if(point5.y > point4a.y){
            point5.y = point4a.y;
            point5a = point5;
        }

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.line(point1, point2);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(point2, point2a);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(point2a, point3);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(point3, point3a);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(point3a, point4);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.line(point4, point4a);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(point4a, point5);
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.line(point5, point5a);

    }

    private void truncateCornersWithGradient(int screenWidth, int screenHeight, int xStep, int yStep, int i, int xOffset, int yOffset, int delta) {
        // Make this coil reach back to the outer coil

        int point1x = xOffset - xStep + delta;
        int point1y = yOffset - delta;

        int point2x = screenWidth - xOffset - delta;
        int point2y = yOffset - delta;

        int point3x = screenWidth - xOffset + delta;
        int point3y = screenHeight - yOffset - delta;

        int point4x = xOffset + delta;
        int point4y = screenHeight - yOffset + delta;

        // Make this coil stop before connecting back to itself
        int point5x = xOffset - delta;
        int point5y = yOffset + yStep + delta;
        //Vector2 point5 = new Vector2(xOffset, yOffset);

        //intermediate points
        int point2ax = screenWidth - xOffset + delta;
        int point2ay = yOffset + delta;

        int point3ax = screenWidth - xOffset - delta;
        int point3ay = screenHeight - yOffset + delta;

        int point4ax = xOffset - delta;
        int point4ay = screenHeight - yOffset - delta;

        int point5ax = xOffset + delta;
        int point5ay = yOffset + yStep - delta;

        //this works well with a delta that is constant

        if(point5y > point4ay){
            point5y = point4ay + 1;
            point5ax = point5x;
            point5ay = point5y;
        }

        shapeRenderer.line(point1x, point1y, point2x,point2y, Color.WHITE, Color.YELLOW);
        shapeRenderer.line(point2x,point2y, point2ax, point2ay, Color.YELLOW, Color.ORANGE);
        shapeRenderer.line(point2ax, point2ay, point3x,point3y, Color.ORANGE, Color.RED);
        shapeRenderer.line(point3x,point3y, point3ax, point3ay, Color.RED, Color.PINK);
        shapeRenderer.line(point3ax, point3ay, point4x,point4y, Color.PINK, Color.PURPLE);
        shapeRenderer.line(point4x,point4y, point4ax, point4ay, Color.PURPLE, Color.BLUE);
        shapeRenderer.line(point4ax, point4ay, point5x,point5y, Color.BLUE, Color.CYAN);
        if (i != COILS - 1) {
            shapeRenderer.line(point5x,point5y, point5ax, point5ay, Color.CYAN, Color.GREEN);
        }
    }


}

// TODO: Challenge - Add truncated corners to the spiral
