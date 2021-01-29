package io.adev.summer.example

import io.adev.summer.example.entity.Framework

interface AppNavigator {
    fun toFrameworkDetails(framework: Framework)
}