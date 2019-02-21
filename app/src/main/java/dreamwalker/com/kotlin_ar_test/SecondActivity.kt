package dreamwalker.com.kotlin_ar_test

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.google.ar.core.*
import com.google.ar.core.exceptions.*
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), Scene.OnUpdateListener {

    override fun onUpdate(p0: FrameTime?) {
        val frame = ar_view.arFrame
        val updateAugmentedImage = frame!!.getUpdatedTrackables<AugmentedImage>(
            AugmentedImage::class.java
        )

        for (augmentedImg in updateAugmentedImage){
            if (augmentedImg.trackingState == TrackingState.TRACKING){
                if(augmentedImg.name == "lion"){
                    val node = MyNode(this, R.raw.lion)
                    node.image = augmentedImg
                    ar_view.scene.addChild(node)
                }
            }
        }
    }

    private var session: Session? = null
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

        }).check()

        ar_view.scene.addOnUpdateListener(this)
    }

    override fun onResume() {
        super.onResume()
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

        }).check()

    }

    override fun onPause() {
        super.onPause()
        if(session != null){
            session!!.pause()
            ar_view.pause()
        }


    }
    private fun setupSession() {
        if (session == null) {
            try {
                session = Session(this)
            } catch (e: UnavailableArcoreNotInstalledException) {
                e.printStackTrace()
            } catch (e: UnavailableApkTooOldException) {
                e.printStackTrace()
            } catch (e: UnavailableSdkTooOldException) {
                e.printStackTrace()
            } catch (e: UnavailableDeviceNotCompatibleException) {
                e.printStackTrace()
            }
            shouldConfigureSession = true
        }
        if (shouldConfigureSession) {
            configSession()
            shouldConfigureSession = false
            ar_view.setupSession(session)
        }

        try {
            session!!.resume()
            ar_view.resume()
        } catch (e: CameraNotAvailableException) {
            e.printStackTrace()
            session = null
            return
        }
    }

    private fun configSession() {
        val config = Config(session)
        if (!buildDatabase(config)) {
            Toast.makeText(this@SecondActivity, "Error", Toast.LENGTH_SHORT).show()
        }

        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        session!!.configure(config)
    }

    private fun buildDatabase(config: Config): Boolean {
        val augmentedImageDatabase: AugmentedImageDatabase
        val bitmap = loadBitmapFromAsset()
        if (bitmap == null) {
            return false
        }
        augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("lion", bitmap)
        config.augmentedImageDatabase = augmentedImageDatabase
        return true
    }

    private fun loadBitmapFromAsset(): Bitmap? {
        val inputStream = assets.open("lion_ar.jpeg")
        return BitmapFactory.decodeStream(inputStream)
    }

}
