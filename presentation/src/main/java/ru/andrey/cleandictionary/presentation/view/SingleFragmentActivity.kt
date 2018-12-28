package ru.andrey.cleandictionary.presentation.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ru.andrey.cleandictionary.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    private val layoutResId: Int
        get() = R.layout.activity_fragment

    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        val manager = supportFragmentManager
        var fragment: Fragment? = manager.findFragmentById(R.id.fragmentContainer)

        if (fragment == null) {
            fragment = createFragment()
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit()
        }
    }
}

