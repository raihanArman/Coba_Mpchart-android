package com.simoku

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.simoku.databinding.ActivityMapsBinding
import com.simoku.model.BarModel
import com.simoku.model.DataModel
import com.simoku.request.ApiService
import com.simoku.request.RetrofitRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var apiService: ApiService?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitRequest.getInstance()?.create(ApiService::class.java)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Maps"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val lokasi1 = LatLng(0.5322860861203229, 101.45307065654427)
        val lokasi2 = LatLng(0.5429741596812956, 101.43087230281546)


        val builder = LatLngBounds.Builder()
        builder.include(LatLng(lokasi1.latitude, lokasi1.longitude))
        builder.include(LatLng(lokasi2.latitude, lokasi2.longitude))
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (Math.min(width, height) * 0.2).toInt()
        val bounds = builder.build()

        val callSensor: Call<DataModel> = apiService!!.getBar()
        callSensor.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(lokasi1)
                        .title("UPTD PUSKESMAS PEKANBARU KOTA")
                        .snippet("fixCO : ${response.body()?.fixCO}\n" +
                                "fixNO2 : ${response.body()?.fixNO2}\n"+
                                "fixNO2 : ${response.body()?.fixNO2}\n"+
                                "fixSO2 : ${response.body()?.fixSO2}\n"+
                                "fixO3 : ${response.body()?.fixO3}\n"+
                                "fixPM10 : ${response.body()?.fixPM10}\n"))
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })
        val callSensor2: Call<DataModel> = apiService!!.getBar2()
        callSensor2.enqueue(object : Callback<DataModel> {
            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(lokasi2)
                        .title("SMAN 7 PEKANBARU")
                        .snippet("fixCO : ${response.body()?.fixCO}\n" +
                                "fixNO2 : ${response.body()?.fixNO2}\n"+
                                "fixNO2 : ${response.body()?.fixNO2}\n"+
                                "fixSO2 : ${response.body()?.fixSO2}\n"+
                                "fixO3 : ${response.body()?.fixO3}\n"+
                                "fixPM10 : ${response.body()?.fixPM10}\n"))
            }

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
            }

        })

        mMap?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val info = LinearLayout(applicationContext)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(applicationContext)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(applicationContext)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        mMap.moveCamera(cu)
        mMap.animateCamera(cu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}