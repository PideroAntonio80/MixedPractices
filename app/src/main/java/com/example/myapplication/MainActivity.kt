package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.UserVipApplication.Companion.prefs
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.DatePickerFragment
import com.example.myapplication.utils.TimePickerFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.etTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.bPhoto.setOnClickListener {
            checkPermission()
        }

        initUI()

        checkUserValues()
    }

    /****************************************** DATEPICKER ******************************************/

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.etDate.setText("$year-$month-$day")
    }

    /****************************************** TIMEPICKER ******************************************/

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(supportFragmentManager, "timePicker")
    }

    private fun onTimeSelected(time: String) {
        binding.etTime.setText(time)
    }

    /****************************************** PERMISOS_CÁMARA ******************************************/

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            Toast.makeText(this, "Abriendo cámara", Toast.LENGTH_LONG).show()
        }
    }

    private fun requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Permisos rechazados", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 888)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 888) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Abriendo cámara", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permisos rechazados por primera vez", Toast.LENGTH_LONG).show()
            }
        }
    }

    /****************************************** SHARED_PREFERENCES ******************************************/



    private fun initUI() {
        binding.bContinue.setOnClickListener {
            accessToDetail()
        }
    }

    private fun accessToDetail() {
        if (binding.etName.text.isNotEmpty()) {
            prefs.saveName(binding.etName.text.toString())
            prefs.saveVip(binding.cbVip.isChecked)
            goToDetail()
        } else {
            info("Aún no has iniciado sesión")
        }
    }

    private fun goToDetail() {
        startActivity(Intent(this, ResultActivity::class.java))
    }

    private fun info(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkUserValues() {
        if (prefs.getName().isNotEmpty()) {
            goToDetail()
        }
    }
}