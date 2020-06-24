package hu.ait.mobilefinal

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import hu.ait.mobilefinal.data.User
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    //field
    var selectedPhotoUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        selectPhotoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    /**
     * Called after an image is selected from the image directory.
     */
    override fun onActivityResult(requestCode:Int, resultCode:Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed and check what the selected image was

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)


            ivSelectPhoto.setImageBitmap(bitmap)
            selectPhotoBtn.alpha = 0f




        }
    }

    /**
     * Register the new user into Firebase
     */
    fun registerClick(v: View) {
        if(!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener

            Toast.makeText(this@RegisterActivity, "Successfully Registered",
                Toast.LENGTH_LONG).show()

            uploadImageToFirebase()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

        }.addOnFailureListener {
            Toast.makeText(this@RegisterActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }
    }


    /**
     * uploading the image to firebase
     */
    private fun uploadImageToFirebase() {
        if(selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d("Photo", "Successfully added image: ${it.metadata?.path}")
            ref.downloadUrl.addOnSuccessListener {
                saveUserToFirebase(it.toString())
            }
        }.addOnFailureListener {
            // there was a problem in uploading the image to firebase
        }
    }

    private fun saveUserToFirebase(profileImageUrl : String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("/users/$uid")


        val user = User(uid, etName.text.toString(),etEmail.text.toString(), profileImageUrl)

        ref.setValue(user).addOnSuccessListener {
            Toast.makeText(this@RegisterActivity,
                "User details saved to Database", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Log.d("TRY2", "Failed to set value to database: ${it.message}")
        }


    }


    /**
     * Check that both the fields have some entries.
     */
    fun isFormValid(): Boolean {
        return when {
            etEmail.text.isEmpty() -> {
                etEmail.error = "this field cannot be empty."
                false
            }
            etPassword.text.isEmpty() -> {
                etPassword.error = "this field cannot be empty"
                false
            }
            else -> true
        }
    }

}
