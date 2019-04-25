package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.DialogInterface;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.ubtechinc.cruzr.sdk.navigation.NavigationApi;
import com.ubtechinc.cruzr.sdk.navigation.model.MapPointModel;
import com.ubtechinc.cruzr.sdk.ros.RosRobotApi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init navigation api
        final NavigationApi navigationApi = NavigationApi.get();
        navigationApi.initialize(this);


        //init list
        SwipeMenuListView listView = findViewById(R.id.listView);
        ArrayList<String> list = rander_list();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setIcon(R.drawable.naviicon);
                //openItem.setTitle("Open");
                // set item title fontsize
                //openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delicon);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                final int positionToRemove = position;
                switch (index) {
                    case 0:
                        // open
                        navigationApi.startNavigationService("");
                        Log.d("button1","Deleted");
                        adb.setTitle("Navigate");
                        adb.setMessage("Navigate to this object?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Navigate", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                //-<remove()

                            }
                        });
                        adb.show();
                        break;

                    case 1:
                        // delete
                        Log.d("button1","Deleted");
                        adb.setTitle("Delete");
                        adb.setMessage("Are you sure you want to delete?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                //-<remove()
                                adapter.notifyDataSetChanged();
                            }
                        });
                        adb.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();

            }
        });
    }

    public void openAddDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "add dialog");
    }

    public ArrayList<String> rander_list(){
        Random rand = new Random();
        ArrayList<String> list = new ArrayList<>();
        for(int i =0; i < 20; i++){
            list.add("object " + i +" at room " + rand.nextInt(9)+ rand.nextInt(9)+ rand.nextInt(9));
        }
        return list;
    }



}
