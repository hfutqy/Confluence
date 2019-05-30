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
然后切到qiyu分支再git rebase origin/trunk


7. git 分支重命名
git branch -m oldName newName
git push origin newName
换绑到远程newName上
git branch --set-upstream-to origin/newName
删除远程oldName分支
git push --delete origin oldName


8. 删除某次提交记录
先把本地的分支放到指定xxxx版本xxx是那次提交version
git reset --hard xxxxxxx
再强行push到远程分支
git push -f

9. git merge x分支（把x分支merge到当前分支）
然后解决冲突
然后push

10. git 合并多次commit为一次  
git rebase -i [commitId], 这个commitId是你多次改动之前的那个commit，即你是基于这个commit开始改动的  
然后开始编辑，把从这开始的多次pick，改成s,或者说squash ，当然要保留一个pick，作为merge的commit。  
然后保存，会跳出commit log，只保留其中一个commit log。  
然后看看git log,会发现只有一次了  
git push -f 强推
中间有问题请用git rebase abort  

11. 还原到前几个版本  
git reset [commitID]
