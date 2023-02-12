#!/bin/bash
git stash
git fetch
git switch deployment
git pull

jq --arg LOG "$3" '.commits += [{
	"sha": "'$1'",
	"status": "'$2'",
	"log": $LOG
}]' data.json > data.temp.json
mv data.temp.json data.json

git add data.json
git commit -m "fix: Automated deployment of $1"
git push
git switch main
git stash pop
