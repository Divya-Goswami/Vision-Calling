package com.agora.cv

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.agora.cv.activities.BaseActivity
import com.agora.cv.activities.RoleActivity
import com.agora.cv.model.ConstantApp
import com.agora.cv.ui.CallActivity


public class MainActivity :  BaseActivity() {
    private val sharedPrefFile = "Agora"

    lateinit var etUsername: TextView
    lateinit var etChannelname: EditText

    // Permission request code of any integer value
    private val PERMISSION_REQ_CODE = 1 shl 4

    private val PERMISSIONS = arrayOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        etChannelname = findViewById(R.id.et_channel_name)
        etUsername = findViewById(R.id.tv_username)

        val username = sharedPreferences.getString("username", "Tester")
        etUsername.setText(" Hello, ${username.toString()}")
    }

    // Hook Click Event
    fun generateRoomId(v: View) {
        val alphanumerics = CharArray(26) { it -> (it + 97).toChar() }.toSet()
            .union(CharArray(9) { it -> (it + 48).toChar() }.toSet())
        var random_id = (0..6).map {
            alphanumerics.toList().random()
        }.joinToString("");

        etChannelname.setText(random_id)
        Toast.makeText(this, "Room ID Generated Successfully", Toast.LENGTH_LONG).show()

        var channel_name = findViewById<EditText>(R.id.et_channel_name).text

        Toast.makeText(this, channel_name, Toast.LENGTH_LONG).show()
    }

    fun navigateToVideo(v: View) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        var channel = etChannelname.text.toString().trim()
        if (channel.length > 0) {
            var editor = sharedPreferences.edit()
            editor.putString("channel_name", channel)
            editor.apply()

            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_LONG).show()
        }
    }



    fun navigateToGroupCall(v: View) {
        if (etChannelname.text.toString().trim().length > 0) {
            Toast.makeText(this, "here", Toast.LENGTH_LONG).show()
            val i = Intent(this, CallActivity::class.java)
            startActivity(i)
        } else {
            Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_LONG).show()
        }

    }

    fun onStartBroadcastClicked(view: View?) {
        if (etChannelname.text.toString().trim().length > 0) {
        checkPermission()
        } else {
        Toast.makeText(this, "Please Enter Channel Name", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        var granted = true
        for (per in PERMISSIONS) {
            if (!permissionGranted(per)) {
                granted = false
                break
            }
        }
        if (granted) {
            resetLayoutAndForward()
        } else {
            requestPermissions()
        }
    }

    private fun permissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray,
    ) {
        if (requestCode == PERMISSION_REQ_CODE) {
            var granted = true
            for (result in grantResults) {
                granted = result == PackageManager.PERMISSION_GRANTED
                if (!granted) break
            }
            if (granted) {
                resetLayoutAndForward()
            } else {
                Toast.makeText(this, R.string.need_necessary_permissions, Toast.LENGTH_LONG).show();
            }
        }
    }

    private fun resetLayoutAndForward() {
        closeImeDialogIfNeeded()
        gotoRoleActivity()
    }

    private fun closeImeDialogIfNeeded() {
        val manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(etChannelname.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun gotoRoleActivity() {
        val intent = Intent(this@MainActivity, RoleActivity::class.java)
        val room: String = etChannelname.getText().toString()
        config().setChannelName(room)
        startActivity(intent)
    }

}