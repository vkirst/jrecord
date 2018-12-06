node {
    jdk = tool name: 'Java10'
    env.JAVA_HOME = "${jdk}"

    stage('Preparation') {
      git branch:'fix_fork_jenkins', url: 'https://github.com/PixarV/jrecord.git'
    }   

    stage('Build') {
      if (isUnix()) {
         sh "./gradlew clean build"
      } else {
         cmd "gradlew.bat clean build"
      }
   }
   stage('Results') {
      junit '**/build/test-results/test/TEST-*.xml'
      archive '**/build/libs/*.jar'
   }
}
