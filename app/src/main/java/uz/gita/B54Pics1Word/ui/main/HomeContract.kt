package uz.gita.B54Pics1Word.ui.main

interface HomeContract {

    interface Model {
        fun getCurrentAnsweringPos():Int
        fun getCurrentQuestionImages():List<Int>
    }

    interface Presenter {
        fun loadCurrentQuestion()
        fun startGameActivity()
        fun startAboutActivity()
    }

    interface View {
        fun setImagesToView(images:List<Int>)
        fun setAnsweringPos(pos:Int)
        fun goToGameActivity()
        fun goToAboutActivity()
    }
}