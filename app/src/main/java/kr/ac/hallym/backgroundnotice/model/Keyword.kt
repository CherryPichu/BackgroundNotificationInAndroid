package kr.ac.hallym.backgroundnotice.model

data class Keyword (
    val id : Int,
    val userid : Int,
    val createAt : String,
    val keyword  : String,
    val listcode : String
)