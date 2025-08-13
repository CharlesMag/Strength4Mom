package com.example.strength4mom

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.strength4mom.ui.navigation.AppContent
import com.example.strength4mom.ui.theme.Strength4MomTheme

import org.junit.Test

import org.junit.Rule

class Strenght4MomUiTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun CheckTopBarTitle() {
        composeTestRule.setContent {
            Strength4MomTheme {
                AppContent()
            }
        }

        composeTestRule.onNodeWithText("Exercise 1").performClick()
        composeTestRule.onNodeWithText("Here goes the description of the exercise").assertIsDisplayed()
    }
}