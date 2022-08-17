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

BUILD SUCCESSFUL in 529ms
4 actionable tasks: 2 executed, 2 up-to-date
[+] Building 1.6s (14/14) FINISHED
 => [internal] load build definition from CheckpointImage.docker                                                                                                                                                                                                                                                                                                                                 0.0s
 => => transferring dockerfile: 49B                                                                                                                                                                                                                                                                                                                                                              0.0s
 => [internal] load .dockerignore                                                                                                                                                                                                                                                                                                                                                                0.0s
 => => transferring context: 2B                                                                                                                                                                                                                                                                                                                                                                  0.0s
 => [internal] load metadata for docker.io/library/ubuntu:latest                                                                                                                                                                                                                                                                                                                                 1.1s
 => [internal] load build context                                                                                                                                                                                                                                                                                                                                                                0.4s
 => => transferring context: 14.26MB                                                                                                                                                                                                                                                                                                                                                             0.4s
 => [1/9] FROM docker.io/library/ubuntu:latest@sha256:34fea4f31bf187bc915536831fd0afc9d214755bf700b5cdb1336c82516d154e                                                                                                                                                                                                                                                                           0.0s
 => CACHED [2/9] WORKDIR /home/app                                                                                                                                                                                                                                                                                                                                                               0.0s
 => CACHED [3/9] RUN apt-get update     && apt-get install -y curl jq libnl-3-200                                                                                                                                                                                                                                                                                                                0.0s
 => CACHED [4/9] RUN release="$(curl -sL https://api.github.com/repos/CRaC/openjdk-builds/releases/latest)"     && asset="$(echo $release | jq '.assets[] | select(.name | test("jdk[0-9]+-crac\\+[0-9]+\\.tar\\.gz"))')"     && id="$(echo $asset | jq .id)"     && name="$(echo $asset | jq -r .name)"     && curl -LJOH 'Accept: application/octet-stream' "https://api.github.com/repos/CRa  0.0s
 => CACHED [5/9] COPY layers/libs /home/app/libs                                                                                                                                                                                                                                                                                                                                                 0.0s
 => CACHED [6/9] COPY layers/classes /home/app/classes                                                                                                                                                                                                                                                                                                                                           0.0s
 => [7/9] COPY layers/resources /home/app/resources                                                                                                                                                                                                                                                                                                                                              0.0s
 => [8/9] COPY layers/application.jar /home/app/application.jar                                                                                                                                                                                                                                                                                                                                  0.0s
 => [9/9] COPY warmup.sh /home/app/warmup.sh                                                                                                                                                                                                                                                                                                                                                     0.0s
 => exporting to image                                                                                                                                                                                                                                                                                                                                                                           0.0s
 => => exporting layers                                                                                                                                                                                                                                                                                                                                                                          0.0s
 => => writing image sha256:686dde450e158f708693583639f22b37af35b7b46a4bda3a03d2893f30c7f275                                                                                                                                                                                                                                                                                                     0.0s
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

13:48:27.089 [main] DEBUG i.m.crac.StartupCracRegistration - Startup detected. Registering CRaC resources
13:48:27.478 [main] INFO  io.micronaut.runtime.Micronaut - Startup completed in 4046ms. Server Running: http://76d863e14f60:8080
Warming up application
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    11  100    11    0     0     11      0  0:00:01 --:--:--  0:00:01    11
Hello World
Sending checkpoint signal to process 8
8:
Command executed successfully
Wait up to 60s for snapshot to be complete
Remaining retries 12 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.4  0.1 149204 12100 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8  136  3.2 6442316 327040 ?      Sl   13:48   0:17 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root        69  0.0  0.1 151564 10816 ?        Rl+  Aug08   0:00 ps aux
13:48:33.059 [Thread-1] DEBUG i.m.c.n.NettyEmbeddedServerCracHander - Stopping netty server io.micronaut.http.server.netty.NettyHttpServer@60a566db
CR: Checkpoint ...
Remaining retries 11 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.3  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8  107  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.2  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       117  0.0  0.1 151564 11076 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 10 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.3  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 84.3  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.1  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       123  0.0  0.1 151564 10780 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 9 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.2  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 69.3  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       129  0.0  0.1 151564 11416 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 8 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.2  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 58.9  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       135  0.0  0.0 151564  9572 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 7 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.2  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 51.2  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       141  0.0  0.0 151564  9608 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 6 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 45.3  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       147  0.0  0.0 151564  9860 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 5 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 40.6  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       153  0.0  0.0 151564  9712 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 4 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 36.8  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       159  0.0  0.1 151564 11228 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 3 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 33.1  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       165  0.0  0.1 151564 11060 ?        Rl+  Aug08   0:00 ps aux
^CRemaining retries 2 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 30.5  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       171  0.0  0.0 151564  9768 ?        Rl+  Aug08   0:00 ps aux
Remaining retries 1 --- processes are:
USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.1  0.1 149236 12132 ?        Ssl  13:48   0:00 /usr/bin/qemu-x86_64 /bin/bash /bin/bash /home/app/warmup.sh
root         8 28.3  3.3 7078824 346156 ?      Sl   13:48   0:19 /usr/bin/qemu-x86_64 /azul-crac-jdk/bin/java /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
root       103  0.0  0.0      0     0 ?        Z    13:48   0:00 [criuengine] <defunct>
root       177  0.0  0.1 151564 10268 ?        Rl+  Aug08   0:00 ps aux
Snapshotting failed
Killing 8
```

### OSX X86:

```

```