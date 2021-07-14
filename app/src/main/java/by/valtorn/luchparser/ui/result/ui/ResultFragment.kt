package by.valtorn.luchparser.ui.result.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentResultBinding
import by.valtorn.luchparser.ui.root.vm.RootVM
import by.valtorn.luchparser.utils.BaseRecyclerAdapter
import by.valtorn.luchparser.utils.viewBinding


class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)
    private val viewModel by activityViewModels<RootVM>()

    private var modemId = String()

    private val adapter = BaseRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modemId = ResultFragmentArgs.fromBundle(requireArguments()).modemId
        initUI()
        activity?.let {
            initVM(it)
        }
    }

    private fun initUI() {
        with(binding) {
            root.setOnRefreshListener {
                if (modemId.isNotEmpty())
                    viewModel.getModem(modemId)
            }
            frClear.setOnClickListener {
                viewModel.clear()
            }
        }
    }

    private fun initVM(activity: FragmentActivity) {
        with(binding) {
            frRecycler.layoutManager = LinearLayoutManager(context)
            frRecycler.adapter = adapter

            viewModel.messages.observe(viewLifecycleOwner) { modemList ->
                frRefreshLayot.isRefreshing = false
                if (modemList == null) {
                    findNavController().popBackStack()
                } else {
                    adapter.updateWithDiffUtils(modemList.map {
                        MessageItem(activity, it)
                    })
                }
            }
        }
    }
}