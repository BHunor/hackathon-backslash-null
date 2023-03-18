package edu.hackathon.moviematch.ui.welcome

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.*
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.api.film.SearchFilmResponse
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse
import edu.hackathon.moviematch.databinding.FragmentWelcomeSearchBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.Preferences
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class WelcomeSearchFragment : Fragment(), IOnItemClickListener {

    companion object {
        val TAG = WelcomeSearchFragment::class.simpleName;
    }

    private var _binding: FragmentWelcomeSearchBinding? = null
    private val binding get() = _binding!!
    private var _searching:Boolean = false
    private val _viewModel: WelcomeSearchViewModel by activityViewModels{
        WelcomeSearchViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }

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

//        _viewModel = WelcomeSearchViewModelFactory(
//            prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE),
//            repo = Repo
//        ).create(WelcomeSearchViewModel::class.java)

        binding.btnSearch.setOnClickListener {
            if(binding.etSearch.text.isNotEmpty()) {
                //            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                //            binding.etSearch.startAnimation(animation)
                binding.mmlogo.visibility = View.GONE
                binding.tvHintText.visibility = View.GONE
                if (!_searching) {
                    val anim = ObjectAnimator.ofFloat(binding.etSearch, "translationY", 0f, -900f)
                    anim.duration = 1000
                    anim.start()

                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.etSearch.translationY = -900f // Az elem helyzetének beállítása
                        }
                    })
                    val anim2 = ObjectAnimator.ofFloat(binding.btnSearch, "translationY", 0f, -900f)
                    anim2.duration = 1000
                    anim2.start()
                    anim.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            binding.etSearch.translationY = -900f // Az elem helyzetének beállítása
                        }
                    })
                    _searching = true;
                }
                binding.progressBar.visibility = View.VISIBLE
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

        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_welcome_search_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.action_fragment_welcome_search_to_registerFragment2)
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
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, _viewModel.askResponse!!.choices[0].message.content)

                    val queries = _viewModel.lastAskedContents
                    queries?.forEach {
                        Log.d(TAG, it)
                    }

                    if (queries == null) {
                        // handle
                        return@observe
                    }

                    // observe
                    _viewModel.searchForFilms(queries)
                    observeFilmsResult()
                }

                ApiResults.INVALID_TOKEN -> {
                    showErrorOnSnackBar()
                    Log.e(TAG, "INVALID TOKEN")
                    return@observe
                }

                ApiResults.UNKNOWN_ERROR -> {
                    showErrorOnSnackBar()
                    Log.e(TAG, "UNKNOWN ERROR")
                    return@observe
                }

                else -> return@observe
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
                    binding.progressBar.visibility = View.GONE
                    Log.d(TAG, _viewModel.loadFilmsResult.toString())

                    if (_viewModel.loadFilmsResponse?.get(0)?.results!!.isEmpty()) {
                        showErrorOnSnackBar()
                        return@observe
                    }

                    val searchFilmResponses = mutableListOf<SearchFilmResultResponse>()
                    _viewModel.loadFilmsResponse?.forEach {
                        if(searchFilmResponses.size < 9) {
                            searchFilmResponses.add(it.results[0]);
                        }
                    }
                    binding.gridView.adapter = GridAdapter(requireContext(), searchFilmResponses, listener = this@WelcomeSearchFragment)
                    binding.gridView.visibility = View.VISIBLE
                    Log.d(TAG, _viewModel.askResponse.toString())
                }

                ApiResults.INVALID_TOKEN -> {
                    Log.e(TAG, "INVALID TOKEN")
                    showErrorOnSnackBar()
                    return@observe
                }

                ApiResults.UNKNOWN_ERROR -> {
                    Log.e(TAG, "UNKNOWN ERROR")
                    showErrorOnSnackBar()
                    return@observe
                }

                else -> return@observe
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
    }

    private fun showErrorOnSnackBar() {
        val text = resources.getText(R.string.unknown_error)
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .setDuration(5000).show()
    }

    override fun onItemClick(item:SearchFilmResultResponse) {
        _viewModel.selectedItem = item
        findNavController().navigate(R.id.action_fragment_welcome_search_to_detailFragment)
    }


}