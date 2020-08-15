package com.goga133.oknaservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.goga133.oknaservice.models.Status
import com.goga133.oknaservice.models.info.Info
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainViewModel: MainViewModel

    companion object{
        private const val DEFAULT_WEB = "https://www.okna-servise.com/"
        private const val DEFAULT_PHONE = "74955056514"
        private const val DEFAULT_EMAIL = "os@okna-servise.com"
    }

    private var web : String = DEFAULT_WEB
    private var phone : String = DEFAULT_PHONE
    private var email : String = DEFAULT_EMAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java);
        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_calculator,
                R.id.nav_lead,
                R.id.nav_contacts,
                R.id.nav_info,
                R.id.nav_settings,
                R.id.nav_auth,
                R.id.nav_personal
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
                if (destination.id == R.id.nav_home)
                    fab.show()
                else
                    fab.hide()
            }
        }


        observeGetPosts();
        mainViewModel.getInfo();

    }

    private fun observeGetPosts() {
        mainViewModel.infoLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })
    }

    private fun viewOneLoading() {
        //
    }

    private fun viewOneSuccess(data: Info?) {
        if (data == null)
            return

        email = data.email
        phone = data.phoneNumber
        web = data.web
    }

    private fun viewOneError(error: Throwable?) {
        Toast.makeText(findViewById<View>(android.R.id.content).rootView.context, when(error) {
            is java.net.SocketTimeoutException -> "Проверьте подключение к интернету!"
            else -> "Ошибка загрузки данных!"
        }, Toast.LENGTH_LONG).show()

        mainViewModel.getInfo();
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return true
    }


    // Обработчик нажатия на иконку на тулбаре сверху:
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Открытие звонилки:
        R.id.action_call -> {
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            })
            true
        }
        // Открытие почты:
        R.id.action_mail -> {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("mailto:$email")
            })
            true
        }
        // Открытие браузера:
        R.id.action_web -> {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(web)
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