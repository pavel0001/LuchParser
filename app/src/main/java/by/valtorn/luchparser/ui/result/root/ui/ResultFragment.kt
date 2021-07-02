package by.valtorn.luchparser.ui.result.root.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentResultBinding
import by.valtorn.luchparser.ui.root.vm.ResultOption
import by.valtorn.luchparser.ui.root.vm.RootVM
import by.valtorn.luchparser.utils.BaseRecyclerAdapter
import by.valtorn.luchparser.utils.viewBinding
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)
    private val viewModel by activityViewModels<RootVM>()

    private var selectOption = ResultOption.TABLE

    private val adapter = BaseRecyclerAdapter { item, _ ->
        if (item is MessageItem) {
            item.message.id?.let {
                findNavController().navigate(ResultFragmentDirections.toDetails(it))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            initVM(it)
        }
        initUI()
    }

    private fun initUI() {
        with(binding) {
            //   if (selectOption == ResultOption.TABLE) {
            frRecycler.layoutManager = LinearLayoutManager(context)
            frRecycler.adapter = adapter
            // }
            frBack.setOnClickListener {
                viewModel.clear()
            }
            frCharts.setOnClickListener {
                viewModel.selectOption(ResultOption.CHARTER)
            }
            frTables.setOnClickListener {
                viewModel.selectOption(ResultOption.TABLE)
            }
        }
    }

    private fun initVM(activity: FragmentActivity) {
        with(binding) {
            viewModel.messages.observe(viewLifecycleOwner) { modemList ->
                if (modemList == null) {
                    findNavController().popBackStack()
                } else {
                    adapter.updateWithDiffUtils(modemList.map {
                        MessageItem(activity, it)
                    })
                }
            }
            viewModel.selectedOption.observe(viewLifecycleOwner) {
                selectOption = it
                when (it) {
                    ResultOption.CHARTER -> {
                        frChartersIndicator.isGone = false
                        frTablesIndicator.isGone = true
                        frRecycler.isGone = true
                        frChart.isGone = false
                    }
                    else -> {
                        frChartersIndicator.isGone = true
                        frTablesIndicator.isGone = false
                        frRecycler.isGone = false
                        frChart.isGone = true
                    }
                }
            }
            viewModel.datasetCharts.observe(viewLifecycleOwner) {
                val livePower = LineDataSet(it.power, "Напряжение").apply {
                    setColor(Color.RED, 1)
                }
                val liveDistance = LineDataSet(it.distance, "Расстояние")
                val liveTemperature = LineDataSet(it.temperature, "Температура")

                val datasets = mutableListOf<ILineDataSet>()
                datasets.add(livePower)
                datasets.add(liveDistance)
                datasets.add(liveTemperature)

                val lineData = LineData(datasets)

                lineData.apply {
                    setValueTextSize(16f)
                    setValueTextColor(activity.getColor(R.color.white))
                }
                frChart.apply {
                    //setBackgroundColor(activity.getColor(R.color.black))
                }
                frChart.data = lineData
                frChart.invalidate()
            }
        }
    }
}