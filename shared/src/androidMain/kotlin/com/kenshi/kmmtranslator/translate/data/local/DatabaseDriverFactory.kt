package com.kenshi.kmmtranslator.translate.data.local

import android.content.Context
import com.kenshi.kmmtranslator.database.TranslateDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    // android 에서는 context 가 존재하지만 iOS 에는 존재하지 않음
    private val context: Context,
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(TranslateDatabase.Schema, context, "translate.db")
    }
}