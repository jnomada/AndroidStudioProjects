package myapp.jsealey.snapchat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class ViewSnapActivity : AppCompatActivity() {

    var messageTextView: TextView? = null
    var snapImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_snap)

        messageTextView = findViewById(R.id.messageTextView)
        snapImageView = findViewById(R.id.snapImageView)

        messageTextView?.text = intent.getStringExtra("message") // Get the message


        // Create an instance of the ImageDownloader
        val task = ImageDownloader()

        // Create a new Bitmap object to store the image
        val myImage: Bitmap
        try {
            // We pass the url to the ImageDownloader and obtain the data and store it to the variable
            myImage =
                task.execute(intent.getStringExtra("imageURL"))
                    .get()
            // We set the imageView Bitmap to the one we just downloaded.
            snapImageView?.setImageBitmap(myImage)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


    }

    inner class ImageDownloader : AsyncTask<String, Void, Bitmap>()  {

        override fun doInBackground(vararg urls: String?): Bitmap? {
            return try {
                // Get the first url
                val url = URL(urls[0])
                // Create a connection to that url
                val connection = url.openConnection() as HttpURLConnection
                // Connect using the connection
                connection.connect()
                // Create a stream to receive the data
                val `in` = connection.inputStream
                // This receives the stream of data, decodes it and stores it in a Bitmap object.
                // Return the Bitmap
                BitmapFactory.decodeStream(`in`)

            } catch (e: Exception) {
                e.printStackTrace()
                // If somehtign goes wrong we return nothing
                return null
            }
        }


}
}