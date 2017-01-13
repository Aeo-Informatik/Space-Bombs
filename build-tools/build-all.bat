echo "----------------Building jar----------------"

start jar-build.bat

echo "----------------Linux32----------------"

start linux32-build.bat
echo "----------------Linux64----------------"

start linux64-build.bat
echo "----------------Windows32----------------"

start windows32-build.bat

echo "----------------Windows64----------------"

start windows64-build.bat
echo "----------------Mac OSX----------------"

start osx-build.bat
echo "----------------Done!----------------"
