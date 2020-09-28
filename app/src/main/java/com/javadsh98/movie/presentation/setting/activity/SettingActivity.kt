package com.javadsh98.movie.presentation.setting.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javadsh98.movie.R
import com.javadsh98.movie.presentation.setting.fragment.SettingFragment

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.constraint_setting, SettingFragment())
            .commit()

    }
}