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
import edu.hackathon.moviematch.databinding.FragmentMoodBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.Preferences
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModel
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModelFactory


class MoodFragment : Fragment() {

    private var _binding: FragmentMoodBinding? = null
    val binding get() = _binding!!

    private val _viewModel: MoodViewModel by activityViewModels{
        MoodViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        binding.happyBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(0)){
                _viewModel.selectedMoodIndexes.add(0)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_yellow)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(0)
                it.setBackgroundColor(
                    resources.getColor(R.color.yellow)
                )
            }
        }

        binding.sadBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(1)){
                _viewModel.selectedMoodIndexes.add(1)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_sad)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(1)
                it.setBackgroundColor(
                    resources.getColor(R.color.sad)
                )
            }
        }

        binding.tiredBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(2)){
                _viewModel.selectedMoodIndexes.add(2)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_tired)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(2)
                it.setBackgroundColor(
                    resources.getColor(R.color.tired)
                )
            }
        }

        binding.romanticBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(3)){
                _viewModel.selectedMoodIndexes.add(3)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_romatic)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(3)
                it.setBackgroundColor(
                    resources.getColor(R.color.romantic)
                )
            }
        }

        binding.adventureBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(4)){
                _viewModel.selectedMoodIndexes.add(4)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_adventure)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(4)
                it.setBackgroundColor(
                    resources.getColor(R.color.adventure)
                )
            }
        }

        binding.boredBtn.setOnClickListener{
            it.setBackgroundColor(
                resources.getColor(R.color.green)
            )
            if(!_viewModel.selectedMoodIndexes.contains(5)){
                _viewModel.selectedMoodIndexes.add(5)
                it.setBackgroundColor(
                    resources.getColor(R.color.darker_bored)
                )
            }else{
                _viewModel.selectedMoodIndexes.remove(5)
                it.setBackgroundColor(
                    resources.getColor(R.color.gray)
                )
            }
        }
        binding.search.setOnClickListener{
            _viewModel.askForFilms()
            findNavController().navigate(R.id.action_fragment_mood_to_filmResultsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}