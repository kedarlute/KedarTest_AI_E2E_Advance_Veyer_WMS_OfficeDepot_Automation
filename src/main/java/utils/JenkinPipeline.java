pipeline {
    agent any

    stages {
        stage('Build and Run Initial Tests') {
            steps {
                // Assuming a Maven project, clean and install, allowing test failures to generate testng-failed.xml
                // -Dmaven.test.failure.ignore=true allows the build to continue even if tests fail,
                // which is necessary for testng-failed.xml to be generated.
                sh 'mvn clean install -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Run Failed Tests') {
            steps {
                script {
                    // Check if testng-failed.xml exists before attempting to run it
                    def failedXmlPath = "target/surefire-reports/testng-failed.xml" // Common path for Maven
                    if (fileExists(failedXmlPath)) {
                        echo "testng-failed.xml found. Rerunning failed tests..."
                        // Execute the failed tests using Maven Surefire plugin, specifying the failed XML
                        sh "mvn test -Dsurefire.suiteXmlFiles=${failedXmlPath}"
                    } else {
                        echo "testng-failed.xml not found. No failed tests to rerun or initial run passed."
                    }
                }
            }
        }

      
    }
}
