package com.emre.notebook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.emre.notebook.R
import com.emre.notebook.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firestore = Firebase.firestore
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addNote) {

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            try {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment("new")
                navController.navigate(action)

            } catch (e:Exception) {

                Toast.makeText(this@MainActivity, "You are already on the adding page", Toast.LENGTH_LONG).show()
            }
        } else if (item.itemId == R.id.signOut) {
            val email = auth.currentUser!!.email
            auth.signOut()

            val intentToLogin = Intent(this@MainActivity, LoginActivity::class.java)
            intentToLogin.putExtra("signOut", email)
            startActivity(intentToLogin)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }
}