package com.imprarce.android.feature_notes.editnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.imprarce.android.feature_notes.NoteViewModel
import com.imprarce.android.feature_notes.databinding.FragmentEditorNoteBinding
import com.imprarce.android.feature_notes.utils.DateFormatUtil
import com.imprarce.android.local.note.NoteItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditorNoteFragment : Fragment() {
    private val viewModel: NoteViewModel by activityViewModels()
    private var _binding: FragmentEditorNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditorNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteId = arguments?.getInt("id_note")

        if(noteId != null){
            viewModel.getNoteItem(noteId)
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.seekBarValue.setText((progress+1).toString());
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        viewModel.noteItem.observe(viewLifecycleOwner){ note ->
            binding.editTextTitle.setText(note.title)
            binding.editTextDescription.setText(note.description)
            binding.seekBar.progress = note.priority
            binding.seekBarValue.setText((binding.seekBar.progress+1).toString())
        }

        binding.saveButton.setOnClickListener {
            if(binding.editTextTitle.text.toString() != ""){
                val currentDate = DateFormatUtil.getCurrentDate()
                viewModel.updateNote(noteItem = NoteItem(
                    note_id = noteId!!,
                    binding.editTextTitle.text.toString(),
                    binding.editTextDescription.text.toString(),
                    binding.seekBar.progress,
                    currentDate
                ))
                Toast.makeText(requireContext(), "Вы изменили заметку", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Тема не может быть пустой", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}