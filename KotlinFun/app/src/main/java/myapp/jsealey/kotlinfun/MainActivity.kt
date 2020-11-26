package myapp.jsealey.kotlinfun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var counter = 0

    fun count(view : View) {
        counter++
        countText.setText(counter.toString())
    }

    fun reset(view : View) {
        counter = 0
        countText.setText(counter.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var countText = findViewById<TextView>(R.id.countText)
        countText.text = counter.toString()





        // Make a counter app where you press a button and it adds + one to the counter and a reset button.
    }
}