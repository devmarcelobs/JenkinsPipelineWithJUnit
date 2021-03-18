pipeline{
    agent any

    tools{
        maven 'mvn 3.6.3'
    }

    stages{
        stage('Checkout'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '/*master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '', url: 'https://github.com/marcelim122/JenkinsPipelineWithJUnit.git']]])
            }
        }
        stage('Build') {
            steps {
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"
                sh "mvn -Dmaven.test.failure.ignore=true clean verify"
            }

            post {
                success {
                    junit checksName: 'Testss', testResults: '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage('Deploy'){
            steps{
                sh 'sshpass -p root scp -v -o StrictHostKeyChecking=no /var/jenkins_home/workspace/lojaVirtualTest/target/*.jar root@172.18.0.4:/var/artifacts'
                sh 'sshpass -p root ssh -t -t root@172.18.0.4 \
                    JAVA_HOME=/usr/local/openjdk-8 \
                    PATH=$PATH:$HOME/bin:$JAVA_HOME/bin \
                    export JAVA_HOME \
                    export JRE_HOME \
                    export PATH'
                sh 'sshpass -p root ssh -t -t root@172.18.0.4 java -jar /var/artifacts/lojavirtual-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
