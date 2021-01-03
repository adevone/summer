package io.adev.summer.example.domain.frameworks

import io.adev.summer.example.entity.Framework

class GetSummer {

    operator fun invoke(): Framework {
        return Framework(
            name = "Summer",
            version = "0.8.17"
        )
    }
}