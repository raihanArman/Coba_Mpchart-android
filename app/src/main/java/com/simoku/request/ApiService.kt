package com.simoku.request

import com.simoku.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("tampil_sensor.php")
    fun getSensor(): Call<List<DataModel>>
    @GET("tampil_sensor_2.php")
    fun getSensor2(): Call<List<DataModel>>

    @GET("tampil_bar.php")
    fun getBar(): Call<DataModel>
    @GET("tampil_bar_2.php")
    fun getBar2(): Call<DataModel>

    @GET("tampil_line.php")
    fun getLine(): Call<List<DataModel>>
    @GET("tampil_line_2.php")
    fun getLine2(): Call<List<DataModel>>
}