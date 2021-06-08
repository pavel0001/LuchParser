package by.valtorn.luchparser.ui.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentDetailsBinding
import by.valtorn.luchparser.ui.details.vm.DetailsVM
import by.valtorn.luchparser.utils.viewBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DetailsVM>()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { viewModel.selectMessage(DetailsFragmentArgs.fromBundle(it).messageId) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initVM()
    }

    private fun initUI() {
        with(binding) {
            fdCancel.setOnClickListener {
                viewModel.clear()
            }
        }
    }

    private fun initVM() {
        with(binding) {
            viewModel.selectMessage.observe(viewLifecycleOwner) {
                if (it == null) {
                    findNavController().popBackStack()
                }
                fdText.text = it.toString()
            }
        }
    }
}