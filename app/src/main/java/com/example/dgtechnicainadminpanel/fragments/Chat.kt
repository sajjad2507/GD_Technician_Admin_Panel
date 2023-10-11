package com.example.dgtechnicainadminpanel.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dgtechnicainadminpanel.Adapter.ChatAdapter
import com.example.dgtechnicainadminpanel.DataModels.ChatMessageModel
import com.example.dgtechnicainadminpanel.R
import com.example.dgtechnicainadminpanel.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : Fragment() {

    lateinit var binding: FragmentChatBinding
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val chatRef: DatabaseReference = database.getReference("chats")
    private lateinit var currentUserId: String
    private lateinit var chatId: String
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
        val receiverUserId = auth.uid.toString()

        currentUserId = arguments?.getString("uId").toString().trim()

        chatId = getChatId(currentUserId, receiverUserId)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        chatAdapter = ChatAdapter(currentUserId, chatId)
        binding.recyclerView.adapter = chatAdapter

        // Set up Firebase listeners to read messages
        chatRef.child(chatId).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessageModel::class.java)
                chatMessage?.let { chatAdapter.addMessage(it) }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle if a message is edited
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Handle if a message is deleted
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Handle if a message is moved
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        binding.sendButton.setOnClickListener {

            if (binding.messageEditText.text.isEmpty()) {
                Toast.makeText(requireContext(), "write something", Toast.LENGTH_SHORT).show()
            } else {
                val message = binding.messageEditText.text.toString()
                sendMessage(message, receiverUserId)
            }

            binding.messageEditText.setText("")

        }

        binding.backBtn.setOnClickListener {

            NavHostFragment.findNavController(this).navigate(R.id.action_chat_to_home2)

        }


        return binding.root
    }

    private fun getChatId(senderId: String, receiverId: String): String {
        // Sort the sender and receiver IDs alphabetically and concatenate them to create a unique chat ID
        return if (senderId < receiverId) {
            "$senderId-$receiverId"
        } else {
            "$receiverId-$senderId"
        }
    }

    private fun sendMessage(message: String, receiverUserId: String) {

        auth = FirebaseAuth.getInstance()
        val currentUserId = auth.uid.toString()

        val messageId = chatRef.child(chatId).push().key // Generate a unique key for the message
        val chatMessage =
            ChatMessageModel(currentUserId, receiverUserId, message, System.currentTimeMillis())

        messageId?.let {
            chatRef.child(chatId).child(it).setValue(chatMessage)
        }
    }
}
