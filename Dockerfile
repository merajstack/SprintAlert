FROM ubuntu:22.04

# Install dependencies
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk \
    git \
    curl \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# Set Java home
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

# Download Android SDK
ENV ANDROID_HOME=/root/android-sdk
RUN mkdir -p $ANDROID_HOME && cd $ANDROID_HOME && \
    curl -o cmdline-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-9862592_latest.zip && \
    unzip -q cmdline-tools.zip && \
    rm cmdline-tools.zip && \
    mkdir -p cmdline-tools/latest && \
    mv cmdline-tools/* cmdline-tools/latest/ 2>/dev/null || true

# Set SDK paths
ENV PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

# Accept licenses and install SDK
RUN yes | sdkmanager --sdk_root=$ANDROID_HOME "platforms;android-34" "build-tools;34.0.0" "platform-tools" "tools"

WORKDIR /app

# Copy project
COPY . /app/

# Build
RUN chmod +x /app/gradlew && \
    /app/gradlew assembleRelease -x lint --no-daemon

# Output
RUN ls -lah /app/app/build/outputs/apk/release/
