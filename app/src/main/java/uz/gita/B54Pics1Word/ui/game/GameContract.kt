package uz.gita.B54Pics1Word.ui.game

import android.widget.TextView
import uz.gita.B54Pics1Word.data.model.QuestionModel

interface GameContract {

    interface Model {
        fun questionData(): QuestionModel
        fun currentQuestionPos(): Int
        fun saveCurrentAnsweringPos(pos: Int)
        fun getCurrentAnsweringPos(): Int
        fun saveCurrentQuestionPos(pos: Int)
        fun getQuestionsCount(): Int
        fun shuffleQuestions()
        fun saveCoinCount(count: Int)
        fun getCoinCount():Int
    }

    interface Presenter {
        fun loadCurrentQuestion()
        fun answerBtnClick(clickedPosition: Int)
        fun variantBtnClick(clickedPosition: Int)
        fun finishActivity()
        fun setCoinCount()
        fun saveCoinCount(count : Int)
        fun loadNextQuestion()
        fun btnAddLetterClick()
        fun setCurrentAnsweringPos()
    }

    interface View {
        fun showQuestionImagesToView(questionImages: List<Int>)

        fun showVariantsToView(variant: String)
        fun setVisibilityToAnswer(answerLength: Int)
        fun clearOldData()
        fun closeActivity()
        fun showDialog(answer : String)

        fun setTextToAnswer(pos: Int, letter: String)
        fun setButtonsInvisible()
        fun setEnabledVariantByPos(pos: Int, state: Boolean)

        fun setCurrentAnsweringPos(int: Int)

        fun wrongAnswerAnimation()

        fun setWrongColorToAnswers()
        fun setCorrectColorToAnswers(pos: Int)
        fun setDefaultColorToAnswers()

        fun setCoinCount(count : Int)

        fun getAnswerButton(): MutableList<TextView>
        fun getVariantButton() : MutableList<TextView>
        fun setDefaultBGColorToAnswers()
    }
}