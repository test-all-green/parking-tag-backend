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
cp -f /var/lib/jenkins/workspace/parking-tag-backend/build/libs/parking-tag-backend-1.0-SNAPSHOT.jar /var/prod/parking-tag-backend/
JENKINS_NODE_COOKIE=dontKillMe nohup java -jar /var/prod/parking-tag-backend/parking-tag-backend-1.0-SNAPSHOT.jar &
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
