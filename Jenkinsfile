pipeline {
    agent any

    options {
        timestamps()
    }

    // Parametreler (şimdilik sadece ENV)
    parameters {
        choice(
            name: 'ENV',
            choices: ['dev', 'qa', 'staging'],
            description: 'Hangi environment\'a karşı test çalışsın?'
        )
    }

    // Jenkins tarafında tanımlı JDK ve Maven isimleri
    tools {
        // Jenkins -> Manage Jenkins -> Global Tool Configuration
        // buradaki isimle eşleşmeli
        jdk 'jdk8'
        maven 'maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                // Kodun bulunduğu SCM'den (Git) check-out
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // Maven tool yolunu al
                    def mvnHome = tool 'maven3'
                    // Windows mu Linux mu diye kontrol
                    def mvnCmd = isUnix() ? "${mvnHome}/bin/mvn" : "\"${mvnHome}\\bin\\mvn.cmd\""

                    // Hedef environment (dev/qa/staging)
                    def envName = params.ENV ?: 'dev'

                    // Koşacağımız Maven goal
                    def goals = "clean test -Denv=${envName}"

                    echo "Running: ${mvnCmd} ${goals}"

                    if (isUnix()) {
                        sh "${mvnCmd} ${goals}"
                    } else {
                        bat "${mvnCmd} ${goals}"
                    }
                }
            }
        }
    }

    post {
        always {
            // Test raporlarını topla
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/surefire-reports/**', fingerprint: true
        }
    }
}
