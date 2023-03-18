package edu.hackathon.moviematch.ui.welcome

import edu.hackathon.moviematch.R
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import edu.hackathon.moviematch.api.film.FilmDetails
import edu.hackathon.moviematch.databinding.FragmentDetailBinding
import edu.hackathon.moviematch.databinding.FragmentWelcomeSearchBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.Preferences


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val _viewModel: WelcomeSearchViewModel by activityViewModels {
        WelcomeSearchViewModelFactory(
            prefs = requireActivity().getSharedPreferences(
                Preferences.PREFERENCES_NAME,
                Context.MODE_PRIVATE
            ), repo = Repo
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.detailMovie(_viewModel.selectedItem!!.movieId)
        _viewModel.detailResult.observe(viewLifecycleOwner) {result ->
            when (result) {

                ApiResults.LOADING -> {
                    // TODO show progress bar
                    return@observe
                }
                ApiResults.SUCCESS -> {
                    var movie:FilmDetails = _viewModel.movieDetailsResponse!!
                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500/"+movie.backdrop_path)
                        .placeholder(R.drawable.rounded)
                        .fitCenter()
                        .into(_binding?.imageView!!)
                    binding.imageView.background = ContextCompat.getDrawable(requireContext(), R.drawable.rounded)
                    binding.title.text = movie.original_title
                    binding.desc.text = movie.overview
                    binding.cat.text = "Categories:";
                    movie.genres.forEach {
                        binding.cat.append(it.name + "; ")
                    }
                    binding.viewswitcher.showNext()
                    binding.btnSearch3.setOnClickListener{
                        _viewModel.backFromDetails = true
                        findNavController().navigate(R.id.action_detailFragment_to_fragment_welcome_search)
                    }
                    binding.btnSearch2.setOnClickListener{
                        //TODO go login fragment
//                        findNavController().navigate(R.id.action_detailFragment_to_fragment_welcome_search)
                    }
                }

                ApiResults.INVALID_TOKEN -> {
                    // WHAT TODO
                    Log.e(WelcomeSearchFragment.TAG, "INVALID TOKEN")
                    return@observe
                }

                ApiResults.UNKNOWN_ERROR -> {
                    Log.e(WelcomeSearchFragment.TAG, "UNKNOWN ERROR")
                    return@observe
                }

                else -> return@observe
            }
        }
    }
}