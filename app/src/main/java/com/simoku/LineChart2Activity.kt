package com.simoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.simoku.databinding.ActivityLineChart2Binding
import com.simoku.databinding.ActivityLineChartBinding
import com.simoku.model.DataModel
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LineChart2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLineChart2Binding
    var apiService: ApiService?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_line_chart2)

        supportActionBar?.title = "Trendline Chart 2"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)
        listDataValues()
    }


    private fun listDataValues(){
        val listEntryFixCO = mutableListOf<Entry>()
        val listEntryFixNO2 = mutableListOf<Entry>()
        val callSensor: Call<List<DataModel>> = apiService!!.getLine2()
        callSensor.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                for(i in response.body()!!.indices){
                    listEntryFixCO.add(Entry(i.toFloat(), response.body()!![i].fixCO.toFloat()))
                    listEntryFixNO2.add(Entry(i.toFloat(), response.body()!![i].fixNO2.toFloat()))
                }
                setDataSensorFixCO(listEntryFixCO)
                setDataSensorFixNO(listEntryFixNO2)
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            }

        })
    }

    private fun setDataSensorFixNO(listEntry: List<Entry>) {
        val lineDataSet = LineDataSet(listEntry, "fixNO2")
        val dataSets = mutableListOf<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)
        binding.reportingChartFixNO.data = data

        binding.reportingChartFixNO.description.isEnabled = true
        val description = Description()
        description.text = "Data 10 terakhir"
        binding.reportingChartFixNO.description = description

        binding.reportingChartFixNO.invalidate()
    }

    private fun setDataSensorFixCO(listEntry: List<Entry>) {
        val lineDataSet = LineDataSet(listEntry, "fixCO")
        val dataSets = mutableListOf<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)
        binding.reportingChartFixCo.data = data

        binding.reportingChartFixCo.description.isEnabled = true
        val description = Description()
        description.text = "Data 10 terakhir"
        binding.reportingChartFixCo.description = description

        binding.reportingChartFixCo.invalidate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}