# Quarkus Kubernetes CRD demo

This is an example project demonstrating how to extend the Kubernetes API with a new resource "Broadcasts" and use the fabric8-kubernetes-client in combination with Quarkus to display the contents of this resource in a webpage using websockets.

Before you start, add the crd that is available in the manifests folder to your k8s cluster. The example.yaml contains a couple of usage examples of this crd for easy testing. After that you can run the service and visit the webpage hosted on port 8080.

```bash
kubectl apply -f manifests/crd.yaml
kubectl apply -f manifests/example.yaml
mvn quarkus:dev
```

See also:
* https://quarkus.io
* https://github.com/fabric8io/kubernetes-client
* https://kubernetes.io/docs/tasks/extend-kubernetes/custom-resources/custom-resource-definitions/
