
set pan=D:\
set folder1=workspace\
set folder2=commonlib\
set libDir=https://gitee.com/rookievips/commonlib.git

if not exist %pan%%folder1% (
   md %pan%%folder1%
)

cd\
D:
cd %folder1%

if not exist %folder2% (
   git clone %libDir%
)else (
   cd %folder2%
   git pull
   git add . 
   git commit -m "push code"
   git status
   git push origin master
)

pause