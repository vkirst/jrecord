node {
    jdk = tool name: 'Java10'
    env.JAVA_HOME = "${jdk}"

    stage('Preparation') {
        checkout([$class: 'GitSCM',
            branches: [[name: 'fix_fork_jenkins']],
            userRemoteConfigs: [[url: 'https://github.com/vkirst/jrecord.git']]
        ])
    }

    stage('Build') {
      if (isUnix()) {
         sh "./gradlew clean build"
      } else {
         cmd "gradlew.bat clean build"
      }
    }
    
    stage('Result'){
        archiveArtifacts artifacts: '**/build/test-results/test/TEST-*.xml', fingerprint: true
        junit '**/build/test-results/test/TEST-*.xml'
    }
}

