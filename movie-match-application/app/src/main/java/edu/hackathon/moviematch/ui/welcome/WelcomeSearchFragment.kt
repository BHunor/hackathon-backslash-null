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
    private var _searching:Boolean = false
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
            if(!binding.etSearch.text.isEmpty()) {
                //            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                //            binding.etSearch.startAnimation(animation)
                binding.mmlogo.visibility = View.GONE
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