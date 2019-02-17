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
import com.zeeshan.todoapp.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_create_user.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CreateUserFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var progress : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance();
        progress = ProgressDialog(activity)

        createUserBtn.setOnClickListener {
            progress.show()
            registerUser( createTextEmailAddress.text.toString(), createTextPassword.text.toString())
//            Snackbar.make(view,"Create User Button Clicked", Snackbar.LENGTH_SHORT).setAction("Action",null).show()
        }

        createLoginBtn.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }



    private fun registerUser(email: String, password: String) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty() )
        {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    progress.dismiss()
                    Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    val authResult = it.result
                    authResult?.user?.uid
                    startActivity(Intent(activity, DashboardActivity::class.java))
                }
                else{
                    progress.dismiss()
                    Toast.makeText(activity, "User not created", Toast.LENGTH_LONG).show()
                }
            }
        }
        else
        {
            createTextEmailAddress.setError("Error")
            createTextPassword.setError("Error")
        }
    }


}
