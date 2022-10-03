package uz.gita.lessonmemorygame.presentation.ui.screens


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.lessonmemorygame.IOnBackPressed
import uz.gita.lessonmemorygame.R
import uz.gita.lessonmemorygame.data.models.GameModel
import uz.gita.lessonmemorygame.data.models.Level
import uz.gita.lessonmemorygame.databinding.ScreenGameBinding
import uz.gita.lessonmemorygame.explode_animation.ExplosionField
import uz.gita.lessonmemorygame.presentation.viewmodel.GameViewModel
import uz.gita.lessonmemorygame.presentation.viewmodel.impl.GameViewModelImpl
import uz.gita.lessonmemorygame.shared.MySharedPreference


@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game), IOnBackPressed {
    private val viewBinding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val navController by lazy { findNavController() }
    private var level = Level.EASY
    private var _width = 0
    private var _height = 0
    private var count = 0
    private var barstatus = 0
    var clickedcount = 0
    private lateinit var myshp: MySharedPreference
    private lateinit var prgBarHorizontal: ProgressBar
    private lateinit var timer: CountDownTimer
    private var imagetop = ArrayList<ImageView>()
    private var timeForTimer = 0L
    private lateinit var images: ArrayList<ImageView>
    private val args: GameScreenArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myshp = MySharedPreference.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prgBarHorizontal = view.findViewById(R.id.prgBar)
        args.level.apply {
            level = this
            count = x * y
        }
        viewBinding.bgButton.setOnClickListener {
            navController.navigate(GameScreenDirections.actionGameScreenToLevelScreen())
            if (::timer.isInitialized) {
                timer.cancel()
            }
            barstatus = 0
        }
        images = ArrayList(count)
        viewBinding.containerMain.post {
            _width = viewBinding.containerMain.width / (level.x + 1)
            _height = viewBinding.containerMain.height / (level.y + 1) - 80
            viewBinding.containerImage.layoutParams.apply {
                width = _width * level.x
                height = _height * level.y
            }
            loadImages()
        }
        viewModel.getDataByLevel(level)
        viewModel.gameModeLiveData.observe(viewLifecycleOwner, gameDataObserver)
        lifecycleScope.launch {
            delay(50)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            val size = images.size
            if (size == 12) {
                timeForTimer = 11500L
            } else if (size == 20) {
                timeForTimer = 28000L
            } else {
                timeForTimer = 40000L
            }
            prgBarHorizontal.max = timeForTimer.toInt()
            timer = object : CountDownTimer(timeForTimer, 2) {
                override fun onTick(millisUntilFinished: Long) {
                    prgBarHorizontal.setProgress(millisUntilFinished.toInt())
                }

                @SuppressLint("SetTextI18n")
                override fun onFinish() {
                    val builder =
                        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
                            .create()
                    val customLayout =
                        layoutInflater.inflate(R.layout.custom_alert_dialog, null)
                    val yes1 = customLayout.findViewById<AppCompatButton>(R.id.ok_button_alert)
                    val text4 =
                        customLayout.findViewById<AppCompatTextView>(R.id.appCompatTextView4)
                    val text5 =
                        customLayout.findViewById<AppCompatTextView>(R.id.appCompatTextView5)
                    text5.text = "Won"
                    text4.text = "You have lost the memory game."
                    yes1.setOnClickListener { b: View? ->
                        navController.navigate(GameScreenDirections.actionGameScreenToLevelScreen())
                        prgBarHorizontal.stopNestedScroll()
                        if (::timer.isInitialized) {
                            timer.cancel()
                        }
                        barstatus = 0
                        builder.hide()
                    }
                    val no1 = customLayout.findViewById<AppCompatButton>(R.id.cancel_button1)
                    no1.setOnClickListener { b: View? ->
                        navController.navigateUp()
                        builder.hide()
                    }
                    builder.setView(customLayout)
                    builder.setCancelable(false)
                    builder.show()
                }
            }.start()
        }
    }

    private fun loadImages() {
        for (x in 0 until level.x) {
            for (y in 0 until level.y) {
                val imageView = ImageView(requireContext())
                viewBinding.containerImage.addView(imageView)
                val lp = imageView.layoutParams as RelativeLayout.LayoutParams
                lp.apply {
                    width = _width
                    height = _height
                }

                imageView.x = x * _width * 1f
                imageView.y = y * _height * 1f
                lp.setMargins(1, 1, 1, 1)
                imageView.setPadding(4)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.layoutParams = lp
                imageView.setImageResource(R.drawable.sss)
                images.add(imageView)

            }
        }
    }
    @SuppressLint("InflateParams")
    private val gameDataObserver = Observer<List<GameModel>> {

        for (i in it.indices) {
            val imageView = images[i]
            imageView.tag = it[i]
            lifecycleScope.launch(Dispatchers.Main) {
                delay(100)
                openView(imageView)
                delay(1500)
                closeView(imageView)
            }
            imageView.setOnClickListener {
                if (imagetop.size < 2 && it.rotationY == 0f) {
                    openView(imageView)
                    imagetop.add(imageView)
                    if (imagetop.first().tag == imageView.tag && imagetop.size == 2) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            explodeanimation(imagetop.first())
                            explodeanimation(imageView)
                            imagetop.first().visibility = ImageView.INVISIBLE
                            imageView.visibility = ImageView.INVISIBLE
                            imagetop.clear()
                            clickedcount += 2
                            if (clickedcount == count) {
                                if (::timer.isInitialized) {
                                    timer.cancel()
                                }
                                val builder =
                                    AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
                                        .create()
                                val customLayout =
                                    layoutInflater.inflate(R.layout.custom_alert_dialog, null)
                                val yes1 =
                                    customLayout.findViewById<AppCompatButton>(R.id.ok_button_alert)
                                yes1.setOnClickListener { b: View? ->
                                    navController.navigate(GameScreenDirections.actionGameScreenToLevelScreen())
                                    if (::timer.isInitialized) {
                                        timer.cancel()
                                    }
                                    barstatus = 0
                                    builder.hide()
                                }
                                val no1 =
                                    customLayout.findViewById<AppCompatButton>(R.id.cancel_button1)
                                no1.setOnClickListener { b: View? ->
                                    navController.navigateUp()
                                    builder.hide()
                                }
                                builder.setView(customLayout)
                                builder.setCancelable(false)
                                builder.show()
                            }
                        }
                    } else if (imagetop.size > 1) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            shakeView(imagetop.first())
                            shakeView(imageView)
                            delay(500)
                            closeView(imagetop.first())
                            closeView(imageView)
                            imagetop.clear()
                        }
                    }
                }
            }
        }
    }

    private fun closeView(imageView: ImageView) {
        imageView.animate()
            .setDuration(100)
            .rotationY(90f)
            .withEndAction {
                imageView.setImageResource(R.drawable.sss)
                imageView.animate()
                    .setDuration(100)
                    .rotationY(0f)
                    .withEndAction {

                    }
                    .start()
            }
            .start()

    }

    override fun onResume() {
        timeForTimer = myshp.getSleepstatus()
        super.onResume()

    }

    override fun onPause() {
        myshp.setSleepstatus(timeForTimer)
        super.onPause()
    }

    override fun onDestroy() {
        barstatus = 0
        super.onDestroy()
    }

    private fun openView(imageView: ImageView) {
        imageView.bringToFront()

        imageView.animate()
            .setDuration(100)
            .scaleXBy(.5f)
            .withEndAction {
                imageView.animate()
                    .scaleYBy(.5f)
                    .setDuration(100)
                    .rotationY(90f)


                    .withEndAction {
                        imageView.setImageResource((imageView.tag as GameModel).image)
                        imageView.animate()
                            .setDuration(100)
                            .rotationY(180f)
                            .withEndAction {
                                imageView.animate()
                                    .setDuration(100)
                                    .scaleXBy(-.5f)
                                    .scaleYBy(-.5f)
                                    .setInterpolator(DecelerateInterpolator())
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()
    }

    private fun explodeanimation(imageView: ImageView) {
        val anim = ExplosionField.attach2Window(activity)
        anim.explode(imageView)
    }

    private fun shakeView(imageView: ImageView) {
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

    override fun onBackPressed(): Boolean {
        return false
    }


}