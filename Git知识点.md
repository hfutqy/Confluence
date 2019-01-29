1. 生成本地ssh key:
通过ssh协议clone代码到本地
本地生成sshkey: 在gitbash中输入ssh-keygen -t rsa -C 'xxx@xxx.com'，一路回车，生成本机公钥。位置：~/.ssh/id_rsa.pub
再用git clone git@XXX:XXX即可

2. revert动作（本质上是重新拉去覆盖当前文件）
git checkout -- filename  

3. git提交：
a. git add .    // 表示添加所有修改and新增的文本
b. git commit -m "修改了xxxx"
c. git push

4. git cherry -pick xxxxxxxxxxx
这里的xxxxxxxx需要根据git log选择哪一次commit提交

5. git切换分支 git checkout master // 切换到master分支上

6.git拉所有的分支下来
git pull --rebase
在本地搞个自己的分支，这样就是自己本地的分支了，名字就叫qiyu
git checkout -b qiyu

git commit -m "xxxx"
git push origin qiyu
// 每次更新
在trunk分支git pull --rebase
git rebase origin/trunk
