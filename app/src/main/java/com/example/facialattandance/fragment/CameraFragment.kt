package com.example.facialattandance.fragment


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.facialattandance.Activity.CameraActivity
import com.example.facialattandance.Activity.HomeActivity
import com.example.facialattandance.R
import kotlinx.android.synthetic.main.fragment_camera.*


/**
 * A simple [Fragment] subclass.
 */
class CameraFragment : Fragment() {
    var image_uri: Uri? = null
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showLoading(false)
//        image.setOnClickListener {
//            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
//                        ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    requestPermissions(permission, PERMISSION_CODE)
//                } else {
//                    openCamera()
//                }
//            }
//            else {
//                openCamera()
//            }
//        }
        val intent = Intent(context, CameraActivity::class.java)
        scan_face.setOnClickListener {
            startActivity(intent)
        }
    }




//    private fun openCamera() {
//        var resolver = requireActivity().contentResolver
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        image_uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] ==
//                        PackageManager.PERMISSION_GRANTED){
//                    openCamera()
//                }
//                else{
//                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK){
//            image.setImageURI(image_uri)
//        }
//    }
//
//    private fun showLoading(state: Boolean) {
//        if (state) {
//            progressBar.visibility = View.VISIBLE
//        } else {
//            progressBar.visibility = View.INVISIBLE
//        }
//    }

}
