package com.example.godbole.megh.location_app_project_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import com.example.godbole.megh.location_app_project_2.Permissions.hasLocationPermission
import com.example.godbole.megh.location_app_project_2.Permissions.requestLocationPermission

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this) {
            finishAffinity() // Exits the app
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map -> checkLocationPermission()
            R.id.places -> startActivity(Intent(this, PlacesActivity::class.java))
            R.id.share -> startActivity(Intent(this, ShareActivity::class.java))
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermission(this)
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MapsActivity::class.java))
    }

    private fun checkLocationPermission() {
        if(hasLocationPermission(this)){
            val mapIntent = Intent(this, MapsActivity::class.java)
            startActivity(mapIntent)
        }
        else{
            requestLocationPermission(this)
        }
    }
}