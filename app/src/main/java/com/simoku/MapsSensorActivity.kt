package com.simoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import android.view.View
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
import java.util.*

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

        val date = Calendar.getInstance()
        val dateNow = DateFormat.format("dd MMMM yyyy", date).toString()
        binding.tvTanggal1.text = dateNow
        binding.tvTanggal2.text = dateNow
        getDataSensor()
        getDataSensor2()
    }

    private fun getDataSensor2() {
        val date = Calendar.getInstance()
        val dateNow = DateFormat.format("yyyy-MM-dd", date).toString()
        val callSensor: Call<List<DataModel>> = apiService!!.getSensor2(dateNow)
        callSensor.enqueue(object : Callback<List<DataModel>>{
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                if(response.body() != null && response.body()!!.isNotEmpty()) {
                    adapter2.setList(response.body()!!)
                    binding.tvKosongSensor2.visibility = View.INVISIBLE
                }else{
                    binding.tvKosongSensor2.visibility = View.VISIBLE
                    binding.rvSensor2.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            }

        })
    }

    private fun getDataSensor() {
        val date = Calendar.getInstance()
        val dateNow = DateFormat.format("yyyy-MM-dd", date).toString()
        val callSensor: Call<List<DataModel>> = apiService!!.getSensor(dateNow)
        callSensor.enqueue(object : Callback<List<DataModel>>{
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                if(response.body() != null && response.body()!!.isNotEmpty()) {
                    adapter.setList(response.body()!!)
                    binding.tvKosongSensor.visibility = View.INVISIBLE
                }else{
                    binding.tvKosongSensor.visibility = View.VISIBLE
                    binding.rvSensor.visibility = View.INVISIBLE
                }
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