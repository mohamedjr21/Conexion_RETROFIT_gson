package com.example.conexion_retrofit_gson;

import retrofit2.http.GET;

import retrofit2.Call;
import retrofit2.http.Query;




public interface APIservicio {
    @GET("weather")
    Call<Tiempo> obtenerTiempo(

            @Query("q") String ciudad,
            @Query("appid") String apiKey,
            @Query("units") String unidades
    );
}