package com.lkd.loankarado;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HomeSelf extends AppCompatActivity {

    Toolbar toolbar;
    static ViewPager pager;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_self);
        toolbar = findViewById(R.id.toolbar);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Home Loan (Self Employed)");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


    }

    static class PagerAdapter extends FragmentStatePagerAdapter {
        String[] titles = {
                "Personal",
                "Professional",
                "Financial",
                "Property"
        };

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new personal();
            } else if (position == 1) {
                return new professional();
            } else if (position == 2) {
                return new financial();
            } else {
                return new property();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    public static class personal extends Fragment {

        EditText mobile;
        EditText name;
        EditText dob;
        EditText email;
        Spinner gender;
        Spinner residence_type;
        EditText residence_address;
        Button next;
        int ag2 = 0;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.personal, container, false);

            mobile = view.findViewById(R.id.mobile);
            name = view.findViewById(R.id.name);
            dob = view.findViewById(R.id.dob);
            email = view.findViewById(R.id.email);
            gender = view.findViewById(R.id.gender);
            residence_type = view.findViewById(R.id.residence);
            residence_address = view.findViewById(R.id.residence_address);
            next = view.findViewById(R.id.next);

            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    long now = System.currentTimeMillis() - 1000;

                    new SingleDateAndTimePickerDialog.Builder(getActivity())
                            //.bottomSheet()
                            .curved()
                            .displayMinutes(false)
                            .displayHours(false)
                            .displayDays(false)
                            .displayMonth(true)
                            .displayYears(true)
                            .displayDaysOfMonth(true)
                            .maxDateRange(new Date(now))
                            .listener(new SingleDateAndTimePickerDialog.Listener() {
                                @Override
                                public void onDateSelected(Date date) {

                                    //date.getTime();

                                    Date dt = new Date(date.getTime());

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                    String dd = sdf.format(dt);


                                    Log.d("dddd", dd);

                                    dob.setText(dd);


                                    ag2 = getAge(dd);


                                    if (ag2 < 18) {
                                        Toast.makeText(getContext(), "You are not eligible to register in this app", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            })
                            .display();

                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String m = mobile.getText().toString();
                    String n = name.getText().toString();
                    String d = dob.getText().toString();
                    String e = email.getText().toString();

                    if (m.length() == 10) {
                        if (n.length() > 0) {
                            if (ag2 > 17) {
                                if (isValidEmail(e)) {
                                    SharePreferenceUtils.getInstance().saveString("mobile", mobile.getText().toString());
                                    SharePreferenceUtils.getInstance().saveString("name", name.getText().toString());
                                    SharePreferenceUtils.getInstance().saveString("dob", dob.getText().toString());
                                    SharePreferenceUtils.getInstance().saveString("email", email.getText().toString());
                                    SharePreferenceUtils.getInstance().saveString("gender", gender.getSelectedItem().toString());
                                    SharePreferenceUtils.getInstance().saveString("residence_type", residence_type.getSelectedItem().toString());
                                    SharePreferenceUtils.getInstance().saveString("residence_address", residence_address.getText().toString());
                                    pager.setCurrentItem(1);
                                } else {
                                    Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(getContext(), "Invalid D.O.B.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid mobile number", Toast.LENGTH_SHORT).show();
                    }


                }
            });

            return view;
        }

        private boolean isValidEmail(String email) {
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            return pattern.matcher(email).matches();
        }

        private int getAge(String dobString) {


            int age = 0;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date date1 = sdf.parse(dobString);
                Calendar now = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();
                dob.setTime(Objects.requireNonNull(date1));
                if (dob.after(now)) {
                    Toast.makeText(getContext(), "Can't be born in the future", Toast.LENGTH_SHORT).show();
                }
                int year1 = now.get(Calendar.YEAR);
                int year2 = dob.get(Calendar.YEAR);
                age = year1 - year2;
                int month1 = now.get(Calendar.MONTH);
                int month2 = dob.get(Calendar.MONTH);
                if (month2 > month1) {
                    age--;
                } else if (month1 == month2) {
                    int day1 = now.get(Calendar.DAY_OF_MONTH);
                    int day2 = dob.get(Calendar.DAY_OF_MONTH);
                    if (day2 > day1) {
                        age--;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return age;


        }

    }

    public static class professional extends Fragment {

        EditText pan;
        Spinner city;
        Spinner company_type;
        Spinner registration_type;
        EditText company_name;
        EditText office_address;
        Spinner office_type;
        Spinner registration_age;
        Button next;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.professional2, container, false);

            pan = view.findViewById(R.id.pan);
            city = view.findViewById(R.id.city);
            company_type = view.findViewById(R.id.company_type);
            registration_type = view.findViewById(R.id.registration_type);
            company_name = view.findViewById(R.id.company_name);
            office_address = view.findViewById(R.id.office_address);
            office_type = view.findViewById(R.id.office_type);
            registration_age = view.findViewById(R.id.registration_age);
            next = view.findViewById(R.id.next);


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String p = pan.getText().toString();
                    String c = company_name.getText().toString();
                    String o = office_address.getText().toString();

                    if (p.length() > 0) {
                        if (c.length() > 0) {
                            if (o.length() > 0) {

                                SharePreferenceUtils.getInstance().saveString("pan", pan.getText().toString());
                                SharePreferenceUtils.getInstance().saveString("city", city.getSelectedItem().toString());
                                SharePreferenceUtils.getInstance().saveString("company_type", company_type.getSelectedItem().toString());
                                SharePreferenceUtils.getInstance().saveString("company_name", company_name.getText().toString());
                                SharePreferenceUtils.getInstance().saveString("registration_type", registration_type.getSelectedItem().toString());
                                SharePreferenceUtils.getInstance().saveString("office_type", office_type.getSelectedItem().toString());
                                SharePreferenceUtils.getInstance().saveString("office_address", office_address.getText().toString());
                                SharePreferenceUtils.getInstance().saveString("registration_age", registration_age.getSelectedItem().toString());

                                pager.setCurrentItem(2);

                            } else {
                                Toast.makeText(getContext(), "Invalid office address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Invalid company name", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid PAN Number", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return view;
        }
    }


    public static class financial extends Fragment {

        EditText yearly_saless;
        EditText income_in_itr;
        EditText oldest_irt_date;
        Spinner current_account;
        EditText loan_details;
        EditText loan_amount;
        EditText message;
        Button next;
        ProgressBar progress;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.financial, container, false);

            yearly_saless = view.findViewById(R.id.yearly_saless);
            income_in_itr = view.findViewById(R.id.income_in_itr);
            oldest_irt_date = view.findViewById(R.id.oldest_irt_date);
            current_account = view.findViewById(R.id.current_account);
            loan_details = view.findViewById(R.id.loan_details);
            loan_amount = view.findViewById(R.id.loan_amount);
            message = view.findViewById(R.id.message);
            next = view.findViewById(R.id.next);
            progress = view.findViewById(R.id.progress);


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String y = yearly_saless.getText().toString();
                    String i = income_in_itr.getText().toString();
                    String o = oldest_irt_date.getText().toString();
                    String l = loan_amount.getText().toString();

                    if (y.length() > 0) {
                        if (i.length() > 0) {
                            if (o.length() > 0) {
                                if (l.length() > 0) {

                                    SharePreferenceUtils.getInstance().saveString("yearly_saless", y);
                                    SharePreferenceUtils.getInstance().saveString("income_in_itr", i);
                                    SharePreferenceUtils.getInstance().saveString("oldest_irt_date", o);
                                    SharePreferenceUtils.getInstance().saveString("current_account", current_account.getSelectedItem().toString());
                                    SharePreferenceUtils.getInstance().saveString("loan_details", l);
                                    SharePreferenceUtils.getInstance().saveString("loan_amount", loan_amount.getText().toString());
                                    SharePreferenceUtils.getInstance().saveString("message", message.getText().toString());

                                    pager.setCurrentItem(3);

                                } else {
                                    Toast.makeText(getContext(), "Invalid loan amount", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Invalid oldest itr date", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Invalid income tax return", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid yearly sales", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return view;
        }
    }


    public static class property extends Fragment {

        Spinner property_sale;
        Spinner property_mode;
        Spinner property_documents;
        Spinner how_to;
        Spinner property_type;
        Spinner property_category;
        EditText value;
        Spinner loan_amount_required;
        Button next;
        ProgressBar progress;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.property, container, false);

            property_sale = view.findViewById(R.id.property_sale);
            property_mode = view.findViewById(R.id.property_mode);
            property_documents = view.findViewById(R.id.property_documents);
            how_to = view.findViewById(R.id.how_to);
            property_type = view.findViewById(R.id.property_type);
            property_category = view.findViewById(R.id.property_category);
            value = view.findViewById(R.id.value);
            loan_amount_required = view.findViewById(R.id.loan_amount_required);
            next = view.findViewById(R.id.next);
            progress = view.findViewById(R.id.progress);


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getActivity().getApplicationContext();

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

                    Call<applyBean> call = cr.apply_home_self(
                            SharePreferenceUtils.getInstance().getString("userId"),
                            SharePreferenceUtils.getInstance().getString("mobile"),
                            SharePreferenceUtils.getInstance().getString("name"),
                            SharePreferenceUtils.getInstance().getString("dob"),
                            SharePreferenceUtils.getInstance().getString("email"),
                            SharePreferenceUtils.getInstance().getString("gender"),
                            SharePreferenceUtils.getInstance().getString("residence_type"),
                            SharePreferenceUtils.getInstance().getString("residence_address"),
                            SharePreferenceUtils.getInstance().getString("pan"),
                            SharePreferenceUtils.getInstance().getString("city"),
                            SharePreferenceUtils.getInstance().getString("company_type"),
                            SharePreferenceUtils.getInstance().getString("company_name"),
                            SharePreferenceUtils.getInstance().getString("registration_type"),
                            SharePreferenceUtils.getInstance().getString("office_type"),
                            SharePreferenceUtils.getInstance().getString("office_address"),
                            SharePreferenceUtils.getInstance().getString("registration_age"),
                            SharePreferenceUtils.getInstance().getString("yearly_saless"),
                            SharePreferenceUtils.getInstance().getString("income_in_itr"),
                            SharePreferenceUtils.getInstance().getString("oldest_irt_date"),
                            SharePreferenceUtils.getInstance().getString("current_account"),
                            SharePreferenceUtils.getInstance().getString("loan_details"),
                            SharePreferenceUtils.getInstance().getString("loan_amount"),
                            SharePreferenceUtils.getInstance().getString("message"),
                            property_sale.getSelectedItem().toString(),
                            property_mode.getSelectedItem().toString(),
                            property_documents.getSelectedItem().toString(),
                            how_to.getSelectedItem().toString(),
                            property_type.getSelectedItem().toString(),
                            property_category.getSelectedItem().toString(),
                            value.getText().toString(),
                            loan_amount_required.getSelectedItem().toString()
                    );

                    call.enqueue(new Callback<applyBean>() {
                        @Override
                        public void onResponse(Call<applyBean> call, Response<applyBean> response) {

                            if (response.body().getStatus().equals("1")) {

                                //SharePreferenceUtils.getInstance().deletePref();

                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<applyBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }
            });

            return view;
        }
    }

}