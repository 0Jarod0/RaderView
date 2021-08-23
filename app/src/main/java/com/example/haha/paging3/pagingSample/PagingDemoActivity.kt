package com.example.haha.paging3.pagingSample

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.haha.customview.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingDemoActivity : AppCompatActivity(){

    private lateinit var cheeseList:RecyclerView
    private lateinit var inputText:EditText
    private lateinit var addButton:Button

    private val viewModel by viewModels<CheeseViewModel>{CheeseViewModelFactory(application)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_demo)

        cheeseList = findViewById(R.id.cheeseList)
        inputText = findViewById(R.id.inputText)
        addButton = findViewById(R.id.addButton)

        val adapter = CheeseAdapter()
        cheeseList.adapter = adapter

        lifecycleScope.launch {
            viewModel.allCheeses.collectLatest {
                adapter.submitData(it)
            }
        }

        initAddButtonListener()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete(){
        ItemTouchHelper(object :ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val cheeseViewHolder = viewHolder as CheeseViewHolder
                return if (cheeseViewHolder.cheese != null) {
                    makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
                } else {
                    makeMovementFlags(0, 0)
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    viewModel.remove(it)
                }
            }

        })
    }

    private fun addCheese(){
        val newCheese = inputText.text.trim()
        if (newCheese.isNotEmpty()){
            viewModel.insert(newCheese)
            inputText.setText("")
        }
    }

    private fun initAddButtonListener(){
        addButton.setOnClickListener {
            addCheese()
        }

        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese()
                return@setOnEditorActionListener true
            }
            false // action that isn't DONE occurred - ignore
        }
        inputText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese()
                return@setOnKeyListener true
            }
            false // event that isn't DOWN or ENTER occurred - ignore
        }

    }
}