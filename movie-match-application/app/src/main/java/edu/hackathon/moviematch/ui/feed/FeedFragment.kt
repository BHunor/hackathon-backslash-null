package edu.hackathon.moviematch.ui.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse
import edu.hackathon.moviematch.databinding.FragmentFeedBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.MainActivity
import edu.hackathon.moviematch.ui.Preferences
import edu.hackathon.moviematch.ui.welcome.*


class FeedFragment : Fragment(), IOnItemClickListener {

    companion object {
        val TAG = FeedFragment::class.simpleName
    }

    private var _binding: FragmentFeedBinding? = null
    val binding get() = _binding!!

    private val _viewModel: FeedViewModel by activityViewModels{
        FeedViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }

    private val _viewModel2: WelcomeSearchViewModel by activityViewModels{
        WelcomeSearchViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).bottomNavView.visibility = View.VISIBLE

//        _viewModel.askForFilms()
//        observeAskResult()
        _viewModel.similarMovies()
        observeSimilar()
    }

    private fun observeAskResult() {
        _viewModel.askResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ApiResults.LOADING -> {
                    return@observe
                }

                ApiResults.SUCCESS -> {
                    val queries = _viewModel.lastAskedContents
                    queries?.forEach {
                        Log.d(TAG, it)
                    }

                    if (queries == null) {
                        //handle
                        return@observe
                    }

                    _viewModel.searchForFilms(queries)
                    observeFilmsResult()
                }

                else -> {
                    Log.e(TAG, "ERROR")
                    showErrorOnSnackBar()
                }
            }
        }
    }

    private fun observeFilmsResult() {
        _viewModel.loadFilmsResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ApiResults.LOADING -> {
                    // TODO show progress bar
                    return@observe
                }

                ApiResults.SUCCESS -> {
//                    _viewModel.similarMovies()
//                    observeSimilar()
                    binding.progressBar.visibility = View.GONE
                    Log.d(WelcomeSearchFragment.TAG, _viewModel.loadFilmsResult.toString())

                    if (_viewModel.loadFilmsResponse?.get(0)?.results!!.isEmpty()) {
                        showErrorOnSnackBar()
                        return@observe
                    }

                    var searchFilmResponses = mutableListOf<SearchFilmResultResponse>()
                    _viewModel.loadFilmsResponse?.forEach {
                            searchFilmResponses.add(it.results.get(0));
                    }
                    binding.gridView.adapter = GridAdapter(requireContext(), searchFilmResponses, listener = this@FeedFragment)
                    binding.gridView.visibility = View.VISIBLE
                }

                ApiResults.INVALID_TOKEN -> {
                    // WHAT TODO
                    Log.e(WelcomeSearchFragment.TAG, "INVALID TOKEN")
                    showErrorOnSnackBar()
                    return@observe
                }

                ApiResults.UNKNOWN_ERROR -> {
                    Log.e(WelcomeSearchFragment.TAG, "UNKNOWN ERROR")
                    showErrorOnSnackBar()
                    return@observe
                }

                else -> return@observe
            }
        }
    }

    fun observeSimilar(){
        _viewModel.similarMoviesResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ApiResults.LOADING -> {
                    return@observe
                }

                ApiResults.SUCCESS -> {
                    Log.d("xxx","asdasd")
                    Log.d("xxx", _viewModel.similarMoviesRespone.toString())
                    binding.gridView.adapter = GridAdapter(requireContext(), _viewModel.similarMoviesRespone!!.results.toMutableList(), listener = this@FeedFragment)
                    binding.gridView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }

                else -> {
                    Log.e(TAG, "ERROR")
                    showErrorOnSnackBar()
                }
            }
        }
    }
    override fun onItemClick(item:SearchFilmResultResponse) {
        _viewModel2.selectedItem = item
        findNavController().navigate(R.id.action_fragment_feed_to_detailFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorOnSnackBar() {
        val text = resources.getText(R.string.unknown_error)
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .setDuration(5000).show()
    }
}