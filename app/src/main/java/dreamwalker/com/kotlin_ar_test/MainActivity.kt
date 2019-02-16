package dreamwalker.com.kotlin_ar_test

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var arrayView: Array<View>
    lateinit var bearRenderable: ModelRenderable
    lateinit var catRenderable: ModelRenderable
    lateinit var cowRenderable: ModelRenderable
    lateinit var dogRenderable: ModelRenderable
    lateinit var elephantRenderable: ModelRenderable
    lateinit var ferretRenderable: ModelRenderable
    lateinit var hippoRenderable: ModelRenderable
    lateinit var horseRenderable: ModelRenderable
    lateinit var koalaRenderable: ModelRenderable
    lateinit var lionRenderable: ModelRenderable
    lateinit var reindeerRenderable: ModelRenderable
    lateinit var wolvarineRenderable: ModelRenderable

    lateinit var arFragment : ArFragment

    private var selected = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupArray()
        setupClickListener()
        setupModel()

        arFragment = supportFragmentManager.findFragmentById(R.id.scene_from_fragment) as ArFragment
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            
            createModel(anchorNode, selected)
            
        }

    }

    private fun createModel(anchorNode: AnchorNode, selected: Int) {
        if(selected == 1){
            val bear = TransformableNode(arFragment.transformationSystem)
            bear.setParent(anchorNode)
            bear.renderable = bearRenderable
            bear.select()

            addName(anchorNode, bear, "Bear")
        }

        if(selected == 2){
            val cat = TransformableNode(arFragment.transformationSystem)
            cat.setParent(anchorNode)
            cat.renderable = catRenderable
            cat.select()
            addName(anchorNode, cat, "Cat")
        }

        if(selected == 3){
            val caw = TransformableNode(arFragment.transformationSystem)
            caw.setParent(anchorNode)
            caw.renderable = catRenderable
            caw.select()
            addName(anchorNode, caw, "Caw")
        }
    }

    private fun addName(anchorNode: AnchorNode, bear: TransformableNode, s: String) {
        ViewRenderable.builder().setView(
            this, R.layout.name_layout
        ).build().thenAccept {
            viewRenderable ->
            val nameView = TransformableNode(arFragment.transformationSystem)
            nameView.localPosition = Vector3(0f, bear.localPosition.y+0.5f, 0f)
            nameView.setParent(anchorNode)
            nameView.renderable = viewRenderable
            nameView.select()

            val txt_name = viewRenderable.view as TextView
            txt_name.text = s
            txt_name.setOnClickListener {
                anchorNode.setParent(null)
            }
        }
    }

    private fun setupArray() {
        arrayView = arrayOf(
                bear, cat, cow, dog, elephant, ferret, hippopotamus,
                horse, koala_bear, lion, reindeer, wolverine
        )
    }

    private fun setupClickListener() {
        for (i in arrayView.indices) {
            arrayView[i].setOnClickListener(this)
        }
    }

    private fun setupModel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ModelRenderable.builder()
                    .setSource(this, R.raw.bear)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        bearRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load Bear model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.cat)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        catRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load cat model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.cow)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        cowRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load cow model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.elephant)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        elephantRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load elephant model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.ferret)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        ferretRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load ferret model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.hippopotamus)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        hippoRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load hippopotamus model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.horse)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        horseRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load horse model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.koala_bear)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        koalaRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load koala_bear model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.reindeer)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        reindeerRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load reindeer model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.wolverine)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        wolvarineRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load wolverine model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }

            ModelRenderable.builder()
                    .setSource(this, R.raw.lion)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        lionRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load lion model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }
            ModelRenderable.builder()
                    .setSource(this, R.raw.dog)
                    .build()
                    .thenAccept { t: ModelRenderable ->
                        dogRenderable = t
                    }
                    .exceptionally { t ->
                        Toast.makeText(this@MainActivity,
                                "Unable to load dog model",
                                Toast.LENGTH_SHORT).show()
                        null
                    }


        }

    }

    override fun onClick(v: View?) {
            if (v!!.id == R.id.bear){
                selected = 1
                mySetBackground(v.id)
            }else if(v.id == R.id.cat){
                selected = 2
                mySetBackground(v.id)
            }
            else if(v.id == R.id.cow){
                selected = 3
                mySetBackground(v.id)
            }
            else if(v.id == R.id.dog){
                selected = 4
                mySetBackground(v.id)
            }
            else if(v.id == R.id.elephant){
                selected = 5
                mySetBackground(v.id)
            }
    }

    private fun mySetBackground(id: Int) {
            for (i in arrayView.indices){
                if (arrayView[i].id ==id){
                    arrayView[i].setBackgroundColor(
                            Color.parseColor("#80333639")
                    )
                }else{
                    arrayView[i].setBackgroundColor(Color.TRANSPARENT)
                }
            }
    }

}
