package com.cuetmart.user

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.cuetmart.user.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val botomnavView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.loginFragment,R.id.profileFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            Toast.makeText(this,item.itemId,Toast.LENGTH_SHORT).show()
            when (item.itemId) {


                R.id.navigation_profile -> {


                    navController.navigateUp()
                    navController.navigate(R.id.profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home->{

                    navController.navigateUp()
                    navController.navigate(R.id.nav_home)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_sell->{
                    navController.navigateUp()
                    navController.navigate(R.id.sellFragment)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

        botomnavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)



        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment) {
                binding.bottomNav.visibility= View.INVISIBLE
                binding.appBarMain.appbarLayout.visibility=View.INVISIBLE

            } else {
                binding.bottomNav.visibility= View.VISIBLE
                binding.appBarMain.appbarLayout.visibility=View.VISIBLE

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}