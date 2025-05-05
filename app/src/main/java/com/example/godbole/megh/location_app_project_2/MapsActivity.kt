package com.example.godbole.megh.location_app_project_2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.godbole.megh.location_app_project_2.databinding.ActivityMapsBinding
import com.google.gson.Gson

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main -> startActivity(Intent(this, MainActivity::class.java))
            R.id.places -> startActivity(Intent(this, PlacesActivity::class.java))
            R.id.share -> startActivity(Intent(this, ShareActivity::class.java))
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            isMapToolbarEnabled = true
            isMyLocationButtonEnabled = true
        }

        mMap.isMyLocationEnabled = true
        mMap.setPadding(0, 100, 0, 100)

        mMap.setOnMyLocationChangeListener { location ->

            findViewById<Button>(R.id.saveLocationBtn).setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.dialog_save_location, null)
                val nameInput = dialogView.findViewById<EditText>(R.id.locationName)
                val descInput = dialogView.findViewById<EditText>(R.id.locationDesc)

                AlertDialog.Builder(this)
                    .setTitle("Save Location")
                    .setView(dialogView)
                    .setPositiveButton("Save") { _, _ ->
                        val name = nameInput.text.toString()
                        val desc = descInput.text.toString()
                        if (name.isNotEmpty()) {
                            val loc = LocationModel(name, desc, location.latitude, location.longitude)
                            val sharedPrefs = getSharedPreferences("locations", Context.MODE_PRIVATE)
                            val editor = sharedPrefs.edit()
                            val gson = Gson()
                            val old = sharedPrefs.getStringSet("places", mutableSetOf())!!.toMutableSet()
                            old.add(gson.toJson(loc))
                            editor.putStringSet("places", old)
                            editor.apply()
                            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }
    }
}