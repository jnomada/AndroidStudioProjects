package myapp.jsealey.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class SnapsActivity : AppCompatActivity() {

    // Create an instance of Firebase Authentication
    val auth = FirebaseAuth.getInstance()

    var snapsListView: ListView? = null
    var emails: ArrayList<String> = ArrayList()
    var snaps: ArrayList<DataSnapshot> = ArrayList()

    ///////////////////////////////////////////////////////////
    // onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snaps)

        snapsListView = findViewById(R.id.snapsListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, emails)
        snapsListView?.adapter = adapter

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("snaps").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                emails.add(snapshot.child("from").value as String)
                snaps.add(snapshot!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })

        snapsListView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val snapshot = snaps.get(i)

            var intent = Intent(this, ViewSnapActivity::class.java)

            // First obtain the information and then pass on to the next activity

            intent.putExtra("imageName", snapshot.child("imageName").value as String)
            intent.putExtra("imageURL", snapshot.child("imageURL").value as String)
            Log.i("ImageURL", snapshot.child("imageURL").value as String)
            intent.putExtra("message", snapshot.child("message").value as String)
            intent.putExtra("snapkey", snapshot.key)

            startActivity(intent)
        }


    }

    ///////////////////////////////////////////////////////////
    // Menu methods

    // Creates the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Create a menuInflater and then use it on the menu resource that we created
        val inflater = menuInflater
        inflater.inflate(R.menu.snaps, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId == R.id.createSnap) {
            // Creates a new snap
            val intent = Intent(this, CreateSnapActivity::class.java)
            startActivity(intent)
        } else if (item?.itemId == R.id.logout) {
            // Logs the user out
            auth.signOut() // Signs the user out of Firebase
            finish() // Finishes the activity and moves the user back a screen
            Toast.makeText(this, "You have successfully signed out", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        auth.signOut()
        super.onBackPressed()
    }

}