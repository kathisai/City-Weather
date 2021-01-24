package com.prathap.weather.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.prathap.weather.R
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private lateinit var mGoogleMap: GoogleMap
    internal var mCurrLocationMarker: Marker? = null
    var mLastLocation: Location? = null
    lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSelectedLocation: LatLng? = null

    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                mLastLocation = location
//                if (mCurrLocationMarker != null) {
//                    mCurrLocationMarker?.remove()
//                }
//
//                //Place current location marker
                val latLng = LatLng(location.latitude, location.longitude)
//                mSelectedLocation = latLng
//                val markerOptions = MarkerOptions()
//                markerOptions.position(latLng)
//                markerOptions.draggable(true)
//                markerOptions.title("Drag marker to add City")
//                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions)

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.0F))
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13f))
                // Enable the zoom controls for the map
                mGoogleMap.uiSettings.isZoomControlsEnabled = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.helpFragment).isVisible = false
        super.onPrepareOptionsMenu(menu)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissionsForApp()
        mFusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }
        btnConfirm.setOnClickListener {
            mSelectedLocation?.let {
                val bundle = bundleOf("location" to it)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mGoogleMap = it

            mLocationRequest = LocationRequest()
            mLocationRequest.interval = 120000 // two minute interval
            mLocationRequest.fastestInterval = 120000
            mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            mFusedLocationClient?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
            mGoogleMap.isMyLocationEnabled = true

//            mGoogleMap.setOnMarkerDragListener(object : OnMarkerDragListener {
//                override fun onMarkerDragStart(marker: Marker) {}
//                override fun onMarkerDrag(marker: Marker) {}
//                override fun onMarkerDragEnd(marker: Marker) {
//                    val latLng = marker.position
//                    mSelectedLocation = marker.position
//                }
//            })
            mGoogleMap.setOnCameraIdleListener(this)
        }


    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            @NonNull permissions: Array<String>,
            @NonNull grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                loadMapFragment()
            } else {
                activity?.let { fragmentActivity ->
                    MaterialDialog(fragmentActivity)
                            .cancelOnTouchOutside(false)
                            .message(R.string.error_dialog_title)
                            .negativeButton(R.string.exit) {
                                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                            }
                            .positiveButton(R.string.grant) {
                                requestPermissionsForApp()
                                it.dismiss()
                            }.show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun loadMapFragment() {
        val mapFragment =
                childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun requestPermissionsForApp() {
        context?.let {
            if (ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions( //Method of Fragment
                        getLocationPermission(),
                        LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                loadMapFragment()
            }

        }

    }

    private fun getLocationPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCameraIdle() {
        mSelectedLocation = mGoogleMap.cameraPosition.target
    }
}