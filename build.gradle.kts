plugins {
    alias(libs.plugins.android.application) apply false
    
    // Sets the kotlin version used for the project but must not be applied to android modules as the android application handles kotlin itself.
    alias(libs.plugins.kotlin.android) apply false
}
