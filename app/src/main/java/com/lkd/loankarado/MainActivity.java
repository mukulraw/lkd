package com.lkd.loankarado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import zendesk.chat.Chat;
import zendesk.chat.ChatEngine;
import zendesk.messaging.MessagingActivity;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    BottomNavigationView navigation;
    TextView home, login, applications, gallery, videos, logout, contact, property, payout;
    FloatingActionButton chat;

    ImageView notification, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chat.INSTANCE.init(this, "yw3Pvlsi7o8SZiLl7timWfW9mZViKLJo");

        toolbar = findViewById(R.id.toolbar);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        login = findViewById(R.id.textView3);
        logout = findViewById(R.id.logout);
        home = findViewById(R.id.home);
        applications = findViewById(R.id.applications);
        gallery = findViewById(R.id.gallery);
        videos = findViewById(R.id.videos);
        contact = findViewById(R.id.contact);
        chat = findViewById(R.id.textView15);
        property = findViewById(R.id.property);
        notification = findViewById(R.id.imageView7);
        share = findViewById(R.id.imageView8);
        payout = findViewById(R.id.payout);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setTitle("");
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
                        return true;
                    case R.id.action_applications:
                        FragmentManager fm1 = getSupportFragmentManager();

                        for (int i = 0; i < fm1.getBackStackEntryCount(); ++i) {
                            fm1.popBackStack();
                        }

                        FragmentTransaction ft1 = fm1.beginTransaction();
                        Applications frag11 = new Applications();
                        ft1.replace(R.id.replace, frag11);
                        //ft.addToBackStack(null);
                        ft1.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_gallery:
                        FragmentManager fm2 = getSupportFragmentManager();

                        for (int i = 0; i < fm2.getBackStackEntryCount(); ++i) {
                            fm2.popBackStack();
                        }

                        FragmentTransaction ft2 = fm2.beginTransaction();
                        Gallery frag12 = new Gallery();
                        ft2.replace(R.id.replace, frag12);
                        //ft.addToBackStack(null);
                        ft2.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_videos:
                        FragmentManager fm3 = getSupportFragmentManager();

                        for (int i = 0; i < fm3.getBackStackEntryCount(); ++i) {
                            fm3.popBackStack();
                        }

                        FragmentTransaction ft3 = fm3.beginTransaction();
                        Videos frag13 = new Videos();
                        ft3.replace(R.id.replace, frag13);
                        //ft.addToBackStack(null);
                        ft3.commit();
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.action_contact:

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", "+91 81309 03409", "")
                                )
                        );

                        startActivity(intent);
                        return false;

                        /*FragmentManager fm4 = getSupportFragmentManager();

                        for (int i = 0; i < fm4.getBackStackEntryCount(); ++i) {
                            fm4.popBackStack();
                        }

                        FragmentTransaction ft4 = fm4.beginTransaction();
                        Contact frag14 = new Contact();
                        ft4.replace(R.id.replace, frag14);
                        //ft.addToBackStack(null);
                        ft4.commit();
                        drawer.closeDrawer(GravityCompat.START);*/
                    default:
                        return false;
                }
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.setSelectedItemId(R.id.action_home);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        applications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.setSelectedItemId(R.id.action_applications);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.setSelectedItemId(R.id.action_gallery);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                PropertyImages frag12 = new PropertyImages();
                ft2.replace(R.id.replace, frag12);
                ft2.addToBackStack(null);
                ft2.commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.setSelectedItemId(R.id.action_videos);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm4 = getSupportFragmentManager();
                FragmentTransaction ft4 = fm4.beginTransaction();
                Contact frag14 = new Contact();
                ft4.replace(R.id.replace, frag14);
                ft4.addToBackStack(null);
                ft4.commit();
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download LoanKaraDo: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Notifications.class);
                startActivity(intent);
            }
        });

        payout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Payout.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        navigation.setSelectedItemId(R.id.action_home);

        final String uid = SharePreferenceUtils.getInstance().getString("userId");

        if (uid.length() > 0) {
            login.setText(SharePreferenceUtils.getInstance().getString("name1"));

        }

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessagingActivity.builder()
                        .withEngines(ChatEngine.engine())
                        .show(view.getContext());

            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm3 = getSupportFragmentManager();
        if (fm3.getBackStackEntryCount() == 0)
        {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.close_app_text))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            finishAffinity();

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    })
                    .show();


        }
        else
        {
            super.onBackPressed();
        }




    }

}