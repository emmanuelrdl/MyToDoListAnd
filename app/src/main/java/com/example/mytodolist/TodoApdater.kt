package com.example.mytodolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ToDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val cbDone: CheckBox

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tvToDoTitle)
            cbDone = view.findViewById(R.id.cbDone)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvToDoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(viewHolder: ToDoViewHolder, position: Int) {
        val currentToDo = todos[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = currentToDo.title
        viewHolder.cbDone.isChecked = currentToDo.isChecked
        toggleStrikeThrough(viewHolder.textView, currentToDo.isChecked)
        viewHolder.cbDone.setOnCheckedChangeListener{_, isChecked ->
            toggleStrikeThrough(viewHolder.textView, isChecked)
            currentToDo.isChecked = !currentToDo.isChecked
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}


