package uz.gita.B54Pics1Word.ui.game

import uz.gita.B54Pics1Word.data.model.QuestionModel
import uz.gita.B54Pics1Word.domain.Repository

class GameModel : GameContract.Model {
    private val repository = Repository.getInstance()

    override fun questionData(): QuestionModel =
        repository.getCurrentQuestionData()

    override fun currentQuestionPos(): Int =
        repository.getCurrentQuestionPos()

    override fun saveCurrentAnsweringPos(pos: Int) {
        repository.saveAnswerPos(pos)
    }

    override fun getCurrentAnsweringPos(): Int {
        return repository.getAnswerPos()
    }

    override fun saveCurrentQuestionPos(pos: Int) =
        repository.saveCurrentQuestionPos(pos)

    override fun getQuestionsCount(): Int {
       return repository.getQuestionSize()
    }

    override fun shuffleQuestions() {
        repository.shuffleQuestionData()
    }

    override fun saveCoinCount(count: Int) {
        repository.saveCoinCount(count)
    }

    override fun getCoinCount(): Int {
        return repository.getCoinCount()
    }
}