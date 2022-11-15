#!/bin/bash
major=$(cat ./app/src/main/kotlin/Versions.kt | grep 'versionMajor =' | cut -d '=' -f2 | xargs)
minor=$(cat ./app/src/main/kotlin/Versions.kt | grep 'versionMinor =' | cut -d '=' -f2 | xargs)
patch=$(cat ./app/src/main/kotlin/Versions.kt | grep 'versionPatch =' | cut -d '=' -f2 | xargs)
version_name="$major.$minor.$patch"
echo "version_name=$version_name" >> $GITHUB_OUTPUT