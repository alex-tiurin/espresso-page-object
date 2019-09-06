package com.atiurin.espressopageobjectexample.data.repositories

import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.data.entities.Contact
import com.atiurin.espressopageobjectexample.data.entities.Message
import com.atiurin.espressopageobjectexample.data.entities.User

val CURRENT_USER = User(1, "Joey Tribbiani", Avatars.JOEY.drawable, "joey", "1234")

val CONTACTS =  arrayListOf(
    Contact(2, "Chandler Bing", "Joey doesn't share food!",Avatars.CHANDLER.drawable),
    Contact(3, "Ross Geller","UNAGI", Avatars.ROSS.drawable),
    Contact(4, "Rachel Green", "I got off the plane!",Avatars.RACHEL.drawable),
    Contact(5, "Phoebe Buffay", "Smelly cat, smelly cat..",Avatars.PHOEBE.drawable),
    Contact(6, "Monica Geller", "I need to clean up..",Avatars.MONICA.drawable),
    Contact(7, "Gunther", "They were on break :(",Avatars.GUNTHER.drawable),
    Contact(8, "Janice","Oh. My. God", Avatars.JANICE.drawable)
)

enum class Avatars(val drawable: Int){
    CHANDLER(R.drawable.chandler),
    ROSS(R.drawable.ross),
    MONICA(R.drawable.monica),
    RACHEL(R.drawable.rachel),
    PHOEBE(R.drawable.phoebe),
    GUNTHER(R.drawable.gunther),
    JOEY(R.drawable.joey),
    JANICE(R.drawable.janice)
}


val MESSAGES =  arrayListOf(
    Message(1, 2, "What's up Chandler"),
    Message(2, 1, "Hi Joey"),
    Message(1, 2, "Let's drink coffee"),
    Message(2, 1, "Ok"),
    Message(1, 3, "Do u wanna coffee?"),
    Message(3, 1, "yep, let's go")
)