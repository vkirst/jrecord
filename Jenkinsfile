node {
    jdk = tool name: 'Java10'
    env.JAVA_HOME = "${jdk}"

    stage('Preparation') {
        checkout scm
    }

    stage('Build') {
      if (isUnix()) {
         sh "./gradlew clean build"
      } else {
         cmd "gradlew.bat clean build"
      }
    }
    
    stage('Result'){
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
        junit '**/build/test-results/test/TEST-*.xml'
    }
}

