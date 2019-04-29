package ua.com.ponomarenko.carmarket.internal

import android.content.Context
import android.widget.Toast

/**
 * Created by Ponomarenko Oleh on 4/29/2019.
 */
fun Context.toast(message: String)= Toast.makeText(this, message, Toast.LENGTH_SHORT).show()