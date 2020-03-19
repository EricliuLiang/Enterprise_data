#!/bin/sh
rm -rf .\src\main\java\org
rm -rf .\src\main\resources\org
java -jar mybatis-generator-core-1.3.2.jar -configfile generatorConfig.xml -overwrite
