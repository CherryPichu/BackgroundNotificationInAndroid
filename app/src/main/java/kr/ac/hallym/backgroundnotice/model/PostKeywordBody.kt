package kr.ac.hallym.backgroundnotice.model

/**
 * Keywod를 업데이트할 때 사용할 Post Body 구조
 */
data class PostKeywordBody(
    val userid : Int,
    val keyword: String,
    val listcode : String
)
