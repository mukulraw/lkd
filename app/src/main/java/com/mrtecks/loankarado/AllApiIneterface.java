package com.mrtecks.loankarado;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {

    @Multipart
    @POST("api/apply_personal.php")
    Call<applyBean> apply_personal(
            @Part("mobile") String mobile,
            @Part("name") String name,
            @Part("dob") String dob,
            @Part("email") String email,
            @Part("gender") String gender,
            @Part("residence_type") String residence_type,
            @Part("residence_address") String residence_address,
            @Part("pan") String pan,
            @Part("city") String city,
            @Part("company_type") String company_type,
            @Part("employement_type") String employement_type,
            @Part("company_name") String company_name,
            @Part("company_address") String company_address,
            @Part("salary_mode") String salary_mode,
            @Part("monthly_salary") String monthly_salary,
            @Part("loan_details") String loan_details,
            @Part("loan_anount") String loan_anount,
            @Part("message") String message
    );
}
