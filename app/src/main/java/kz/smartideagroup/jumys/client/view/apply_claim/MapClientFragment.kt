package kz.smartideagroup.jumys.client.view.apply_claim

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationRequest
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.fragment_map_client.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.common.utils.GpsUtils
import kz.smartideagroup.jumys.common.utils.REQUEST_LOCATION_CODE
import kz.smartideagroup.jumys.common.views.BaseFragment

class MapClientFragment : BaseFragment(R.layout.fragment_map_client) {

    companion object {
        const val LATITUDE = 42.330639
        const val LONGITUDE = 69.600967
        const val ZOOM = 13.0f
        const val AZIMUTH = 0.0f
        const val TILT = 0.0f
        const val DURATION = 0F
    }

    private lateinit var locationRequest: LocationRequest
    private var mapKit: MapKit? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initMap()
        initViewModel()
        initPermissions()
        initListeners()
        initObservers()
    }

    private fun initPermissions() {
        val grant = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (grant != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permissions))
                    .setMessage(getString(R.string.give_permissions_for_geolocation))
                    .setPositiveButton(R.string.ok) { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_LOCATION_CODE
                        )
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_CODE
                )
            }
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        locationRequest = LocationRequest.create()

        GpsUtils(requireContext(), locationRequest).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                // turn on GPS
                if(isGPSEnable){
                    if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return
                    }
                }
            }
        })

        val userLoc = mapKit!!.createUserLocationLayer(map_view.mapWindow)
        userLoc.isVisible = true

    }

    private fun initMap() {
        mapKit = MapKitFactory.getInstance()

        map_view.map.move(
            CameraPosition(Point(LATITUDE, LONGITUDE), ZOOM, AZIMUTH, TILT),
            com.yandex.mapkit.Animation(com.yandex.mapkit.Animation.Type.SMOOTH, DURATION),
            null
        )
    }

    private fun initViewModel() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

    override fun onStop() {
        super.onStop()
        map_view.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        map_view.onStart()
        MapKitFactory.getInstance().onStart()
    }


}