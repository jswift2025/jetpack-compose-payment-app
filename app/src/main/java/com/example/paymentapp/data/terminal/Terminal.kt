package com.example.paymentapp.data.terminal

enum class Terminal(val displayName: String, val id: Int) {
    RP_350X("350X", 0),
    RP_457X("457C", 1),
    RP_45BT("45-BT", 2),
    MOBY_3000("Moby-3000", 3),
    MOBY_8500("Moby-8500", 4),
    MOBY_5500("Moby-5500", 5),
    G5X("G5X", 6),
    C2X("C2X", 7),
    MAGTEK_ADYMAMO("Magtek-aDynamo", 8)
}