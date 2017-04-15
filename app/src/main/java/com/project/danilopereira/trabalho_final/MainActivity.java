package com.project.danilopereira.trabalho_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.danilopereira.trabalho_final.adapter.GamesAdapter;
import com.project.danilopereira.trabalho_final.dao.GameDao;
import com.project.danilopereira.trabalho_final.model.Game;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvGames;
    private GamesAdapter gamesAdapter;
    private GameDao gameDao;
    private List<Game> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, InsertActivity.class), InsertActivity.CODE_NEW_GAME);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        rvGames = (RecyclerView) findViewById(R.id.rvGame);
        
        loadGamesList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(MainActivity.this, "Canceled",
                    Toast.LENGTH_LONG).show();
        }
        else if(resultCode == InsertActivity.CODE_NEW_GAME){
            loadGamesList();
        }
        else{
            loadGamesList();
        }
    }

    private void loadGamesList() {
        gameDao = new GameDao(this);
        games = gameDao.list();
        setupList(games);
    }

    private void setupList(List<Game> games){
        gamesAdapter = new GamesAdapter(MainActivity.this, games, gamesOnClickListener());
        rvGames.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvGames.setAdapter(gamesAdapter);
    }

    private GamesAdapter.GamesOnClickListener gamesOnClickListener(){
        return new GamesAdapter.GamesOnClickListener() {
            @Override
            public void onLongClickCell(View view, final int position) {
                final Game game = games.get(position);


                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int retorno = 0;

                        switch (item.getItemId()){
                            case R.id.menu_delete:
                                retorno = gameDao.delete(game.getId());
                                break;
                            case R.id.menu_edit:
                                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                                intent.putExtra("game", game);
                                startActivityForResult(intent, 1);
                                break;
                        }

                        if (retorno > 0) {
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                        }

                        games.remove(position);
                        gamesAdapter.notifyItemRemoved(position);
                        gamesAdapter.notifyItemRangeChanged(position, games.size());

                        loadGamesList();

                        return false;
                    }
                });

                popupMenu.show();
            }
        };
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
