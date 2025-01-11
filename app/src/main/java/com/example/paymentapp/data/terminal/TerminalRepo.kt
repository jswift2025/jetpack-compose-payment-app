package com.example.paymentapp.data.terminal

interface TerminalRepo {

    /**
     * Get a list of available terminal types
     */
    fun getAvailableTerminals(): List<Terminal>
}