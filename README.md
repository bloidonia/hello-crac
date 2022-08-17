## Hello CRaC

This is a simple Micronaut Framework application with a single endpoint `/` which returns "Hello World".

It includes the experimental CRaC support, and I am trying to generate a docker image with a checkpoint.

To generate the image, run the following command:

```
./build.sh
```

### Results:

#### M1 Mac

Doesn't work (as expected), the emulation is too slow

#### x86 Mac

Gets all the way to running the image, but then fails with:

```
Error (criu/util.c:618): exited, status=1
Error (criu/util.c:618): exited, status=1
Error (criu/uffd.c:272): uffd: Lazy pages are not available: Function not implemented
Error (criu/cr-restore.c:1483): 7 killed by signal 11: Segmentation fault
Error (criu/cr-restore.c:2400): Restoring FAILED.
```

#### x86 Mac running Ubuntu in Parallels

Fails after it sends `JDK.checkpoint` to the process with:

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