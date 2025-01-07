package com.example.diary.Main.Fragments.DataEntries

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diary.DataBase.DataCC
import com.example.diary.DataBase.EdataBase
import com.example.diary.Main.ModelV.MainVM
import com.example.diary.R
import com.example.diary.databinding.FragmentEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class Edit: Fragment() {
    private lateinit var bind:FragmentEditBinding
    private lateinit var database:EdataBase
    private lateinit var sharedVM:MainVM
    private lateinit var currentItem:DataCC
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Instances
        sharedVM = ViewModelProvider(requireActivity())[MainVM::class.java]
        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_edit,container,false)
        database = EdataBase.getData(requireContext())
        //CrashProtection
        if(sharedVM.updatespermission){
            sharedVM.CurrentItemad.value = currentItem
            sharedVM.updatespermission = false
        }
        currentItem = sharedVM.CurrentItemad.value!!
       //Toast.makeText(requireContext(),sharedVM.CurrentItemad.value!!.Text,Toast.LENGTH_SHORT).show()

        //setData
        bind.Data.setText(currentItem.Text)
        bind.Titlee.setText(currentItem.Title)
        //end
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.Update.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    fun check():Boolean
    {
        return bind.Data.text.isNotEmpty()||bind.Titlee.text.isNotEmpty()
    }
    fun fetchData(data:DataCC){
        currentItem = data
    }

    override fun onDestroy() {
        if(check())
        {
            val texto = bind.Data.text.toString()
            val titlo = bind.Titlee.text.toString()
            val idd = currentItem.id
            val Datacc = DataCC(idd,titlo,texto,currentItem.Date)
            CoroutineScope(Dispatchers.IO).launch{
                database.EDBDao().UpdateData(Datacc)
            }
        }
        super.onDestroy()
    }
}