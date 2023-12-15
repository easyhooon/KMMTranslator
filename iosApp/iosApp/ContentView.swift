import SwiftUI
import shared

struct ContentView: View {
    
    let appModule: AppModule

	var body: some View {
        // 다크 모드 대응
        ZStack {
            Color.background
                .ignoresSafeArea()
            TranslateScreen(
                historyDataSource: appModule.historyDataSource,
                translateUseCase: appModule.translateUseCase,
                parser: appModule.voiceParser
            )
        }
	}
}
