package dreamwalker.com.kotlin_ar_test

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class SecondActivity : AppCompatActivity() {

    private lateinit var session: Session
    private var shouldConfigureSession = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Dexter.withActivity(this).withPermission(
            Manifest.permission.CAMERA
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                setupSession()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {

            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                Toast.makeText(this@SecondActivity, "Permission Camera need to use Camera", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupSession() {
       if (session == null){
           try {
               session = Session(this)
           }catch (e:UnavailableArcoreNotInstalledException){
               e.printStackTrace()
           }
           catch (e:UnavailableApkTooOldException){
               e.printStackTrace()
           }
           catch (e:UnavailableSdkTooOldException){
               e.printStackTrace()
           }

       }
    }
}
