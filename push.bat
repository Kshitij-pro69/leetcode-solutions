@echo off
cd /d "%~dp0"
git add .
set /p msg="Enter problem name: "
git commit -m "solved: %msg%"
git push
echo Done! Check your GitHub!
pause