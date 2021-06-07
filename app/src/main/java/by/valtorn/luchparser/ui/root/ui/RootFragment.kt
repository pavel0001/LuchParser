package by.valtorn.luchparser.ui.root.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentRootBinding
import by.valtorn.luchparser.ui.root.vm.RootVM
import by.valtorn.luchparser.utils.BaseRecyclerAdapter
import by.valtorn.luchparser.utils.viewBinding


class RootFragment : Fragment(R.layout.fragment_root) {

    private val binding by viewBinding(FragmentRootBinding::bind)
    private val viewModel by activityViewModels<RootVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            initUI()
            initVM(it)
        }
    }

    private fun initUI() {
        with(binding) {
            frNumberText.setText("794B0007")
            frEnter.setOnClickListener {
                viewModel.getModem(frNumberText.text.toString())
            }
        }
    }

    private fun initVM(activity: FragmentActivity) {
        viewModel.modem.observe(viewLifecycleOwner) { modem ->
            modem?.let {
                findNavController().navigate(RootFragmentDirections.toResultFragment())
            }
        }
    }
}