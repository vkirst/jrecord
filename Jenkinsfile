node {
    docker.image('openjdk').inside {
        stage("Preparation") {
            checkout scm
            sh './gradlew clean'
        }
        stage("Compile project") {
            sh './gradlew compileJava'
        }
        stage("Test fast") {
            sh './gradlew test'
        }
        stage("Assemble") {
            sh './gradlew assemble'
        }
        stage('Publish local'){
            sh './gradlew publish'
            archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
            junit '**/build/test-results/test/TEST-*.xml'
        }
        stage('Publish remote'){
            sh 'git checkout artifacts'
            sh 'git pull'
            sh 'cp -r temp/* repos/releases/'
            sh 'git add repos/'
            sh 'git commit -m "new build"'
            sh 'git push'
            sh 'git checkout fix_fork_jenkins'
        }
    }
}

