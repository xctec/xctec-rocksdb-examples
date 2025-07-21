# 存储2024国考职位数据示例

### 1. 初始化和清空数据
``` bash
# 初始化
curl -s http://127.0.0.1:8080/test/imp
# 清理
curl -s http://127.0.0.1:8080/test/clean
```

### 2.get
``` bash
curl -s http://127.0.0.1:8080/test/get?id=1000002
```

### 3.del
``` bash
curl -s http://127.0.0.1:8080/test/del?id=1000002
```

### 4. iterator
``` bash 
curl -s 'http://127.0.0.1:8080/test/iterator?seekKey=1000005&order=next'
curl -s 'http://127.0.0.1:8080/test/iterator?seekKey=1000005&order=prev'
```