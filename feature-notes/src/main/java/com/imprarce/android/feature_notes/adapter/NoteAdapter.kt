package com.imprarce.android.feature_notes.adapter

import android.app.AlertDialog
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.imprarce.android.feature_notes.R
import com.imprarce.android.feature_notes.helpers.OnDeleteNoteClicked
import com.imprarce.android.feature_notes.helpers.OnEditNoteClicked
import com.imprarce.android.feature_notes.utils.ThemeUtils.isDarkTheme
import com.imprarce.android.local.note.NoteItem
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(
    private val notes: List<NoteItem>,
    private val onDeleteNoteClicked: OnDeleteNoteClicked<NoteItem>,
    private val onEditNoteClicked: OnEditNoteClicked
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), Filterable {

    private var filteredNotes : List<NoteItem> = notes.sortedByDescending { it.priority }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note_recycler_view, parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = filteredNotes[position]
        holder.titleTextView.text = currentNote.title
        holder.descriptionTextView.text = currentNote.description
        holder.dateCreateTextView.text = "Заметка создана: " + currentNote.date

        val constraintLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutNote)

        setPriorityBackground(constraintLayout, currentNote.priority)

        holder.deleteIcon.setOnClickListener {
            showDeleteConfirmationDialog(it.context) {
                onDeleteNoteClicked.onDeleteItemClick(currentNote)
            }
        }

        holder.itemView.setOnClickListener {
            onEditNoteClicked.onItemClicked(currentNote)
        }
    }

    override fun getItemCount() = filteredNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_TextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_TextView)
        val dateCreateTextView: TextView = itemView.findViewById(R.id.date_create_TextView)
        val deleteIcon: ImageView = itemView.findViewById(R.id.delete_icon)
    }

    private fun setPriorityBackground(constraintLayout: ConstraintLayout, priority: Int){
        when(priority+1){
            1 -> constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_note_first)
            2 -> constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_note_second)
            3 -> constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_note_third)
            4 -> constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_note_fourth)
            5 -> constraintLayout.setBackgroundResource(R.drawable.custom_rectangle_note_fifth)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, onDeleteConfirmed: () -> Unit) {
        val message = "Вы уверены, что хотите удалить эту заметку?"
        val spannableMessage = SpannableString(message)

        val colorResId = if (isDarkTheme(context)) {
            R.color.dark_gray
        } else {
            R.color.black
        }

        spannableMessage.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
            0, message.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle("Удаление заметки")
            .setMessage(spannableMessage)
            .setPositiveButton("Удалить") { dialog, _ ->
                onDeleteConfirmed.invoke()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<NoteItem>()
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                for (note in notes) {
                    if (note.title.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(note)
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredNotes = results?.values as List<NoteItem>
                notifyDataSetChanged()
            }
        }
    }


}