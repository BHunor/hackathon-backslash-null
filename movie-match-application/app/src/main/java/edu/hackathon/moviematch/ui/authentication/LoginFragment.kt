package edu.hackathon.moviematch.ui.authentication

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
import edu.hackathon.moviematch.api.user.LoginResults
import edu.hackathon.moviematch.databinding.FragmentLoginBinding
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.Preferences
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchFragment
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModel
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModelFactory



class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    private val _viewModel: AuthenticationViewModel by activityViewModels{
        AuthenticationViewModelFactory(prefs = requireActivity().getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_PRIVATE), repo = Repo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener() {
            if (binding.emailEdittext.text.isEmpty() || binding.passwordEdittext.text.isEmpty()) {
                return@setOnClickListener
            }
            _viewModel.login(
                binding.emailEdittext.text.toString(),
                binding.passwordEdittext.text.toString()
            )
            _viewModel.loginResult.observe(viewLifecycleOwner) { result ->
                when (result) {

                    LoginResults.LOADING -> {
                        // TODO show progress bar
                        return@observe
                    }

                    LoginResults.SUCCESS -> {
                        findNavController().navigate(R.id.fragment_feed)
                    }

                    LoginResults.INVALID_CREDENTIALS -> {
                        showErrorOnSnackBar();
                        return@observe
                    }

                    LoginResults.UNKNOWN_ERROR -> {
                        showErrorOnSnackBar();
                        return@observe
                    }

                    else -> return@observe
                }
            }

        }
    }
    fun showErrorOnSnackBar() {
        val text = "Invalid user credentials!"
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .setDuration(5000).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}