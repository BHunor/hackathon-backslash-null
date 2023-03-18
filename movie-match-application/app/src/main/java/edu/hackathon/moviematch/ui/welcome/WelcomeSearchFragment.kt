package edu.hackathon.moviematch.ui.welcome

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.api.search.SearchApi
import edu.hackathon.moviematch.databinding.FragmentWelcomeSearchBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.Preferences

class WelcomeSearchFragment : Fragment() {

    companion object {
        val TAG = WelcomeSearchFragment::class.simpleName;
    }

    private var _binding: FragmentWelcomeSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var _viewModel: WelcomeSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewModel = WelcomeSearchViewModelFactory(
            prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE),
            repo = Repo
        ).create(WelcomeSearchViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            _viewModel.askResult.removeObservers(viewLifecycleOwner)
            if (binding.etSearch.text.isEmpty()) {
                return@setOnClickListener
            }

            _viewModel.askForFilms(
                binding.etSearch.text.toString()
            )
            observeAskResult()
        }
    }

    private fun observeAskResult() {
        _viewModel.askResult.observe(viewLifecycleOwner) {result ->
            when (result) {

                ApiResults.LOADING -> {
                    // TODO show progress bar
                    return@observe
                }

                ApiResults.SUCCESS -> {
                    Log.d(TAG, _viewModel.askResponse.toString())
                }

                ApiResults.INVALID_TOKEN -> {
                    // WHAT TODO
                    Log.e(TAG, "INVALID TOKEN")
                    return@observe
                }

                ApiResults.UNKNOWN_ERROR -> {
                    Log.e(TAG, "UNKNOWN ERROR")
                    return@observe
                }

                else -> return@observe
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}