package tech.burythehatchetwith.burythehatchet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.beust.klaxon.JsonObject
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_submission.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray

/**
 * Submission activity for creating arguments.
 * @author Aaron Vontell
 */
class SubmissionActivity : AppCompatActivity() {

    data class Fact(val id : Int, val title : String, val src : String, val url : String, val validity : Double, val keywords : List<String>)

    val factEndpoint : String = "165.227.176.116:8080/factchecker"
    val keywordEndpoint : String = "165.227.176.116:8080/keywords"
    var lastType = 0L
    var factModel = mutableListOf<Fact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        // Attach listeners for text changing
        submissionText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val now = System.currentTimeMillis();
                //if (now - lastType > 700) {
                    getFacts("http://165.227.176.116:8080/factchecker", p0.toString())
                //}
                lastType = now

            }

        })

    }

    fun getFacts(URL : String, body: String) {

        doAsync {

            Fuel.post(URL, listOf("content" to body)).responseString { request, response, result ->
                when(result) {
                    is Result.Failure ->{
                        //Log.e("HTTP Request","Failed")
                        //Log.e("REsult", result.toString())
                    } is Result.Success ->{
                    //Log.e("HTTP Request", "Success")
                    var serverResponse = result.get()
                    var jarr = JSONArray(serverResponse)

                    // Now change the UI / underlying model
                    sourcesOverview.visibility = View.VISIBLE;
                    if (jarr.length() == 0) {
                        sourcesOverview.text = "No evidence available"
                    } else {
                        sourcesOverview.text = "${jarr.length()} sources available"
                    }

                    Log.e("RESP", serverResponse);

                    val mapper = jacksonObjectMapper()

                    factModel.clear()
                    (0 until jarr.length())
                            .map { jarr.get(it) }
                            .forEach { factModel.add(mapper.readValue<Fact>(it.toString())) }

                    Log.e("FACTS", factModel.size.toString());

                    uiThread {
                        Log.e("DONE", "Yeah boi")
                    }

                }
                }
            }

        }
    }

}
