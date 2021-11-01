package com.jimdac_todolist.simplelottoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }

    private val clearButton: Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val autoRunButton: Button by lazy {
        findViewById(R.id.autoRunButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.NumberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf(
            findViewById(R.id.textView1),
            findViewById(R.id.textView2),
            findViewById(R.id.textView3),
            findViewById(R.id.textView4),
            findViewById(R.id.textView5),
            findViewById(R.id.textView6)
        )
    }
    private val pickNumberSet = hashSetOf<Int>()

    private var didAutoRun:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //1~45값으로 설정
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        //add버튼 리스너
        addButtonListener()
        //자동생성버튼 리스너
        initAutoRunButtonListener()
        //초기화 버튼 리스너
        clearButtonListener()
    }

    //번호 추가하기 버튼 눌렀을때 실행되는 리스너
    private fun addButtonListener() {
        addButton.setOnClickListener {

            if (didAutoRun){
                Toast.makeText(this@MainActivity, "자동생성 버튼을 이미 사용하였습니다. " +
                        "수동으로 번호 추가 버튼을 사용하기 위해서는 초기화 버튼을 눌러주세요", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 6) {
                Toast.makeText(this@MainActivity, "번호는 6개 까지만 선택이 가능합니다.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (numberPicker.value in pickNumberSet) {
                Toast.makeText(this@MainActivity, "해당번호는 이미 선택했습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            setNumberAndBackground(pickNumberSet.size,numberPicker.value)
            pickNumberSet.add(numberPicker.value)

        }
    }
    //자동생성버튼 눌렀을 때 발생되는 리스너
    private fun initAutoRunButtonListener() {
        autoRunButton.setOnClickListener {
            didAutoRun = true
            
            //(6 - 수동으로 입력한 번호 개수) 만큼 랜덤번호를 불러와 랜덤번호 설정
            getRandomNumber().forEachIndexed { index, value ->
                numberTextViewList[index].text = value.toString()
                setNumberAndBackground(index,value)
            }
        }
    }

    // 로또번호 생성하는 함수
    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (i !in pickNumberSet){ //만약 해당숫자가 뽑은 숫자가 아니라면
                    this.add(i) //그 숫자를 넣는다.
                }
            }
        }
        
        //1~45까지의 수 중에서 랜덤으로 섞어 최대 6개를 뽑아 정렬한 후 리스트형으로 반환
        numberList.shuffle().let {
            return (pickNumberSet + numberList.subList(0, (6 - pickNumberSet.size))).sorted()
        }

    }

    //초기화 버튼 리스너
    private fun clearButtonListener(){
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.text = null
                it.background = null
            }
            didAutoRun = false
        }
    }
    
    //버튼의 백그라운들와 텍스트를 번호의 범위마다 설정
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setNumberAndBackground(index: Int, value: Int) {
        when (value) {
            in 1..10 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_yellow)
                numberTextViewList[index].text = value.toString()
            }
            in 11..20 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_blue)
                numberTextViewList[index].text = value.toString()
            }
            in 21..30 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_red)
                numberTextViewList[index].text = value.toString()
            }
            in 31..40 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_gray)
                numberTextViewList[index].text = value.toString()
            }
            in 41..45 -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_green)
                numberTextViewList[index].text = value.toString()
            }
            else -> {
                numberTextViewList[index].background = getDrawable(R.drawable.circle_green)
                numberTextViewList[index].text = value.toString()
            }
        }
    }
}