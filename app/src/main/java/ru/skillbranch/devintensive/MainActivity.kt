package ru.skillbranch.devintensive

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    lateinit var bender: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        bender = Bender.fromStateOrDefault(savedInstanceState)
        benderObj = bender

        iv_bender.setColorFilter(bender.status.color(), PorterDuff.Mode.MULTIPLY)
        tv_text.text = bender.askQuestion()

        et_message.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) sendQuestion()
            false
        }

        iv_send.setOnClickListener { sendQuestion() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        bender.saveState(outState)
    }

    private fun sendQuestion() {
        val text = bender.listenAnswer(et_message.text.toString()).first

        tv_text.text = text
        iv_bender.setColorFilter(bender.status.color(), PorterDuff.Mode.MULTIPLY)
        et_message.setText("")
    }


}