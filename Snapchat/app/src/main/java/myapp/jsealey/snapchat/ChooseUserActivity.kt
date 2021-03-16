package myapp.jsealey.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChooseUserActivity : AppCompatActivity() {

    var chooseUserListView: ListView? = null
    var emails: ArrayList<String> = ArrayList() // Create a new ArrayList of type String
    var keys: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)

        chooseUserListView = findViewById(R.id.chooseUserListView) // Get the ListView

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, emails) // Create Array adapter using the emails ArrayList

        chooseUserListView?.adapter = adapter // Set the ListView to use the adapter above

        // We want to create an event listener that detects when any changes are made to the users in our database and lets us know of these changes

        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val email = snapshot?.child("email")?.value as String // Gets the value of the user's email
                emails.add(email) // Adds the email to the ArrayList
                keys.add(snapshot.key.toString()) // Adds the user key to the key ArrayList
                adapter.notifyDataSetChanged() // We let the adapter know that the data has changed in the ArrayList

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })

        chooseUserListView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val snapMap: Map<String, String> = mapOf("from" to FirebaseAuth.getInstance().currentUser!!.email!!, "imageName" to intent.getStringExtra("imageName").toString(), "imageURL" to intent.getStringExtra("imageURL").toString(), "message" to intent.getStringExtra("imageMessage").toString())

            // Obtain the user key that was selected, create a new snap. push() is used to add a new child with a random name. We set the values using the Map we created beforehand
            FirebaseDatabase.getInstance().getReference().child("users").child(keys.get(i)).child("snaps").push().setValue(snapMap)

            val intent = Intent(this, SnapsActivity::class.java) // Goes back to list of snaps
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Wipes everything out from the back button activity history
            startActivity(intent) // Start the activity




        }
    }
}