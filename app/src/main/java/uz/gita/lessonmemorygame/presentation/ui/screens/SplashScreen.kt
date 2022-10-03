package uz.gita.lessonmemorygame.presentation.ui.screens


import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.lessonmemorygame.R
import uz.gita.lessonmemorygame.databinding.ScreenSplashBinding

import uz.gita.lessonmemorygame.presentation.viewmodel.SplashViewModel
import uz.gita.lessonmemorygame.presentation.viewmodel.impl.SplashViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewBinding: ScreenSplashBinding by viewBinding(ScreenSplashBinding::bind)
    private val navController by lazy { findNavController() }
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.openLevelScreenLiveData.observe(this) {

            navController.navigate(SplashScreenDirections.actionSplashScreenToMenuScreen())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            val animation =  AnimationUtils.loadAnimation(requireContext(),R.anim.bounce)
            viewBinding.image.startAnimation(animation)
        }

    }


}