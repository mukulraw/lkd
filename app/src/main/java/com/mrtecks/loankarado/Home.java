package com.mrtecks.loankarado;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.santalu.autoviewpager.AutoViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Home extends Fragment {

    CardView personal, business, homesalaried, homeself, propertysalaried, propertyself;

    AutoViewPager banner;

    static MainActivity mainActivity;

    CircleIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        mainActivity = (MainActivity) getActivity();

        personal = view.findViewById(R.id.cardView2);
        indicator = view.findViewById(R.id.indicator);
        business = view.findViewById(R.id.cardView);
        homesalaried = view.findViewById(R.id.cardView4);
        homeself = view.findViewById(R.id.cardView3);
        propertysalaried = view.findViewById(R.id.cardView6);
        propertyself = view.findViewById(R.id.cardView5);
        banner = view.findViewById(R.id.imageView2);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, Personal.class);
                startActivity(intent);

            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, Business.class);
                startActivity(intent);

            }
        });

        homesalaried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, HomeSalaried.class);
                startActivity(intent);

            }
        });

        homeself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, HomeSelf.class);
                startActivity(intent);

            }
        });


        propertysalaried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, PropertySalaried.class);
                startActivity(intent);

            }
        });

        propertyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainActivity, PropertySelf.class);
                startActivity(intent);

            }
        });

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

        Call<bannerBean> call = cr.getBanner();

        call.enqueue(new Callback<bannerBean>() {
            @Override
            public void onResponse(Call<bannerBean> call, Response<bannerBean> response) {

                PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, response.body().getData());
                banner.setAdapter(adapter);
                indicator.setViewPager(banner);


            }

            @Override
            public void onFailure(Call<bannerBean> call, Throwable t) {

            }
        });

        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        List<String> banners = new ArrayList<>();

        public PagerAdapter(@NonNull FragmentManager fm, int behavior, List<String> banners) {
            super(fm, behavior);
            this.banners = banners;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            page page = new page();
            Bundle b = new Bundle();
            b.putString("url", banners.get(position));
            page.setArguments(b);
            return page;
        }

        @Override
        public int getCount() {
            return banners.size();
        }
    }

    public static class page extends Fragment {
        ImageView image;
        String url;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.page, container, false);

            url = getArguments().getString("url");

            image = view.findViewById(R.id.imageView4);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(url, image, options);

            return view;
        }
    }

}
