package com.example.facialattandance

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.facialattandance.Activity.CameraActivity
import com.example.facialattandance.Activity.EmployeeActivity
import com.example.facialattandance.Activity.LoginActivity
import com.example.facialattandance.Activity.SplashScreenActivity
import com.example.facialattandance.dialogs.DatePickerActivity
import com.example.facialattandance.dialogs.DatePickerFragment
import com.example.facialattandance.fragment.EmployeeFragment
import com.example.facialattandance.utils.HOSTING_URL
import com.example.facialattandance.utils.uploadImage
import kotlinx.android.synthetic.main.activity_add_employee.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AddEmployeeActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var requestQueue: RequestQueue? = null
    private var isBODChoosen = false
    private var PERMISSION_CODE = 1000
    private var IMAGE_PICK_CODE = 1001

    private var isLoading = false
    private var profileImageUri: Uri? = null

    fun init() {
        requestQueue = Volley.newRequestQueue(this)
        dob_button.setOnClickListener {
            isBODChoosen = true
            val datePicker = DatePickerActivity()
            datePicker.show(supportFragmentManager, "date picker")
        }

        employ_date_button.setOnClickListener {
            isBODChoosen = false
            val datePicker = DatePickerActivity()
            datePicker.show(supportFragmentManager, "date picker")
        }

        image_profile.setOnClickListener {
            pickImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        init()
        showLoading(false)

        Log.d(TAG, "onCreate: ")
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })


    }


    fun addEmployee(imageUrl: String?) {
        val POST_EMPLOYEE_URL = HOSTING_URL + "api/employee/create"
        showLoading(true)

        val name = name_edittext.text.toString()
        val position = position_edittext.text.toString()
        val department = department_edittext.text.toString()
        val organization = SplashScreenActivity.currentOrganization?.name
        val employe_date = employ_date_button.text.toString()
        val bod = dob_button.text.toString()
        val profile_url = imageUrl
        val email = email_edittext.text.toString()
        Log.d(TAG, "addEmployee: profile is: $profile_url")

        val json = JSONObject()
        json.put("name", name)
        json.put("position", position)
        json.put("department", department)
        json.put("organization", organization)
        json.put("employed_date", employe_date)
        json.put("email", email)
        json.put("profile_url", profile_url)
        json.put("birth_of_date", bod)
        if (profile_url != null) {
            json.put("profile_url", profile_url)
        }

        val request = object : JsonObjectRequest(Request.Method.POST, POST_EMPLOYEE_URL, json, Response.Listener {
            Log.d(TAG, "addEmployee: added")
            EmployeeFragment.shouldLoadNewEmployee = true
            showLoading(false)
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
            startFaceRegistrationActivity()

        }, Response.ErrorListener {
            showLoading(false)
            Log.d(TAG, "addEmployee: error")
            Log.d(TAG, "addEmployee: $it")
            Toast.makeText(this, "Provided Information is not valid", Toast.LENGTH_SHORT).show()

        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                if (SplashScreenActivity.currentUser == null) {
                    SplashScreenActivity.currentUser = SplashScreenActivity.retrieveUser()
                }
                Log.d(EmployeeActivity.TAG, "getHeaders: token: ${SplashScreenActivity.currentUser!!.token}")
                params.put("Content-Type", "application/json")
                params.put("Authorization", "Bearer " + SplashScreenActivity.currentUser!!.token)
                return params
            }
        }

        showLoading(false)
        requestQueue?.add(request)

    }

    fun startFaceRegistrationActivity() {
        val intent = Intent(this, CameraActivity::class.java)
        intent.putExtra(CameraActivity.CAMERA_MODE_KEY, CameraActivity.REGISTER_MODE)
        intent.putExtra(CameraActivity.OWNER_NAME, name_edittext.text.toString())
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    companion object {
        val TAG = "AddEmployeeActivity"
    }

    fun pickImage() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(baseContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(baseContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setType("image/*")
                startActivityForResult(intent, IMAGE_PICK_CODE)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ")
        menuInflater.inflate(R.menu.add_employee_activity_menu, menu!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_next -> {
                Log.d(TAG, "onOptionsItemSelected: next")
                //ToDo
                if (isAllFieldFilled()) {
                    Log.d(TAG, "onOptionsItemSelected: $profileImageUri")
                    if (profileImageUri != null) {
                        showLoading(true)
                        uploadImage(
                                profileImageUri!!,
                                name_edittext?.text.toString(),
                                listener = { url ->
                                    run {
                                        addEmployee(url)
                                    }
                                }
                        )

                    } else {
                        addEmployee(null)
                    }
                } else {
                    Toast.makeText(this, "Please fill all info", Toast.LENGTH_SHORT).show()
                }

            }
        }

        return true
    }

    fun isAllFieldFilled(): Boolean {
        return (
                email_edittext?.text.toString() != "" &&
                        name_edittext?.text.toString() != "" &&
                        position_edittext?.text.toString() != "" &&
                        department_edittext?.text.toString() != "" &&
                        !dob_button?.text?.startsWith("DATE")!! &&
                        !employ_date_button?.text?.startsWith("EMPLOY")!!
                )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet: ")

        val c = Calendar.getInstance()
//        val pattern = "dd-MM-yyyy"
        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth
        val currentDate: String = simpleDateFormat.format(c.getTime())

        Log.d("AddMeetingDialog", "onDateSet: $currentDate")
        if (isBODChoosen) {
            dob_button.text = currentDate
        } else {
            employ_date_button.text = currentDate
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_profile.setImageURI(data?.data)
            profileImageUri = data?.data
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    pickImage()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun showLoading(loading: Boolean) {
        isLoading = loading
        Log.d(LoginActivity.TAG, "showLoading: ${loading}")
        if (isLoading) {
            progress_bar.visibility = ProgressBar.VISIBLE
            blurForeground.visibility = LinearLayout.VISIBLE
        } else {
            progress_bar.visibility = ProgressBar.INVISIBLE
            blurForeground.visibility = LinearLayout.INVISIBLE
        }

    }
}