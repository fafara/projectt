package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MenuCommercant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
public String _CurrentFragment;
private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_commercant);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState( );

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            final Fragment principalfragment = new PrincipalFragment();
            transaction.replace(R.id.frm, principalfragment,"Principal");
            transaction.addToBackStack("Principal");
            transaction.commit();
            _CurrentFragment ="Principalfragment";
            getSupportFragmentManager().executePendingTransactions();






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed( );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater( ).inflate(R.menu.menu_commercant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId( );

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null ;
        int id = item.getItemId( );

        if (id == R.id.nav_transaction)
                { }

        else if (id == R.id.nav_duplicata)
                { }

        else if (id == R.id.nav_product)
                {
                    fragment = new CatalogueFragment();
                }

        else if (id == R.id.nav_addcompte)
        {
            fragment = new UserFragment();
            /*/intent = new Intent(MenuCommercant.this, AddUser.class);
            startActivity(intent);/*/

        }
        else if (id == R.id.nav_id_terminal)
        {
            fragment = new IDTerminalFragment();
        }

        else if (id == R.id.nav_quitter)
        {
            Process.killProcess(Process.myPid());
            System.exit(1);

        }

    if (fragment != null) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.frm,fragment);

        ft.commit();

    }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

