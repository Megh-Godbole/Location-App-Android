package com.example.godbole.megh.location_app_project_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.File

class ShareActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var locations: List<LocationModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        spinner = findViewById(R.id.locationSpinner)
        val sharedPrefs = getSharedPreferences("locations", MODE_PRIVATE)
        val savedData = sharedPrefs.getStringSet("places", emptySet())!!
        locations = savedData.map { Gson().fromJson(it, LocationModel::class.java) }

        val names = locations.map { it.name }
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)

        findViewById<Button>(R.id.shareBtn).setOnClickListener {
            val selected = spinner.selectedItemPosition
            val loc = locations[selected]
            val data = "Name: ${loc.name}\nDescription: ${loc.description}\nCoordinates: ${loc.latitude}, ${loc.longitude}"

            val file = File(cacheDir, "${loc.name}.txt")
            file.writeText(data)

            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Share location via"))
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
            R.id.places -> startActivity(Intent(this, PlacesActivity::class.java))
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
