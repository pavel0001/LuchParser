package by.valtorn.luchparser.ui.details.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentDetailsBinding
import by.valtorn.luchparser.ui.details.vm.DetailsVM
import by.valtorn.luchparser.utils.viewBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DetailsVM>()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { viewModel.selectMessage(DetailsFragmentArgs.fromBundle(it).messageId) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            initUI()
            initVM(it)
        }
    }

    private fun initUI() {
        with(binding) {
            fdCancel.setOnClickListener {
                viewModel.clear()
            }
        }
    }

    private fun initVM(activity: FragmentActivity) {
        with(binding) {
            viewModel.selectMessage.observe(viewLifecycleOwner) {
                if (it == null) {
                    findNavController().popBackStack()
                }
                fdText.text = it.toString()

                val dataset = mutableListOf(
                    Entry(-1F, 0F),
                    Entry(2F, 1F),
                    Entry(3F, 2F),
                    Entry(4F, 0F)
                )
                val liveDataset = LineDataSet(dataset, "Hello world!")
                val lineData = LineData(liveDataset)
                lineData.apply {
                    setValueTextSize(16f)
                    setValueTextColor(activity.getColor(R.color.white))
                }
                fdChart.apply {
                    setBackgroundColor(activity.getColor(R.color.black))
                }
                fdChart.data = lineData
                fdChart.invalidate()

            }
        }
    }
}