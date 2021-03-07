package com.example.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    var score=0
    var level=0
    private var t:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t=task()
    }
    fun onClick(view:View){
       val button= view as Button
        if (button.text==t.toString()) score++
        t=task()
    }
    @SuppressLint("SetTextI18n")
    private fun task():Int?{
        var trueAnswer: Int? = null
        level++
        if (level==16){
            val intent=Intent(this,Score::class.java)
            intent.putExtra("ball",score)
            startActivity(intent)
            finish()
        }
        else {
            levels.text = "$level/15"
            val belgi = Random.nextInt(1..4)
            val firstNumber = Random.nextInt(1..25)
            val secondNumber = Random.nextInt(1..25)
            firstOp.text = firstNumber.toString()
            secoondOp.text = secondNumber.toString()
            when (belgi) {
                1 -> {
                    operator.text = "+"
                    trueAnswer = firstNumber + secondNumber
                    fourVariants(1, trueAnswer)
                }
                2 -> {
                    operator.text = "-"
                    trueAnswer = firstNumber - secondNumber
                    fourVariants(2, trueAnswer)
                }
                3 -> {
                    operator.text = "x"
                    trueAnswer = firstNumber * secondNumber
                    fourVariants(3, trueAnswer)
                }
                4 -> {
                    val c = div(firstNumber)
                    secoondOp.text = c.toString()
                    operator.text = "/"
                    trueAnswer = firstNumber / c
                    fourVariants(4, trueAnswer)
                }
            }
        }
        return trueAnswer
    }
    private fun fourVariants(belgi:Int, trueAnswer:Int){
        val dist:ArrayList<Int>
        val ret=IntArray(4)
        when (belgi) {
            1 -> {
                dist=random(-10,10,4)
                ret[0]=trueAnswer+dist[3]
                ret[1]=trueAnswer+dist[0]
                ret[2]=trueAnswer+dist[1]
                ret[3]=trueAnswer+dist[2]
            }
            2 -> {
                dist = if (trueAnswer>0) random(-trueAnswer,10,4)
                else random(-10,-trueAnswer,4)
                ret[0]=trueAnswer+dist[3]
                ret[1]=trueAnswer+dist[0]
                ret[2]=trueAnswer+dist[1]
                ret[3]=trueAnswer+dist[2]
            }
            3 -> {
                dist=random(-9,9,4)
                ret[0]=trueAnswer+dist[3]
                ret[1]=(trueAnswer/10-dist[0])*10+trueAnswer%10
                ret[2]=trueAnswer+dist[1]
                ret[3]=trueAnswer/2+dist[2]
            }
            else -> {
                dist = if (trueAnswer > 10) random(-10, 10, 4)
                else random(-1, 10, 4)
                ret[0] = trueAnswer+dist[3]
                ret[1] = trueAnswer * 2 + dist[0] / 2
                ret[2] = trueAnswer + dist[1]
                ret[3] = trueAnswer / 2 + dist[2] * 2
            }
        }
        val k= Random.nextInt(4)
        ret[k]=trueAnswer
        varA.text="${ret[0]}"
        varB.text="${ret[1]}"
        varC.text="${ret[2]}"
        varD.text="${ret[3]}"
    }
    private fun random(from:Int, to:Int, k:Int):ArrayList<Int> {
        var n = 0
        val array = arrayListOf<Int>()
        do {
            val rand = Random.nextInt(from..to)
            if (!array.contains(rand)&&rand!=0) {
                array.add(rand)
                n++
            }
        } while (n < k)
        return array
    }
    fun div(k:Int):Int {
        val l = arrayListOf<Int>()
        for (i in 1..k)
            if (k % i == 0) l.add(i)

        val b = Random.nextInt(0 until l.size)
        return l[b]
    }
}