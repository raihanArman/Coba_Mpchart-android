package com.simoku

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.MenuItem
import androidx.core.content.ContextCompat
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
import com.github.mikephil.charting.utils.ColorTemplate
import android.graphics.Color

import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.components.Legend
import android.R.attr.entries
import com.github.mikephil.charting.components.Description


class BarChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarChartBinding
    var apiService: ApiService?= null
    val severityStringList = mutableListOf<String>()
    val severityStringList2 = mutableListOf<String>()

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
        val colors = mutableListOf<Int>()
        colors.add(ContextCompat.getColor(this, R.color.purple_500));
        colors.add(ContextCompat.getColor(this, R.color.teal_700));
        colors.add(ContextCompat.getColor(this, R.color.teal_200));
        colors.add(ContextCompat.getColor(this, R.color.green));
        colors.add(ContextCompat.getColor(this, R.color.yellow));

        for (i in listBar.indices) {
            val dataObject: BarModel = listBar[i]
            values.add(BarEntry(i.toFloat(), dataObject.value.toFloat()))
        }
        var set1: BarDataSet

        with(binding) {
            if (severityBarChart2.getData() != null &&
                severityBarChart2.getData().getDataSetCount() > 0
            ) {
                set1 = severityBarChart2.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                severityBarChart2.getData().notifyDataChanged()
                severityBarChart2.notifyDataSetChanged()
            } else {
                val set = BarDataSet(values, "")
                set.setColors(colors)
                set.valueTextColor = Color.rgb(155, 155, 155)
                val data = BarData(set)

                data.barWidth = 0.9f // set custom bar width

                binding.severityBarChart2.setData(data)

                binding.severityBarChart2.setFitBars(true) // make the x-axis fit exactly all bars

                binding.severityBarChart2.invalidate() // refresh
            }
        }
    }

    private fun initializeBarChart2() {
        severityStringList2.add("fixCO")
        severityStringList2.add("fixNO2")
        severityStringList2.add("fixSO2")
        severityStringList2.add("fixO3")
        severityStringList2.add("fixPM10")

        with(binding) {
            val desc: Description
            val L: Legend

            L = severityBarChart2.getLegend()
            desc = severityBarChart2.getDescription()
            desc.setText("") // this is the weirdest way to clear something!!

            L.isEnabled = false


            val leftAxis: YAxis = severityBarChart2.getAxisLeft()
            val rightAxis: YAxis = severityBarChart2.getAxisRight()
            val xAxis: XAxis = severityBarChart2.getXAxis()

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.textSize = 10f
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(true)


            leftAxis.textSize = 10f
            leftAxis.setDrawLabels(true)
            leftAxis.setDrawAxisLine(true)
            leftAxis.setDrawGridLines(true)

            rightAxis.setDrawAxisLine(true)
            rightAxis.setDrawGridLines(true)
            rightAxis.setDrawLabels(true)

            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            xAxis.valueFormatter =
                IndexAxisValueFormatter(severityStringList)

            severityBarChart2.setScaleEnabled(false)
            severityBarChart2.setDoubleTapToZoomEnabled(false)
            severityBarChart2.setBackgroundColor(Color.rgb(255, 255, 255))
            severityBarChart2.animateXY(2000, 2000)
            severityBarChart2.setDrawBorders(false)
            severityBarChart2.setDescription(desc)
            severityBarChart2.setDrawValueAboveBar(true)
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

                createBarChart(listBar)
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })

    }

    private fun createBarChart(listBar: List<BarModel>) {
        val values = mutableListOf<BarEntry>()
        val colors = mutableListOf<Int>()
        colors.add(ContextCompat.getColor(this, R.color.purple_500));
        colors.add(ContextCompat.getColor(this, R.color.teal_700));
        colors.add(ContextCompat.getColor(this, R.color.teal_200));
        colors.add(ContextCompat.getColor(this, R.color.green));
        colors.add(ContextCompat.getColor(this, R.color.yellow));

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
                val set = BarDataSet(values, "")
                set.setColors(colors)
                set.valueTextColor = Color.rgb(155, 155, 155)
                val data = BarData(set)

                data.barWidth = 0.9f // set custom bar width

                binding.severityBarChart.setData(data)

                binding.severityBarChart.setFitBars(true) // make the x-axis fit exactly all bars

                binding.severityBarChart.invalidate() // refresh
            }
        }

    }

    private fun initializeBarChart() {
        severityStringList.add("fixCO")
        severityStringList.add("fixNO2")
        severityStringList.add("fixSO2")
        severityStringList.add("fixO3")
        severityStringList.add("fixPM10")

        with(binding) {
            val desc: Description
            val L: Legend

            L = severityBarChart.getLegend()
            desc = severityBarChart.getDescription()
            desc.setText("") // this is the weirdest way to clear something!!

            L.isEnabled = false


            val leftAxis: YAxis = severityBarChart.getAxisLeft()
            val rightAxis: YAxis = severityBarChart.getAxisRight()
            val xAxis: XAxis = severityBarChart.getXAxis()

            xAxis.position = XAxisPosition.BOTTOM
            xAxis.textSize = 10f
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(true)


            leftAxis.textSize = 10f
            leftAxis.setDrawLabels(true)
            leftAxis.setDrawAxisLine(true)
            leftAxis.setDrawGridLines(true)

            rightAxis.setDrawAxisLine(true)
            rightAxis.setDrawGridLines(true)
            rightAxis.setDrawLabels(true)

            xAxis.granularity = 1f
            xAxis.isGranularityEnabled = true
            xAxis.valueFormatter =
                IndexAxisValueFormatter(severityStringList)

            severityBarChart.setScaleEnabled(false)
            severityBarChart.setDoubleTapToZoomEnabled(false)
            severityBarChart.setBackgroundColor(Color.rgb(255, 255, 255))
            severityBarChart.animateXY(2000, 2000)
            severityBarChart.setDrawBorders(false)
            severityBarChart.setDescription(desc)
            severityBarChart.setDrawValueAboveBar(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}