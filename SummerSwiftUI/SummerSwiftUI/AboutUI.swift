//
//  ContentView.swift
//  SummerSwiftUI
//
//  Created by Kirill Terekhov on 02.01.2021.
//

import SwiftUI
import URLImage
import shared

class AboutViewState: BaseState, AboutView {
    @Published var about: About? = nil
    @Published var isLoading: Bool = false
}

struct AboutUI: View {

    @ObservedObject var state = AboutViewState()
    var viewModel = AboutViewModel()
    init() {
        state.bind(viewModel)
    }

    var body: some View {
        VStack {
            if let about = state.about {
                Text(about.frameworkName)
                Text(about.author)
                if let logoUrl = URL(string: about.logoUrl) {
                    URLImage(url: logoUrl) { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    }
                }
            } else {
                ProgressView()
            }
        }
    }
}

struct AboutUI_Previews: PreviewProvider {
    static var previews: some View {
        AboutUI()
    }
}
