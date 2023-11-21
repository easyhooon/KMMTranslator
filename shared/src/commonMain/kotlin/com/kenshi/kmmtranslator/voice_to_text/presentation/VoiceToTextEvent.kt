package com.kenshi.kmmtranslator.voice_to_text.presentation

sealed class VoiceToTextEvent {
    object Close: VoiceToTextEvent()
    data class PermissionResult(
        val isGranted: Boolean,
        val isPermanentlyDeclined: Boolean,
    ): VoiceToTextEvent()
    data class ToggleRecording(val languageCode: String): VoiceToTextEvent()
    // Reset 이벤트는 iOS 만 해당
    object Reset: VoiceToTextEvent()
}