GRADLE_PROPERTIES="./gradle.properties"
export GRADLE_PROPERTIES
echo "Gradle Properties should exist at $GRADLE_PROPERTIES"

if [ ! -f "$GRADLE_PROPERTIES" ]; then
    echo "Gradle Properties does not exist"
    echo "Creating Gradle Properties file..."
    touch $GRADLE_PROPERTIES
    echo "Writing curseForgeApiKey to gradle.properties..."
    echo "curseForgeApiKey=00000-00000-00000-00000-00000" >> $GRADLE_PROPERTIES
    echo "mavenUsername=null" >> $GRADLE_PROPERTIES
    echo "mavenPassword=null" >> $GRADLE_PROPERTIES
fi