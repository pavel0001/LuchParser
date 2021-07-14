package by.valtorn.luchparser.ui.root.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.valtorn.luchparser.R
import by.valtorn.luchparser.databinding.FragmentRootBinding
import by.valtorn.luchparser.ui.root.vm.RootVM
import by.valtorn.luchparser.utils.viewBinding
import timber.log.Timber
import java.util.*

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

class RootFragment : Fragment(R.layout.fragment_root) {

    private val binding by viewBinding(FragmentRootBinding::bind)
    private val viewModel by activityViewModels<RootVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            initUI(it)
            initVM(it)
        }
    }

    private fun initUI(activity: FragmentActivity) {
        with(binding) {
            //frNumberText.setText("794B0007")
            frEnter.setOnClickListener {
                if (!frNumberText.text.isNullOrBlank() && frNumberText.text.toString().length == 8) {
                    viewModel.getModem(
                        frNumberText.text.toString().toUpperCase(Locale.getDefault())
                    )
                } else {
                    frNumberText.error = getString(R.string.root_error_number)
                }
                activity.hideKeyboard(binding.root)
            }
        }
    }

    private fun initVM(activity: FragmentActivity) {
        with(binding) {
            viewModel.messages.observe(viewLifecycleOwner) { modem ->
                Timber.i("modemlist ${modem.toString()}")
                modem?.let {
                    if (it.isNotEmpty()) {
                        findNavController().navigate(
                            RootFragmentDirections.toResultFragment(
                                frNumberText.text.toString().toUpperCase(Locale.getDefault())
                            )
                        )
                    } else {
                        Toast.makeText(activity, R.string.root_message_empty, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
            viewModel.progress.observe(viewLifecycleOwner) { frProgress.isGone = !it }
        }
    }
}