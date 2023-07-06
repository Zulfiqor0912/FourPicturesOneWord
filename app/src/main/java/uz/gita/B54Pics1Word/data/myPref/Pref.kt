package uz.gita.B54Pics1Word.data.myPref

import android.content.Context
import android.content.SharedPreferences
import uz.gita.B54Pics1Word.app.App

class Pref private constructor() {
    private val sharedPreferences: SharedPreferences =
        App.appContext.getSharedPreferences("FOUR_PICTURES_ONE_WORD", Context.MODE_PRIVATE)

    companion object {
        private lateinit var myPref: Pref

        fun getInstance(): Pref {
            if (!this::myPref.isInitialized) myPref = Pref()
            return myPref
        }
    }

    fun saveQuestionPos(pos: Int) {
        sharedPreferences.edit().putInt("QUESTION_POSITION", pos).apply()
    }

    fun getQuestionPos(): Int {
        return sharedPreferences.getInt("QUESTION_POSITION", 0)
    }

    fun saveAnswerPos(ansPos: Int) {
        sharedPreferences.edit().putInt("ANSWER_POSITION", ansPos).apply()
    }

    fun getAnswerPos(): Int {
        return sharedPreferences.getInt("ANSWER_POSITION", 0)
    }

    fun saveCoinCount(coin: Int) {
        sharedPreferences.edit().putInt("COIN_COUNT", coin).apply()
    }

    fun getCoinCount(): Int {
        return sharedPreferences.getInt("COIN_COUNT", 0)
    }
}