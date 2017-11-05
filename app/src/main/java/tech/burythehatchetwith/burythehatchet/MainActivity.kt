package tech.burythehatchetwith.burythehatchet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.json.JSONArray

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

        val font = FontUtil.get("Helvetica.ttc", this)
        val dataEndpoint : String = "http://165.227.176.116:8080/threads"
        FontUtil.overrideFonts(findViewById(android.R.id.content), -1.0f, font, null,  null)
        getTopics(dataEndpoint)

        //startActivity(Intent(this, SubmissionActivity::class.java))

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

    fun jsonToDm(jarr : JSONArray){
        val mapper = jacksonObjectMapper()
        threadModel.clear()
        (0 until jarr.length())
                .map { jarr.get(it) }
                .forEach { threadModel.add(mapper.readValue<MainActivity.userThread>(it.toString())) }

        //val intent = Intent(this, ArgumentsActivity::class.java)
        val intent = Intent(this, SubmissionActivity::class.java)

        // For each item, we want to inflate a view and render components
        topicList.removeAllViews()
        for (i in 0 until threadModel.size) {

            var currentThread = threadModel.get(i)
            println(currentThread.title)
            val view = LayoutInflater.from(this).inflate(R.layout.recyclerview_item_row, null)
            view.titleTextView.text = currentThread.title
            view.daysLeftTextView.text = currentThread.expiration.toString()
            view.tagView.text = currentThread.tags.split(",").get(0)
            Picasso.with(this)
                    .load(currentThread.image)
                    .resize(1250, 505)
                    .centerCrop()
                    .into(view.topicImage)

            view.onClick {
                intent.putExtra("id", currentThread.id)
                intent.putExtra("title", currentThread.title)
                intent.putExtra("image", currentThread.image)
                intent.putExtra("expiration", currentThread.expiration)
                intent.putExtra("tag", currentThread.tags.split(",").get(0))
                startActivity(intent)
            }

            topicList.addView(view)

        }

    }
}
