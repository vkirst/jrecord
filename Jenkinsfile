node {
    jdk = tool name: 'Java10'
    env.JAVA_HOME = "${jdk}"

    stage("checkout") {
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
    stage('Publish'){
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
        junit '**/build/test-results/test/TEST-*.xml'
    }
}
