echo "----------------Building jar----------------"
./jar-build.sh
echo "----------------Linux32----------------"
./linux32-build.sh
echo "----------------Linux64----------------"
./linux64-build.sh
echo "----------------Windows32----------------"
./windows32-build.sh
echo "----------------Windows64----------------"
./windows64-build.sh
echo "----------------Mac OSX----------------"
./osx-build.sh
echo "----------------Done!----------------"
