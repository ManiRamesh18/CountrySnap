package com.example.newsapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Country
import com.example.newsapp.data.repo.CountryListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private var countryListRepository: CountryListRepository): ViewModel()
{
    private val _collegeList : MutableState<Result> = mutableStateOf(Result(isLoading =true));
    val collegeList : State<Result> = _collegeList

    private val _flagUnicode : MutableState<String>  = mutableStateOf("")
    val flagUnicode : State<String> = _flagUnicode

    init {
        getAllList()
    }

    private fun getAllList()
    {
        viewModelScope.launch{
            val response = countryListRepository.getCountryFlags()

            withContext(Dispatchers.Main)
            {
                _collegeList.value = Result(isLoading = false, postData = response)
            }
        }
    }

    fun getFlagUnicodeBasedOnCountry(countryName : String)
    {
        _collegeList.value.postData.forEach { it ->
            if(it.name == countryName)
            {
                _flagUnicode.value = it.flag
                return@forEach
            }
        }
    }
}

data class Result(
    val isLoading : Boolean = false,
    val postData : List<Country> = emptyList(),
    val errorMessage : String = ""
)

