package com.example.godbole.megh.location_app_project_2

import android.Manifest
import android.app.Activity
import android.content.Context

import com.vmadalin.easypermissions.EasyPermissions

object Permissions {

    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    fun requestLocationPermission(activity: Activity){
        EasyPermissions.requestPermissions(
            activity,
            "This application cannot work without Location Permission",
            1,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


}