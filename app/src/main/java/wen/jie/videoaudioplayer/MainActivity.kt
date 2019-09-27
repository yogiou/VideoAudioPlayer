package wen.jie.videoaudioplayer

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import wen.jie.videoaudioplayer.view.FileListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()
    }

    fun init() {
        val newFragment = FileListFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, newFragment).commit()
    }
}
