node {
    stage("checkout") {
        checkout scm
    }
    stage("Compile project") {
        sh './gradlew compile'
    }
    stage("Test fast") {
        sh './gradlew test'
    }
    stage("Assemble") {
        sh './gradlew assemble'
    }
}
