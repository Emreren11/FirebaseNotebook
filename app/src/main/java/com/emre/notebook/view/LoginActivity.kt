package com.emre.notebook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.emre.notebook.R
import com.emre.notebook.databinding.ActivityLoginBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var email = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore

        val currentUser = auth.currentUser

        if (currentUser != null) {
            val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intentToMain)
            finish()
        }
        val intentFromMain = intent
        val signOutEmail = intentFromMain.getStringExtra("signOut")

        signOutEmail?.let {
            binding.emailText.setText(signOutEmail)
        }
    }

    fun signUp(view: View) {

        email = binding.emailText.text.toString()
        pass = binding.passText.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {

                val note = hashMapOf<String, Any>()
                note.put("title", "Sample Note")
                note.put("mainText", "Sample Note")
                note.put("date", Timestamp.now())
                firestore.collection(email).document(note.get("title").toString()).set(note)

                val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentToMain)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@LoginActivity, "Enter email and password", Toast.LENGTH_LONG).show()
        }
    }

    fun signIn(view: View) {

        email = binding.emailText.text.toString()
        pass = binding.passText.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {

            auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {

                val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentToMain)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@LoginActivity, "Enter email and password", Toast.LENGTH_LONG).show()
        }
    }
}