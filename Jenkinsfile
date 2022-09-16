pipeline {
    agent {
        node {
            label 'metersphere'
        }
    }
    options { quietPeriod(600) }
    environment {
        IMAGE_NAME = 'rackshift'
        IMAGE_PREFIX = 'registry.fit2cloud.com/north'
    }
    stages {
        stage('Build') {
            steps {
                sh '''
                    rm -rf ${WORKSPACE}/rackshift-server/src/main/resources/static
                    mkdir -p ${WORKSPACE}/rackshift-server/src/main/resources/static
                    cd ${WORKSPACE}/rackshift-web
                    cnpm install
                    cnpm run build
                    cp -r ${WORKSPACE}/rackshift-web/dist/* ${WORKSPACE}/rackshift-server/src/main/resources/static
                    cd ${WORKSPACE}/rackshift-dhcp-proxy
                    mvn clean install
                   '''
            }
        }
        stage('Docker build & push') {
            steps {
                sh '''
                    docker login --username=north-developer --password=Calong@2015 registry.fit2cloud.com
                    cd ${WORKSPACE}/rackshift-server
                    mvn clean install -DskipTests
                    docker build -t ${IMAGE_PREFIX}/${IMAGE_NAME}:${BRANCH_NAME} .
                    docker push ${IMAGE_PREFIX}/${IMAGE_NAME}:${BRANCH_NAME}

                    cd ${WORKSPACE}/rackshift-dhcp-proxy
                    docker build -t ${IMAGE_PREFIX}/rackshift-dhcp-proxy:${BRANCH_NAME} .
                    docker push ${IMAGE_PREFIX}/rackshift-dhcp-proxy:${BRANCH_NAME}
                   '''
            }
        }
    }
}