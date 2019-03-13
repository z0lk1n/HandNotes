package online.z0lk1n.android.handnotes.ui.main

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import online.z0lk1n.android.handnotes.R

class MainActivity : AppCompatActivity(), ToolbarTuning {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setHomeVisibility(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setToolbarColor(color: Int) {
        main_toolbar?.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
}