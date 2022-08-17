#!/bin/sh

set -e

# Set a trap to close the app once the script finishes
trap 'echo "Killing $PROCESS" && kill $PROCESS' EXIT

echo "Starting application"
# Run the app in the background
/azul-crac-jdk/bin/java \
    -XX:CRaCCheckpointTo=cr \
    -XX:+UnlockDiagnosticVMOptions \
    -XX:+CRTraceStartupTime \
    -Djdk.crac.trace-startup-time=true \
    -jar application.jar &
PROCESS=$!
echo "Started application as process $PROCESS"

# Wait for the app to be started
# TODO: Make this cleverer
echo "Waiting for application to start"
sleep 10

# Warm up the app.  For now, just call curl, we may want a way for this to be configurable
echo "Warming up application"
curl http://localhost:8080
echo ""

# Take a snapshot
echo "Sending checkpoint signal to process $PROCESS"
/azul-crac-jdk/bin/jcmd $PROCESS JDK.checkpoint
ls -lh /home/app/cr

echo "Waiting for snapshot to be complete"
retries=60
while [ $retries -gt 0 ]; do
    if [ kill -0 $PROCESS 2>/dev/null ]; then
        echo ".done"
        break
    fi
    echo -n "."
    sleep 5
    retries=$((retries - 1))
done

if [ kill -0 $PROCESS 2>/dev/null ]; then
    echo "Snapshotting complete"
else
    echo "Snapshotting failed"
    exit 1
fi
