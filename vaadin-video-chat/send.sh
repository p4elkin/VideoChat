mv target/vaadin-video-chat-1.0-SNAPSHOT.war target/vaadin-video-chat.war
scp -P 5144 target/vaadin-video-chat.war dev@virtuallypreinstalled.com://home/dev/jetty8/webapps
