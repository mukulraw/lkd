package com.mrtecks.loankarado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    BottomNavigationView navigation;
    TextView rewards, login, terms, about, address, logout, cart, orders, refer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        login = findViewById(R.id.textView3);
        logout = findViewById(R.id.logout);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setTitle("Loan Kara Do");
        toolbar.setTitleTextColor(Color.WHITE);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:

                        FragmentManager fm = getSupportFragmentManager();

                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }

                        FragmentTransaction ft = fm.beginTransaction();
                        Home frag1 = new Home();
                        ft.replace(R.id.replace, frag1);
                        //ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.action_applications:
                        /*FragmentManager fm1 = getSupportFragmentManager();

                        for (int i = 0; i < fm1.getBackStackEntryCount(); ++i) {
                            fm1.popBackStack();
                        }

                        FragmentTransaction ft1 = fm1.beginTransaction();
                        Category frag11 = new Category();
                        ft1.replace(R.id.replace, frag11);
                        //ft.addToBackStack(null);
                        ft1.commit();
                        drawer.closeDrawer(GravityCompat.START);

                        break;*/
                    case R.id.action_gallery:
                        /*FragmentManager fm2 = getSupportFragmentManager();

                        for (int i = 0; i < fm2.getBackStackEntryCount(); ++i) {
                            fm2.popBackStack();
                        }

                        FragmentTransaction ft2 = fm2.beginTransaction();
                        Search frag12 = new Search();
                        ft2.replace(R.id.replace, frag12);
                        //ft.addToBackStack(null);
                        ft2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;*/
                    case R.id.action_videos:
                        /*FragmentManager fm3 = getSupportFragmentManager();

                        for (int i = 0; i < fm3.getBackStackEntryCount(); ++i) {
                            fm3.popBackStack();
                        }

                        FragmentTransaction ft3 = fm3.beginTransaction();
                        Orders frag13 = new Orders();
                        ft3.replace(R.id.replace, frag13);
                        //ft.addToBackStack(null);
                        ft3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;*/
                    case R.id.action_contact:
                        /*FragmentManager fm4 = getSupportFragmentManager();

                        for (int i = 0; i < fm4.getBackStackEntryCount(); ++i) {
                            fm4.popBackStack();
                        }

                        FragmentTransaction ft4 = fm4.beginTransaction();
                        Cart frag14 = new Cart();
                        ft4.replace(R.id.replace, frag14);
                        //ft.addToBackStack(null);
                        ft4.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        break;*/
                }
                return true;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharePreferenceUtils.getInstance().deletePref();

                Intent intent = new Intent(MainActivity.this, Splash.class);
                startActivity(intent);
                finishAffinity();

            }
        });

        navigation.setSelectedItemId(R.id.action_home);

        final String uid = SharePreferenceUtils.getInstance().getString("userId");

        if (uid.length() > 0) {
            login.setText(SharePreferenceUtils.getInstance().getString("phone"));

        }


    }
}