package br.com.zup.marvel.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.data.model.Marvel
import br.com.zup.marvel.data.repository.MarvelRepository
import br.com.zup.marvel.domain.repository.AuthRepository

class HomeViewModel: ViewModel() {
    private val authenticationRepository = AuthRepository()

    private val marvelRepository = MarvelRepository()

    private var _marvelListState = MutableLiveData<List<Marvel>>()
    val marvelListState: LiveData<List<Marvel>> = _marvelListState

    fun getListMarvel() {
       val listMarvel =  marvelRepository.getMarvelList()
        _marvelListState.value = listMarvel
    }

    fun getUserName() = authenticationRepository.getNameUser()

    fun logout() = authenticationRepository.logoutUser()

}