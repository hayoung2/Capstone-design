package com.example.testapp.fragments

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapp.MainActivity
import com.example.testapp.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class SearchFragment : Fragment() {
    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_search, container, false)

        val mapView = MapView(context)

        view.findViewById<Button>(R.id.map_page_location_btn).setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                context as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                val mgr = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?



                try {
                    val userNowLocation: Location? = mgr?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    val uLatitude = userNowLocation!!.latitude
                    Log.d("여기다 여기가 안된다", userNowLocation.toString())
                    val uLongitude = userNowLocation!!.longitude
                    val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                    //Log.d("값을 가져와라 제발", uLatitude.toString()+"   "+uLongitude.toString())
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude), true);
                    Log.d("들어가니? ", "들어감")
                } catch (e: NullPointerException) {
                    Log.e("LOCATION_ERROR", e.toString())
                    Log.d("값 가져와ㅏ라 ", "에러남")
                }
            } else {
                Toast.makeText(context, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()

            }
        }

        val mapViewContainer = view.findViewById(R.id.map_view) as ViewGroup
        mapViewContainer.addView(mapView)

        return view

    }





}