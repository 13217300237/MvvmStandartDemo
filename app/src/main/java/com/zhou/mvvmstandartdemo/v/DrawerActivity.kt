package com.zhou.mvvmstandartdemo.v

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import com.zhou.baselib.BaseActivity
import com.zhou.baselib.third.pop.HorizontalPosition
import com.zhou.baselib.third.pop.SmartPopupWindow
import com.zhou.baselib.third.pop.VerticalPosition
import com.zhou.baselib.utils.DeviceUtils
import com.zhou.mvvmstandartdemo.R
import com.zhou.mvvmstandartdemo.vm.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_drawer.*


class DrawerActivity : BaseActivity<LoginActivityViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_drawer
    }

    /**
     * 注册数据监听
     */
    override fun initObserver() {

    }

    private var topExpandShown = false
    private var topExpandHeight: Float = -1f
    private var topExpandY: Float = -1f

    private var switchProductY: Float = -1f
    private var switchProductHeight: Int = -1

    override fun initView() {
        drawerLeft.setOnClickListener {
            layoutDrawer.openDrawer(Gravity.LEFT)
        }

        drawerRight.setOnClickListener {
            layoutDrawer.openDrawer(Gravity.RIGHT)
        }

        clOrder.visibility = View.VISIBLE
        clOpenOrder.visibility = View.GONE
        btnOpenOrder.visibility = View.GONE

        clOrder.setOnClickListener {
            clOrder.visibility = View.GONE

            clOpenOrder.visibility = View.VISIBLE
            btnOpenOrder.visibility = View.VISIBLE
        }

        clOpenOrder.setOnClickListener {
            clOpenOrder.visibility = View.GONE
            btnOpenOrder.visibility = View.GONE

            clOrder.visibility = View.VISIBLE
        }

        // 中间标题点击事件，用动画效果展示扩展空间
        topExpand.visibility = View.INVISIBLE
        tvMidTitle.setOnClickListener {
            switchTopExpand()
        }
        ivExpanded.setOnClickListener {
            doAnimator(ifShow = false)
        }

        val ob = topExpand.viewTreeObserver
        ob.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (topExpandHeight == -1f) {
                        topExpandHeight = topExpand.height.toFloat()
                        topExpandY = topExpand.y
                    }
                    if (ob.isAlive)
                        ob.removeOnGlobalLayoutListener(this)
                }
            }

        })


        var ob2 = cl_2.viewTreeObserver
        ob2.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (switchProductY == -1f) {
                        switchProductY = cl_2.y
                        switchProductHeight = cl_2.height
                        Log.d("xxxfadsf", "$switchProductY")

                    }
                    if (ob.isAlive)
                        ob.removeOnGlobalLayoutListener(this)
                }
            }

        })

        switchProduct.setOnClickListener {
            pop.showAtAnchorView(
                switchProduct,
                VerticalPosition.BELOW,
                HorizontalPosition.ALIGN_LEFT
            )
            // 隐藏底部布局
        }
    }


    private val pop: SmartPopupWindow by lazy {
        val mPopupContentView =
            LayoutInflater.from(this).inflate(R.layout.layout_switch_product, null, false)

        val w = resources.displayMetrics.widthPixels - DeviceUtils.dp2px(8f) * 2
        val h = resources.displayMetrics.heightPixels -
                switchProductY -
                switchProductHeight -
                DeviceUtils.dp2px(11f) -
                DeviceUtils.getStatusBarHeight(this)

        val pop = SmartPopupWindow.Builder
            .build(this, mPopupContentView)
            .setAlpha(1f)
            .setAnimationStyle(0)
            .setSize(w, h.toInt())
            .createPopupWindow()
        pop
    }

    private fun switchTopExpand() {
        if (!topExpandShown) {
            doAnimator(ifShow = true)
        } else {
            doAnimator(ifShow = false)
        }
    }

    private var animationRunning = false
    private fun doAnimator(ifShow: Boolean) {
        if (animationRunning)// 动画运行中，不接收任何动画指令
            return

        animationRunning = true
        val totalHeight = topExpandHeight// 获得原始高度
        val oriY = topExpandY

        // 初始状态
        topExpand.visibility = View.VISIBLE
        val calY = if (ifShow) oriY - totalHeight else oriY
        val ani = ValueAnimator.ofFloat(0f, 1f)
        ani.interpolator = if (ifShow) BounceInterpolator() else LinearInterpolator()
        ani.duration = if (ifShow) 500 else 100
        ani.addUpdateListener {
            val currentFloat = it.animatedValue as Float
            if (ifShow)
                topExpand.y = calY + totalHeight * currentFloat
            else
                topExpand.y = calY - totalHeight * currentFloat
        }

        ani.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animationRunning = false
                topExpandShown = ifShow
                if (ifShow) {
                    topExpand.visibility = View.VISIBLE
                } else {
                    topExpand.visibility = View.GONE
                }
            }
        })

        ani.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        layoutDrawer.closeDrawers()
    }
}