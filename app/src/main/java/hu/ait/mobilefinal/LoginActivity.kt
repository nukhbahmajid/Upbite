package hu.ait.mobilefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.multidex.MultiDex
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        setContentView(R.layout.activity_login)
    }

    /**
     * If the user already exists, log them in and move to the Home
     * Screen Activity.
     */
    fun loginClick(v: View) {
        if(!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            startActivity(Intent(this@LoginActivity, HomeScreen::class.java))
            return@addOnSuccessListener

        }.addOnFailureListener {
            Toast.makeText(this@LoginActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }

    }

    /**
     * If the user does not have an account, move to the register activity.
     */
    fun moveToSignUp(v: View) {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
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
