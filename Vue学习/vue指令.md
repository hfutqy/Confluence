## 学习笔记，记录vue的v-指令
#### v-bind  为标签绑定一种属性
```$xslt
<div id="app">
	<div 
	class="test" 
	v-bind:class="[ isActive ? 'active' : '', isGreen ? 'green' : '']" 
	style="width:200px; height:200px; text-align:center; line-height:200px;">
		hi vue
	</div>
	<div 
	:style="{color:color, fontSize:size, background: isRed ? '#FF0000' : ''}">
		hi vue
	</div>
</div>
```

#### 条件渲染  v-else-if
```$xslt
<div id="app">
	<div v-if="type === 'A'">
	  A
	</div>
	<div v-else-if="type === 'B'">
	  B
	</div>
	<div v-else-if="type === 'C'">
	  C
	</div>
	<div v-else>
	  Not A/B/C
	</div>
</div>
```

#### v-show 切换display属性。先做条件渲染，再做css样式切换
```$xslt
<h1 v-show="ok">Hello!</h1>
```
#### v-for 遍历一个list对象
```$xslt
<div id="app">
	<ul>
		<li v-for="item,index in items" :key="index">
		{{index}}{{ item.message }}
		</li>
	</ul>
	<ul>
		<li v-for="value, key in object">
			{{key}} : {{ value }}
		</li>
	</ul>
</div>
```
#### v-on 事件处理
```$xslt
<div id="app">
	<div id="example-1">
		<button v-on:click="counter += 1"> 数值 :  {{ counter }} </button><br />
		<button v-on:dblclick="greet('abc', $event)">Greet</button>
	</div>
</div>
```

#### 组件基础
```$xslt
// 定义一个名为 button-counter 的新组件
Vue.component('button-counter', {
  data: function () {
    return {
      count: 0
    }
  },
  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
})
```