package com.imprarce.android.feature_notes

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imprarce.android.feature_notes.adapter.NoteAdapter
import com.imprarce.android.feature_notes.databinding.FragmentNotesBinding
import com.imprarce.android.feature_notes.helpers.OnDeleteNoteClicked
import com.imprarce.android.feature_notes.helpers.OnEditNoteClicked
import com.imprarce.android.local.note.NoteItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(), OnDeleteNoteClicked<NoteItem>, OnEditNoteClicked {
    private val viewModel: NoteViewModel by activityViewModels()

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }

        viewModel.noteList.observe(viewLifecycleOwner){ notes ->
            setAdapter(notes)
        }

        binding.toolbar.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (binding.recyclerViewNotes.adapter != null) {
                    filterRecyclerViewItems(query)
                } else {
                    binding.toolbar.editTextSearch.text.clear()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.toolbar.buttonClear.visibility = View.GONE
                } else {
                    binding.toolbar.buttonClear.visibility = View.VISIBLE
                }
            }
        })

        binding.toolbar.buttonClear.setOnClickListener {
            binding.toolbar.editTextSearch.text.clear()
            hideKeyboard()
        }

    }

    private fun setAdapter(noteList: List<NoteItem>) {
        val adapter = NoteAdapter(noteList, this, this)
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDeleteItemClick(item: NoteItem) {
        viewModel.deleteNote(item)
    }

    override fun onItemClicked(item: NoteItem) {
        val bundle = bundleOf("id_note" to item.note_id)
        findNavController().navigate(R.id.action_notesFragment_to_editorNoteFragment, bundle)
    }

    private fun filterRecyclerViewItems(query: String) {
        val adapter = binding.recyclerViewNotes.adapter as NoteAdapter
        adapter.filter.filter(query)
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}