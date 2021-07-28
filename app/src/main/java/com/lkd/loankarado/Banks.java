package com.lkd.loankarado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lkd.loankarado.payoutPOJO.Datum;
import com.lkd.loankarado.payoutPOJO.payoutBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Banks extends AppCompatActivity {

    private Toolbar toolbar;
    ProgressBar progress;

    RecyclerView grid;
    GalleryAdapter adapter;
    GridLayoutManager manager;
    List<Datum> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar3);
        progress = findViewById(R.id.progressBar3);
        grid = findViewById(R.id.grid);

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
        toolbar.setTitle("Bank's Options");

        adapter = new GalleryAdapter(this, list);
        manager = new GridLayoutManager(this, 1);
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<payoutBean> call = cr.getBanks();

        call.enqueue(new Callback<payoutBean>() {
            @Override
            public void onResponse(Call<payoutBean> call, Response<payoutBean> response) {

                if (response.body().getStatus().equals("1")) {
                    adapter.setData(response.body().getData());
                }

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<payoutBean> call, Throwable t) {
                progress.setVisibility(View.GONE);

            }
        });

    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        Context context;
        List<Datum> list = new ArrayList<>();

        public GalleryAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.payout_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.setIsRecyclable(false);
            final Datum item = list.get(position);

            holder.loan.setText(item.getLoan());
            holder.amount.setText(item.getAmount());
            holder.payout.setText("₹" + item.getPayout());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView loan, amount, payout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                loan = itemView.findViewById(R.id.loan);
                amount = itemView.findViewById(R.id.amount);
                payout = itemView.findViewById(R.id.payout);
            }
        }
    }

}