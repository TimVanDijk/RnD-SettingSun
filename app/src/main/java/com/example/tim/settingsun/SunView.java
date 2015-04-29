package com.example.tim.settingsun;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

//import com.example.snake.game.model.Game;
//import com.example.snake.game.model.Point;

public class SunView extends View implements Observer{

    private Game game;
    private float margin_horizontal;
    private float margin_vertical;

    private float cell_size;
    private float cell_radius;
    private float cell_spacing;

    private float circle_margin;

    private final int BLOCK_COLOR = Color.CYAN;


    public SunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SunView(Context context) {
        super(context);
        init();
    }

    private void init()
    {
        // nothing to do
    }


    public void setGame(Game game) {

        if(this.game!= null)
            this.game.deleteObserver(this);
        this.game = game;
        this.game.addObserver(this);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(game==null)
            return;

        // calculate the dimensions
        cell_size = Math.min(w / game.getWidth(), h / game.getHeight());

        // Spacing will be 2 percent of the width, with a minimum of 1px
        cell_spacing = Math.max(cell_size * 0.10f, 1f);

        // calculate the required margin
        margin_horizontal = (w - (cell_size * game.getWidth())) / 2;
        margin_vertical = (h - (cell_size * game.getHeight())) / 2;

        circle_margin = 0.1f * cell_size;
        cell_radius = (cell_size - circle_margin * 2f) * 0.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawCircle(1, 5, 0.5f, paint);
        this.drawBackground(canvas);
        if(game == null)
            return;

        drawSunBlocks(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawARGB(0, 255, 255, 255);
    }

    private void drawSunBlockAdapt(Canvas canvas, Block b, Paint paint) {
        float x = margin_horizontal + b.x * cell_size;
        float y = margin_vertical + b.y * cell_size;
        if (b.x > 0)
            x += (b.x - 1) * cell_spacing;
        if (b.y > 0)
            y += (b.y - 1) * cell_spacing;
        drawSunBlock(canvas, x, y, b.type, paint);
    }

    private void drawSunBlocks(Canvas canvas) {
        Paint sunBlockPaint = new Paint();
        Block[] blocks = game.getPuzzle().getBlocks();
        for (Block b: blocks) {
            drawSunBlockAdapt(canvas, b, sunBlockPaint);
        }
    }


    private void drawSunBlock(Canvas canvas, float x, float y, int type, Paint paint) {
        float xSize = BlockInfo.getDimensions(type).x * cell_size;
        float ySize = BlockInfo.getDimensions(type).y * cell_size;
        float centerX = BlockInfo.getCenter(type).x;
        float centerY = BlockInfo.getCenter(type).y;
        paint.setColor(BLOCK_COLOR);
        //canvas.drawRoundRect(x, y, x + xSize, y + ySize, cell_radius, cell_radius, paint);
        paint.setColor(BlockInfo.getColor(type));
        canvas.drawCircle(x + centerX, y + centerY, 0.5f * cell_size - circle_margin, paint);
    }

    @Override
    public void update(Observable observable, Object data) {
        this.postInvalidate();

    }

}