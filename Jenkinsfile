pipeline {
    agent any

    options {
        timestamps()
    }

    // Parametre (dev / qa / staging)
    parameters {
        choice(
                name: 'ENV',
                choices: ['dev', 'qa', 'staging'],
                description: 'Hangi environmenta karşı test koşulsun?'
        )
    }

    // Jenkins tarafında tanımlı tool isimleri
    tools {
        jdk   'jdk8'
        maven 'maven3'
    }

    stages {

        stage('Checkout') {
            steps {
                // Kodu GitHub'dan çek
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Jenkins tool path'leri
                    def jdkHome  = tool 'jdk8'
                    def mavenHome = tool 'maven3'

                    withEnv([
                            "JAVA_HOME=${jdkHome}",
                            "PATH+MAVEN=${mavenHome}/bin"
                    ]) {
                        // ENV parametresiyle testleri çalıştır
                        bat "mvn clean test -Denv=${params.ENV}"
                    }
                }
            }
        }

        stage('Archive Reports') {
            steps {
                // JUnit raporlarını Jenkins'e tanıt
                junit 'target/surefire-reports/junitreports/TEST-*.xml'

                // HTML + XML raporları arşivle
                archiveArtifacts artifacts: 'target/surefire-reports/**/*.html, target/surefire-reports/**/*.xml', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Pipeline tamamlandı."
        }
    }
}
