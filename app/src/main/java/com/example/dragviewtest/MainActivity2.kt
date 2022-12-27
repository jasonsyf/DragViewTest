package com.example.dragviewtest

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity2 : AppCompatActivity() {
    var lastX = 0
    var lastY = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        divider.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val x = event!!.x.toInt()
                val y = event.y.toInt()
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // 记录触摸点坐标
                        lastX = x;
                        lastY = y;
                    }
                    MotionEvent.ACTION_MOVE -> {
                        // 计算偏移量
                        val offsetX = x - lastX;
                        val offsetY = y - lastY;
                        val left = divider.getLeft() + offsetX
                        val right = divider.getRight() + offsetX
                        val leftweight = (left).toFloat() / parentView.right
                        val rightweight =
                            (parentView.right - right).toFloat() / parentView.right

                        if (leftweight > 0.2f && rightweight > 0.2f) {
                            // 在当前left、top、right、bottom的基础上加上偏移量
                            divider.layout(
                                left,
                                divider.getTop(),
                                right,
                                divider.getBottom()
                            );
                            val leftParam = LinearLayout.LayoutParams(
                                0,
                                LayoutParams.MATCH_PARENT,
                                (left).toFloat() / parentView.right
                            )
                            val rightParam = LinearLayout.LayoutParams(
                                0,
                                LayoutParams.MATCH_PARENT,
                                (parentView.right - right).toFloat() / parentView.right
                            )

                            Log.i(
                                "ACTION_MOVE",
                                "onTouch: leftweight==" + leftweight + "==rightweight===" + rightweight
                            )
                            leftView.layoutParams = leftParam
                            rightView.layoutParams = rightParam
                        }
//                        offsetLeftAndRight(offsetX);
//                        offsetTopAndBottom(offsetY);
//                        divider.scrollX = event.x.toInt()
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                }
                return true
            }
        })
    }
}