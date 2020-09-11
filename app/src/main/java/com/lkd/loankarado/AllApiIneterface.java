package com.lkd.loankarado;

import com.lkd.loankarado.loanPOJO.loanBean;
import com.lkd.loankarado.videoPOJO.videoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {

    @Multipart
    @POST("api/login.php")
    Call<loginBean> login(
            @Part("phone") String phone,
            @Part("token") String token,
            @Part("referrer") String referrer
    );

    @Multipart
    @POST("api/verify.php")
    Call<loginBean> verify(
            @Part("phone") String phone,
            @Part("otp") String otp
    );


    @Multipart
    @POST("api/apply_personal.php")
    Call<applyBean> apply_personal(
            @Part("userId") String userId,
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

    @Multipart
    @POST("api/apply_business.php")
    Call<applyBean> apply_business(
            @Part("userId") String userId,
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
            @Part("company_name") String company_name,
            @Part("registration_type") String registration_type,
            @Part("office_type") String office_type,
            @Part("office_address") String office_address,
            @Part("registration_age") String registration_age,
            @Part("yearly_sales") String yearly_sales,
            @Part("income_in_itr") String income_in_itr,
            @Part("oldest_irt_date") String oldest_irt_date,
            @Part("current_account") String current_account,
            @Part("loan_details") String loan_details,
            @Part("loan_anount") String loan_anount,
            @Part("message") String message
    );

    @Multipart
    @POST("api/apply_home_salaried.php")
    Call<applyBean> apply_home_salaried(
            @Part("userId") String userId,
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
            @Part("message") String message,
            @Part("property_sale") String property_sale,
            @Part("property_mode") String property_mode,
            @Part("property_documents") String property_documents,
            @Part("how_to") String how_to,
            @Part("property_type") String property_type,
            @Part("property_category") String property_category,
            @Part("value") String value,
            @Part("loan_amount_required") String loan_amount_required
    );

    @Multipart
    @POST("api/apply_property_salaried.php")
    Call<applyBean> apply_property_salaried(
            @Part("userId") String userId,
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
            @Part("message") String message,
            @Part("property_sale") String property_sale,
            @Part("property_mode") String property_mode,
            @Part("property_documents") String property_documents,
            @Part("how_to") String how_to,
            @Part("property_type") String property_type,
            @Part("property_category") String property_category,
            @Part("value") String value,
            @Part("loan_amount_required") String loan_amount_required
    );


    @Multipart
    @POST("api/apply_home_self.php")
    Call<applyBean> apply_home_self(
            @Part("userId") String userId,
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
            @Part("company_name") String company_name,
            @Part("registration_type") String registration_type,
            @Part("office_type") String office_type,
            @Part("office_address") String office_address,
            @Part("registration_age") String registration_age,
            @Part("yearly_sales") String yearly_sales,
            @Part("income_in_itr") String income_in_itr,
            @Part("oldest_irt_date") String oldest_irt_date,
            @Part("current_account") String current_account,
            @Part("loan_details") String loan_details,
            @Part("loan_anount") String loan_anount,
            @Part("message") String message,
            @Part("property_sale") String property_sale,
            @Part("property_mode") String property_mode,
            @Part("property_documents") String property_documents,
            @Part("how_to") String how_to,
            @Part("property_type") String property_type,
            @Part("property_category") String property_category,
            @Part("value") String value,
            @Part("loan_amount_required") String loan_amount_required
    );

    @Multipart
    @POST("api/apply_property_self.php")
    Call<applyBean> apply_property_self(
            @Part("userId") String userId,
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
            @Part("company_name") String company_name,
            @Part("registration_type") String registration_type,
            @Part("office_type") String office_type,
            @Part("office_address") String office_address,
            @Part("registration_age") String registration_age,
            @Part("yearly_sales") String yearly_sales,
            @Part("income_in_itr") String income_in_itr,
            @Part("oldest_irt_date") String oldest_irt_date,
            @Part("current_account") String current_account,
            @Part("loan_details") String loan_details,
            @Part("loan_anount") String loan_anount,
            @Part("message") String message,
            @Part("property_sale") String property_sale,
            @Part("property_mode") String property_mode,
            @Part("property_documents") String property_documents,
            @Part("how_to") String how_to,
            @Part("property_type") String property_type,
            @Part("property_category") String property_category,
            @Part("value") String value,
            @Part("loan_amount_required") String loan_amount_required
    );

    @GET("api/getBanner.php")
    Call<bannerBean> getBanner();

    @GET("api/getGallery.php")
    Call<bannerBean> getGallery();

    @GET("api/getVideos.php")
    Call<videoBean> getVideos();

    @Multipart
    @POST("api/getMyLoan.php")
    Call<loanBean> getMyLoan(
            @Part("user_id") String user_id
    );

}
