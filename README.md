## Hello CRaC

This is a simple Micronaut Framework application with a single endpoint `/` which returns "Hello World".

It includes the experimental CRaC support, and I am trying to generate a docker image with a checkpoint.

To generate the image, run the following command:

```
./createDockerCheckpoint.sh
```

## Results:

### OSX M1:

As expected, doesn't work as it takes much longer than 20s to create the checkpoint under emulation

```
❯ ./createDockerCheckpoint.sh

> Configure project :
[native-image-plugin] Instrumenting task with the native-image-agent: test
[native-image-plugin] Instrumenting task with the native-image-agent: testNativeImage

BUILD SUCCESSFUL in 429ms
4 actionable tasks: 4 up-to-date
[+] Building 0.6s (14/14) FINISHED
 => [internal] load build definition from CheckpointImage.docker                                                                                                                                                                                                                                                                                                                                 0.0s
 => => transferring dockerfile: 49B                                                                                                                                                                                                                                                                                                                                                              0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                                                                                                                                0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                                                                                                                  0.0s
 => [internal] load metadata for docker.io/library/ubuntu:latest                                                                                                                                                                                                                                                                                                                                 0.5s
 => [1/9] FROM docker.io/library/ubuntu:latest@sha256:34fea4f31bf187bc915536831fd0afc9d214755bf700b5cdb1336c82516d154e                                                                                                                                                                                                                                                                           0.0s
 => [internal] load build context                                                                                                                                                                                                                                                                                                                                                                0.0s
 => => transferring context: 4.45kB                                                                                                                                                                                                                                                                                                                                                              0.0s
 => CACHED [2/9] WORKDIR /home/app                                                                                                                                                                                                                                                                                                                                                               0.0s
 => CACHED [3/9] RUN apt-get update     && apt-get install -y curl jq libnl-3-200                                                                                                                                                                                                                                                                                                                0.0s
 => CACHED [4/9] RUN release="$(curl -sL https://api.github.com/repos/CRaC/openjdk-builds/releases/latest)"     && asset="$(echo $release | jq '.assets[] | select(.name | test("jdk[0-9]+-crac\\+[0-9]+\\.tar\\.gz"))')"     && id="$(echo $asset | jq .id)"     && name="$(echo $asset | jq -r .name)"     && curl -LJOH 'Accept: application/octet-stream' "https://api.github.com/repos/CRa  0.0s
 => CACHED [5/9] COPY layers/libs /home/app/libs                                                                                                                                                                                                                                                                                                                                                 0.0s
 => CACHED [6/9] COPY layers/classes /home/app/classes                                                                                                                                                                                                                                                                                                                                           0.0s
 => CACHED [7/9] COPY layers/resources /home/app/resources                                                                                                                                                                                                                                                                                                                                       0.0s
 => CACHED [8/9] COPY layers/application.jar /home/app/application.jar                                                                                                                                                                                                                                                                                                                           0.0s
 => CACHED [9/9] COPY warmup.sh /home/app/warmup.sh                                                                                                                                                                                                                                                                                                                                              0.0s
 => exporting to image                                                                                                                                                                                                                                                                                                                                                                           0.0s
 => => exporting layers                                                                                                                                                                                                                                                                                                                                                                          0.0s
 => => writing image sha256:5204724fe0e0bfc9e1eb46627b771f0680a20a299529ec93688e64fd6ebcccab                                                                                                                                                                                                                                                                                                     0.0s
 => => naming to docker.io/library/checkpoint                                                                                                                                                                                                                                                                                                                                                    0.0s

Use 'docker scan' to run Snyk tests against images to find vulnerabilities and learn how to fix them
Starting application
Started application as process 8
Waiting for application to start
 __  __ _                                  _
|  \/  (_) ___ _ __ ___  _ __   __ _ _   _| |_
| |\/| | |/ __| '__/ _ \| '_ \ / _` | | | | __|
| |  | | | (__| | | (_) | | | | (_| | |_| | |_
|_|  |_|_|\___|_|  \___/|_| |_|\__,_|\__,_|\__|
  Micronaut (v3.6.0)

13:23:18.817 [main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 4023ms. Server Running: http://55b542b322ce:8080
Warming up application
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    11  100    11    0     0      9      0  0:00:01  0:00:01 --:--:--    10
Hello World
Sending checkpoint signal to process 8
8:
Command executed successfully
total 40K
-rw-r--r-- 1 root root  12 Aug 17 13:22 cppath
-rw------- 1 root root 577 Aug 17 13:22 dump4.log
-rw------- 1 root root 32K Aug 17 13:22 perfdata
Wait up to 20s for snapshot to be complete
.CR: Checkpoint ...
.........Snapshotting failed
Killing 8
```

### OSX X86:

```

```