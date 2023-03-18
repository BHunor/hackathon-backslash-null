package edu.hackathon.moviematch.ui.welcome

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.databinding.FragmentWelcomeSearchBinding
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
            prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE)
        ).create(WelcomeSearchViewModel::class.java)
    }

}