package com.example.tim.settingsun;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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

    public SunView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SunView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SunView (Context context) {
        super(context);
    }

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
        // calculate the dimensions
        cell_size = Math.min(w / game.getWidth(), h / game.getHeight());
        //readjust
        cell_spacing = cell_size * 0.10f;
        cell_size = 0.9f * cell_size;
        // calculate the required margin
        margin_horizontal = (w - (cell_size * game.getWidth()) - (cell_spacing * game.getWidth())) / 2;
        margin_vertical = (h - (cell_size * game.getHeight()) - (cell_spacing * game.getHeight())) / 2;
        circle_margin = 0.2f * cell_size;
        cell_radius = cell_size * 0.15f;
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        this.drawBackground(canvas);
        if (game != null && game.getPuzzle().getBlocks()[0] != null) {
            drawSunBlocks(canvas);
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            canvas.drawText("Moves:" + game.getMoves(), margin_horizontal / 2.0f, margin_vertical / 2.0f, paint);
        }

    }

    /**
     * Creates a white background for the canvas
     * @param canvas the canvas that we are painting white
     */
    private void drawBackground (Canvas canvas) {
        canvas.drawARGB(0, 255, 255, 255);
    }

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
        float xSize = BlockInfo.getDimensions(type).x * cell_size + (BlockInfo.getDimensions(type).x - 1) * cell_spacing;
        float ySize = BlockInfo.getDimensions(type).y * cell_size + (BlockInfo.getDimensions(type).y - 1) * cell_spacing;
        float centerX = BlockInfo.getCenter(type).x * cell_size + 0.5f *(BlockInfo.getDimensions(type).x - 1) * cell_spacing;
        float centerY = BlockInfo.getCenter(type).y * cell_size + 0.5f *(BlockInfo.getDimensions(type).y - 1) * cell_spacing;;
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