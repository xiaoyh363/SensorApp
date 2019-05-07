package com.xiaoyh.retrofit

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.xiaoyh.retrofit.bean.request.TokenBean
import com.xiaoyh.retrofit.fragment.IndexFragment
import com.xiaoyh.retrofit.fragment.SensorFragment
import com.xiaoyh.retrofit.fragment.TestFragment
import com.xiaoyh.retrofit.util.common.LogUtil
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private lateinit var indexFragment: IndexFragment
    private lateinit var sensorFragment: SensorFragment
    private lateinit var testFragment: TestFragment

    private lateinit var profilePhoto: CircleImageView
    private lateinit var profileUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        indexFragment = IndexFragment()
        sensorFragment = SensorFragment()
        testFragment = TestFragment()

        addFragment(indexFragment, R.id.fragment)

        // 为 nav 绑定监听事件
        nav_user.setNavigationItemSelectedListener(this)

        // 为 nav 的头部控件绑定事件
        val navHeader = nav_user.getHeaderView(0)
        profilePhoto = navHeader.findViewById(R.id.profile_photo)
        profileUserName = navHeader.findViewById(R.id.profile_username)
        profilePhoto.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        syncUser()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.profile_photo -> {
                LogUtil.d(TokenBean.getInstance().toString())
                if (TokenBean.getInstance().isEmpty) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
            }
        }
    }

    // Drawer's item 的 点击事件
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_index -> replaceFragment(indexFragment, R.id.fragment)
            R.id.nav_sensor -> replaceFragment(sensorFragment, R.id.fragment)
            R.id.nav_test -> replaceFragment(testFragment, R.id.fragment)
            R.id.nav_share -> {

            }
            R.id.nav_setting -> startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }
        return true
    }

    // 同步用户方法
    private fun syncUser() {
        if (TokenBean.getInstance().isEmpty) {
            profileUserName.text = resources.getString(R.string.profile_username_default)
        } else {
            profileUserName.text = TokenBean.getInstance().appId.toString()
        }
    }

    // 先关闭 drawer 再退出
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

    private fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction { add(frameId, fragment) }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
}