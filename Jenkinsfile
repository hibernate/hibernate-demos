pipeline {
    agent {
        label 'Worker&&Containers'
    }
    tools {
        jdk 'OpenJDK 11 Latest'
        maven 'Apache Maven 3.8'
    }
    options {
        disableConcurrentBuilds()
    }
    environment {
        TESTCONTAINERS_RYUK_DISABLED = 'true'
    }
    stages {
        stage('Build Hibernate Search Demos') {
            steps {
                sh "./ci/build-hibernate-search.sh"
            }
        }
    }
    post {
        always {
            zulipNotification smartNotification: 'disabled', stream: 'hibernate-infra', topic: 'activity'
        }
    }
}
