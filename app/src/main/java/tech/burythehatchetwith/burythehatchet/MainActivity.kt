package tech.burythehatchetwith.burythehatchet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.net.URL

private var linearLayoutManager: LinearLayoutManager? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val font = FontUtil.get("Helvetica.ttc", this)
        FontUtil.overrideFonts(findViewById(android.R.id.content), -1.0f, font, null,  null)

        startActivity(Intent(this, SubmissionActivity::class.java))

    }



    fun getTopics() {

        doAsync {
            val result = URL("<api call>").readText()
            uiThread {

            }
        }
    }

}
