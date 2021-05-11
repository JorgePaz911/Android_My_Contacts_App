package com.example.mycontacts.view.mainactivity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mycontacts.R;

/**
 * Main Menu Activity
 * Has its own fragment
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.mainactivity);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navView = (NavigationView)findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(this);

    }

    /**
     * Options for the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_btns, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * When an item is selected from the action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        switch(item.getItemId()) {
            /*case R.id.hideMenu:
                android.support.v7.app.ActionBar actionBar = getSupportActionBar();
                actionBar.hide();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * When an item is selected from the navigation drawer
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.sortFN){
            Toast.makeText(this,R.string.sort_fn,Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.sortLN){
            Toast.makeText(this,R.string.sort_ln,Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.sortEM){
            Toast.makeText(this,R.string.sort_em,Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.sortNum){
            Toast.makeText(this,R.string.sort_num,Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
