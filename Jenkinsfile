pipeline {
    agent none

    stages {
        stage('Test') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    chmod +x gradlew
                '''
            }
        }
        stage('Build') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    chmod +x gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('Deploy Prod') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    ssh -o StrictHostKeyChecking=no -i ~/.ssh/ooclserver_rsa root@39.98.243.100 "rm -f /opt/parking-tag-backend/parking-tag-1.0-SNAPSHOT.jar"
                    scp -o StrictHostKeyChecking=no -i ~/.ssh/ooclserver_rsa /var/lib/jenkins/workspace/prod-parking-tag-backend/build/libs/parking-tag-1.0-SNAPSHOT.jar root@39.98.243.100:/opt/parking-tag-backend/ 
                    ssh -o StrictHostKeyChecking=no -i ~/.ssh/ooclserver_rsa root@39.98.243.100 < deploy.sh
                '''
            }
        }
    }
}
