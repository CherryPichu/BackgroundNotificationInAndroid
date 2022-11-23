package kr.ac.hallym.backgroundnotice

class UserMeta(id : Int, keyword : String, email : String?) {
    private var id : Int = id; // 자동으로 getter setter 구현됨
    private var keyword : String = keyword;
    private var email : String? = email;

    constructor(id : Int, keyword : String): this(id, keyword, null){

    }



}