package com.simoku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.simoku.databinding.ActivityBarChartBinding
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import com.github.mikephil.charting.components.XAxis.XAxisPosition

import com.github.mikephil.charting.components.XAxis
import com.simoku.model.BarModel
import com.simoku.model.DataModel
import org.json.JSONException

import org.json.JSONObject

import java.util.ArrayList

import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.interfaces.datasets.IDataSet

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import com.github.mikephil.charting.data.BarData

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class BarChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarChartBinding
    var apiService: ApiService?= null
    val severityStringList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bar_chart)

        supportActionBar?.title = "Bar Chart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)

        initializeBarChart()
        onPostSeveritylist()


        initializeBarChart2()
        onPostSeveritylist2()

    }

    private fun onPostSeveritylist2() {
        val listBar = mutableListOf<BarModel>()
        val callSensor: Call<DataModel> = apiService!!.getBar2()
        callSensor.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                binding.tvDate2.text = "Tanggal ${DateFormat.format("dd MMMM yyyy HH:mm", response.body()!!.date)}"
                listBar.add(BarModel("fixCO", response.body()!!.fixCO))
                listBar.add(BarModel("fixNO2", response.body()!!.fixNO2))
                listBar.add(BarModel("fixSO2", response.body()!!.fixSO2))
                listBar.add(BarModel("fixO3", response.body()!!.fixO3))
                listBar.add(BarModel("fixPM10", response.body()!!.fixPM10))

                createBarChart2(listBar)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })
    }

    private fun createBarChart2(listBar: List<BarModel>) {
        val values = mutableListOf<BarEntry>()
        val listColor = mutableListOf<Int>()
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)

        for (i in listBar.indices) {
            val dataObject: BarModel = listBar[i]
            values.add(BarEntry(i.toFloat(), dataObject.value.toFloat()))
        }
        var set1: BarDataSet

        with(binding) {
            if (severityBarChart2.getData() != null &&
                severityBarChart2.getData().getDataSetCount() > 0
            ) {
                set1 = severityBarChart.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                severityBarChart2.getData().notifyDataChanged()
                severityBarChart2.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "Data Set")
                set1.setColors(listColor)
                set1.setDrawValues(true)
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set1)
                val data = BarData(dataSets)
                severityBarChart2.setData(data)
                severityBarChart2.setVisibleXRange(1f, 5f)
                severityBarChart2.setFitBars(true)
                val xAxis: XAxis = severityBarChart.getXAxis()
                xAxis.granularity = 1f
                xAxis.isGranularityEnabled = true
                xAxis.valueFormatter =
                    IndexAxisValueFormatter(severityStringList) //setting String values in Xaxis
                for (set in severityBarChart.getData()
                    .getDataSets()) set.setDrawValues(!set.isDrawValuesEnabled)
                severityBarChart2.invalidate()
            }
        }
    }

    private fun initializeBarChart2() {
        with(binding) {
            severityBarChart2.getDescription().setEnabled(false)

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            severityBarChart2.setMaxVisibleValueCount(5)
            severityBarChart2.getXAxis().setDrawGridLines(false)
            // scaling can now only be done on x- and y-axis separately
            severityBarChart2.setPinchZoom(false)
            severityBarChart2.setDrawBarShadow(false)
            severityBarChart2.setDrawGridBackground(false)
            val xAxis: XAxis = severityBarChart2.getXAxis()
            xAxis.setDrawGridLines(false)
            severityBarChart2.getAxisLeft().setDrawGridLines(false)
            severityBarChart2.getAxisRight().setDrawGridLines(false)
            severityBarChart2.getAxisRight().setEnabled(false)
            severityBarChart2.getAxisLeft().setEnabled(true)
            severityBarChart2.getXAxis().setDrawGridLines(false)
            // add a nice and smooth animation
            severityBarChart2.animateY(1500)
            severityBarChart2.getLegend().setEnabled(false)
            severityBarChart2.getAxisRight().setDrawLabels(false)
            severityBarChart2.getAxisLeft().setDrawLabels(true)
            severityBarChart2.setTouchEnabled(false)
            severityBarChart2.setDoubleTapToZoomEnabled(false)
            severityBarChart2.getXAxis().setEnabled(true)
            severityBarChart2.getXAxis().setPosition(XAxisPosition.BOTTOM)
            severityBarChart2.invalidate()
        }
    }

    fun onPostSeveritylist() {
        //this method will be running on UI thread
        val listBar = mutableListOf<BarModel>()
        val callSensor: Call<DataModel> = apiService!!.getBar()
        callSensor.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                binding.tvDate.text = "Tanggal ${DateFormat.format("dd MMMM yyyy HH:mm", response.body()!!.date)}"
                listBar.add(BarModel("fixCO", response.body()!!.fixCO))
                listBar.add(BarModel("fixNO2", response.body()!!.fixNO2))
                listBar.add(BarModel("fixSO2", response.body()!!.fixSO2))
                listBar.add(BarModel("fixO3", response.body()!!.fixO3))
                listBar.add(BarModel("fixPM10", response.body()!!.fixPM10))

                severityStringList.add("fixCO")
                severityStringList.add("fixNO2")
                severityStringList.add("fixSO2")
                severityStringList.add("fixO3")
                severityStringList.add("fixPM10")

                createBarChart(listBar)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })

    }

    private fun createBarChart(listBar: List<BarModel>) {
        val values = mutableListOf<BarEntry>()
        val listColor = mutableListOf<Int>()
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)
        listColor.add(R.color.black)

        for (i in listBar.indices) {
            val dataObject: BarModel = listBar[i]
            values.add(BarEntry(i.toFloat(), dataObject.value.toFloat()))
        }
        var set1: BarDataSet

        with(binding) {
            if (severityBarChart.getData() != null &&
                severityBarChart.getData().getDataSetCount() > 0
            ) {
                set1 = severityBarChart.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                severityBarChart.getData().notifyDataChanged()
                severityBarChart.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "Data Set")
                set1.setColors(listColor)
                set1.setDrawValues(true)
                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set1)
                val data = BarData(dataSets)
                severityBarChart.setData(data)
                severityBarChart.setVisibleXRange(1f, 5f)
                severityBarChart.setFitBars(true)
                val xAxis: XAxis = severityBarChart.getXAxis()
                xAxis.granularity = 1f
                xAxis.isGranularityEnabled = true
                xAxis.valueFormatter =
                    IndexAxisValueFormatter(severityStringList) //setting String values in Xaxis
                for (set in severityBarChart.getData()
                    .getDataSets()) set.setDrawValues(!set.isDrawValuesEnabled)
                severityBarChart.invalidate()
            }
        }

    }

    private fun initializeBarChart() {
        with(binding) {
            severityBarChart.getDescription().setEnabled(false)

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            severityBarChart.setMaxVisibleValueCount(5)
            severityBarChart.getXAxis().setDrawGridLines(false)
            // scaling can now only be done on x- and y-axis separately
            severityBarChart.setPinchZoom(false)
            severityBarChart.setDrawBarShadow(false)
            severityBarChart.setDrawGridBackground(false)
            val xAxis: XAxis = severityBarChart.getXAxis()
            xAxis.setDrawGridLines(false)
            severityBarChart.getAxisLeft().setDrawGridLines(false)
            severityBarChart.getAxisRight().setDrawGridLines(false)
            severityBarChart.getAxisRight().setEnabled(false)
            severityBarChart.getAxisLeft().setEnabled(true)
            severityBarChart.getXAxis().setDrawGridLines(false)
            // add a nice and smooth animation
            severityBarChart.animateY(1500)
            severityBarChart.getLegend().setEnabled(false)
            severityBarChart.getAxisRight().setDrawLabels(false)
            severityBarChart.getAxisLeft().setDrawLabels(true)
            severityBarChart.setTouchEnabled(false)
            severityBarChart.setDoubleTapToZoomEnabled(false)
            severityBarChart.getXAxis().setEnabled(true)
            severityBarChart.getXAxis().setPosition(XAxisPosition.BOTTOM)
            severityBarChart.invalidate()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}