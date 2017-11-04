package tech.burythehatchetwith.burythehatchet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*

private var linearLayoutManager: LinearLayoutManager? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val font = FontUtil.get("Helvetica.ttc", this)
        FontUtil.overrideFonts(findViewById(android.R.id.content), -1.0f, font, null,  null)
        val topics: String = getTopics("http://165.227.176.116:8080/threads")
        println(topics)

        startActivity(Intent(this, SubmissionActivity::class.java))

    }



    fun getTopics(URL : String) : String {
        var serverResponse : String = ""
        URL.httpGet().responseString{request, response, result ->
            when(result){
                is Result.Failure ->{
                    Log.e("HTTP Request","Failed")
                }is Result.Success ->{
                    Log.e("HTTP Request", "Success")
                    serverResponse = result.get()
                }
            }
        }
        //Parse topics
        return serverResponse
    }
}
