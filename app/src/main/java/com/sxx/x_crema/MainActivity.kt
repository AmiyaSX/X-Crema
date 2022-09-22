package com.sxx.x_crema

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorPrivacyManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.sxx.x_crema.databinding.ActivityMainBinding
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import java.util.jar.Manifest

private const val TAG = "MainActivity"

private const val PERMISSIONS_REQUEST_CODE = 10

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.fragment_container)
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
        if (isGranted) {
            navigateToCamera()
        } else {
            Toast.makeText(this, "相机的正常使用需要开启相机权限，请自行前往设置开启权限。", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkCameraPermission()
        checkDevice()
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //相机权限监测和申请
    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> navigateToCamera()
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    //检查设备是否支持麦克风和摄像头切换开关
    private fun checkDevice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val sensorPrivacyManager =  applicationContext
                    .getSystemService(SensorPrivacyManager::class.java)
                        as SensorPrivacyManager
                val supportsMicrophoneToggle = sensorPrivacyManager
                    .supportsSensorToggle(SensorPrivacyManager.Sensors.MICROPHONE)
                val supportsCameraToggle = sensorPrivacyManager
                    .supportsSensorToggle(SensorPrivacyManager.Sensors.CAMERA)
            } else {

            }
        }

    }
    private fun navigateToCamera() {
        navController.navigate(R.id.action_Homeragment_to_CameraFragment)
    }
}