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
import com.simoku.databinding.ActivityLineChartBinding
import com.simoku.model.DataModel
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LineChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLineChartBinding
    var apiService: ApiService?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_line_chart)

        supportActionBar?.title = "Trendline Chart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)

        listDataValues()
        listDataValues2()
    }

    private fun listDataValues2() {
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
                setDataSensorFixCO2(listEntryFixCO)
                setDataSensorFixNO2(listEntryFixNO2)
            }

            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            }

        })
    }

    private fun setDataSensorFixNO2(listEntry: List<Entry>) {
        val lineDataSet = LineDataSet(listEntry, "fixNO2")
        val dataSets = mutableListOf<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)
        binding.reportingChartFixNO2.data = data

        binding.reportingChartFixNO2.description.isEnabled = true
        val description = Description()
        description.text = "Data 10 terakhir"
        binding.reportingChartFixNO2.description = description

        binding.reportingChartFixNO2.invalidate()
    }

    private fun setDataSensorFixCO2(listEntry: List<Entry>) {
        val lineDataSet = LineDataSet(listEntry, "fixCO")
        val dataSets = mutableListOf<ILineDataSet>()
        dataSets.add(lineDataSet)

        val data = LineData(dataSets)
        binding.reportingChartFixCo2.data = data

        binding.reportingChartFixCo2.description.isEnabled = true
        val description = Description()
        description.text = "Data 10 terakhir"
        binding.reportingChartFixCo2.description = description

        binding.reportingChartFixCo2.invalidate()
    }

    private fun listDataValues(){
        val listEntryFixCO = mutableListOf<Entry>()
        val listEntryFixNO2 = mutableListOf<Entry>()
        val callSensor: Call<List<DataModel>> = apiService!!.getLine()
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