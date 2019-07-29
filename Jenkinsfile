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
                    ./gradlew test
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
        stage('Deploy Dev') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    scp -o StrictHostKeyChecking=no -i ~/.ssh/ooclserver_rsa -f /var/lib/jenkins/workspace/prod-parking-tag-backend/build/libs/parking-tag-1.0-SNAPSHOT.jar root@39.98.243.100:/opt/parking-tag-backend/ 
                    ssh -o StrictHostKeyChecking=no -i ~/.ssh/ooclserver_rsa root@39.98.243.100 < deploy.sh
                '''
            }
        }
        stage('Approve of Deploy Prod') {
          steps {
            input message: 'deploy to Prod?'
          }
        }

        stage('Deploy Prod') {
            agent {
                label 'master'
            }
            steps {
                sh '''
                    echo deploy prod
                '''
            }
        }
    }
}
