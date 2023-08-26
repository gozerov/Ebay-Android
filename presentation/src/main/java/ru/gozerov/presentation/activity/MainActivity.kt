package ru.gozerov.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.utils.NavigatorType
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.findNavigationProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            findNavigationProvider().getRouter().newRootScreen(Screens.tabs())
    }

    override fun onResume() {
        super.onResume()
        findNavigationProvider().setNavigator(this, NavigatorType.GLOBAL)
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }

}
