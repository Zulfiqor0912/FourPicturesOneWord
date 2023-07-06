package uz.gita.B54Pics1Word.ui.game

import android.annotation.SuppressLint
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

import uz.gita.B54Pics1Word.data.model.QuestionModel
import uz.gita.B54Pics1Word.utils.Constants

class GamePresenter constructor(private val gameView: GameContract.View) : GameContract.Presenter {
    private val gameModel: GameContract.Model = GameModel()
    private lateinit var typedAnswers: ArrayList<String>
    private lateinit var typedVariants: ArrayList<Boolean>
    private var isDeleted: Boolean = false

    override fun loadCurrentQuestion() {
        isDeleted = false
        gameView.clearOldData()
        gameView.showQuestionImagesToView(gameModel.questionData().imageQuestions)
        gameView.showVariantsToView(gameModel.questionData().variant)
        gameView.setVisibilityToAnswer(gameModel.questionData().answer.length)
        gameView.setCoinCount(gameModel.getCoinCount())
        setCurrentAnsweringPos()
        initTypedArrays()
    }


    override fun answerBtnClick(clickedPosition: Int) {
        val question: QuestionModel = gameModel.questionData()
        val variant: String = question.variant
        val typedLetter: String = typedAnswers[clickedPosition]

        for (i in 0 until Constants.MAX_VARIANTS.value) {
            if (variant[i].toString() == typedLetter && typedVariants[i]) {
                gameView.setEnabledVariantByPos(i, true)
                typedVariants[i] = false
                YoYo.with(Techniques.FlipInY).duration(600).playOn(gameView.getVariantButton()[i])
                typedAnswers[clickedPosition] = null.toString()
                gameView.setTextToAnswer(clickedPosition, "")
                gameView.setDefaultColorToAnswers()
                return
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun btnAddLetterClick() {
        val answerText: String = gameModel.questionData().answer
        if (5 <= gameModel.getCoinCount()) {
            for (i in answerText.indices) {
                if (gameView.getAnswerButton()[i].text.toString() == answerText[i].toString()) {
                    continue
                }
                gameView.getAnswerButton()[i].callOnClick()
                for(j in i until answerText.length){
                    if(gameView.getAnswerButton()[j].text.toString() == answerText[i].toString()){
                        gameView.getAnswerButton()[j].callOnClick()
                        break
                    }
                }
                for (j in 0 until gameView.getVariantButton().size) {
                    if (!typedVariants[j] && answerText[i].toString() == gameView.getVariantButton()[j].text.toString()) {
                        gameView.getVariantButton()[j].callOnClick()
                        typedVariants[j] = true
                        gameView.setCorrectColorToAnswers(i)
                        gameView.getAnswerButton()[i].isClickable = false
                        saveCoinCount(gameModel.getCoinCount() - 5)
                        setCoinCount()
                        return
                    }
                }
            }
        }
    }

    override fun setCurrentAnsweringPos() {
        gameView.setCurrentAnsweringPos(gameModel.getCurrentAnsweringPos())
    }


    override fun variantBtnClick(clickedPosition: Int) {
        val positionAnswer: Int = typedAnswers.indexOf(null.toString())
        if (positionAnswer == -1) {
            return
        }
        val question: QuestionModel = gameModel.questionData()

        val variant: String = question.variant
        val letter: String = variant[clickedPosition].toString()
        gameView.setTextToAnswer(positionAnswer, letter)
        YoYo.with(Techniques.Tada).duration(600).playOn(gameView.getAnswerButton()[positionAnswer])
        typedAnswers[positionAnswer] = letter
        gameView.setEnabledVariantByPos(clickedPosition, false)
        typedVariants[clickedPosition] = true
        isWin()
    }


    override fun finishActivity() {
        gameView.closeActivity()
    }

    override fun setCoinCount() {
        gameView.setCoinCount(gameModel.getCoinCount())
    }

    override fun saveCoinCount(count: Int) {
        gameModel.saveCoinCount(count)
    }

    override fun loadNextQuestion() {
        loadCurrentQuestion()
    }


    private fun initTypedArrays() {
        val answerSize: Int = gameModel.questionData().answer.length
        typedAnswers = ArrayList()

        for (i in 0 until answerSize) {
            typedAnswers.add(null.toString())
        }

        typedVariants = ArrayList(Constants.MAX_VARIANTS.value)
        for (i in 0 until Constants.MAX_VARIANTS.value) {
            typedVariants.add(false)
        }

    }

    private fun isWin() {
        val question: QuestionModel = gameModel.questionData()
        val answer: String = question.answer
        val sb = StringBuilder()
        for (i in 0 until typedAnswers.size) {
            sb.append(typedAnswers[i])
        }
        val userAnswer: String = sb.toString()

        if (userAnswer == answer) {
            gameView.setButtonsInvisible()
            gameView.showDialog(answer)
            gameModel.saveCoinCount(gameModel.getCoinCount() + 10)
            gameModel.saveCurrentAnsweringPos(gameModel.getCurrentAnsweringPos() + 1)
            saveQuestionPos(gameModel.currentQuestionPos() + 1)
            if (gameModel.currentQuestionPos() == gameModel.getQuestionsCount()) {
                gameModel.shuffleQuestions()
            }
        } else if (userAnswer.length == answer.length) {
            gameView.setWrongColorToAnswers()
            gameView.wrongAnswerAnimation()
        }
    }

    private fun saveQuestionPos(i: Int) {
        gameModel.saveCurrentQuestionPos(i)
    }
}