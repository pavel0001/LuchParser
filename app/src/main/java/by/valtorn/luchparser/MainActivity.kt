package by.valtorn.luchparser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.valtorn.luchparser.databinding.ActivityMainBinding
import by.valtorn.luchparser.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_graph)
    }
}