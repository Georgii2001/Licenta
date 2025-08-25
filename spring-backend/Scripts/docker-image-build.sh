aws ecr get-login-password --region eu-central-1 --profile jenkins | docker login --username AWS --password-stdin $ECR
docker build -t $ECR/travelwithme-backend:"${VERSION}" .
docker push $ECR/travelwithme-backend:"${VERSION}"
docker rmi -f $ECR/travelwithme-backend:"${VERSION}"
rm target/*.jar
