1. 初始化，使用contains方法取包含关系
```
                // 判断是否是现阶段支持的推送类型
                List<Integer> typeArray = Arrays.asList(2,4,5,7,14,18,21,26);
                if(!typeArray.contains(params.getInt(CommonConstant.RES_TYPE))){
                    msg = "";
                }
```
