package ru.gozerov.presentation.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.gozerov.presentation.R
import ru.gozerov.presentation.databinding.ActivityMainBinding
import ru.gozerov.presentation.utils.Screens
import ru.gozerov.presentation.utils.ToolbarAction
import ru.gozerov.presentation.utils.ToolbarHolder
import ru.gozerov.presentation.utils.ToolbarHolder.ActionType
import ru.gozerov.presentation.utils.findNavigationProvider
import ru.gozerov.presentation.utils.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarHolder {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            if (getSharedPreferences("EbaySharedPreferences", Context.MODE_PRIVATE).getString("API_TOKEN", "") == ""){
                findNavigationProvider().getRouter().newRootScreen(Screens.signIn())
            } else
                findNavigationProvider().getRouter().newRootScreen(Screens.tabs())
        }
        binding.navigateUpButton.setOnClickListener {
            findNavigationProvider().getRouter().exit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.fragments.forEach {
            log("parent: ${it.javaClass.simpleName}")
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

    override fun onToolbarChange(
        actions: Map<ActionType, ToolbarAction?>,
        label: String,
        textAppearance: Int?
    ) {
        binding.toolbarTitle.text = label
        textAppearance?.let { binding.toolbarTitle.setTextAppearance(textAppearance) }

        resetToolbarViewsVisibility()

        actions.keys.forEach { actionType ->
            when(actionType) {
                ActionType.NONE -> {}
                ActionType.NAV_UP -> {
                    binding.navigateUpButton.visibility = View.VISIBLE
                }

                ActionType.NOTIFICATION -> {
                    binding.toolbarRightButton1.setOnClickListener {
                        actions.getValue(actionType)?.invoke()
                    }
                    binding.toolbarRightButton1.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.baseline_notification_24
                        )
                    )
                    binding.toolbarRightButton1.visibility = View.VISIBLE
                }
                ActionType.CART -> {
                    binding.toolbarRightButton2.setOnClickListener {
                        actions.getValue(actionType)?.invoke()
                    }
                    binding.toolbarRightButton2.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.baseline_cart_24
                        )
                    )
                    binding.toolbarRightButton2.visibility = View.VISIBLE
                }
                ActionType.SHARE -> {
                    binding.toolbarRightButton1.setOnClickListener {
                        actions.getValue(actionType)?.invoke()
                    }
                    binding.toolbarRightButton1.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.baseline_share_24
                        )
                    )
                    binding.toolbarRightButton1.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun resetToolbarViewsVisibility() {
        binding.toolbarRightButton1.visibility = View.GONE
        binding.toolbarRightButton2.visibility = View.GONE
        binding.navigateUpButton.visibility = View.GONE
    }


}
