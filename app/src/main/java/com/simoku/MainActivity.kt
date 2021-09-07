package com.simoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.simoku.databinding.ActivityMainBinding
import com.simoku.model.BarModel
import com.simoku.model.DataModel
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var apiService: ApiService?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnSensor.setOnClickListener {
            val intent = Intent(this, MapsSensorActivity::class.java)
            startActivity(intent)
        }
        binding.btnBar.setOnClickListener {
            val intent = Intent(this, BarChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnLine.setOnClickListener {
            val intent = Intent(this, LineChartActivity::class.java)
            startActivity(intent)
        }

        binding.btnLine2.setOnClickListener {
            val intent = Intent(this, LineChart2Activity::class.java)
            startActivity(intent)
        }

        binding.btnMaps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        showSensor1()
        showSensor2()

    }

    private fun showSensor2() {val callSensor: Call<DataModel> = apiService!!.getBar2()
        callSensor.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                val dataModel: DataModel = response.body()!!
                setFeedback(dataModel.fixCO.toDouble(), binding.ivFixco2, binding.tvFixco2)
                setFeedback(dataModel.fixNO2.toDouble(), binding.fixno2, binding.tvFixno2)
                setFeedback(dataModel.fixSO2.toDouble(), binding.fixso2, binding.tvFixso2)
                setFeedback(dataModel.fixO3.toDouble(), binding.fixo2, binding.tvFixo2)
                setFeedback(dataModel.fixPM10.toDouble(), binding.fixpm2, binding.tvFixpm2)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })
    }

    private fun showSensor1() {
        val callSensor: Call<DataModel> = apiService!!.getBar()
        callSensor.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                val dataModel: DataModel = response.body()!!
                setFeedback(dataModel.fixCO.toDouble(), binding.ivFixco, binding.tvFixco)
                setFeedback(dataModel.fixNO2.toDouble(), binding.fixno,binding.tvFixno)
                setFeedback(dataModel.fixSO2.toDouble(), binding.fixso, binding.tvFixso)
                setFeedback(dataModel.fixO3.toDouble(), binding.fixo, binding.tvFixo)
                setFeedback(dataModel.fixPM10.toDouble(), binding.fixpm, binding.tvFixpm)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })
    }

    fun setFeedback(value: Double, imageView: ImageView, textView: TextView){
        if(value >= 300){
            imageView.setImageResource(R.drawable.simo_bad)
            textView.text = "Berbahaya"
        }else if(value in 200.0..299.0){
            imageView.setImageResource(R.drawable.simo_poor)
            textView.text = "Sangat tidak sehat"
        }else if(value in 101.0..199.0){
            imageView.setImageResource(R.drawable.simo_avarege)
            textView.text = "Tidak sehat"
        }else if(value in 51.0..100.0){
            imageView.setImageResource(R.drawable.simo_good)
            textView.text = "Sedang"
        }else if(value in -200.0..50.00){
            imageView.setImageResource(R.drawable.simo_happy)
            textView.text = "Baik"
        }
    }

}