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
                    export BUILD_ID=dontKillMe
pid=$(netstat -nlp | grep :8088 | awk '{print $7}' | awk -F"/" '{ print $1 }');
if [ -n "${pid}" ]; then
kill -9 ${pid};
fi
scp -i ooclserver_rsa -f /var/lib/jenkins/workspace/parking-tag-backend/build/libs/parking-tag-1.0-SNAPSHOT.jar root@39.98.243.100:/opt/parking-tag-backend/
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar /opt/parking-tag-backend/parking-tag-1.0-SNAPSHOT.jar &
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
