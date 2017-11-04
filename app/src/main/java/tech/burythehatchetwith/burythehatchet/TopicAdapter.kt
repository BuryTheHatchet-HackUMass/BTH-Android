package tech.burythehatchetwith.burythehatchet

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * A RecyclerView adapter for the topics list
 * @author Aaron Vontell
 */
class TopicAdapter(val items: List<Thread>, val listener: (Item) -> Unit) : RecyclerView.Adapter() {

    data class Thread(val id: Long, val title: String, val url: String)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
