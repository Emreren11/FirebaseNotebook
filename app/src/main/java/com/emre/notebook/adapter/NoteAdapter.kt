package com.emre.notebook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.emre.notebook.databinding.RecyclerRowBinding
import com.emre.notebook.model.Notes
import com.emre.notebook.view.HomeFragmentDirections

class NoteAdapter(private val noteList: ArrayList<Notes>): RecyclerView.Adapter<NoteAdapter.NoteHolder>() {
    class NoteHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val title = noteList[position].title
        holder.binding.rcTextView.text = title
        val mainText = noteList[position].mainText
        if (mainText.isEmpty()) {
            holder.binding.rcMainTextView.visibility = View.GONE
        } else if (mainText.length > 100) {
            holder.binding.rcMainTextView.text = mainText.take(100) + "..."
        } else {
            holder.binding.rcMainTextView.text = mainText
        }

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment("old", title)
            Navigation.findNavController(it).navigate(action)
        }
    }
}