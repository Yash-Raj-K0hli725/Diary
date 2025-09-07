package com.example.diary.Main.Fragments.DataEntries

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.diary.DataBase.DiaryEntry
import com.example.diary.Main.Utils.SharedModel
import com.example.diary.R
import com.example.diary.databinding.FragmentAddinBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class Addin : Fragment() {
    lateinit var bind: FragmentAddinBinding
    private val sharedVM: SharedModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentAddinBinding.inflate(inflater, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPress)
        onTextChanged()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPress
        )
        return bind.root
    }

    private fun changeMood(v: View) {
        for (i in listOf(bind.lovedBg, bind.sadBg, bind.happyLottie)) {
            if (v.id == i.id) {
                continue
            }
            i.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.translucent_grey)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cal = Calendar.getInstance()
        bind.apply {
            inpTitle.hint = getString(R.string.title_hint)
            val cDate = cal.get(Calendar.DATE).toString()
            val cMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(cal.time)
            date.text = "$cMonth $cDate"
            day.text = SimpleDateFormat("EEEE", Locale.getDefault()).format(cal.time)
            happyLottie.setOnClickListener { v ->
                happyLottie.apply {
                    playAnimation()
                    backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.card4)
                }
                changeMood(v)
            }
            sadLottie.apply {
                setOnClickListener {
                    playAnimation()
                    sadBg.apply {
                        backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.card4)
                        changeMood(sadBg)
                    }
                }
            }
            lovedLottie.apply {
                setOnClickListener {
                    playAnimation()
                    lovedBg.apply {
                        backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.card4)
                        changeMood(lovedBg)
                    }
                }
            }
            back.setOnClickListener {
                makeDiaryEntry()
                findNavController().popBackStack()
            }
        }

    }

    private fun checkInputs(): Boolean {
        return (bind.data.text.isEmpty() && bind.inpTitle.text!!.isEmpty())
    }

    private fun makeDiaryEntry() {
        if (checkInputs()) return
        val title = bind.inpTitle.text.toString()
        val text = bind.data.text.toString()
        val entry = DiaryEntry(title, text, Date())
        sharedVM.makeDiaryEntry(entry)
    }

    private var isCardUP = false //card Anim flag
    private fun onTextChanged() {
        bind.data.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!isCardUP) {
                    animateCard()
                } else if (s.isNullOrEmpty()) {
                    animateCard()
                }
            }
        })
    }

    private fun animateCard() {
        val set = ConstraintSet()
        set.clone(bind.addinMain)
        bind.apply {
            if (!isCardUP) {
                set.connect(
                    card.id,
                    ConstraintSet.TOP,
                    day.id,
                    ConstraintSet.BOTTOM
                )
            } else {
                set.connect(
                    card.id,
                    ConstraintSet.TOP,
                    title.id,
                    ConstraintSet.BOTTOM
                )
            }

            TransitionManager.beginDelayedTransition(addinMain, ChangeBounds().apply {
                duration = 600
                interpolator = DecelerateInterpolator(1.8f)
            })
            set.applyTo(addinMain)
        }
        isCardUP = !isCardUP
    }

    private val onBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isCardUP) {
                animateCard()
            } else {
                makeDiaryEntry()
                findNavController().popBackStack()
            }
        }
    }
}