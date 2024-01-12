package addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor():ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog
    private val _myTaskText = MutableLiveData<String>()
    val myTaskText: LiveData<String> = _myTaskText

    fun onDialogClose(){
        _showDialog.value = false
    }

    fun onTaskCreated(){
        onDialogClose()
        _myTaskText.value = ""
    }

    fun onShowDialogClick(){
        _showDialog.value = true
    }
    fun onTaskTextChanged(taskText:String){
        _myTaskText.value = taskText
    }
}