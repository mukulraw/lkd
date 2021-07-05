package com.lkd.loankarado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lkd.loankarado.payoutPOJO.Data;
import com.lkd.loankarado.payoutPOJO.payoutBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Payout extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar progress;

    TextView home, lap, business, personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);

        toolbar = findViewById(R.id.toolbar3);
        progress = findViewById(R.id.progressBar3);
        home = findViewById(R.id.textView21);
        lap = findViewById(R.id.textView23);
        business = findViewById(R.id.textView25);
        personal = findViewById(R.id.textView28);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Payout Structure");

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<payoutBean> call = cr.getPayout();

        call.enqueue(new Callback<payoutBean>() {
            @Override
            public void onResponse(Call<payoutBean> call, Response<payoutBean> response) {

                if (response.body().getStatus().equals("1")) {
                    Data item = response.body().getData();

                    home.setText(item.getHomeLoan());
                    lap.setText(item.getLoanAgainstProperty());
                    business.setText(item.getBusinessLoan());
                    personal.setText(item.getPersonalLoan());

                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<payoutBean> call, Throwable t) {
                progress.setVisibility(View.GONE);

            }
        });

    }
}