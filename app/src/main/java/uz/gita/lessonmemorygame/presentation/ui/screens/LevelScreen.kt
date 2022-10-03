package uz.gita.lessonmemorygame.presentation.ui.screens

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.lessonmemorygame.R

import uz.gita.lessonmemorygame.data.models.Level
import uz.gita.lessonmemorygame.databinding.ScreenLevelBinding


import uz.gita.lessonmemorygame.presentation.viewmodel.LevelViewModel
import uz.gita.lessonmemorygame.presentation.viewmodel.impl.LevelViewModelImpl
import kotlin.math.ceil

@AndroidEntryPoint
class LevelScreen : Fragment(R.layout.screen_level) {
    private  lateinit var btnEasy:AppCompatButton
    private  lateinit var btnMedium:AppCompatButton
    private  lateinit var btnHard:AppCompatButton
    private val viewBinding by viewBinding(ScreenLevelBinding::bind)
    private val navController by lazy { findNavController() }
    private val viewModel: LevelViewModel by viewModels<LevelViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        viewBinding.btnEasy.setOnClickListener { viewModel.openGame(Level.EASY) }
//        viewBinding.btnMedium.setOnClickListener { viewModel.openGame(Level.MEDIUM) }
//        viewBinding.btnHard.setOnClickListener { viewModel.openGame(Level.HARD) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        lifecycleScope.launch(Dispatchers.Default) {
//            delay(2000)
//            view.isClickable =false
//        }
//        view.isClickable =true
        viewBinding.bgButton.setOnClickListener{
            navController.navigate(LevelScreenDirections.actionLevelScreenToMenuScreen())
        }
        viewModel.openGameLiveData.observe(viewLifecycleOwner, openGameObserver)
        btnEasy=view.findViewById(R.id.btnEasy)
        btnMedium=view.findViewById(R.id.btnMedium)
        btnHard=view.findViewById(R.id.btnHard)
        btnEasy.setOnClickListener { viewModel.openGame(Level.EASY) }
        btnMedium.setOnClickListener { viewModel.openGame(Level.MEDIUM) }
        btnHard.setOnClickListener { viewModel.openGame(Level.HARD) }
        valueAnimated(viewBinding.appText, 300f)
        valueAnimated(btnEasy, 700f)
        valueAnimated(btnMedium, 1000f)
        valueAnimated(btnHard, 1300f)

    }


    private val openGameObserver = Observer<Level> {
        Log.d("TTT", "Qayta iwladi")
        findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(it))
    }

    private fun rotateViewManual(view: View) {
        lifecycleScope.launchWhenResumed {
            val duration = 2000.0
            val refreshRate = 16L
            val numberOfFrames = ceil(duration / refreshRate).toInt()
            val firstAngle: Float = 360F / numberOfFrames
            repeat(numberOfFrames + 1) {
                delay(refreshRate)
                val angle = it * firstAngle
                view.rotation = angle
                view.scaleX = (it % 20).toFloat()
                view.scaleY = (it % 20).toFloat()
//                view.alpha = it / 100F
//                view.x = it * 100f
            }

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

    //    aylantirib joyiga quyadi
    private fun viewAnimate(view: View) {
        view.animate()
            .setDuration(1000)
            .rotationBy(-720f)
//            .alpha(0.5f)
            .withEndAction {
//                view.animate().xBy(300f)
            }
    }

    //aylantiroradi tuxtamey
    private fun animatorXml(view: View) {
        val rotateAnim = AnimatorInflater.loadAnimator(context, R.animator.animation) as AnimatorSet

        rotateAnim.setTarget(view)
        rotateAnim.start()
    }

    //aylantiradi joyiga quyadi
    private fun viewAnimation(view: View) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.animation)
        view.startAnimation(animation)
    }
    private fun shakeView(imageView: Button) {
        imageView.animate()
            .setDuration(250)
            .xBy(12f)
            .scaleXBy(.1f)
            .scaleYBy(.1f)
            .withEndAction {
                imageView.animate()
                    .setDuration(150)
                    .xBy(-24f)
                    .withEndAction {
                        imageView.animate()
                            .setDuration(250)
                            .xBy(24f)

                            .withEndAction {
                                imageView.animate()
                                    .setDuration(150)
                                    .xBy(-12f)
                                    .scaleXBy(-.1f)
                                    .scaleYBy(-.1f)
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()
    }

}