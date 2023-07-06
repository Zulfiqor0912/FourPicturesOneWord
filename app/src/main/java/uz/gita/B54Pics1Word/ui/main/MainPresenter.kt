package uz.gita.B54Pics1Word.ui.main

class MainPresenter(private val mainView: MainActivity): HomeContract.Presenter {
    private val maiModel: HomeContract.Model = MainModel()

    override fun loadCurrentQuestion() {
        mainView.setAnsweringPos(maiModel.getCurrentAnsweringPos())
        mainView.setImagesToView(maiModel.getCurrentQuestionImages())
    }

    override fun startGameActivity() {
        mainView.goToGameActivity()
    }

    override fun startAboutActivity() {
        mainView.goToAboutActivity()
    }


}