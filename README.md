# 低仿大众点评后台管理系统

<<<<<<< HEAD
### ϵͳ���幦������
![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/structure.png)
### ���ݿ����

#### �Զ���Ϊ���ĵ�ʵ���ϵ

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/E-order.png)

#### ���û���Ϊ���ĵ�ʵ���ϵ
=======
### 系统整体功能需求
![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/structure.png)
### 数据库设计

#### 以订单为中心的实体关系

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/E-order.png)

#### 以用户组为中心的实体关系
>>>>>>> 97256294cf11c62c74e37d10c9c5a5013bf0cc1e
![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/E-user.png)


#### 后台

分为三种用户角色：系统管理员、普通管理员、业务员

* 超级管理员界面

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/back_root.png)

说明：超级管理员拥有最高的权限，所以显示所有的菜单。

1. 点击用户显示分配的用户组，点击用户组，显示相应已经分配的菜单和动作
2. 可以对用户进行分配用户组，以及可以对用户组进行分配菜单和动作
3. 可以分别对用户、用户组、菜单和动作右击进行管理

* 普通管理员界面

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/back_admin.png)

说明：普通管理员拥有对广告、商户的管理，以及对订单、评论、报表查看的功能

* 业务员界面

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/back_service.png)

说明：业务员拥有对广告、商户进行浏览、查询的功能，但没有管理的功能，以及对订单、评论、报表查看的功能

* 报表统计功能

![image](https://github.com/SavorTheFlavor/publicComment/raw/master/images/back_report.png)

说明：按类别和时间间隔（1小时）分类统计出当前系统前一天的订单数量分布
