package ru.ponomarenko.hwbasics2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.service.MainService;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String DATA_KEY = "DATA_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MainService.getInstance().initSharedPreference(this);
        String saveData = MainService.getInstance().getStringFromSharedPreference(MainService.DATA_KEY);

        if (saveData == null){
            Toast.makeText(this, "Нет данных", Toast.LENGTH_LONG).show();
        }
        else{
            try {
                Type type = new TypeToken<ArrayList<Note>>(){}.getType();
                ArrayList<Note> data = new GsonBuilder().create().fromJson(saveData, type);
                MainService.getInstance().getNoteRepo().setData( data);
            }
            catch (Exception e){
                Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_LONG).show();
            }

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar(isLandscape());

        if (savedInstanceState == null) getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new NotesFragment())
                //.addToBackStack("")
                .commit();

    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void initToolBar(boolean isLandscape) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!isLandscape)
            initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_drawer_about:
                        openAboutFragment();
                        return true;
                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }
                return false;
            }


        });
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container, new AboutFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about:
                openAboutFragment();
                break;
            case R.id.action_exit:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}