真·动态数据源
---

## 一、简介

### 1. 背景
常规的动态数据源存在以下问题
- 有事务的时候无法切换数据源
- 不支持多数据库操作，只能写到一个数据库
- 没有监听回调，无法扩展

### 2. 目标
此项目实现了DataSource底层接口，通过静态代理实现了真动态数据源，可以在任何场景有效的切换数据源。同时支持数据源的多写，支持读写监听回调，可以采用JAVA的SPI机制进行扩展。

### 3. 特性
- 支持多数据源切换，支持读写分离
- 支持在任何场景切换数据源
- 使用简单，依赖少（只依赖JDK）

## 二、快速入门

### 1. 引入maven坐标

```xml
    <dependency>
        <groupId>cloud.yxkj</groupId>
        <artifactId>yxkj-dds</artifactId>
        <version>${project.version}</version>
    </dependency>
```

### 2. 构建数据源

```java
import cloud.yxkj.dds.YxkjDds;
import javax.sql.DataSource;

// 构建动态数据源
public YxkjDds buildYxkjDds(Map<?, DataSource> multipleDataSource) {
    return new YxkjDds(dataSourceMap);
}

```

### 3. 切换数据源

```java
 import javax.sql.DataSource;
 import cloud.yxkj.dds.YxkjDds;

Map<String, DataSource> multipleDataSource = new HashMap<>();
multipleDataSource.put("A", dataSource);
multipleDataSource.put("B", dataSource);
multipleDataSource.put("C", dataSource);

YxkjDds dds = buildYxkjDds(multipleDataSource);
// 指定当前只操作一个数据源
dds.setActive("A");
// 指定当前同时操作多个数据源
dds.setActive("A", "B");

```

### 4. 监听（尚未完成）
