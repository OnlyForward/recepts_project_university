package com.example.a123.recepts_project_university.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a123.recepts_project_university.R;
import com.example.a123.recepts_project_university.fragments.AboutApp;
import com.example.a123.recepts_project_university.fragments.CreateRecipes;
import com.example.a123.recepts_project_university.fragments.ReceptsList;
import com.example.a123.recepts_project_university.fragments.SavedRecipes;
import com.example.a123.recepts_project_university.fragments.Settings;

public class MainActivity extends BasicActivity {

    private boolean hide = true;
    private  onQueryWritten listener;



    public interface onQueryWritten{
        void getReceipts(String receipts);
    }

    public  void setItemListener(onQueryWritten listener1){
        listener = listener1;
    }

    public  void setItemListenerDb(onQueryWritten listener1){
        listener = listener1;
    }

    @Override
    public Fragment createFragment() {
        return new ReceptsList();
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

        getMenuInflater().inflate(R.menu.main, menu);
        if (hide) {
            menu.setGroupVisible(R.id.group_search, hide);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    listener.getReceipts(s);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        } else {
            menu.setGroupVisible(R.id.group_search, hide);
            this.invalidateOptionsMenu();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_recipes:
                fragment = createFragment();
                hide = true;
                this.invalidateOptionsMenu();
                break;
            case R.id.nav_settings:
                fragment = new Settings();
                this.invalidateOptionsMenu();
                hide = false;
                break;
            case R.id.nav_saved_recipes:
                fragment = new SavedRecipes();
                this.invalidateOptionsMenu();
                hide = true;
                break;
            case R.id.nav_about:
                hide = false;
                fragment = new AboutApp();
                this.invalidateOptionsMenu();
                break;
            case R.id.nav_create:
                hide = false;
                fragment = new CreateRecipes();
                this.invalidateOptionsMenu();
                break;
            case R.id.nav_quit:
                Log.i("Quit", "he came here");
                finishAffinity();
                break;
        }

        if(fragment!=null) {
            startFragment(fragment);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void startFragment(Fragment fragment) {
        String name = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment, name).commit();
    }
}
