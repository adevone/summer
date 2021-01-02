//
//  SummerSwiftUIApp.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import shared

@main
struct SummerSwiftUIApp: App {
    var body: some Scene {
        IosDIKt.bind(userDefaults: UserDefaults.standard)
        return WindowGroup {
            MainUI()
        }
    }
}
