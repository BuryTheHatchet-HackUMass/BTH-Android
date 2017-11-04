package tech.burythehatchetwith.burythehatchet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.beust.klaxon.JsonObject
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_submission.*
import org.json.JSONArray

/**
 * Submission activity for creating arguments.
 * @author Aaron Vontell
 */
class SubmissionActivity : AppCompatActivity() {

    val factEndpoint : String = "165.227.176.116:8080/factchecker"
    val keywordEndpoint : String = "165.227.176.116:8080/keywords"
    var lastCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        // Attach listeners for text changing
        submissionText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val result = p0!!.split(" ").toString().toLowerCase()

                //if (result.length != lastCount) {
                    lastCount = result.length
                    getFacts("http://165.227.176.116:8080/factchecker", p0.toString())
                //}

            }

        })

    }

    fun getFacts(URL : String, body: String) {
        Log.e("BOD", body)
        Fuel.post(URL, listOf("content" to body)).responseString { request, response, result ->
            when(result){
                is Result.Failure ->{
                    //Log.e("HTTP Request","Failed")
                    //Log.e("REsult", result.toString())
                } is Result.Success ->{
                    //Log.e("HTTP Request", "Success")
                    var serverResponse = result.get()
                    var jobj = JSONArray(serverResponse)

                    // Now change the UI / underlying model

                }
            }
        }
    }

}
