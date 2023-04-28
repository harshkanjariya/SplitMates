package com.harshk.splitmates

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import com.harshk.splitmates.ui.MainFragment
import com.harshk.splitmates.ui.SettingsFragment
import com.harshk.splitmates.utils.GoogleService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        onBackPressedDispatcher.addCallback {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val googleService = GoogleService(this@MainActivity)
            googleService.initializeFolders()
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_fragment, MainFragment())
            .commit()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(MainFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.settings -> {
                    replaceFragment(SettingsFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.logout -> {
                    val googleService = GoogleService(this@MainActivity)
                    googleService.signOut()
                    drawerLayout.closeDrawers()
                    replaceFragment(MainFragment())
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    return@setNavigationItemSelectedListener false
                }
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .commit()
        drawerLayout.closeDrawers()
    }
}