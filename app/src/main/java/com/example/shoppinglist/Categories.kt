package com.example.shoppinglist

class Categories {

    var CategoryName : String? = null
    var CategoryID : Int? = null
    var CategoryImage : String? = null

    constructor(){

    }

    constructor(CategoryName: String?, CategoryID: Int?, CategoryImage: String?){
        this.CategoryID = CategoryID
        this.CategoryImage = CategoryImage
        this.CategoryName = CategoryName
    }
}