package myapp.jsealey.snapchat

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class CreateSnapActivity : AppCompatActivity() {

    var createSnapImageView: ImageView? = null
    var messageEditText: EditText? = null

    // Creates a random unique string which we use for the image file names
    val imageName = UUID.randomUUID().toString() + ".jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        messageEditText = findViewById(R.id.messageEditText)

    }

    fun chooseImage(view: View) {
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }

    }

    fun getPhoto() {
        val intent: Intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data;

        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                createSnapImageView = findViewById(R.id.createSnapImageView)
                createSnapImageView?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String?>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Does corresponding checks to make sure that we have the permission to access External storage
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    fun nextClicked(view: View) {
    // Get the data from an ImageView as bytes
        createSnapImageView?.isDrawingCacheEnabled = true
        createSnapImageView?.buildDrawingCache()
        val bitmap = (createSnapImageView?.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Creates a folder to store the image if it doesn't already exist, gives the image a filename
        var uploadTask = FirebaseStorage.getInstance().getReference().child("images").child(imageName).putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "Unable to upload image", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener() { taskSnapshot ->


            Toast.makeText(this, "Image upload successful", Toast.LENGTH_SHORT).show()

            // Move to the chooseUserActivity

            val intent: Intent = Intent(this, ChooseUserActivity::class.java)

            //val firebaseUri = taskSnapshot.storage.downloadUrl // Obtains the url where the image was stored
            val firebaseUri = taskSnapshot.storage.downloadUrl.result // FIX THIS!"!! <<<<<-----------------

            intent.putExtra("imageURL", firebaseUri.toString())
            intent.putExtra("imageName", imageName)
            intent.putExtra("imageMessage", messageEditText?.text.toString())
            startActivity(intent)
            }
    }


}