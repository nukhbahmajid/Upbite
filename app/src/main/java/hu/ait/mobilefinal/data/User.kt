package hu.ait.mobilefinal.data


/**
 * Attributes of the User class, the image URL is optional so
 * is set to empty string at first.
 */

class User(var uid: String = "",
           var username: String = "",
           var email: String = "",
           var profileImageUrl: String = "") {
    constructor() : this("", "","", "")

}