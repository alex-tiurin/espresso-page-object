package com.atiurin.espressopageobjectexample.activity

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atiurin.espressopageobjectexample.view.CircleImageView
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.adapters.MessageAdapter
import com.atiurin.espressopageobjectexample.data.entities.Contact
import com.atiurin.espressopageobjectexample.data.entities.Message
import com.atiurin.espressopageobjectexample.data.repositories.CURRENT_USER
import com.atiurin.espressopageobjectexample.data.repositories.ContactRepositoty
import com.atiurin.espressopageobjectexample.data.repositories.MessageRepository

const val INTENT_CONTACT_ID_EXTRA_NAME = "contactId"

class ChatActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessageAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var contact: Contact
    private val onItemClickListener: View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val context = this
        //TOOLBAR
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = getColor(R.color.colorPrimaryDark)
        val mIntent = intent
        val title = findViewById<TextView>(R.id.toolbar_title)
        val contactId = mIntent.getIntExtra(INTENT_CONTACT_ID_EXTRA_NAME, -1)
        if (contactId < 0){
            Log.d("EspressoGuide", "Something goes wrong!")
        }
        contact = ContactRepositoty.getContact(contactId)
        title.text = contact.name
        val avatar = findViewById<CircleImageView>(R.id.toolbar_avatar)
        avatar.setImageDrawable(getDrawable(contact.avatar))
        //message input area
        val messageInput = findViewById<EditText>(R.id.message_input_text)
        val sendBtn = findViewById<ImageView>(R.id.send_button)
        val attachBtn = findViewById<ImageView>(R.id.attach_button)

        //recycler view and adapter
        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(
            ArrayList<Message>(),
            object : MessageAdapter.OnItemClickListener {
                override fun onItemClick(message: Message) {
                    Log.w("EspressoGuid", "Clicked message ${message.text}")
                }
            })
        recyclerView = findViewById<RecyclerView>(R.id.messages_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        sendBtn.setOnClickListener(
            object: View.OnClickListener{
                override fun onClick(v: View?) {
                    if (messageInput.text.isEmpty()){
                        Toast.makeText(context, "Type message text", Toast.LENGTH_LONG).show()
                    }else{
                        val mes = Message(CURRENT_USER.id, contactId, messageInput.text.toString())
                        val curMessages = MessageRepository.messages
                        curMessages.add(mes)
                        updateAdapter(curMessages)
                        messageInput.setText("")
                        recyclerView.smoothScrollToPosition(viewAdapter.itemCount - 1)
                    }
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                updateAdapter(ArrayList())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        viewAdapter.updateData(MessageRepository.getChatMessages(contact.id))
        viewAdapter.notifyDataSetChanged()
    }

    private fun updateAdapter(list: ArrayList<Message>){
        MessageRepository.messages = list
        viewAdapter.updateData(list)
        viewAdapter.notifyDataSetChanged()
    }
}