package com.example.edo.studiebarometer;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.edo.studiebarometer.GSON.GsonRequest;
import com.example.edo.studiebarometer.Models.Course;
import com.example.edo.studiebarometer.VOLLEYHELPER.VolleyHelper;
import com.example.edo.studiebarometer.database.DatabaseHelper;
import com.example.edo.studiebarometer.database.Databaseinfo;

import java.lang.reflect.Type;
import java.util.List;

public class InvoerScherm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Course[] courses;
    List<Course> subjects;
    DatabaseHelper dbHelper;
    ContentValues values;
//    int chosenId= 0;
    Cursor rs;
    String names[] = {"IIPMEDT", "IIPSEN", "IIPBIT", "IIPFIT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer_scherm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hardcoded From JSON", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(1).setChecked(true);

        requestSubjects();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.invoer_scherm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, DetailsScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, OverzichtScherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, Persoonsscherm.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void showButtons(View view) {
        Intent intent = new Intent(this, InvoerScherm.class);
        startActivity(intent);
    }
    public void createDatabase()
    {
        int getLengte = subjects.size();


          jsonToDatabase(getLengte);
    }
    public void grabJsonFirstPeriod(View view) {
        int getLengte = subjects.size();
        String periode = "1";


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);





    }

    public void grabJsonSecondPeriod(View view) {

        int getLengte = subjects.size();

        String periode = "2";
//        dePeriode = 1;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);

    }

    public void grabJsonThirthPeriod(View view) {
        int getLengte = subjects.size();
        String periode = "3";
//        dePeriode = 1;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);
    }

    public void grabJsonFourthPeriod(View view) {
        int getLengte = subjects.size();
        String periode = "4";


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_frame);
        grabPeriod(rl, getLengte, periode);



    }

    private void requestSubjects() {
        Type type = new TypeToken<List<Course>>() {
        }.getType();
        GsonRequest<List<Course>> request = new GsonRequest<List<Course>>(
                "http://fuujokan.nl/subject_lijst.json", type, null,
                new Response.Listener<List<Course>>() {
                    @Override
                    public void onResponse(List<Course> response) {
                        processRequestSucces(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                processRequestError(error);
            }
        }
        );
        VolleyHelper.getInstance(this).addToRequestQueue(request);

    }

    private void processRequestSucces(List<Course> subjects) {

        this.subjects = subjects;
        createDatabase();
    }

    private void processRequestError(VolleyError error) {
    }


    public void grabPeriod(RelativeLayout rl, int lengte, String periode) {

        int groeyX = 150;
        int groeyY = 80;
        int positie = 0;
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        Button b4 = (Button) findViewById(R.id.button4);
        Button b5 = (Button) findViewById(R.id.button5);
        TextView v1 = (TextView) findViewById(R.id.Textkeuze);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.VISIBLE);
        v1.setVisibility(View.GONE);
        rs = dbHelper.query(Databaseinfo.CourseTables.COURSE, new String[]{"*"},  "period like " + periode, null, null, null, null);

        rs.moveToFirst();
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        int studierichting = preferences2.getInt("Periode", 0);
        for (int j = 0; j < 4; j++) {
            if (j > 1) {
                makeLayout(rl, 125, groeyY, 0, j, positie, "TopText");
            } else {
                makeLayout(rl, 135, groeyY, 0, j, positie, "TopText");
            }
        }
       do
        {

            String name = rs.getString(rs.getColumnIndex("name"));
            String ects = rs.getString(rs.getColumnIndex("ects"));
            String grade = rs.getString(rs.getColumnIndex("grade"));
            String period = rs.getString(rs.getColumnIndex("period"));

            positie++;
            if(name.equals("IIPXXXX"))
            {
                name = names[studierichting -1];
            }
            makeLayout(rl, groeyX, groeyY, 1, 0, positie,name);
            makeLayout(rl, groeyX, groeyY, 1, 1, positie, ects);
            makeLayout(rl, 135, groeyY, 1, 2, positie, grade);
            makeLayout(rl, 135, groeyY, 1, 3, positie, period);
            makeLayout(rl, 120, groeyY, 1, 4, positie, "button");

        }
       while(rs.moveToNext());

    }

    public void makeLayout(RelativeLayout rl, int groeyX, int groeyY, int i, int j, int positie, String textTextField) {

        int x = 5;
        int y = 5;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (j == 4)
        {
            layoutParams.setMargins(x + (groeyX * j), y + (groeyY * positie) - 25, 0, 0);
        }
        else
        {
            layoutParams.setMargins(x + (groeyX * j), y + (groeyY * positie), 0, 0);
        }

        TextView textView = new TextView(this);
        Button button = new Button(this);

        textView.setLayoutParams(layoutParams);
        button.setLayoutParams(layoutParams);

        if (i == 0) {
            if (j == 0) {
                textView.setText("Name");
            } else if (j == 1) {
                textView.setText("Ects");
            } else if (j == 2) {
                textView.setText("Grade");
            } else {
                textView.setText("Period");
            }
        } else {
            if (j == 0) {
                textView.setText(textTextField);
            } else if (j == 1) {
                textView.setText(textTextField);
            } else if (j == 2) {
                textView.setText(textTextField);
            } else if(j == 3){
                textView.setText(textTextField);
            }
            else
            {
                button.setText("Edit");
                button.setId(positie - 1);
                button.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int chosenId = v.getId();
                        rs.moveToFirst();
                        for(int i = 0; i < chosenId; i++)
                        {
                            rs.moveToNext();
                        }
                        String name = rs.getString(rs.getColumnIndex("name"));
                        Intent i = new Intent(getApplicationContext(), DetailsScherm.class);
                        i.putExtra("chosenName", name);
                        startActivity(i);
                    }
                });

            }
        }
        if (j == 4 && i >0) {
            rl.addView(button);
        } else {
            rl.addView(textView);
        }


    }
    public void jsonToDatabase(int lengte)
    {
        dbHelper = DatabaseHelper.getHelper(this);
        values = new ContentValues();
        int change = 0;
        for (int i = 0; i < lengte; i++) {
            Cursor rs = dbHelper.query(Databaseinfo.CourseTables.COURSE, new String[]{"*"},  "name like '" + subjects.get(i).name + "'", null, null, null, null);
            if(rs.moveToFirst()) {

            }
            else
            {
                change = 1;
                values.put(Databaseinfo.CourseColumn.NAME, subjects.get(i).name);
                values.put(Databaseinfo.CourseColumn.ECTS, subjects.get(i).ects);
                values.put(Databaseinfo.CourseColumn.GRADE, subjects.get(i).grade);
                values.put(Databaseinfo.CourseColumn.PERIOD, subjects.get(i).period);
                values.put(Databaseinfo.CourseColumn.GEHAALD, "O");
                dbHelper.insert(Databaseinfo.CourseTables.COURSE, null, values);
            }
        }
        if(change == 1) {

            Snackbar.make(this.findViewById(android.R.id.content), "De database is aangepast.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
