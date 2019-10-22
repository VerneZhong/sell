# 解决rancher agent 连接 rancher server 超时的问题
vi /usr/lib/sysctl.d/00-system.conf
# 添加如下代码：
net.ipv4.ip_forward=1
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-arptables = 1
# 重启network服务
systemctl restart network
# 查看是否修改成功
sysctl net.ipv4.ip_forward
# 如果返回为
net.ipv4.ip_forward = 1
# 则表示成功了，然后重启docker 
systemctl restart docker
# 重新运行rancher agent
docker run -e CATTLE_AGENT_IP="192.168.1.130"  --rm --privileged -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/rancher:/var/lib/rancher rancher/agent:v1.2.11 http://192.168.1.128:8080/v1/scripts/1EE72781214B3D5BC1F0:1546214400000:abbap1SFhPbiUvcXi62ph1iOaM