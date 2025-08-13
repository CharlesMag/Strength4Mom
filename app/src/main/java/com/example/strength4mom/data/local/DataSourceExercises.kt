package com.example.strength4mom.data.local

import com.example.strength4mom.R
import com.example.strength4mom.data.dto.ExoItemClass

/**
 * A data class to represent the information presented in the exercise card
 */

//My listOf exercises to be used by the app
object DataSourceExercises {
    val exos = listOf(
        ExoItemClass.Exo(
            R.drawable.gobletsquatstart,
            R.string.exercise_1,
            4,
            10,
            R.string.exercise_description_1,
            1
        ),
        ExoItemClass.Exo(
            R.drawable.singlelegdeadlift,
            R.string.exercise_2,
            5,
            10,
            R.string.exercise_description_2,
            2
        ),
        ExoItemClass.Exo(
            R.drawable.singleleglutebridge,
            R.string.exercise_3,
            4,
            15,
            R.string.exercise_description_3,
            3
        )
    )
}


////
//fun todoL(){
//   val exo:Exo =  ExoNetwork().mapToUIInfo()
//}
//fun ExoNetwork.mapToUIInfo(): Exo{
//   return Exo(
//       imageResourceID = this.image.mapToUIInfo(),
//
//   )
//}
//
//fun Image.mapToUIInfo(): Int{
//    return when(this){
//        Image.SQUAT -> R.drawable.gobletsquatstart
//        Image.SINGLE_LIFT -> TODO()
//        Image.SINGLE_B -> TODO()
//    }
//}
//
//
//data class ExoNetwork(
//    val image: Image = Image.SQUAT,
//    val name: String = "" ,
//    val numberSet: Int = 0,
//    val numberRep: Int = 0,
//    val description: String =  "",
//    val Id: Int = 0,
//) {
//
//}
//
//enum class Image{
//    SQUAT,
//    SINGLE_LIFT,
//    SINGLE_B
//}