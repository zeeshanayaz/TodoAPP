package com.zeeshan.todoapp.dashboard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zeeshan.todoapp.MainActivity
import com.zeeshan.todoapp.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.todo_add_dialog.view.*
import com.google.firebase.auth.FirebaseUser



class DashboardActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user = FirebaseAuth.getInstance().currentUser
        databaseRef = FirebaseDatabase.getInstance().reference
//        println("User "+user?.email)

        fabAddTodo.setOnClickListener {
            val addTodoDialog = LayoutInflater.from(this).inflate(R.layout.todo_add_dialog, null)
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(addTodoDialog)
//                .setTitle("Add Todos")
                .show()

            addTodoDialog.btn_add_todo.setOnClickListener {
                val todo = addTodoDialog.input_todo.text.toString()
                val key = databaseRef.child("ToDo").child(""+user?.uid).push()
//                databaseRef.child("ToDo").child("UserId").setValue(user?.uid)
                key.child("Task").setValue(todo)

                dialogBuilder.dismiss()

                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
            }

//            Snackbar.make(it, "Fab Button Clicked!", Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
//                Toast.makeText(this, "Signing Out", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
