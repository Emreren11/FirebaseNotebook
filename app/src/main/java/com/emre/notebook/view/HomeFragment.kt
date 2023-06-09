package com.emre.notebook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.emre.notebook.R
import com.emre.notebook.adapter.NoteAdapter
import com.emre.notebook.databinding.FragmentHomeBinding
import com.emre.notebook.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteList: ArrayList<Notes>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = Firebase.firestore
        auth = Firebase.auth
        noteList = ArrayList<Notes>()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        noteAdapter = NoteAdapter(noteList)
        binding.recyclerView.adapter = noteAdapter
    }

    private fun getData() {

        val currentUser = auth.currentUser!!.email!!
        firestore.collection(currentUser).orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {

                        val documents = value.documents

                        noteList.clear()
                        for (document in documents) {
                            val title = document.get("title") as String
                            val mainText = document.get("mainText") as String
                            val documentID = document.id
                            val note = Notes(title, mainText, documentID)
                            noteList.add(note)

                        }
                        noteAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }

}