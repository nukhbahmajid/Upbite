package hu.ait.mobilefinal.data

class ChatMessage (
    val id: String = "",
    val text : String = "",
    val fromId : String = "",
    val toId : String = "",
    val timestamp : Long = -1) {
    constructor() : this("", "", "", "", -1)
}