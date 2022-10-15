package com.example.nested_recyclerview.model

data class ParentItem(
    var title: String,
    var ChildItemList: List<ChildItem>
)
