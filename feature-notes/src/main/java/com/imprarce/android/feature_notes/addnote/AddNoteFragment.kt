package com.imprarce.android.feature_notes.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imprarce.android.feature_notes.NoteViewModel
import com.imprarce.android.feature_notes.databinding.FragmentAddNoteBinding
import com.imprarce.android.feature_notes.utils.DateFormatUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BottomSheetDialogFragment() {
    private val viewModel: NoteViewModel by activityViewModels()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBottomSheet()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.seekBarValue.setText((progress+1).toString());
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.createButton.setOnClickListener {
            if (binding.editTextTitle.text.toString() != "") {
                createNewNote()
            } else {
                Toast.makeText(requireContext(), "Введите тему", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createNewNote(){
        val currentDate = DateFormatUtil.getCurrentDate()
        viewModel.addNewNote(
            binding.editTextTitle.text.toString(),
            binding.editTextDescription.text.toString(),
            binding.seekBar.progress,
            currentDate
        )
        findNavController().popBackStack()
    }

    private fun setBottomSheet() {
        val bottomSheet: FrameLayout =
            dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!

        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            peekHeight = resources.displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED

            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}