#!/bin/sh
#chkconfig: 2345 90 90
#description: Script to start, stop and restart the XDR ticket consumer

# check arguments
if [ $# -lt 2 ]
then
    echo "Wrong arguments"
    echo "Usage: 'run.sh <START | STOP | RESTART> <PROFILE>'"
    echo "Exiting..."
    exit
fi

PROFILE=$2
APP_NAME="${project.artifactId}"
APP_VERSION="${project.version}"
APP_MAIN_CLASS="org.javeriana.cm.donlimpio.rest.api.main.App"
APP_BASE_DIR="/opt/${project.artifactId}/${project.version}"
APP_CONF_DIR="$APP_BASE_DIR/conf/"
APP_LIB_DIR="$APP_BASE_DIR/lib/*"
APP_JAR_DIR="$APP_BASE_DIR/bin"
APP_JAR_NAME="${project.artifactId}.jar"
APP_JAR_PATH="$APP_JAR_DIR/$APP_JAR_NAME"
APP_SPRING_XML="classpath:$PROFILE/spring-web.xml"
APP_LOGGER_CONFIG="$APP_BASE_DIR/conf/log4j2.xml"

PATH_TO_JAR="$APP_JAR_DIR/$APP_JAR_NAME"
PATH_TO_PID="$APP_BASE_DIR/$APP_NAME-pid"


##########################################
# Function: set_java_home
# Description: checks if JAVA_HOME env variable is set, 
#              if not, sets a default value for it.
#
##########################################
set_java_home() {
    # check first if JAVA_HOME exists
    if [ -d $JAVA_HOME ]
    then
        echo "JAVA_HOME found: $JAVA_HOME"
    else
        defaultJavaHome=/usr/java/jdk1.7.0_72
        echo "JAVA_HOME not found, setting to default: $defaultJavaHome"
        export JAVA_HOME=defaultJavaHome
    fi 
}

# check if user wants to run start, stop, restart or status.
case $1 in
        "start")
            echo "Starting $APP_NAME..."
            set_java_home
            if [ ! -f $PATH_TO_PID ]; then
                java -Xms1024m -Xmx2048m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled \
                        -cp "$APP_JAR_PATH:$APP_CONF_DIR:$APP_LIB_DIR" $APP_MAIN_CLASS \
                        -profile "$PROFILE" \
                        -springConfig "$APP_SPRING_XML" \
                        -logConfig "$APP_LOGGER_CONFIG" &
                echo $! > $PATH_TO_PID 
                echo "Started $APP_NAME..."
            else
                echo "$APP_NAME already started..."
            fi
        ;;
        "stop")
           	if [ -f $PATH_TO_PID ]; then
				PID=$(cat $PATH_TO_PID);
				echo "Stopping $APP_NAME..."
				kill $PID;
				echo "$APP_NAME stopped ..."
				rm -rf $PATH_TO_PID
            else
                echo "$APP_NAME is not running ..."
            fi
        ;;
        "restart")
            echo "Restarting $APP_NAME..."
            if [ -f $PATH_TO_PID ]; then
                PID=$(cat $PID);
                echo "$APP_NAME stopping ...";
                kill $PID;
                echo "$APP_NAME stopped ...";
                rm $PATH_TO_PID
                echo "$APP_NAME starting ..."
                    java -Xms1024m -Xmx2048m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled \
                        -cp "$APP_JAR_PATH:$APP_CONF_DIR:$APP_LIB_DIR" $APP_MAIN_CLASS \
                        -profile "$PROFILE" \
                        -springConfig "$APP_SPRING_XML" \
                        -logConfig "$APP_LOGGER_CONFIG" &
                    echo $! > $PATH_TO_PID
                echo "$APP_NAME started ..."
            else
                echo "$APP_NAME is not running ..."
            fi
        ;;
        "status")
            if [ -f $PATH_TO_PID ]
            then
                    echo "$APP_NAME is running!"
            else
                    echo "$APP_NAME is stopped!"
            fi
        ;;
esac
