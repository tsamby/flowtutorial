package com.embassylegacy.flowtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    lateinit var flow: Flow<Int>
    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>
    private var TAG : String = "Tsamby"

    var flow2 =flowOf(4, 2, 5, 1, 7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFlow()
        setupClicks()
    }

    fun setupFlow(){
        flow= flow{
            Log.d(TAG, "Start Flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
               // Log.d(TAG, "Emitting $it")
                emit(it)

            }
        }.map {
            it * it
        }.flowOn(Dispatchers.Default)

        flow2.onEach { delay(400) }.flowOn(Dispatchers.Default)

        flowOne = flowOf("Novuyo", "Junior", "Musa", "Bongi").flowOn(Dispatchers.Default)
        flowTwo = flowOf("Tsambani", "Langa", "Khulu").flowOn(Dispatchers.Default)

    }

    //Anything, written above flowOn will run in background thread.

    fun setupClicks(){
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
//                flow.collect {
//                    Log.d(TAG, it.toString())
//                }

 //               (1..5).asFlow().onEach{ delay(300)}.flowOn(Dispatchers.Default).collect{Log.d(TAG, it.toString())}

                //channelFlow{} - This builder creates cold-flow with the elements using send provided by the builder itself
//                channelFlow {
//                    (0..10).forEach {
//                        send(it)
//                    }
//                }.map {
//                    it * 5
//                }.flowOn(Dispatchers.Default).collect{
//                    Log.d(TAG, it.toString())
//                }


               // when the button is clicked the scope is launched.
               // flowOne is zipped with flowTwo to give a pair of values that we have created a string and,
                flowOne.zip(flowTwo)
                { firstString, secondString ->
                    "$firstString $secondString"
                }.collect {
                    Log.d(TAG, it)
                }

            }

        }
    }
}

