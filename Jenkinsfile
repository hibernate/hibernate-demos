pipeline {
    agent {
        label 'Worker&&Containers'
    }
    tools {
        jdk 'OpenJDK 17 Latest'
        maven 'Apache Maven 3.9'
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
}
