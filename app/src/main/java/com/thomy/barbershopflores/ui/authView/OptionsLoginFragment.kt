package com.thomy.barbershopflores.ui.authView

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thomy.barbershopflores.R
import com.thomy.barbershopflores.core.data.model.model.UserDao
import com.thomy.barbershopflores.core.data.model.model.UserGoogle
import com.thomy.barbershopflores.databinding.FragmentOptionsLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class OptionsLoginFragment : Fragment() {

    val TAG: String = "OptionsLoginFragment"
    private var _binding: FragmentOptionsLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient
    private   val RC_SIGN_IN= 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding.btnGoogle.setOnClickListener {
            signIn()
        }

        binding.btnInitEmail.setOnClickListener {
            findNavController().navigate(R.id.action_optionsLoginFragment_to_loginFragment)
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_optionsLoginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        binding.btnGoogle.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {

            val auth = auth.signInWithCredential(credential).await()
            val firebaseUser = auth.user
            withContext(Dispatchers.Main) {
                updateUI(firebaseUser)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun updateUI(firebaseUser: FirebaseUser?) {
        if(firebaseUser != null) {

            val user = UserGoogle(firebaseUser.uid, firebaseUser.displayName!!, firebaseUser.photoUrl.toString())
            val usersDao = UserDao()
            usersDao.addUser(user)
                findNavController().navigate(R.id.action_optionsLoginFragment_to_homeFragment)
        } else {
            binding.btnGoogle.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}


