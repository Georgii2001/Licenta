export NAMESPACE=travelwithme-dev
kubectl config set-cluster minikube --server=https://192.168.49.2:8443
helm upgrade travelwithme-backend travelwithme/ --install --set image.repository=$ECR/travelwithme-backend \
                                                                     --set image.tag="${VERSION}" \
                                                                     --set namespace="${NAMESPACE}" \
                                                                     --namespace="${NAMESPACE}" \
                                                                     --create-namespace                                
