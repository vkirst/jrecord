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
            checkout scm:[
                $class: 'GitSCM', 
                branches: [[name: 'artifacts']]
            ] 
            sh 'git status'
            sh 'git pull'
            sh 'cp -vr temp/* repos/'
            sh 'git add repos'
        }
    }
}

