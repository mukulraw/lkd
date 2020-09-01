package com.mrtecks.loankarado;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrtecks.loankarado.loanPOJO.Datum;
import com.mrtecks.loankarado.loanPOJO.loanBean;
import com.mrtecks.loankarado.videoPOJO.videoBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Applications extends Fragment {

    RecyclerView grid;
    ProgressBar progress;
    GalleryAdapter adapter;
    static MainActivity mainActivity;
    GridLayoutManager manager;
    List<Datum> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.applications, container, false);
        mainActivity = (MainActivity) getActivity();

        list = new ArrayList<>();
        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progressBar3);

        progress.setVisibility(View.VISIBLE);

        adapter = new GalleryAdapter(mainActivity , list);
        manager = new GridLayoutManager(mainActivity , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        Bean b = (Bean) mainActivity.getApplicationContext();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.HEADERS);
        logging.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(1000, TimeUnit.SECONDS).readTimeout(1000, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS).addInterceptor(logging).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<loanBean> call = cr.getMyLoan(SharePreferenceUtils.getInstance().getString("userId"));

        call.enqueue(new Callback<loanBean>() {
            @Override
            public void onResponse(Call<loanBean> call, Response<loanBean> response) {

                adapter.setData(response.body().getData());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<loanBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        return view;
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
            View view = inflater.inflate(R.layout.loan_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.setIsRecyclable(false);
            Datum item = list.get(position);

            holder.type.setText(item.getLoanType());
            holder.amount.setText("Loan Amount: ₹" + item.getAmount());
            holder.status.setText("Current Status: " + item.getStatus());
            holder.date.setText(item.getCreated());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView type, amount, status, date;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                type = itemView.findViewById(R.id.textView8);
                amount = itemView.findViewById(R.id.textView9);
                status = itemView.findViewById(R.id.textView10);
                date = itemView.findViewById(R.id.textView11);
            }
        }
    }

}
