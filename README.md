## Hello CRaC

This is a simple Micronaut Framework application with a single endpoint `/hello` which returns "Hello World".

It includes the experimental CRaC support, and the experimental crac gradle plugin.

### Setup:
1. Clone [micronaut-gradle-plugin](https://github.com/micronaut-projects/micronaut-gradle-plugin) check out the `crac-plugin` branch and run `./gradlew publishToMavenLocal`

### Building the image

To generate the image **on an x86 linux/osx machine**, run:

```
./gradlew dockerBuildCrac
```

### Running the image:

To run the image, run:

```
docker run -p 8080:8080 --privileged hello:latest
```

Then navigate to http://localhost:8080/hello/magic

## Timings

As with all timings, accuracy must be questioned, and nothing beats trying it yourself.

### Setup

1. Build the CRaC docker image as above with `./gradlew dockerBuildCrac`
1. Rename this image with `docker tag hello:latest hello-crac:latest`
1. Build the regular docker image with `./gradlew dockerBuild`
1. Run the timing script for both images via:
   1. `./timing.sh hello:latest` for the _regular_ image.
   1. `./timing.sh hello-crac:latest` for the `CRaC` version.

### Results

The results I see on a `2.6 GHz 6-Core Intel Core i7` 2019 MBP with 16GB RAM and Docker Desktop 4.11.1:

```
➜  hello-crac git:(main) ✗ ./timing.sh hello:latest
Hello tim!
real	0m3.166s
user	0m0.004s
sys	0m0.006s

➜  hello-crac git:(main) ✗ ./timing.sh hello-crac:latest
Hello tim!
real	0m1.030s
user	0m0.005s
sys	0m0.007s
```

So that's 3s wallclock time for the default image, and 1s for the CRaC image. 
## Notes

---
> ℹ️ After the below investigations, you need to use ubuntu 18.04...  It doesn't work with 22 either as a base image, or a host OS
---

### Results with `ubuntu:18.04` as the base image:

#### M1 Mac

☑️ Doesn't work (as expected), the emulation is too slow

#### x86 Mac

✅ Works, and generates a runnable image...  I need to investigate the "_Time to first request_" metric.

#### x86 Mac running Ubuntu (22) in Parallels

❌ Fails as below with `ubuntu:latest` as the base image.

#### EC2 Ubuntu (18) box

✅ Works!  Slight permissions issue as the CR files get created owned by Root.  So you either need to insert a `chmod` or run the script as root

#### EC2 Ubuntu (22) box

❌ Fails as below with `ubuntu:latest` as the base image.

---

### Results with `ubuntu:latest` as the base image:

#### M1 Mac

☑️ Doesn't work (as expected), the emulation is too slow

#### x86 Mac

❌ Gets all the way to running the image, but then fails with:

```
Error (criu/util.c:618): exited, status=1
Error (criu/util.c:618): exited, status=1
Error (criu/uffd.c:272): uffd: Lazy pages are not available: Function not implemented
Error (criu/cr-restore.c:1483): 7 killed by signal 11: Segmentation fault
Error (criu/cr-restore.c:2400): Restoring FAILED.
```

#### x86 Mac running Ubuntu (22) in Parallels

❌ Fails after it sends `JDK.checkpoint` to the process with:

```
STARTUPTIME 18066422503692 restore-native
JVM: invalid info for restore provided (may be failed checkpoint)
#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGBUS (0x7) at pc=0x00007f9968689d5c, pid=7, tid=15
#
# JRE version: OpenJDK Runtime Environment (17.0+2) (build 17-crac+2-10)
# Java VM: OpenJDK 64-Bit Server VM (17-crac+2-10, mixed mode, sharing, tiered, compressed oops, compressed class ptrs, g1 gc, linux-amd64)
# Problematic frame:
# V  [libjvm.so+0xc89d5c]  PerfTraceTime::~PerfTraceTime()+0x2c
#
# Core dump will be written. Default location: Core dumps may be processed with "/usr/share/apport/apport -p%p -s%s -c%c -d%d -P%P -u%u -g%g -- %E" (or dumping to /home/app/core.7)
#
# An error report file with more information is saved as:
# /home/app/hs_err_pid7.log
#
# If you would like to submit a bug report, please visit:
#   https://bugreport.java.com/bugreport/crash.jsp
#
/home/app/warmup.sh: line 43:     7 Aborted                 (core dumped) /azul-crac-jdk/bin/java -XX:CRaCCheckpointTo=cr -XX:+UnlockDiagnosticVMOptions -XX:+CRTraceStartupTime -Djdk.crac.trace-startup-time=true -jar application.jar
.done
EXITED WITH 134
```
Tried running the script as root as well, but that also fails the same way...

#### EC2 Ubuntu (22) box

❌ Fails the same as Ubuntu in Parallels

Tried running as root as well, and also failed...
