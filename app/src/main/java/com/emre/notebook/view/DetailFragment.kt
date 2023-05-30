package com.emre.notebook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.emre.notebook.databinding.FragmentDetailBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var title: String
    private var deleteControl = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener {
            delete()
            deleteControl = true
        }
        deleteControl = false

        auth = Firebase.auth
        firestore = Firebase.firestore

        arguments?.let {
            val info = DetailFragmentArgs.fromBundle(it).info
            title = DetailFragmentArgs.fromBundle(it).title

            if (info == "new") {
                binding.imageView.visibility = View.GONE

            } else {
                binding.imageView.visibility = View.VISIBLE
                val document = firestore.collection(auth.currentUser!!.email!!).document(title)
                document.get().addOnSuccessListener {
                    binding.titleText.setText(it.get("title").toString())
                    binding.mainText.setText(it.get("mainText").toString())
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        val titleText = binding.titleText.text.toString()
        val mainText = binding.mainText.text.toString()
        val currentUser = auth.currentUser!!.email!!

        if (!deleteControl) {
            if (titleText.isNotEmpty()) {
                val note = hashMapOf(
                    "title" to titleText,
                    "mainText" to mainText,
                    "date" to Timestamp.now()
                )
                firestore.collection(currentUser).document(titleText).set(note)
                if (!title.equals(titleText)) {
                    firestore.collection(currentUser).document(title).delete()
                }
            } else {
                Toast.makeText(requireContext(), "The note couldn't be saved. Please fill in the title of your note for it to be saved.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun delete() {
        val currentUser = auth.currentUser!!.email!!
        firestore.collection(currentUser).document(title).delete()

        val action = DetailFragmentDirections.actionDetailFragmentToHomeFragment()
        Navigation.findNavController(binding.root).navigate(action)
    }
}