#!/bin/bash

jq --arg LOG "$3" '.commits[.commits | length] |= . + {
	"sha": "'$1'",
	"status": "'$2'",
	"log": $LOG
}' data.json > data.temp.json

git fetch
git switch deployment
git pull
rm data.json
mv data.temp.json data.json
git add data.json
git commit -m "fix: Automated deployment of $1"
git push
git switch main
