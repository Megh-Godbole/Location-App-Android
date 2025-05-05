package com.example.godbole.megh.location_app_project_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
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
            R.id.share -> startActivity(Intent(this, ShareActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
