


#!/bin/bash


echo "----------------------------------------------------------------------------"
echo "删除 Eclipse 的 bin 目录"
echo "----------------------------------------------------------------------------"
# 强制递归删除目录 [9](@ref)
rm -rf user-server/bin
rm -rf admin-server/bin
rm -rf service/bin
rm -rf dao/bin

# 执行 Maven 命令序列
mvn package -Dmaven.test.skip=true -U
mvn clean
mvn eclipse:clean
mvn archetype:create-from-project

echo "----------------------------------------------------------------------------"
echo "覆盖文件并删除指定文件"
echo "----------------------------------------------------------------------------"

# 删除目标文件 [8](@ref)
rm -f target/generated-sources/archetype/src/main/resources/archetype-resources/todo.txt
rm -f target/generated-sources/archetype/src/main/resources/archetype-resources/release_checklist.txt
rm -f target/generated-sources/archetype/src/main/resources/archetype-resources/pom-archetype.xml
rm -f target/generated-sources/archetype/src/main/resources/archetype-resources/mvn-archetype-create-from-project.bat
rm -f target/generated-sources/archetype/src/main/resources/archetype-resources/archetype-catalog.xml

# 递归删除 node_modules 目录 [9](@ref)
rm -rf target/generated-sources/archetype/src/main/resources/archetype-resources/admin-front/node_modules
rm -rf target/generated-sources/archetype/src/main/resources/archetype-resources/user-front/node_modules

# 备份并替换 pom.xml [8](@ref)
cp -f target/generated-sources/archetype/pom.xml target/generated-sources/archetype/pom.bak.xml
cp -f pom-archetype.xml target/generated-sources/archetype/pom.xml

echo "----------------------------------------------"
echo "安装并部署 Archetype"
echo "----------------------------------------------"

# 进入目录执行操作
cd target/generated-sources/archetype
mvn clean
mvn install
cd ../../../

echo "----------------------------------------------"
echo "执行: mvn eclipse:eclipse"
echo "----------------------------------------------"

# 生成 Eclipse 配置并打包 [8](@ref)
mvn eclipse:eclipse
mvn package -Dmaven.test.skip=true

# 注释掉的本地 Archetype 生成命令（保留参考）
# mvn archetype:generate -DarchetypeCatalog=local




