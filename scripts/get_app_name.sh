#!/bin/bash
input=$1

appName=${input/\//_}
#echo "app_name=appName" >> $GITHUB_OUTPUT
echo "app_name=$appName"