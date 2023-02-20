package com.example.meliapp.ui

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.meliapp.R
import com.example.meliapp.databinding.ActivityMainBinding
import com.example.meliapp.utils.BottomNavBarUtil
import com.example.meliapp.utils.BottomNavBarUtil.Companion.hideBottomNav
import com.google.android.material.internal.ViewUtils.hideKeyboard


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration) // 0 = scale, 1 = slide
        bottomNavBar(navController)
        hideBottomNav(binding ,this)
    }
    private fun bottomNavBar(navController: NavController) {
        navController.currentDestination?.id?.let { id ->
            if (id == R.id.shopCartFragment) {
                binding.content.btnSecondFav.isActivated = false
            } else if (id == R.id.FirstFragment) {
                binding.content.btnHome.isActivated = false
            }
        }
        binding.content.btnHome.setOnClickListener {
            navController.navigate(R.id.FirstFragment)
            binding.content.btnHome.isActivated = false
            binding.content.fabAddWord.isActivated = true
            binding.content.btnSecondFav.isActivated = true
        }
        binding.content.fabAddWord.setOnClickListener {
            navController.navigate(R.id.shopCartFragment)
            binding.content.btnHome.isActivated = true
            binding.content.fabAddWord.isActivated = false
            binding.content.btnSecondFav.isActivated = true
        }
        binding.content.btnSecondFav.setOnClickListener {
           Toast.makeText(this, "Aquí estaran los Favoritos", Toast.LENGTH_SHORT).show()
            binding.content.btnHome.isActivated = true
            binding.content.fabAddWord.isActivated = true
        }
    }

    //crear funcion para escuchar cuando se levanta un teclado de android


    //cuando se levanta el teclado, se oculta el bottom nav bar
    //cuando se baja el teclado, se vuelve a mostrar el bottom nav bar

    private fun hideBottomNavBar() {

    }

    private fun showBottomNavBar() {

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}