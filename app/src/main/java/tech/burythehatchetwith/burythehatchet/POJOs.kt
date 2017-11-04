package tech.burythehatchetwith.burythehatchet

/**
 * A representation of a POJOs which is displayed to the user
 * @author Aaron Vontell
 */
data class Thread(val id: Long,
                  val title: String,
                  val expiration: Int,
                  val tags: ArrayList<String>,
                  val side1: String,
                  val side2: String,
                  val numSize1: Int,
                  val numSize2: Int,
                  val image: String)
