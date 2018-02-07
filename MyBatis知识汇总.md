## 一、xml的自动转义与控制它不转义
在使用mybatis 时我们sql是写在xml 映射文件中，如果写的sql中有一些特殊的字符的话，在解析xml文件的时候会被转义，但我们不希望他被转义，所以我们要使用`<![CDATA[ ]]>`来解决。<br>
`<![CDATA[   ]]>`是什么，这是XML语法。在CDATA内部的所有内容都会被解析器忽略。<br>
如果文本包含了很多的"<"字符 <=和"&"字符——就象程序代码一样，那么最好把他们都放到CDATA部件中。<br>
但是有个问题那就是` <if test="">   </if>   <where>   </where>  <choose>  </choose>  <trim>  </trim> `等这些标签都不会被解析，所以我们只把有特殊字符的语句放在` <![CDATA[   ]]>`  尽量缩小 `<![CDATA[  ]]>` 的范围。<br>
实例如下：
```
<select id="allUserInfo" parameterType="java.util.HashMap" resultMap="userInfo1">  
  <![CDATA[  
  SELECT newsEdit,newsId, newstitle FROM shoppingGuide  WHERE 1=1  AND  newsday > #{startTime} AND newsday <= #{endTime}  
  ]]>  
  <if test="etidName!=''">  
   AND newsEdit=#{etidName}  
  </if>  
 </select>  
```
因为这里有` ">"  "<=" `特殊字符所以要使用`<![CDATA[   ]]> `来注释，但是有`<if> `标签，所以把`<if>`等 放外面

## 二、LAST_INSERT_ID()的使用
1.LAST_INSERT_ID()是与当前数据库访问链接关联的。我们习惯于用它返回insert语句插入数据后获取**自增主键id**。此外，也可以通过这个方式LASt_INSERT_ID(x)指定LASt_INSERT_ID()的值为x，相当于存在该链接的缓存中，这时候取LASt_INSERT_ID()的值就是x。<br>
2.`<selectKey>`的使用,如下所示,自测只能用于`<insert>`模块，order是指在该insert模块语句执行结束再执行selectKey模块。**keyProperty**就很好用了，他可以将SELECT LAST_INSERT_ID()就是selectKey模块的返回值，回写到指定的入参的属性中。入参最好用JavaBean，然后指定keyProperty的值为入参bean中的一个属性，这样该接口使用后，接口入参Bean的属性就会被重写。

```
<insert id="insert" parameterType="com.my.testBean">
<selectKey resultType="java.lang.Integer" keyProperty="code" order="AFTER" >
      SELECT LAST_INSERT_ID()
</selectKey>
  update table set num= LAST_INSERT_ID(num+1)
 </insert>
```
上述testBean里面有个code属性，那么这个接口调用后入参的testBean.getCode() = LAST_INSERT_ID(num+1)
