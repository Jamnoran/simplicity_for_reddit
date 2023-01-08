package com.simplicity.simplicityaclientforreddit.main.screen.test

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class TestLogic : BaseLogic() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    fun start() {
        firebase()

        foreground {
            val text =
                "#Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold** and *italic* text\n\n[Link with desc](https://www.google.se/)\n\n[https://www.svt.se/](https://www.svt.se/)\n\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\n\n~~Strikethrough~~ text \n\n`This is some inlined` code\n\n^(Superscript is this)\n\n&gt;!This is a spoiler!&lt;\n\n# Header in the middle of the text\n\n* Bullted list\n* with a couple of points\n\n1. Numbered list in a bulleted list\n\n&amp;#x200B;\n\n1. Simple numbered list\n\n&gt; This is a quote from the great emperor\n\n&amp;#x200B;\n\n    Code blocks looks like this\n\nImage:\n\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\n\n&amp;#x200B;\n\n&amp;#x200B;\n\n|Column 1|Column 2|Column 3|\n|:-|:-|:-|\n|Row 2|Row 2|Row 2|"
            _stateFlow.emit(UiState.Success(text))
        }
    }

    private fun firebase() {
        background {
            signIn()
//            setUserData()
        }
    }

    private fun setUserData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")
        val child = myRef.child("TestUser")

        val jsonString = """
{
  "name": "John",
  "age": 30,
  "city": "New York"
}
"""

        val user = JSONObject(jsonString)

        child.setValue(user)
        child.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
                foreground {
                    _stateFlow.emit(UiState.Success("Value is $value"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
                foreground {
                    _stateFlow.emit(UiState.Success("Failed to read value. ${error.toException()}"))
                }
            }
        })
    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInAnonymously:success")
                val user = FirebaseAuth.getInstance().currentUser
                foreground {
                    user?.let {
                        _stateFlow.emit(UiState.Success("User isAnonymous: ${user.isAnonymous}"))
                    }
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInAnonymously:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
                foreground {
                    _stateFlow.emit(UiState.Success("User is null with exception ${task.exception}"))
                }
            }
        }
    }

    companion object {
        private val TAG: String = "TestLogic"
    }
}
