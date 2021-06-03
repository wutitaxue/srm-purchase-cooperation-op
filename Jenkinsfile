pipeline {
    agent any
    environment {
      DOCKER_REGISTRY='open-registry.going-link.com'
      PROJECT_NAME='op-deliver-rcwl-group'
      APPLICATION_NAME='srm-purchase-cooperation-op'
    }
    stages {
        stage('Maven Build') {
            steps {
                sh '''
                mvn clean package -U -DskipTests=true -Dmaven.javadoc.skip=true
                cp target/app.jar src/main/docker/app.jar
                '''
            }
        }
        stage('Docker Build') {
            steps {
                sh '''
                CUR_BUILD_TIME=$(date "+%Y%m%d%H%M%S")
                GIT_BRANCH=$(echo $BRANCH_NAME | cut -d / -f 2)
                IMAGE_TAG=${GIT_BRANCH}-${CUR_BUILD_TIME}
                echo "本次构建的镜像为: ${IMAGE_TAG}"
                echo ${IMAGE_TAG} > target/version
                docker build --pull -t ${DOCKER_REGISTRY}/${PROJECT_NAME}/${APPLICATION_NAME}:${IMAGE_TAG} src/main/docker
                docker push ${DOCKER_REGISTRY}/${PROJECT_NAME}/${APPLICATION_NAME}:${IMAGE_TAG}
                '''
            }
        }
        stage('Deploy To K8s') {
            steps {
                sh '''
                DEFAULT_NAMESPACE="isrm-op-1-13-0-dev"
                if [[ "$BRANCH_NAME" =~ deployment/dev* ]];then
                  echo "当前执行的分支名为：$BRANCH_NAME"
                  echo "部署到测试环境:$DEFAULT_NAMESPACE"
                  kubectl --kubeconfig /root/.kube/config-all  config use-context rcwl-dev

                elif [[ "$BRANCH_NAME" =~ deployment/uat* ]];then
                  echo "当前执行的分支名为：$BRANCH_NAME"
                  DEFAULT_NAMESPACE=isrm-op-1-13-0-uat
                  echo "部署到uat环境:${DEFAULT_NAMESPACE}"
                  kubectl --kubeconfig /root/.kube/config-all  config use-context rcwl-uat

                elif [[ "$BRANCH_NAME" =~ deployment/prd* ]];then
                  echo "当前执行的分支名为：$BRANCH_NAME"
                  DEFAULT_NAMESPACE=isrm-op-1-13-0-prd
                  echo "部署到prod环境:${DEFAULT_NAMESPACE}"
                  kubectl --kubeconfig /root/.kube/config-all  config use-context rcwl-prod

                else
                  echo "无匹配分支"
                  exit 0
                fi
                export IMAGE_TAG=$(cat target/version)
                TEMPLATE_DEPLOY_FILE="/acdata/k8s-deploy-files/${APPLICATION_NAME}.yaml"
                envsubst < $TEMPLATE_DEPLOY_FILE | kubectl --kubeconfig /root/.kube/config-all -n ${DEFAULT_NAMESPACE} apply -f -
                rm -rf target/
                '''
            }
        }
    }
}
