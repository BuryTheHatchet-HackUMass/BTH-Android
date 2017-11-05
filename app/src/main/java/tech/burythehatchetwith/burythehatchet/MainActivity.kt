package tech.burythehatchetwith.burythehatchet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.beust.klaxon.*
import com.beust.klaxon.JsonArray
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_submission.*
import org.json.JSONArray
import org.json.JSONObject

private var linearLayoutManager: LinearLayoutManager? = null

class MainActivity : AppCompatActivity() {

    data class userThread(val id: Long,
                      val title: String,
                      val expiration: Int,
                      val tags: String,
                      val side_one: String,
                      val side_two: String,
                      val image: String)


    var threadModel = mutableListOf<MainActivity.userThread>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val font = FontUtil.get("Helvetica.ttc", this)
        val dataEndpoint : String = "http://165.227.176.116:8080/threads"
        FontUtil.overrideFonts(findViewById(android.R.id.content), -1.0f, font, null,  null)
        //getTopics(dataEndpoint)
        //println(topics)

        startActivity(Intent(this, SubmissionActivity::class.java))

    }



    fun getTopics(URL : String){
        var serverResponse : String = "-1"
        URL.httpGet().responseString{request, response, result ->
            when(result){
                is Result.Failure ->{
                    Log.e("HTTP Request", "Failure")
                }is Result.Success ->{
                    Log.e("HTTP Request", "Success")
                    var topics: JSONArray = JSONArray(result.get())
                    jsonToDm(topics)
                }
            }
        }
    }

    fun parseString(json : String) : JsonArray<JsonObject>{
        val parser: Parser = Parser()
        val stringBuilder: StringBuilder = StringBuilder(json)
        return parser.parse(stringBuilder) as JsonArray<JsonObject>
    }

    fun jsonToDm(jarr : JSONArray){
        val mapper = jacksonObjectMapper()
        threadModel.clear()
        (0 until jarr.length())
                .map { jarr.get(it) }
                .forEach { threadModel.add(mapper.readValue<MainActivity.userThread>(it.toString())) }

        println(threadModel.get(0).title)
        Log.e("Threads", threadModel.size.toString())
    }
}
