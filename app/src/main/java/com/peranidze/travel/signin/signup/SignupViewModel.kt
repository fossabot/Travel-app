package com.peranidze.travel.signin.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peranidze.cache.PreferenceHelper
import com.peranidze.data.user.interactor.SignUpUserUseCase
import io.reactivex.disposables.CompositeDisposable

class SignupViewModel(
    private val signUpUserUseCase: SignUpUserUseCase,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val signUpLiveData: MutableLiveData<SignUpState> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData

    fun doSignUp(email: String, password: String) {
        signUpLiveData.postValue(SignUpState.Loading)
        disposables.add(
            signUpUserUseCase
                .execute(SignUpUserUseCase.Params(email, password))
                .subscribe({
                    preferenceHelper.isUserLoggedIn = true
                    signUpLiveData.postValue(SignUpState.Success(it))
                }, {
                    signUpLiveData.postValue(SignUpState.Error(it.message))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
