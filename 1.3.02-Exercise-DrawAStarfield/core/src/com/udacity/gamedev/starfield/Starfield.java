package com.udacity.gamedev.starfield;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Start here!
 * <p>
 * In this exercise we'll draw a star field of white points on a black background. The number of
 * points will be defined by a density parameter that states what proportion of the pixels should be
 * white.
 * <p>
 * TODO: Run what you've got before making any changes
 * <p>
 * One thing to note is we're using two new LibGDX classes, Array, and Vector2. We're using a custom
 * Array type so LibGDX can control the memory, and avoid unfortunate garbage collection events.
 * Vector2 is a super simple class for holding a 2D position. You can find more information in the
 * LibGDX Javadocs, or just by right clicking on the class name, and selecting Go To > Declaration.
 * <p>
 * One new utility class we'll be using in this exercise is com.badlogic.gdx.math.Vector2. You can
 * find more information in the LibGDX Javadocs.
 * <p>
 * Remember you can set up a Desktop run configuration using the dropdown in the toolbar, or you can
 * open the terminal at the bottom of the screen and run
 * <p>
 * $ ./gradlew desktop:run
 */


public class Starfield extends ApplicationAdapter {

    private static final float STAR_DENSITY = 0.01f;
    ShapeRenderer shapeRenderer;
    Array<Star> stars;
    float firstThirdOfScreenEnd;
    float lastThirdOfScreenStart;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        initStars(STAR_DENSITY);
    }

    public void initStars(float density) {
        // Figure out how many stars to draw. You'll need the screen dimensions.
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        //number of pixels on the screen times the density of the start on the sky
        int starCount = (int) (screenHeight * screenWidth * density);

        //Create a new array of Vector2's to hold the star positions
        stars = new Array<Star>(starCount);

        firstThirdOfScreenEnd = screenHeight / 3;
        lastThirdOfScreenStart = (screenHeight / 3) * 2;

        //fill the array of star positions
        Random random = new Random();
        for (int i = 0; i < starCount; i++) {
            int x = random.nextInt(screenWidth);
            int y = random.nextInt(screenHeight);

          /*  if (y < firstThirdOfScreenEnd || y > lastThirdOfScreenStart) {
                continue;
            }*/

            stars.add(new Star(new Vector2(x, y),
                    new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1)));
        }
    }

    @Override
    public void resize(int width, int height) {
        initStars(STAR_DENSITY);
        shapeRenderer = new ShapeRenderer();
    }

    //this method is the main loop and is called 60 times/s
    @Override
    public void render() {
        //Make the night sky black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Begin a shapeRenderer batch using ShapeType.Point
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        //Loop through the star positions and use shapeRenderer to draw points
        for (Star star : stars) {
            shapeRenderer.setColor(star.color);
            shapeRenderer.point(star.pos.x, star.pos.y, 0);
        }

        //End the shapeRenderer batch
        shapeRenderer.end();

    }

    @Override
    public void dispose() {
        //Dispose of our ShapeRenderer
        shapeRenderer.dispose();
        super.dispose();
    }

    class Star {
        public Vector2 pos;
        public Color color;

        public Star(Vector2 pos, Color color) {
            this.pos = pos;
            this.color = color;
        }
    }


}
// TODO: Challenge - Make technicolor stars using shapeRenderer.setColor();
// TODO: Challenge - Draw the Milky Way using a band of denser stars


