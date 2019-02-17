package com.zeeshan.todoapp


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.zeeshan.todoapp.R.id.loginCreateUserBtn
import com.zeeshan.todoapp.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_log_in.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LogInFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance();
        progress = ProgressDialog(activity)

        if (auth.currentUser != null) {
            startActivity(Intent(activity, DashboardActivity::class.java))
        }

        loginCreateUserBtn.setOnClickListener{
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, CreateUserFragment())
                .addToBackStack(null)
                .commit()

//            Snackbar.make(view,"Login Button Clicked",Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }

        loginLoginButton.setOnClickListener {
            Snackbar.make(view,"Trying to connect Server, Please Wait...",Snackbar.LENGTH_SHORT).setAction("Action",null).show()
            authenticateUser(textEmailAddress.text.toString(), textPassword.text.toString())
        }


    }

    private fun authenticateUser(email: String, password: String) {

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty())
        {
            progress.show()

            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    progress.dismiss()
                    startActivity(Intent(activity, DashboardActivity::class.java))
            }
                .addOnFailureListener {
                    progress.dismiss()
                    it.printStackTrace()
                    Toast.makeText(activity, "Error in signin ", Toast.LENGTH_LONG).show()
                }
        }
        else
        {
            textEmailAddress.setError("Error")
            textPassword.setError("Error")
        }

    }
}
