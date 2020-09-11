package com.lkd.loankarado;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.lkd.loankarado.videoPOJO.Datum;
import com.lkd.loankarado.videoPOJO.videoBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Videos extends Fragment  {

    RecyclerView galleryView;
    static MainActivity mainActivity;
    ProgressBar progress;
    GalleryAdapter adapter;
    StaggeredGridLayoutManager manager;
    List<Datum> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery , container , false);
        mainActivity = (MainActivity) getActivity();

        list = new ArrayList<>();

        galleryView = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progressBar2);


        adapter = new GalleryAdapter(mainActivity , list);
        manager = new StaggeredGridLayoutManager(1 , StaggeredGridLayoutManager.VERTICAL);

        galleryView.setAdapter(adapter);
        galleryView.setLayoutManager(manager);

        progress.setVisibility(View.VISIBLE);

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

        Call<videoBean> call = cr.getVideos();

        call.enqueue(new Callback<videoBean>() {
            @Override
            public void onResponse(Call<videoBean> call, Response<videoBean> response) {

                adapter.setData(response.body().getData());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<videoBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        return view;
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>
    {
        Context context;
        List<Datum> list = new ArrayList<>();

        public GalleryAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.video_list_item , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.setIsRecyclable(false);
            Datum item = list.get(position);

            final String iidd = getYouTubeId(item.getUrl());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage("https://img.youtube.com/vi/" + iidd + "/0.jpg" , holder.image , options);
            //loader.displayImage(item.getThumbnail() , holder.image , options);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + iidd));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + iidd));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }

                }
            });

            holder.title.setText(item.getTitle());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.textView);
            }
        }
    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }

}
