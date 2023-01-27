package com.example.projquizz

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.projquizz.ajoutBDD.LoadActivity
import com.example.projquizz.quizzLogic.QuizzCategoryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val READ_REQUEST_CODE = 1
    private val viewModel: LoadActivity by viewModels()

    // Action sur les boutons
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonDisplayQUIZZ.setOnClickListener{
            val intent = Intent(this, QuizzCategoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonAddQUIZZ.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/json"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }

        button3.setOnClickListener{
            Toast.makeText(this, "PlaceHolder", Toast.LENGTH_SHORT).show()
        }
    }

    // Pour l'import de JSON
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            if (uri != null) {
                viewModel.importQuestions(uri)
                Toast.makeText(this, "JSON imported", Toast.LENGTH_SHORT).show()
            }
        }
    }
}