package com.kenshi.kmmtranslator.translate.data.local

import com.kenshi.kmmtranslator.database.TranslateDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    // context 를 전달하지 않고 사용 가능
    // 데스크톱 프로젝트에서도 사용하는 방법
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(TranslateDatabase.Schema, "translate.db")
    }
}