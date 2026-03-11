pipeline {
    agent any
    tools {
        jdk "jdk-21" // valid options are: "jdk-8", "jdk-16", "jdk-17" or "jdk-21", choose which one you need
    }
    environment {
        discord_webhook = credentials('gt-discord-webhook')
    }
    stages {
        stage('Update submodule') {
            steps {
                echo 'Updating submodule'
                sh 'git submodule update --init --recursive'
            }
        }
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build & Publish') {
            steps {
                echo 'Building & Publishing'
                sh './gradlew build :publish'
            }
        }
    }
    post {
        always {
            discordSend(
                webhookURL: env.discord_webhook,
                thumbnail: "https://raw.githubusercontent.com/GT-Reimagined/gt-reimagined.github.io/refs/heads/main/icon.png",
                title: "GT5 Reimagined ${TAG_NAME} #${BUILD_NUMBER}",
                link: env.BUILD_URL,
                result: currentBuild.currentResult,
                description: "Build: [${BUILD_NUMBER}](${env.BUILD_URL})\nStatus: ${currentBuild.currentResult}"
            )
        }
    }
}
