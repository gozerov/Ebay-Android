package ru.gozerov.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.ToolbarHolder.ToolbarType
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.log

class MainActivity : AppCompatActivity(), ToolbarHolder {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.addOnBackStackChangedListener {
            log(supportFragmentManager.backStackEntryCount.toString())
        }
        if (savedInstanceState == null)
            findNavigationProvider().getRouter().newRootScreen(Screens.tabs())
        binding.navigateUpButton.setOnClickListener {
            findNavigationProvider().getRouter().exit()
        }
    }

    override fun onResume() {
        super.onResume()
        findNavigationProvider().setNavigator(this, R.id.fragmentContainerGlobal)
    }

    override fun onPause() {
        super.onPause()
        findNavigationProvider().removeNavigator()
    }
    override fun onToolbarChange(type: ToolbarType, label: String) {
        binding.toolbarTitle.text = label
        when(type) {
            ToolbarType.NONE -> {
                binding.navigateUpButton.visibility = View.GONE
            }
            ToolbarType.NAV_UP_ONLY -> {
                binding.navigateUpButton.visibility = View.VISIBLE
            }
        }
    }

}
