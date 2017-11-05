package tech.burythehatchetwith.burythehatchet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_submission.*
import kotlinx.android.synthetic.main.data_entry_view.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.JSONArray

/**
 * Submission activity for creating arguments.
 * @author Aaron Vontell
 */
class SubmissionActivity : AppCompatActivity() {

    data class Fact(val id : Int, val title : String, val src : String, val url : String, val validity : Double, val keywords : List<String>)

    val factEndpoint : String = "165.227.176.116:8080/factchecker"
    val keywordEndpoint : String = "165.227.176.116:8080/keywords"
    var hash = 0
    private var dataReceived : JSONArray? = null
    var factModel = mutableListOf<Fact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        // Attach listeners for text changing
        submissionText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) { }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                getFacts("http://165.227.176.116:8080/factchecker", p0.toString())

            }

        })

    }

    fun displayData() {

        Log.e("DISPLAY", "Displaying data links");

        // First parse our Fact array into an object that we can use
        val mapper = jacksonObjectMapper()
        var jarr = dataReceived
        factModel.clear()
        (0 until jarr!!.length())
            .map { jarr.get(it) }
            .forEach { factModel.add(mapper.readValue<Fact>(it.toString())) }

        // For each item, we want to inflate a view and render components
        factList.removeAllViews()
        for (i in 0 until factModel.size) {

            var currentFact = factModel.get(i)
            val view = LayoutInflater.from(this).inflate(R.layout.data_entry_view, factList)
            view.dataTitle.text = currentFact.title
            view.dataSource.text = "Page from ${currentFact.src}"
            view.dataValidity.text = "${currentFact.validity*100}%"

            // TODO: Change validity percentage color
            factList.addView(view)

        }

    }

    fun getFacts(URL : String, body: String) {

        Fuel.post(URL, listOf("content" to body)).responseString { request, response, result ->
            when(result) {
                is Result.Failure ->{
                    //Log.e("HTTP Request","Failed")
                    //Log.e("REsult", result.toString())
                } is Result.Success ->{
                //Log.e("HTTP Request", "Success")
                val serverResponse = result.get()
                val jarr = JSONArray(serverResponse)

                val last = jarr.hashCode()
                if (last != hash) {

                    hash = last

                    // Now change the UI / underlying model
                    sourcesOverview.visibility = View.VISIBLE;
                    if (jarr.length() == 0) {
                        sourcesOverview.text = "No evidence available"
                    } else {
                        sourcesOverview.text = "${jarr.length()} sources available"
                    }

                    displayData()

                }

                }
            }
        }

    }
}
