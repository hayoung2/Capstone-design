package com.example.testapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)


        val mapView = MapView(this)
        val lat=LatLng(intent.getStringExtra("yPos")!!.toDouble(),intent.getStringExtra("xPos")!!.toDouble())
            try {
                val uLatitude = lat!!.latitude
                Log.d("여기다 여기가 안된다", lat.toString())
                val uLongitude = lat!!.longitude
                val marker=MapPOIItem()
                marker.itemName="나와시발"
                marker.selectedMarkerType=MapPOIItem.MarkerType.YellowPin
                marker.mapPoint=MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                marker.markerType=MapPOIItem.MarkerType.RedPin
                //Log.d("값을 가져와라 제발", uLatitude.toString()+"   "+uLongitude.toString())
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude), true);
                mapView.addPOIItem(marker)
                Log.d("들어가니? ", "들어감")
            } catch (e: NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())
                Log.d("값 가져와ㅏ라 ", "에러남")
            }

    val mapViewContainer = findViewById(R.id.map_view) as ViewGroup
    mapViewContainer.addView(mapView)

    }
}