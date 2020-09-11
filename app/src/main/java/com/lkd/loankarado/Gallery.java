package com.lkd.loankarado;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Gallery extends Fragment {

    RecyclerView galleryView;
    static MainActivity mainActivity;
    ProgressBar progress;
    GalleryAdapter adapter;
    StaggeredGridLayoutManager manager;
    ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery, container, false);
        mainActivity = (MainActivity) getActivity();

        list = new ArrayList<>();

        galleryView = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progressBar2);


        adapter = new GalleryAdapter(mainActivity, list);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

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

        Call<bannerBean> call = cr.getGallery();

        call.enqueue(new Callback<bannerBean>() {
            @Override
            public void onResponse(Call<bannerBean> call, Response<bannerBean> response) {

                adapter.setData(response.body().getData());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<bannerBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        return view;
    }

    class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        Context context;
        ArrayList<String> list = new ArrayList<>();

        public GalleryAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(ArrayList<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.gallery_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.setIsRecyclable(false);
            final String item = list.get(position);

            final DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            final ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item, holder.image, options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    //dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    //      WindowManager.LayoutParams.MATCH_PARENT);
                    dialog.setContentView(R.layout.zoom_dialog);
                    dialog.setCancelable(true);
                    dialog.show();

                    ImageView img = dialog.findViewById(R.id.image);
                    loader.displayImage(item, img, options);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}
