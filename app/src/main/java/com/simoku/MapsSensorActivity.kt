package com.simoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.simoku.adapter.TableAdapter
import com.simoku.databinding.ActivityMapsSensorBinding
import com.simoku.model.DataModel
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsSensorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsSensorBinding
    private val adapter: TableAdapter by lazy {
        TableAdapter()
    }
    private val adapter2: TableAdapter by lazy {
        TableAdapter()
    }

    var apiService: ApiService ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps_sensor)

        supportActionBar?.title = "Sensor"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)

        binding.rvSensor.layoutManager = LinearLayoutManager(this)
        binding.rvSensor.adapter = adapter

        binding.rvSensor2.layoutManager = LinearLayoutManager(this)
        binding.rvSensor2.adapter = adapter2

        getDataSensor()
        getDataSensor2()
    }

    private fun getDataSensor2() {
        val callSensor: Call<List<DataModel>> = apiService!!.getSensor2()
        callSensor.enqueue(object : Callback<List<DataModel>>{
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                adapter2.setList(response.body()!!)
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            }

        })
    }

    private fun getDataSensor() {
        val callSensor: Call<List<DataModel>> = apiService!!.getSensor()
        callSensor.enqueue(object : Callback<List<DataModel>>{
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                adapter.setList(response.body()!!)
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}