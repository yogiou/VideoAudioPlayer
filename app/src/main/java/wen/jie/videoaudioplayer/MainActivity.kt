package wen.jie.videoaudioplayer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import wen.jie.videoaudioplayer.view.PlayerFragment
import wen.jie.videoaudioplayer.view.FileListFragment

class MainActivity : AppCompatActivity(), FileListFragment.FileListFragmentAdapter {
    companion object {
        private const val REQUEST_CODE_READ_PERMISSION = 1002
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val newFragment = FileListFragment(this)
                    swapFragment(newFragment, FileListFragment.TAG)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()
    }

    private fun init() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val newFragment = FileListFragment(this)
            swapFragment(newFragment, FileListFragment.TAG)
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_READ_PERMISSION)
        }
    }

    private fun swapFragment(fragment: Fragment, fragmentTag: String) {
        val ft = supportFragmentManager?.beginTransaction()
        ft?.addToBackStack(fragmentTag)
        ft?.replace(R.id.container, fragment)
        ft?.commit()
    }

    override fun goToAudioPlayerFragment(path: String) {
        val audioFragment = PlayerFragment()
        val bundle = Bundle()
        bundle.putString(PlayerFragment.PATH_KEY, path)
        audioFragment.arguments = bundle
        swapFragment(audioFragment, PlayerFragment.TAG)
    }

    override fun goToVideoPlayerFragment(path: String) {
        val videoFragment = PlayerFragment()
        val bundle = Bundle()
        bundle.putString(PlayerFragment.PATH_KEY, path)
        videoFragment.arguments = bundle
        swapFragment(videoFragment, PlayerFragment.TAG)
    }
}
