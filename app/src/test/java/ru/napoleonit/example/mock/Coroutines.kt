package ru.napoleonit.example.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val mockScope = CoroutineScope(Dispatchers.Unconfined + SupervisorJob())