package tech.burythehatchetwith.burythehatchet

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_arguments.*
import kotlinx.android.synthetic.main.arg_button.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.textColor

class ArgumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arguments)

        val id : Int = intent.getIntExtra("id", 1)
        val title : String = intent.getStringExtra("title")
        val image : String = intent.getStringExtra("image")
        val expiration : Int = intent.getIntExtra("expiration", 3)
        val tag : String = intent.getStringExtra("tag")

        titleTextView.text = title
        daysLeftTextView.text = expiration.toString()
        tagView.text = tag
        Picasso.with(this)
                .load(image)
                .resize(1250, 505)
                .centerCrop()
                .into(topicImage)

        createArguments()

    }

    @SuppressLint("NewApi")
    fun createArguments() {

        val args : List<String> = listOf("Temperatures have been rising annually", "Humans have not been the cause of climate change",
                "Sea levels are rising at an unprecedented rate due to global warming", "CO2 is already saturated in earth’s atmosphere",
                "Permafrost is melting at unprecedented rates", "Hurricane activity is a result of natural weather patterns")

        var lastLinearLayout: LinearLayout? = null

        for (i in 0 until args.size) {

            val good = i % 2 == 0
            val content = args.get(i)

            // If a YES argument, create a new linear layout, and use the appropriate
            val view = LayoutInflater.from(this).inflate(R.layout.arg_button, null)
            view.argContent.text = content
            if (good) {
                lastLinearLayout = LinearLayout(baseContext)
                lastLinearLayout.orientation = LinearLayout.HORIZONTAL
                view.argContent.textColor = getColor(android.R.color.black)
                view.grayCover.backgroundColor = getColor(R.color.proGray)
                view.colorCover.backgroundDrawable = getDrawable(R.drawable.blue_basic_gradient)
                view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                argList.addView(lastLinearLayout)

            } else {
                view.argContent.textColor = getColor(android.R.color.white)
                view.grayCover.backgroundColor = getColor(R.color.negGray)
                view.colorCover.backgroundDrawable = getDrawable(R.drawable.red_basic_gradient)
                view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }
            lastLinearLayout!!.addView(view)
        }

    }

}
