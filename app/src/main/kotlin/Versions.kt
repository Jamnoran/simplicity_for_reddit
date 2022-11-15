object Versions {
    private const val versionMajor = 1
    private const val versionMinor = 2
    private const val versionPatch = 1
    const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    const val versionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
}
