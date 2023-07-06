package uz.gita.B54Pics1Word.ui.main

import uz.gita.B54Pics1Word.domain.Repository


class MainModel: HomeContract.Model {
    private val repository: Repository = Repository.getInstance()

    override fun getCurrentAnsweringPos()= repository.getAnswerPos()


    override fun getCurrentQuestionImages(): List<Int> {
        return repository.getCurrentQuestionData().imageQuestions
    }
}