package com.example.task_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import com.example.task_01.board_canvas;
import com.googlecode.tesseract.android.TessBaseAPI;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

public class MainActivity extends AppCompatActivity{
    private Button mget_radius,mdraw_arc;
    private board_canvas Paint;
    private EditText radius;
    private float Radius_circle;
    private String mstart_angle_string, msweep_angle_string;
    private float mstart_angle, msweep_angle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paint = (board_canvas)findViewById(R.id.board_custom);
        radius = (EditText)findViewById(R.id.radius_text);
        mget_radius = (Button)findViewById(R.id.get_radius);
        mdraw_arc = (Button)findViewById(R.id.draw_arc);


        mget_radius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String circle_string = radius.getText().toString();
                Radius_circle =  Float.parseFloat(circle_string);
                Paint.radius_board = Radius_circle;
                Paint.function = "set_radius";
                Toast.makeText(MainActivity.this,"Radius: " +  Radius_circle, Toast.LENGTH_SHORT).show();
            }
        });

        mdraw_arc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Paint.function = "circle";
                openDialogue();
                //Toast.makeText(MainActivity.this, "X= " + Paint.cx + "  Y= " + Paint.cy + "  Center= " + Paint.radius_board, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      // board_canvas board_canvas = new board_canvas(this);
        switch (item.getItemId()) {
            case R.id.clear:
                Paint.delete();
                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.pen:
                Paint.function = "pen";
                //Paint.function = "select_point";
                return true;
            case  R.id.select_point:
                Paint.function = "select_point";
                Toast.makeText(MainActivity.this, "X= " + Paint.cx + "  Y= " + Paint.cy, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.draw_arc:
                Paint.function = "arc";
                return true;
            case R.id.draw_circle:
                Paint.function = "circle";
                Toast.makeText(MainActivity.this, "X= " + Paint.cx + "  Y= " + Paint.cy + "  Center= " + Paint.radius_board, Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialogue() {
        AlertDialog.Builder dialogue = new AlertDialog.Builder(MainActivity.this);
        dialogue.setTitle("Enter start and sweep angle:");
        //Toast.makeText(MainActivity.this,"Great Success Dialouge", Toast.LENGTH_SHORT).show();

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText start_angle_edit = new EditText(this);
        start_angle_edit.setHint("start angle");
        layout.addView(start_angle_edit);

        final EditText sweep_angle_edit = new EditText(this);
        sweep_angle_edit.setHint("sweep angle");
        layout.addView(sweep_angle_edit);

        dialogue.setView(layout);

        start_angle_edit.setText(String.valueOf(Paint.start_angle_board));
        sweep_angle_edit.setText(String.valueOf(Paint.sweep_angle_board));

        dialogue.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mstart_angle_string = start_angle_edit.getText().toString();
                mstart_angle = Float.parseFloat(mstart_angle_string);

                msweep_angle_string = sweep_angle_edit.getText().toString();
                msweep_angle = Float.parseFloat(msweep_angle_string);

                Paint.start_angle_board = mstart_angle;
                Paint.sweep_angle_board = msweep_angle;

                Paint.function = "set_arc";

            }
        });

        dialogue.show();
    }

}