package com.example.paymentapp.data.terminal.impl

import com.example.paymentapp.data.terminal.Terminal
import com.example.paymentapp.data.terminal.TerminalRepo

class TerminalRepoImpl() : TerminalRepo {

    override fun getAvailableTerminals(): List<Terminal> {
        // Create a more strongly typed Terminal Type that will represent an actual terminal
        return Terminal.values().toList()

    }
}