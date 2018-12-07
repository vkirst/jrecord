node {
    jdk = tool name: 'Java10'
    env.JAVA_HOME = "${jdk}"

    stage('Preparation') {
        checkout scm
    }

    stage('Build') {
      if (isUnix()) {
         sh "./gradlew clean assemble"
      } else {
         cmd "gradlew.bat clean assemble"
      }
    }
    
    stage('Test') {
      if (isUnix()) {
         sh "./gradlew test"
      } else {
         cmd "gradlew.bat test"
      }
    }

    stage('Pack') {
      if (isUnix()) {
         sh "./gradlew build"
      } else {
         cmd "gradlew.bat build"
      }
    }

    stage('Result'){
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
        junit '**/build/test-results/test/TEST-*.xml'
    }
}

