#!/bin/bash
APP_HOME="$(cd "$(dirname "$0")" && pwd)"
export GRADLE_USER_HOME=${GRADLE_USER_HOME:-"$HOME/.gradle"}
export JAVA_HOME=${JAVA_HOME:-/opt/homebrew/opt/openjdk@11}
export PATH=$JAVA_HOME/bin:$PATH

cd "$APP_HOME"
gradle "$@"
