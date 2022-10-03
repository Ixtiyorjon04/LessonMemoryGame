package uz.gita.lessonmemorygame.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.lessonmemorygame.R
import uz.gita.lessonmemorygame.databinding.ScreenAboutBinding



@AndroidEntryPoint
class AboutScreen:Fragment(R.layout.screen_about) {
    private val viewBinding by viewBinding(ScreenAboutBinding::bind)
//    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val navController by lazy { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.ok.setOnClickListener {
            navController.navigateUp()
        }
    }
}