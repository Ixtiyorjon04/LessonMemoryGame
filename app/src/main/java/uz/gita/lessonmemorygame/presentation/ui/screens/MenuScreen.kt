package uz.gita.lessonmemorygame.presentation.ui.screens

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.lessonmemorygame.R
import uz.gita.lessonmemorygame.databinding.ScreenMenuBinding

import uz.gita.lessonmemorygame.presentation.viewmodel.GameViewModel
import uz.gita.lessonmemorygame.presentation.viewmodel.impl.GameViewModelImpl

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.screen_menu) {
    private val viewBinding: ScreenMenuBinding by viewBinding(ScreenMenuBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        valueAnimated(viewBinding.btnExit, 1300f)
        valueAnimated(viewBinding.btnAbout, 1000f)
        valueAnimated(viewBinding.btnPlay, 700f)
        valueAnimated(viewBinding.appText, 150f)
        valueAnimated(viewBinding.appText1, 350f)
        viewBinding.btnPlay.setOnClickListener {
            navController.navigate(MenuScreenDirections.actionMenuScreenToLevelScreen())
        }
        viewBinding.btnExit.setOnClickListener {
            activity?.finish()
        }
        viewBinding.btnAbout.setOnClickListener {
            navController.navigate(MenuScreenDirections.actionMenuScreenToAboutScreen())
        }
    }

    private fun valueAnimated(view: View, y: Float) {
        ValueAnimator.ofFloat(0f, y).apply {
            addUpdateListener {
                view.y = it.animatedValue as Float
            }
            duration = 2000
//            repeatCount = 1
//            repeatMode = ValueAnimator.RESTART
            interpolator = BounceInterpolator()
            start()
        }

    }
}