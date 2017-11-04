package tech.burythehatchetwith.burythehatchet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_submission.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.net.URL

/**
 * Submission activity for creating arguments.
 * @author Aaron Vontell
 */
class SubmissionActivity : AppCompatActivity() {

    val factEndpoint : String = "165.227.176.116/factchecker"
    val keywordEndpoint : String = "165.227.176.116/keywords"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        // Attach listeners for text changing
        submissionText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var result = p0!!.split(" ").toString().toLowerCase()
                getTopics()

            }

        })

    }


    fun getTopics() {
        doAsync {
            val result = URL(factEndpoint).readText()
            uiThread {
                Log.e("RES", result);
            }
        }
    }

}
