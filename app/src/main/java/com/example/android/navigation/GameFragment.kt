/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is Android Jetpack?",
                    answers = listOf("all of these", "tools", "documentation", "libraries")),
            Question(text = "Base class for Layout?",
                    answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            Question(text = "Layout for complex Screens?",
                    answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            Question(text = "Pushing structured data into a Layout?",
                    answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")),
            Question(text = "Inflate layout in fragments?",
                    answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")),
            Question(text = "Build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "Android vector format?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Android Navigation Component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Registers app with launcher?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "Mark a layout for Data Binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)


        randomizeQuestions()


        binding.game = this


        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId

            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++

                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {

                        Navigation.findNavController(view).navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment())
                    }
                } else {

                    Navigation.findNavController(view).navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment2(11,22))
                }
            }
        }
        return binding.root
    }


    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }


    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
