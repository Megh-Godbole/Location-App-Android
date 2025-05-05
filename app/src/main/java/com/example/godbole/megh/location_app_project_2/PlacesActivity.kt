package com.example.godbole.megh.location_app_project_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class PlacesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        enableEdgeToEdge()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val listView = findViewById<ListView>(R.id.placesListView)
        val sharedPrefs = getSharedPreferences("locations", MODE_PRIVATE)
        val savedData = sharedPrefs.getStringSet("places", emptySet())!!
        val locations = savedData.map { Gson().fromJson(it, LocationModel::class.java) }

        val names = locations.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, names)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val loc = locations[position]
            Toast.makeText(this, "${loc.latitude}, ${loc.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main -> startActivity(Intent(this, MainActivity::class.java))
            R.id.map -> startActivity(Intent(this, MapsActivity::class.java))
            R.id.share -> startActivity(Intent(this, ShareActivity::class.java))
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
