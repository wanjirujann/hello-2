package com.example.hello

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //@Multipart
    //@FormUrlEncoded
    @POST("register")
    fun registerStudent(@Body requestBody: RequestBody): Call<RegistrationResponse>

    @POST("login")
    fun loginStudent(@Body requestBody: RequestBody): Call<LoginResponse>

/*
    @FormUrlEncoded
    @POST("register")
    fun registerStudent(
        @Field("first_name") fistName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String
    ): Call<RegistrationResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginStudent(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

 */

}