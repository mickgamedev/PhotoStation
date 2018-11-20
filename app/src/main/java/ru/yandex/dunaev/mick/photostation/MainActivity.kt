package ru.yandex.dunaev.mick.photostation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.runtimepermission.kotlin.askPermission
import ru.yandex.dunaev.mick.photostation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askPermission{}.onDeclined { finish() }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val model: MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = model

        binding.bottomMenu.apply{
            setOnNavigationItemSelectedListener {menuItemSelected(it)}
            selectedItemId = R.id.menu_photo_id
        }

        model.trollFace = getDrawable(R.drawable.ic_trollface)
    }

    private fun menuItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.menu_photo_id -> setFragment(PhotoCaptureFragment().apply {
                onCaptureComplete = this@MainActivity::onCaptureComplete
            })
            R.id.menu_assistant_id -> setFragment(PhotoViewFragment())
        }
        return true
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun onCaptureComplete(){
        binding.bottomMenu.selectedItemId = R.id.menu_assistant_id
    }
}
