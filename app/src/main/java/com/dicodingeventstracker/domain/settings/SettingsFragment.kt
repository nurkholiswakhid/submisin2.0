package com.dicodingeventstracker.domain.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicodingeventstracker.databinding.FragmentSettingsBinding
import com.dicodingeventstracker.domain.viewmodels.SettingThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingThemeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup back button
        binding.btnBackSetting.setOnClickListener {
            requireActivity().onBackPressed()
        }

        // Observe theme settings
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.themeSetting.collect { isDarkModeActive ->
                binding.switchChangeTheme.isChecked = isDarkModeActive
            }
        }

        // Handle theme change
        binding.switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}