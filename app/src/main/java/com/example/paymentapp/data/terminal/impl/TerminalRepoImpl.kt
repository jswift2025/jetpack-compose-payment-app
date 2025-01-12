package com.example.paymentapp.data.terminal.impl

import com.example.paymentapp.BuildConfig
import com.example.paymentapp.data.terminal.Terminal
import com.example.paymentapp.data.terminal.TerminalRepo

class TerminalRepoImpl : TerminalRepo {

    override fun getAvailableTerminals(): List<Terminal> {
        return BuildConfig.TERMINALS.toList()
    }
}