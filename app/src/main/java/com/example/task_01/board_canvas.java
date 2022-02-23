package com.example.task_01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class board_canvas extends View {

    public Path path = new Path();
    public Path path1 = new Path();
    public Path path2 = new Path();
    public Paint paint = new Paint();
    public Canvas canvas;
    public String function = "pen";
    public float x_point = 0,y_point = 0, cx,cy;
    public float radius_board;
    public float sweep_angle_board = 0, start_angle_board = 0;

    public board_canvas(Context context) {
        super(context);
    }

    public board_canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(8f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                x_point = event.getX();
                y_point = event.getY();
                //invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                //invalidate();
                break;
            default:
                return false;
        }

        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (function == "pen"){
            canvas.drawPath(path,paint);
        }else if (function == "select_point"){
            //ath1.addCircle(x_point,y_point,radius_board,Path.Direction.CW);
            cx = x_point;
            cy = y_point;
        }else if (function == "set_radius"){
            path1.addCircle(cx,cy,radius_board,Path.Direction.CW);
        }else if (function == "circle"){
            //path1.addCircle(cx,cy,radius_board,Path.Direction.CW);
            canvas.drawPath(path1,paint);
            //canvas.drawCircle(x_point,y_point,200,paint);
/*
            RectF oval = new RectF();
            oval.set(cx-radius_board, cy-radius_board,cx+radius_board,cy+radius_board);
            canvas.drawArc(oval,0,360,true,paint);*/
        }else if (function == "set_arc"){
            RectF oval = new RectF();
            oval.set(cx-radius_board, cy-radius_board,cx+radius_board,cy+radius_board);
            //canvas.drawArc(oval,start_angle_board,sweep_angle_board,false,paint);
            path2.addArc(oval,start_angle_board,sweep_angle_board);
            //canvas.drawPath(path2,paint);
        }else if (function == "arc"){
            canvas.drawPath(path2,paint);
        }
    }




    public void delete(){
        path.reset();
        path1.reset();
        path2.reset();
        invalidate();
    }


}
