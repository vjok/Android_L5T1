package com.example.predator.l5t1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int seconds = 0;
    int minutes = 0;
    static final int ADD_NEW_PART = 311;
    public static final String EXTRA_MESSAGE = "com.example.predator.l4";
    public ArrayList<Part> parts = new ArrayList<>();
    ListView listView = null;
    TextView length = null;
    Button buttonStart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lwParts);
        buttonStart = findViewById(R.id.btnStart);
        buttonStart.setOnClickListener(this);
        length = findViewById(R.id.twLength);
        //read();
        read();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        if(parts.size() == 0) {
            Toast.makeText(MainActivity.this,"No parts added",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this,TimerCountdownActivity.class);
            intent.putExtra(EXTRA_MESSAGE, parts);
            startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menuBtn)
        {
            Intent intent = new Intent(this, AddNewPartActivity.class);
            startActivityForResult(intent, ADD_NEW_PART);
        }
        else if(item.getItemId() == R.id.btnClear)
        {
            clearList();
        }

        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        PartArrayAdapter adapter = new PartArrayAdapter(this, parts);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_PART && resultCode == RESULT_OK)
        {
            Part part = (Part) data.getSerializableExtra("PART");
            parts.add(part);
            TextView noPartsAdded = findViewById(R.id.twPA);
            noPartsAdded.setText(null);
            CountTime();
            length.setText("Total length "+minutes+" minutes "+seconds+" seconds.");
        }
    }

    public void CountTime()
    {
        seconds = 0;
        minutes = 0;
        for(int i = 0; i < parts.size(); i++) {
            Part part = parts.get(i);
            seconds = seconds+part.getTime();
            if(seconds >= 60)
            {
                minutes = minutes+1;
                seconds = seconds-60;
            } }
    }


    public void save()
    {
        try {
            FileOutputStream fileOutput = openFileOutput("partFile", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(parts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read()
    {

        try {
            FileInputStream fileInput = openFileInput("partFile");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);
            ArrayList<Part> listOfParts = (ArrayList<Part>) objectInput.readObject();
            parts = listOfParts;
            CountTime();
            length.setText("Total length "+minutes+" minutes "+seconds+" seconds.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearList()
    {
        new AlertDialog.Builder(this)
                .setTitle("Clear workouts")
                .setMessage("Do you really want to clear workouts?")
                .setIcon(R.drawable.erroricon)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parts.clear();
                        Toast.makeText(MainActivity.this,"Workouts cleared", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton("No",null).show();

    }

}
