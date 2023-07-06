package uz.gita.B54Pics1Word.ui.game

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import uz.gita.B54Pics1Word.R
import uz.gita.B54Pics1Word.ui.WinActivity
import uz.gita.B54Pics1Word.utils.Constants

class GameActivity : AppCompatActivity(), GameContract.View {
    private lateinit var presenter: GameContract.Presenter

    private lateinit var questionsImg: MutableList<ImageView>
    private lateinit var variants: MutableList<TextView>
    private lateinit var answers: MutableList<TextView>
    private lateinit var btnAddLetter: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var txtCurrentGame: TextView
    private lateinit var txtCoinCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        presenter = GamePresenter(this)
        findViews()
        presenter.loadCurrentQuestion()
    }

    private fun findViews() {
        questionsImg = ArrayList(Constants.MAX_IMAGES.value)

        questionsImg.add(findViewById(R.id.image1))
        questionsImg.add(findViewById(R.id.image2))
        questionsImg.add(findViewById(R.id.image3))
        questionsImg.add(findViewById(R.id.image4))

        val parentViewGroupOfAnswers = findViewById<LinearLayout>(R.id.answers)
        answers = ArrayList()
        for (i in 0 until Constants.MAX_ANSWERS.value) {
            val btn: TextView = parentViewGroupOfAnswers.getChildAt(i) as TextView
            btn.tag = i
            answers.add(btn)
        }
        variants = ArrayList()
        val parentViewGroupOfVariants = findViewById<RelativeLayout>(R.id.variants)
        var count = 0
        for (i in 0 until parentViewGroupOfVariants.childCount) {
            val btn = parentViewGroupOfVariants.getChildAt(i)
            if (btn is TextView) {
                btn.tag = count++
                variants.add(btn)
            }
        }
        btnAddLetter = findViewById(R.id.btnAddLetter)
        txtCoinCount = findViewById(R.id.txtCoinCount)
        txtCurrentGame = findViewById(R.id.textQuestionPosInGame)
        btnBack = findViewById(R.id.btnBackToMenu)
        addClickActions()
    }

    private fun addClickActions() {
        for (i in 0 until Constants.MAX_VARIANTS.value) {
            variants[i].setOnClickListener {
                presenter.variantBtnClick(it.tag as Int)
            }
        }

        for (i in 0 until Constants.MAX_ANSWERS.value) {
            answers[i].setOnClickListener {
                presenter.answerBtnClick(it.tag as Int)
            }
        }
        btnAddLetter.setOnClickListener {
            presenter.btnAddLetterClick()
        }
        btnBack.setOnClickListener {
            presenter.finishActivity()
        }
    }

    override fun showQuestionImagesToView(questionImages: List<Int>) {
        for (i in 0 until Constants.MAX_IMAGES.value) {
            this.questionsImg[i].setImageResource(questionImages[i])
        }
    }

    override fun showVariantsToView(variant: String) {
        for (i in variant.indices) {
            variants[i].text = variant[i].toString()
        }
    }

    override fun setVisibilityToAnswer(answerLength: Int) {
        btnAddLetter.visibility = View.VISIBLE
        for (i in 0 until answerLength) {
            answers[i].visibility = View.VISIBLE
        }

        for (i in answerLength until Constants.MAX_ANSWERS.value) {
            answers[i].visibility = View.GONE
        }
    }

    override fun clearOldData() {
        for (answerBtn in answers) {
            answerBtn.text = ""

        }
        setDefaultColorToAnswers()
        for (variantBtn in variants) {
            variantBtn.visibility = View.VISIBLE
            variantBtn.isEnabled = true
        }
        setDefaultBGColorToAnswers()
    }

    override fun closeActivity() {
        finish()
    }

    override fun showDialog(answer: String) {
        val dialog = WinActivity()

        WinActivity.listener = View.OnClickListener {
            presenter.loadNextQuestion()
            presenter.setCurrentAnsweringPos()
            setDefaultBGColorToAnswers()
            dialog.dismiss()
        }
        WinActivity.answer = answer
        dialog.show(supportFragmentManager, "dialog")

    }

    override fun setTextToAnswer(pos: Int, letter: String) {
        answers[pos].isClickable = true
        answers[pos].text = letter
    }

    override fun setButtonsInvisible() {
        for (i in 0 until Constants.MAX_ANSWERS.value) {
            answers[i].visibility = View.GONE
        }
        for (i in 0 until Constants.MAX_VARIANTS.value) {
            variants[i].visibility = View.GONE
        }
        btnAddLetter.visibility = View.GONE
    }

    override fun setEnabledVariantByPos(pos: Int, state: Boolean) {
        variants[pos].isEnabled = state
    }

    override fun setCurrentAnsweringPos(int: Int) {
        txtCurrentGame.text = int.toString()
    }

    override fun wrongAnswerAnimation() {
        YoYo.with(Techniques.Shake).duration(600).playOn(findViewById(R.id.answers))
    }

    override fun setWrongColorToAnswers() {
        for (i in 0 until Constants.MAX_ANSWERS.value) {
            answers[i].setTextColor(resources.getColor(R.color.red));
        }
    }

    override fun setCorrectColorToAnswers(pos: Int) {
        answers[pos].setBackgroundResource(R.drawable.bg_answers_green)
    }

    override fun setDefaultColorToAnswers() {
        for (i in 0 until Constants.MAX_ANSWERS.value) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    answers[i].setTextColor(resources.getColor(R.color.white));
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    answers[i].setTextColor(resources.getColor(R.color.black));
                }
            }
        }
    }

    override fun setDefaultBGColorToAnswers() {
        for (i in 0 until Constants.MAX_ANSWERS.value) {
            answers[i].setBackgroundResource(R.drawable.bg_answers)
        }
    }

    override fun setCoinCount(count: Int) {
        txtCoinCount.text = count.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.finishActivity()
    }

    override fun getAnswerButton(): MutableList<TextView> = answers
    override fun getVariantButton(): MutableList<TextView> = variants
}