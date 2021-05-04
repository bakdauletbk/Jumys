package kz.smartideagroup.jumys.client.view.apply_claim

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationRequest
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.android.synthetic.main.fragment_map_client.*
import kz.smartideagroup.jumys.R
import kz.smartideagroup.jumys.client.model.response.apply_claim.AddressOrderResponse
import kz.smartideagroup.jumys.client.view.apply_claim.bottom_sheet.OrderBottomSheet
import kz.smartideagroup.jumys.client.viewmodel.apply_claim.MapClientViewModel
import kz.smartideagroup.jumys.common.utils.GpsUtils
import kz.smartideagroup.jumys.common.utils.REQUEST_LOCATION_CODE
import kz.smartideagroup.jumys.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick


class MapClientFragment : BaseFragment(R.layout.fragment_map_client), MapObjectTapListener {

    companion object {
        const val LATITUDE = 42.330639
        const val LONGITUDE = 69.600967
        const val ZOOM = 13.0f
        const val AZIMUTH = 0.0f
        const val TILT = 0.0f
        const val DURATION = 0F
    }

    private lateinit var viewModel: MapClientViewModel

    private lateinit var locationRequest: LocationRequest
    private var mapKit: MapKit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateTo(R.id.homeClientFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initMap()
        initViewModel()
        getOrderAddress()
        initPermissions()
        initListeners()
        initObservers()
    }


    private fun getOrderAddress() {
        viewModel.getAddressOrderList()
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
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
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
        viewModel = ViewModelProvider(this)[MapClientViewModel::class.java]
    }

    private fun initListeners() {
        ll_back_pressed.onClick {
            navigateTo(R.id.homeClientFragment)
        }

        btn_list_order.onClick {
            navigateTo(R.id.orderListFragment)
        }
    }

    private fun initObservers() {
        viewModel.addressOrderList.observe(viewLifecycleOwner, {
            setOrderAddress(it)
        })
    }

    @SuppressLint("UseCompatLoadingForColorStateLists", "UseCompatLoadingForDrawables")
    private fun setOrderAddress(it: List<AddressOrderResponse>?) {
        for (i in it!!.indices) {

            val textView = TextView(requireContext())
            val price = it[i].price!!.toLong()
            textView.text = price.toString()
            textView.textSize = resources.getDimension(R.dimen.dimens_6sp)
            textView.setTextColor(resources.getColorStateList(R.color.white))
            textView.setPadding(10)
            textView.background = resources.getDrawable(R.drawable.shape_text_view_map)

            val pointCollection: MapObjectCollection = map_view.map.mapObjects.addCollection()

            val placeMark: PlacemarkMapObject = pointCollection.addPlacemark(
                Point(
                    it[i].latitude!!.toDouble(), it[i].longitude!!.toDouble()
                ),
                ViewProvider(textView)
            )

            placeMark.userData = it[i]

            placeMark.addTapListener(this)
        }
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

    override fun onMapObjectTap(mapObject: MapObject, p1: Point): Boolean {
        val orderData: AddressOrderResponse = mapObject.userData as AddressOrderResponse
        val bottomSheetDialogFragment = OrderBottomSheet.newInstance()
        bottomSheetDialogFragment.setOrder(orderData)
        bottomSheetDialogFragment.show(
            requireActivity().supportFragmentManager,
            "Bottom Sheet Dialog"
        )
        return true
    }


}