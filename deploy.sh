pid=$(netstat -nlp | grep :8088 | awk '{print $7}' | awk -F"/" '{ print $1 }');
if [ -n "${pid}" ]; then
kill -9 ${pid};
fi
nohup java -jar /opt/parking-tag-backend/parking-tag-1.0-SNAPSHOT.jar &
