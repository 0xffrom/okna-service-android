package com.goga133.oknaservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.goga133.oknaservice.ui.info.SettingsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainViewModel: MainViewModel

    private lateinit var siteUrl : String
    private lateinit var phoneNumber : String
    private lateinit var emailAddress : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // ViewModel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java);
        mainViewModel.emailAddress.observe(this, Observer { emailAddress = it })
        mainViewModel.phoneNumber.observe(this, Observer { phoneNumber = it })
        mainViewModel.siteUrl.observe(this, Observer { siteUrl = it })
        // View Model

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_calculator,
                R.id.nav_lead,
                R.id.nav_contacts,
                R.id.nav_info,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            if (navController.currentDestination?.id != R.id.nav_calculator)
                navController.navigate(R.id.nav_calculator)
            else
                navController.popBackStack()
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            run {
                if (destination.id == R.id.nav_calculator || destination.id == R.id.nav_lead)
                    fab.hide()
                else
                    fab.show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // Обработчик нажатия на иконку на тулбаре сверху:
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Открытие звонилки:
        R.id.action_call -> {
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            })
            true
        }
        // Открытие почты:
        R.id.action_mail -> {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:$emailAddress")
            })
            true
        }
        // Открытие браузера:
        R.id.action_web -> {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(siteUrl)
            })
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}