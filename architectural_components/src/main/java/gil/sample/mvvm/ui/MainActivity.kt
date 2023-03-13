package gil.sample.mvvm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import gil.sample.mvvm.R
import gil.sample.mvvm.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() { //}, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    //private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
    override fun onSupportNavigateUp(): Boolean {
        println("*** on onSupportNavigateUp()")
        //val foo =

        // navController.navigateUp(appBarConfiguration)
        //        || super.onSupportNavigateUp()

        //println("*** DONE on onSupportNavigateUp() - $foo")
        //return foo

        return true
    }
    **/

    /**
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("*** onOptionsItemSelected")
        when (item.itemId) {
            android.R.id.home -> {
                println("*** back selected")
                return goBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        println("*** onNavigationItemSelected")
        // Handle navigation view item clicks here.
        when (item.itemId) {
            android.R.id.home -> {
                println("*** back selected")
                return goBack()
            }
        }
        return true
    }
    **/

    override fun onNavigateUp(): Boolean {
        println("*** onNavigateUp()")
        val foo = super.onNavigateUp()
        println("*** FOO = $foo")
        return foo
    }

    //    // Handle action bar item clicks here. The action bar will
    //    // automatically handle clicks on the Home/Up button, so long
    //    // as you specify a parent activity in AndroidManifest.xml.
    //    return when (item.itemId) {
    //        R.id.action_settings -> true
    //        else -> super.onOptionsItemSelected(item)
    //    }
    //}

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /**
    override fun onSupportNavigateUp(): Boolean {
    println("*** onSupportNavigateUp()")
    val navController = findNavController(R.id.nav_host_fragment_content_main)
    val foo = navController.navigateUp(appBarConfiguration)
    || super.onSupportNavigateUp()
    println("*** can go back = $foo")
    return foo
    }
     **/
/**
    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp()
        println("*** onSupportNavigateUp()")
        return true
    }
**/
    private fun goBack() : Boolean {
        println("*** go back")
        // A note:
        // navigateUp() will attempt to pop backstack and if nothing exists, leave the app and
        // return to previous flow i.e. another app deep linked to this Activity and flow is
        // returned to it.
        // popBackStack() will attempt to pop backstack and if nothing exists, do nothing
        //
        //val foo = navController.navigateUp()

        //need to resolve
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        //val test2 : UsersCrFragmentDirections =

        val test = navController.currentBackStackEntry
        println("*** test = $test")

        //val foo = navController.navigateUp(appBarConfiguration)
        val foo = navController.navigateUp()
        println("*** done go back - $foo")
        return foo
    }
}