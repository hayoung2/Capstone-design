package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.testapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class LoginActivity : AppCompatActivity() {
    companion object{
        var currentUser: String?=null
        var type:String? =null
        var currentName:String?=null
        var profileImg:String?=""
    }
    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    private val RC_SIGN_IN=9001
    private var googleSignInClient: GoogleSignInClient?=null
    private var firebaseAuth: FirebaseAuth?=null
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        mBinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth= FirebaseAuth.getInstance()



        binding.btnGoogle.setOnClickListener {
            val signInIntent=googleSignInClient?.signInIntent
            startActivityForResult(signInIntent,RC_SIGN_IN)
            type="g"
        }

        binding.btnEmail.setOnClickListener {
            val intent=Intent(this,EmailLoginActivity::class.java)
            startActivity(intent)
        }

        binding.txtSignup.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {

            }
            else if (tokenInfo != null) {
                val intent = Intent(this, MainActivity::class.java)
                UserApiClient.instance.me { user, error ->
                    currentUser=user?.id.toString()
                    currentName=user?.kakaoAccount?.profile?.nickname.toString()
                    profileImg=user?.kakaoAccount?.profile?.profileImageUrl.toString()
                    Log.d("확인 부탁 합"," 확인차 "+profileImg)
                    myRef.child("User").get().addOnSuccessListener {
                       Log.d("확인 ",it.value.toString())
                    }
                        myRef.child("User").child(user?.id.toString()).setValue(User(user?.kakaoAccount?.profile?.nickname.toString()))
                }
                type="k"
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    currentUser=user?.id.toString()
                    currentName=user?.kakaoAccount?.profile?.nickname.toString()
                    profileImg=user?.kakaoAccount?.profile?.profileImageUrl.toString()
                    Log.d("확인 부탁 합"," 확인차 "+profileImg)
                }
                val intent = Intent(this, MainActivity::class.java)
                type="k"
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
         // 로그인 버튼
        binding.btnKakao.setOnClickListener {
            type="k"
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode===RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account=task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }catch (e: ApiException){

            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(acct.idToken,null)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener(this){
            if (it.isSuccessful){
                val user=firebaseAuth?.currentUser
                currentUser=user?.uid
                currentName=user?.displayName
                profileImg=user?.photoUrl.toString()
                Log.d("확인 부탁 합"," 확인차 "+profileImg)
               type="g"
                myRef.child("User").get().addOnSuccessListener {
                    if(!it.hasChild(user!!.uid)){
                        myRef.child("User").child(user!!.uid).setValue(User(user.displayName.toString()))
                    }
                }
               val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }else{
                Toast.makeText(this,"실패",Toast.LENGTH_SHORT).show()
            }
        }
    }


    data class User(var name:String)

}