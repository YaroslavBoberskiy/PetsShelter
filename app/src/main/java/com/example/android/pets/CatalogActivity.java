/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.DbContract;
import com.example.android.pets.data.PetsDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new PetsDbHelper(this);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String projection [] = {
                DbContract.PetsEntry._ID,
                DbContract.PetsEntry.COLUMN_PET_NAME,
                DbContract.PetsEntry.COLUMN_PET_BREED,
                DbContract.PetsEntry.COLUMN_PET_GENDER,
                DbContract.PetsEntry.COLUMN_PET_WEIGHT
        };

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.

        Cursor cursor = db.query(
                DbContract.PetsEntry.TABLE_NAME,          // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("The pets table contains " + cursor.getCount() + " pets. \n");
            displayView.append(DbContract.PetsEntry._ID +
                    " - " + DbContract.PetsEntry.COLUMN_PET_NAME +
                    " - " + DbContract.PetsEntry.COLUMN_PET_BREED +
                    " - " + DbContract.PetsEntry.COLUMN_PET_GENDER +
                    " - " + DbContract.PetsEntry.COLUMN_PET_WEIGHT + "\n");

            int idColumnIdx = cursor.getColumnIndex(DbContract.PetsEntry._ID);
            int nameColumnIdx = cursor.getColumnIndex(DbContract.PetsEntry.COLUMN_PET_NAME);
            int breedColumnIdx = cursor.getColumnIndex(DbContract.PetsEntry.COLUMN_PET_BREED);
            int genderColumnIdx = cursor.getColumnIndex(DbContract.PetsEntry.COLUMN_PET_GENDER);
            int weightColumnIdx = cursor.getColumnIndex(DbContract.PetsEntry.COLUMN_PET_WEIGHT);

            while (cursor.moveToNext()) {
                int _id = cursor.getInt(idColumnIdx);
                String name = cursor.getString(nameColumnIdx);
                String breed = cursor.getString(breedColumnIdx);
                int gender = cursor.getInt(genderColumnIdx);
                int weight = cursor.getInt(weightColumnIdx);

                displayView.append(_id+" - "+name+" - "+breed+" - "+gender+" - "+weight + "\n");
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
