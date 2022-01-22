package com.tsu.mobilegamelab4.googlemap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.chooselevel.ChooseLevelViewModel
import com.tsu.mobilegamelab4.databinding.ActivityGoogleMapBinding
import com.tsu.mobilegamelab4.game.GameActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<GoogleMapViewModel>()
    private lateinit var binding: ActivityGoogleMapBinding
    private lateinit var googleMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapGoogleMap) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.bonusButton.isEnabled = false
        binding.bonusButton.visibility = View.GONE

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.bonusButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra(ChooseLevelViewModel.LEVEL_KEY, -1)
            startActivity(intent)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        configureGoogleMap()
        initializeMyLocation()
    }

    private fun configureGoogleMap() {
        googleMap.addMarker(
            MarkerOptions()
                .position(GoogleMapViewModel.TSU_LAT_LNG)
                .title(resources.getString(R.string.marker_is_tsu))
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(GoogleMapViewModel.TSU_LAT_LNG))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                GoogleMapViewModel.TSU_LAT_LNG,
                GoogleMapViewModel.ZOOM
            )
        )
        googleMap.addCircle(
            CircleOptions()
                .center(GoogleMapViewModel.TSU_LAT_LNG)
                .radius(GoogleMapViewModel.AREA_RADIUS)
                .strokeColor(Color.RED)
                .fillColor(ContextCompat.getColor(this, R.color.colorRedTranslucent))
        )
    }

    private fun initializeMyLocation() {
        showMyLocation(true)

        lifecycleScope.launch {
            while (true) {
                delay(GoogleMapViewModel.LOCATION_CHECK_INTERVAL)
                if (!(ActivityCompat.checkSelfPermission(
                        this@GoogleMapActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this@GoogleMapActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                            )
                ) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            location?.let {
                                viewModel.isUserInArea(
                                    it,
                                    onSuccess = {
                                        binding.bonusButton.visibility = View.VISIBLE
                                        binding.bonusButton.isEnabled = true
                                    },
                                    onFailure = {
                                        binding.bonusButton.visibility = View.GONE
                                        binding.bonusButton.isEnabled = false
                                    }
                                )
                            }
                        }
                }
            }
        }
    }

    private val showMyLocationHandler = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        Log.d("PERMISSIONS", "Launcher result: $granted");
        if (granted.containsValue(false)) {
            Log.d(
                "PERMISSIONS",
                "At least one of the permissions was not granted, launching again..."
            )
        } else {
            showMyLocation(false)
        }
    }

    private fun showMyLocation(haveToAskPermissions: Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (haveToAskPermissions) {
                askPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            return
        }
        googleMap.isMyLocationEnabled = true
    }

    private fun askPermissions(permissions: Array<String>) {
        if (!hasPermissions(permissions)) {
            Log.d(
                "PERMISSIONS",
                "Launching multiple contract permission launcher for ALL required permissions"
            )
            showMyLocationHandler.launch(permissions)
        } else {
            Log.d("PERMISSIONS", "All permissions are already granted")
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("PERMISSIONS", "Permission is not granted: $permission")
                return false
            }
            Log.d("PERMISSIONS", "Permission already granted: $permission")
        }
        return true
    }
}