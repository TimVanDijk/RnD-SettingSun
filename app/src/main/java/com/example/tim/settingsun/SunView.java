package com.example.tim.settingsun;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * The View class used to draw the game, get information about the screen, and draw the score.
 * @authors Ward Theunisse, Tim van Dijk, Martijn Heitkönig en Luuk van Bitterswijk
 */
public class SunView extends View implements Observer{

    private Game game;
    private float margin_horizontal;

    private float margin_vertical;

    private float cell_size;
    private float cell_radius;
    private float cell_spacing;

    private float circle_margin;

    private final int BLOCK_COLOR = Color.rgb(103,224,240);
    private final int BLOCK_SHADOW = Color.rgb(60,189,207);

    /**
     * Standardconstructor, with attribute set and default style parameters
     * (Method not used)
     * @param context De context the SunView is created in
     * @param attrs A set of attributes for the SunView
     * @param defStyleAttr Style attribute to be used
     */
    public SunView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Standardconstructor, with attribute set
     * (Method not used)
     * @param context De context the SunView is created in
     * @param attrs A set of attributes for the SunView
     */
    public SunView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Standardconstructor
     * @param context De context the SunView is created in
     */
    public SunView (Context context) {
        super(context);
    }

    /**
     * Method to connect the view to a game
     * @param game the game the view must be a reflection of
     */
    public void setGame (Game game) {
        if(this.game != null)
            this.game.deleteObserver(this);
        this.game = game;
        this.game.addObserver(this);

    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (game == null)
            return;
        //get the initial cell size
        cell_size = Math.min(w / game.getWidth(), h / game.getHeight());
        //readjust to create cell spacing
        cell_spacing = cell_size * 0.10f;
        cell_size = 0.9f * cell_size;
        //calculate the required margin
        margin_horizontal = (w - (cell_size * game.getWidth()) - (cell_spacing * game.getWidth())) / 2;
        margin_vertical = (h - (cell_size * game.getHeight()) - (cell_spacing * game.getHeight())) / 2;
        //calculate the margin for the circles and the rounding of block edges
        circle_margin = 0.2f * cell_size;
        cell_radius = cell_size * 0.15f;
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        if (game != null && game.getPuzzle().getBlocks()[0] != null) {
            drawSunBlocks(canvas);
            //draw score
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            float textHeight = 40;

            if (margin_horizontal < margin_vertical) {
                paint.setTextSize(textHeight);
                canvas.drawText("Moves:" + game.getMoves(), margin_horizontal / 2.0f, margin_vertical / 2.0f, paint);
            }
            else {
                textHeight *= 1.5f;
                paint.setTextSize(textHeight);
                canvas.drawText("Moves:" + game.getMoves(), margin_horizontal / 4.0f, textHeight * 1.5f , paint);
            }
        }

    }


    /**
     * Adapts the values in a block to values that can be drawn on the screen
     * @param canvas the canvas to be drawn on
     * @param b the block to be drawn
     * @param paint the Paint doing the drawing
     */
    private void drawSunBlockAdapt(Canvas canvas, Block b, Paint paint) {
        float x = margin_horizontal + b.x * cell_size;
        float y = margin_vertical + b.y * cell_size;
        x += b.x * cell_spacing;
        y += b.y * cell_spacing;
        drawSunBlock(canvas, x, y, b.type, paint);
    }

    /**
     * Draws all blocks in the puzzle on the canvas
     * @param canvas the canvas on which we are painting our blocks
     */
    private void drawSunBlocks (Canvas canvas) {
        Paint sunBlockPaint = new Paint();
        Block[] blocks = game.getPuzzle().getBlocks();
        for (Block b: blocks) {
            drawSunBlockAdapt(canvas, b, sunBlockPaint);
        }
    }

    /**
     * Draws a block from the puzzle on the canvas
     * @param canvas
     * @param x
     * @param y
     * @param type
     * @param paint
     */
    private void drawSunBlock (Canvas canvas, float x, float y, int type, Paint paint) {
        //Create the dimensions and adjust for cell spacing
        float xSize = BlockInfo.getDimensions(type).x * cell_size + (BlockInfo.getDimensions(type).x - 1) * cell_spacing;
        float ySize = BlockInfo.getDimensions(type).y * cell_size + (BlockInfo.getDimensions(type).y - 1) * cell_spacing;
        float centerX = BlockInfo.getCenter(type).x * cell_size + 0.5f *(BlockInfo.getDimensions(type).x - 1) * cell_spacing;
        float centerY = BlockInfo.getCenter(type).y * cell_size + 0.5f *(BlockInfo.getDimensions(type).y - 1) * cell_spacing;
        //draw the block and the circles
        paint.setColor(BLOCK_SHADOW);
        canvas.drawRoundRect(x, y, x + xSize, y + ySize + cell_spacing*0.5f, cell_radius, cell_radius, paint);
        paint.setColor(BLOCK_COLOR);
        canvas.drawRoundRect(x, y, x + xSize, y + ySize, cell_radius, cell_radius, paint);
        paint.setColor(BlockInfo.getColor(type));
        canvas.drawCircle(x + centerX, y + centerY, 0.5f * cell_size - circle_margin, paint);
        paint.setColor(BlockInfo.getColor(type));
        canvas.drawCircle(x + centerX, y + centerY, 0.5f * cell_size - circle_margin, paint);
    }

    @Override
    public void update (Observable observable, Object data) {
        this.postInvalidate();
    }

    /**
     * Gets the coordinates of a block considering block widths, margins and cell spacings.
     * @param b the block whose coordinates we want
     * @return a tuple containing block b's coordinates
     */
    public Tuple<Float> getBlockCoords (Block b) {
        float x = margin_horizontal + b.x * cell_size;
        float y = margin_vertical + b.y * cell_size;
        x += b.x * cell_spacing;
        y += b.y * cell_spacing;
        x-= cell_spacing / 2f;
        y-= cell_spacing / 2f;
        return new Tuple<>(x,y);
    }

    /**
     * Gets the size of a block considering block widths, margins and cell spacings.
     * @param b the block whose size we want
     * @return a tuple containing block b's size
     */
    public Tuple<Float> getBlockSize (Block b) {
        float x = margin_horizontal + b.x * cell_size;
        float y = margin_vertical + b.y * cell_size;
        x += b.x * cell_spacing;
        y += b.y * cell_spacing;
        x-= cell_spacing/2f;
        y-= cell_spacing/2f;
        float xSize = BlockInfo.getDimensions(b.type).x * cell_size + (BlockInfo.getDimensions(b.type).x - 1) * cell_spacing;
        float ySize = BlockInfo.getDimensions(b.type).y * cell_size + (BlockInfo.getDimensions(b.type).y - 1) * cell_spacing;
        return new Tuple<>(xSize,ySize);
    }

}