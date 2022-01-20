package com.tsu.mobilegamelab4.googlemap

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tsu.mobilegamelab4.R
import com.tsu.mobilegamelab4.databinding.ActivityGoogleMapBinding

class GoogleMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityGoogleMapBinding
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0

        val tsu = LatLng(56.469483, 84.948689)
        p0?.addMarker(
            MarkerOptions()
                .position(tsu)
                .title("Marker in TSU")
        )
        p0?.moveCamera(CameraUpdateFactory.newLatLng(tsu))
        p0?.animateCamera(CameraUpdateFactory.newLatLngZoom(tsu, 17f))
        p0?.addCircle(
            CircleOptions()
                .center(tsu)
                .radius(60.0)
                .strokeColor(Color.RED)
                .fillColor(ContextCompat.getColor(this, R.color.colorRedTranslucent))
        )

        showMyLocation(true)
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        googleMap?.isMyLocationEnabled = true
//    }

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
        googleMap?.isMyLocationEnabled = true
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