package ru.agafonovilya.mapapp.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.agafonovilya.mapapp.App
import ru.agafonovilya.mapapp.R
import ru.agafonovilya.mapapp.databinding.ActivityMainBinding
import ru.agafonovilya.mapapp.model.markersDataBase.MyMarker
import ru.agafonovilya.mapapp.viewModel.MarkerViewModel
import ru.agafonovilya.mapapp.viewModel.MainViewModelFactory

private const val PERMISSION_REQUEST_CODE = 10

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MarkerViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMapFragment()
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribeToLiveData().observe(this) { onLiveDataChanged(it) }
    }

    private fun onLiveDataChanged(allMarkers: List<MyMarker>) {
        allMarkers.forEach {
            mMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).flat(true))
        }
    }

    private fun initMapFragment() {
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

        setMapSettings()
        setClickListeners()
    }

    private fun setMapSettings() {
        if (checkPermission()) return

        mMap.isMyLocationEnabled = true
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isCompassEnabled = true
        uiSettings.isMyLocationButtonEnabled = true
    }

    private fun setClickListeners() {
        mMap.setOnMapLongClickListener { latLng ->
            val marker = mMap.addMarker(MarkerOptions().position(latLng).flat(true))
            marker?.let { viewModel.insertMarker(it) }
        }

        binding.buttonToMarkers.setOnClickListener {
            startActivity(Intent(this, MarkerListActivity::class.java))
        }
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
            return true
        }
        return false
    }

    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            || !ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)
                ) {
                    setMapSettings()
                }
            }
        }
    }


}