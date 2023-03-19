package edu.hackathon.moviematch.ui.mood

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse
import edu.hackathon.moviematch.databinding.FragmentFilmResultsBinding
import edu.hackathon.moviematch.databinding.FragmentMoodBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.Preferences
import edu.hackathon.moviematch.ui.feed.FeedFragment
import edu.hackathon.moviematch.ui.welcome.GridAdapter
import edu.hackathon.moviematch.ui.welcome.IOnItemClickListener
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModel
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModelFactory


class FilmResultsFragment : Fragment(), IOnItemClickListener {

    private var _binding: FragmentFilmResultsBinding? = null
    val binding get() = _binding!!

    private val _viewModel: MoodViewModel by activityViewModels{
        MoodViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }

    private val _viewModel2: WelcomeSearchViewModel by activityViewModels{
        WelcomeSearchViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun observer(){
        _viewModel.askResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ApiResults.LOADING -> {
                    return@observe
                }

                ApiResults.SUCCESS -> {
                    Log.d("XXX", _viewModel.lastAskedContents.toString())
                    _viewModel.searchForFilms(_viewModel.lastAskedContents!!.toList())
                    observer2();
                }

                else -> {
                    Log.e(FeedFragment.TAG, "ERROR")
//                    showErrorOnSnackBar()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmResultsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    fun observer2(){
        _viewModel.loadFilmsResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ApiResults.LOADING -> {
                    return@observe
                }

                ApiResults.SUCCESS -> {
                    Log.d("XXX", _viewModel.loadFilmsResponse?.get(0)?.results.toString());
                    binding.gridView.adapter = _viewModel.loadFilmsResponse?.get(0)?.results?.let { GridAdapter(requireContext(), it.toMutableList(), listener = this@FilmResultsFragment) }
                    binding.progressBar2.visibility = View.GONE
                    binding.gridView.visibility = View.VISIBLE
                }

                else -> {
                    Log.e(FeedFragment.TAG, "ERROR")
//                    showErrorOnSnackBar()
                }
            }
        }
    }

    override fun onItemClick(item: SearchFilmResultResponse) {
        _viewModel2.selectedItem = item
        findNavController().navigate(R.id.action_filmResultsFragment_to_detailFragment)
    }


}