val TERMINAL_TYPES_ARRAY by extra("com.example.paymentapp.data.terminal.Terminal[]")

mapOf(
    "NULL" to "null",
    "TERMINALS_FIELD" to "TERMINALS",
    "TERMINALS_TYPE" to "com.example.paymentapp.data.terminal.Terminal[]",

    // Terminal Types
    "RP_45BT" to "com.example.paymentapp.data.terminal.Terminal.RP_45BT",
    "MOBY_3000" to "com.example.paymentapp.data.terminal.Terminal.MOBY_3000",
    "MOBY_5500" to "com.example.paymentapp.data.terminal.Terminal.MOBY_5500",
    "MOBY_8500" to "com.example.paymentapp.data.terminal.Terminal.MOBY_8500",
    "G5X" to "com.example.paymentapp.data.terminal.Terminal.G5X",
    "C2_X" to "com.example.paymentapp.data.terminal.Terminal.C2_X",
    "MAGTEK_ADYNAMO" to "com.example.paymentapp.data.terminal.Terminal.MAGTEK_ADYNAMO",
).forEach { (name, type) ->
    project.extra.set(name, type)
}