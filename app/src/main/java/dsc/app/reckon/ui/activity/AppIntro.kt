package dsc.app.reckon.ui.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import dsc.app.reckon.R
import dsc.app.reckon.utils.PrefManager
import kotlinx.android.synthetic.main.app_intro.*

class AppIntro : AppCompatActivity(), OnClickListener {

    /**
     * Declare global variables
     * */
    private lateinit var mViewPager : ViewPager
    private val appIntroLayouts : IntArray = intArrayOf(
            R.layout.ai_1_screen,
            R.layout.ai_2_screen,
            R.layout.ai_3_screen,
            R.layout.ai_1_screen
    )
    lateinit var dotsLayout : LinearLayout
    lateinit var dots : Array<ImageView>
    lateinit var mPageAdapter : dsc.app.reckon.OnBoarding.PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Do this before setting the content view
        if (PrefManager(this).checkPref()){
            gotoLiveStockPicker()
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if ( Build.VERSION.SDK_INT >= 19){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        setContentView(R.layout.app_intro)
        btn_get_started.visibility = View.GONE

        //Use [Synthetic] instead of [Find view by id]
        //[Initialize] views
        mViewPager = view_pager
        dotsLayout = dots_layout

        //Set On click Listeners
        i_btn_next.setOnClickListener(this)
        btn_get_started.setOnClickListener(this)

        mPageAdapter = dsc.app.reckon.OnBoarding.PageAdapter(appIntroLayouts, this)
        mViewPager.adapter = mPageAdapter

        makeDots(0)

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                makeDots(position)
                if (position == appIntroLayouts.size -1){
                    dots_layout.visibility = View.INVISIBLE
                    i_btn_next.visibility = View.INVISIBLE
                    btn_get_started.visibility = View.VISIBLE
                }else{
                    dots_layout.visibility = View.VISIBLE
                    i_btn_next.visibility = View.VISIBLE
                    btn_get_started.visibility = View.INVISIBLE
                }
            }

        })
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.i_btn_next -> {
                gotoNextAppIntoScreen()
            }
            R.id.btn_get_started -> {
                gotoLiveStockPicker()
                PrefManager(this).writeSp()
            }
        }
    }

    //Create the dots
    private fun makeDots(position : Int){
        dotsLayout.removeAllViews()
        dots = Array(appIntroLayouts.size) { _ -> ImageView(this)}
        for (i in 0 until appIntroLayouts.size -1){
            dots[i] = ImageView(this)
            if (i == position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dots))
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dots))
            }
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    //Width, Height
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            //Left, Top, Right, Bottom
            params.setMargins(6,0,6,0)
            dotsLayout.addView(dots[i],params)
        }
    }

    private fun gotoNextAppIntoScreen() {
        val nextScreen : Int = mViewPager.currentItem +1
        if (nextScreen < appIntroLayouts.size){
            mViewPager.currentItem = nextScreen
        }else{
            PrefManager(this).writeSp()
        }
    }

    private fun gotoLiveStockPicker(){
        startActivity(Intent(this, AppEntryPoint::class.java))
        finish()
    }
}
