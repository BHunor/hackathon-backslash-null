package edu.hackathon.moviematch.ui.welcome

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import edu.hackathon.moviematch.R
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.Preferences

class DetailFragment : Fragment() {
    private val _viewModel: WelcomeSearchViewModel by activityViewModels{
        WelcomeSearchViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
}