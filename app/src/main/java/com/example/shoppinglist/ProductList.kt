package com.example.quizflix.models

class ProductList {

    var CategoryID : Int? = null
    var Name : String? = null


    constructor(){

    }

    constructor(CategoryID: Int?,  Name: String? ) {
        this.CategoryID = CategoryID
        this.Name = Name
    }




}


