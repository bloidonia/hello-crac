## Hello CRaC

This is a simple Micronaut Framework application with a single endpoint `/` which returns "Hello World".

It includes the experimental CRaC support, and I am trying to generate a docker image with a checkpoint.

To generate the image, run the following command:

```
./createDockerCheckpoint.sh
```

## Results:

### OSX M1:

As expected, doesn't work

```
❯ ./createDockerCheckpoint.sh

> Configure project :
[native-image-plugin] Instrumenting task with the native-image-agent: test
[native-image-plugin] Instrumenting task with the native-image-agent: testNativeImage

BUILD SUCCESSFUL in 450ms
4 actionable tasks: 4 up-to-date
[+] Building 1.1s (14/14) FINISHED
 => [internal] load build definition from CheckpointImage.docker                                                                                                                                                                                                                                                                                                                                 0.0s
 => => transferring dockerfile: 998B                                                                                                                                                                                                                                                                                                                                                             0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                                                                                                                                0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                                                                                                                  0.0s
 => [internal] load metadata for docker.io/library/alpine:3.16.2                                                                                                                                                                                                                                                                                                                                 1.0s
 => [internal] load build context                                                                                                                                                                                                                                                                                                                                                                0.0s
 => => transferring context: 4.45kB                                                                                                                                                                                                                                                                                                                                                              0.0s
 => [1/9] FROM docker.io/library/alpine:3.16.2@sha256:bc41182d7ef5ffc53a40b044e725193bc10142a1243f395ee852a8d9730fc2ad                                                                                                                                                                                                                                                                           0.0s
 => CACHED [2/9] WORKDIR /home/app                                                                                                                                                                                                                                                                                                                                                               0.0s
 => CACHED [3/9] RUN apk update     && apk upgrade     && apk add curl jq     && rm -rf /var/cache/apk/*                                                                                                                                                                                                                                                                                         0.0s
 => CACHED [4/9] RUN release="$(curl -sL https://api.github.com/repos/CRaC/openjdk-builds/releases/latest)"     && asset="$(echo $release | jq '.assets[] | select(.name | test("jdk[0-9]+-crac\\+[0-9]+\\.tar\\.gz"))')"     && id="$(echo $asset | jq .id)"     && name="$(echo $asset | jq -r .name)"     && curl -LJOH 'Accept: application/octet-stream' "https://api.github.com/repos/CRa  0.0s
 => CACHED [5/9] COPY layers/libs /home/app/libs                                                                                                                                                                                                                                                                                                                                                 0.0s
 => CACHED [6/9] COPY layers/classes /home/app/classes                                                                                                                                                                                                                                                                                                                                           0.0s
 => CACHED [7/9] COPY layers/resources /home/app/resources                                                                                                                                                                                                                                                                                                                                       0.0s
 => CACHED [8/9] COPY layers/application.jar /home/app/application.jar                                                                                                                                                                                                                                                                                                                           0.0s
 => [9/9] COPY warmup.sh /home/app/warmup.sh                                                                                                                                                                                                                                                                                                                                                     0.0s
 => exporting to image                                                                                                                                                                                                                                                                                                                                                                           0.0s
 => => exporting layers                                                                                                                                                                                                                                                                                                                                                                          0.0s
 => => writing image sha256:24eb3dba2284db820e605de7378d8d9dc8b47a8de3af1e4a19031edf3b17ae6e                                                                                                                                                                                                                                                                                                     0.0s
 => => naming to docker.io/library/checkpoint                                                                                                                                                                                                                                                                                                                                                    0.0s

Use 'docker scan' to run Snyk tests against images to find vulnerabilities and learn how to fix them
WARNING: The requested image's platform (linux/amd64) does not match the detected host platform (linux/arm64/v8) and no specific platform was requested
Starting application
Started application as process 8
Waiting for application to start
qemu-x86_64: Could not open '/lib64/ld-linux-x86-64.so.2': No such file or directory
Warming up application
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
curl: (7) Failed to connect to localhost port 8080 after 3 ms: Connection refused
Killing 8
sh: can't kill pid 8: No such process
```