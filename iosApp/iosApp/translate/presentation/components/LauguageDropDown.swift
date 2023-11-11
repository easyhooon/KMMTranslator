//
//  LauguageDropDown.swift
//  iosApp
//
//  Created by 이지훈 on 11/11/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LauguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu {
            VStack {
                ForEach(UiLanguage.Companion().allLanguages, id: \.self.language.langCode) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                            selectLanguage(language)
                        }
                    )
                }
            }
        } label: {
            HStack {
                SmallLanguageIcon(language: language)
                Text(language.language.langName)
                    .foregroundColor(.lightBlue)
                Image(systemName: isOpen ? "chevron.up" : "chevronDown")
                    .foregroundColor(.lightBlue)
            }
        }
    }
}

#Preview {
    LauguageDropDown(
        language: UiLanguage(language: .german, imageName: "german"),
        isOpen: true,
        selectLanguage: { language in }
    )
}
