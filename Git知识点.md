1. 生成本地ssh key:
通过ssh协议clone代码到本地
本地生成sshkey: 在gitbash中输入ssh-keygen -t rsa -C 'xxx@xxx.com'，一路回车，生成本机公钥。位置：~/.ssh/id_rsa.pub
再用git clone git@XXX:XXX即可

2. revert动作（本质上是重新拉去覆盖当前文件）
git checkout -- filename
