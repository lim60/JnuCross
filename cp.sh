#!/bin/bash

WECROSS_HOME=${HOME}/backup/wecross-demo/
ROUTER_PATMENT=$WECROSS_HOME/routers-payment/

cp dist/apps/wecross-1.3.0.jar $WECROSS_HOME/WeCross/apps/

cp dist/apps/wecross-1.3.0.jar $ROUTER_PATMENT/127.0.0.1-8250-25500/apps/

cp dist/apps/wecross-1.3.0.jar $ROUTER_PATMENT/127.0.0.1-8251-25501/apps/

cp dist/conf/database.properties $ROUTER_PATMENT/127.0.0.1-8250-25500/conf/

cp dist/conf/database.properties $ROUTER_PATMENT/127.0.0.1-8251-25501/conf/

cp dist/lib/mysql-connector-j-8.1.0.jar $ROUTER_PATMENT/127.0.0.1-8251-25501/lib/

cp dist/lib/mysql-connector-j-8.1.0.jar $ROUTER_PATMENT/127.0.0.1-8250-25500/lib/
