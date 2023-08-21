package ru.gozerov.ebayandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.SignInBody
import ru.gozerov.domain.models.SignUpBody
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.usecases.PerformSignIn
import ru.gozerov.ebayandroid.app.appComponent
import ru.gozerov.ebayandroid.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var performSignIn: PerformSignIn

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                performSignIn.execute(
                    arg = SignInBody("Android Admin", "qwerty"),
                    onSuccess = {
                        Toast.makeText(this@MainActivity, it.value, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }


    }

}