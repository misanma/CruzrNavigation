package com.example.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.content.DialogInterface;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener {

    DatabaseHelper mDatabaseHelper;
    ArrayAdapter adapter;
    ArrayList<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init navigation api
        final NavigationApi navigationApi = NavigationApi.get();
        navigationApi.initialize(this);

        mDatabaseHelper = new DatabaseHelper(this);

        //init list
        SwipeMenuListView listView = findViewById(R.id.listView);

        populateList(listView);

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
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                final int positionToRemove = position;
                switch (index) {
                    case 0:
                        // open
                        Log.d("button1","Navigated");
                        adb.setTitle("Navigate");
                        adb.setMessage("Navigate to this object?");
                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Navigate", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){

                                //TODO: navigation code
                                toastMessage("Navigating.....");
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
                                mDatabaseHelper.deleteDataByName((String)adapter.getItem(position));
                                adapter.remove(adapter.getItem(position));
                                adapter.notifyDataSetChanged();
                                toastMessage("Deleted");
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

    public void populateList(SwipeMenuListView listView){

        Cursor data = mDatabaseHelper.getData();

        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }


    public void openAddDialog(){
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "add dialog");
    }


    public void toastMessage(String messeage){
        Toast.makeText(this, messeage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyTexts(String  name, String desc) {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        Log.d("button1","Adding");
        adb.setTitle("Add location");
        adb.setMessage("Adding this " + name + "?");
        adb.setNegativeButton("Cancel", null);
        final String apname = name;
        final String apdesc = desc;
        adb.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

                //placeholder coordinates
                int x = 0;
                int y = 0;
                int z = 0;
                boolean insertData = mDatabaseHelper.addData(apname, apdesc, x, y ,z);
                if (!insertData) {
                    adapter.add(apname);
                    adapter.notifyDataSetChanged();
                    toastMessage("New object added");
                }else{
                    toastMessage("Oops, something went wrong.");
                }
            }
        });
        adb.show();

    }
}
