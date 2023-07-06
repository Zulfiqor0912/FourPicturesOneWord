package uz.gita.B54Pics1Word.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import uz.gita.B54Pics1Word.R
import uz.gita.B54Pics1Word.ui.game.GameActivity
import uz.gita.B54Pics1Word.utils.Constants


class MainActivity : AppCompatActivity(), HomeContract.View {
    private lateinit var playButton: AppCompatButton
    private lateinit var aboutButton: AppCompatButton
    private lateinit var exitButton: AppCompatButton
    private lateinit var questionsImg: MutableList<ImageView>
    private lateinit var currentQuestionPos: AppCompatTextView

    private lateinit var presenter: HomeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)
        findViews()
        connectClickActions()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCurrentQuestion()
    }

    private fun findViews() {
        questionsImg = ArrayList()

        questionsImg.add(findViewById(R.id.image1))
        questionsImg.add(findViewById(R.id.image2))
        questionsImg.add(findViewById(R.id.image3))
        questionsImg.add(findViewById(R.id.image4))

        currentQuestionPos = findViewById(R.id.textQuestionPos)

        playButton = findViewById(R.id.buttonPlay)
        aboutButton = findViewById(R.id.buttonAbout)
        exitButton = findViewById(R.id.buttonExit)
    }

    private fun connectClickActions() {
        playButton.setOnClickListener {
            presenter.startGameActivity()
        }

        aboutButton.setOnClickListener {
            presenter.startAboutActivity()
        }
        exitButton.setOnClickListener {
            finish()
        }
    }

    override fun setImagesToView(images: List<Int>) {
        for (i in 0 until Constants.MAX_IMAGES.value) {
            questionsImg[i].setImageResource(images[i])
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setAnsweringPos(pos: Int) {
        currentQuestionPos.text = (pos).toString()
    }

    override fun goToGameActivity() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    override fun goToAboutActivity() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_about)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
        val btnBack = dialog.findViewById<AppCompatButton>(R.id.btnBack)
        btnBack.setOnClickListener { dialog.dismiss() }
    }
}