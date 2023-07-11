# 项目笔记
## 创建vue3项目
```
npm init vue@latest
```
```
cd partner-manager // 进入项目文件夹
npm install // 安装npm相关依赖
npm run dev // 启动vue项目命令
```
## 安装element-plus(Vue3的UI框架)
```
npm install element-plus --save
```
## 在main.js中 引入element-plus并调用
```
import ElementPlus from 'element-plus' // 引入 element-plus
import 'element-plus/dist/index.css' // 引入 css样式
app.use(ElementPlus)
```
## 安装Vue的icon组件
```
npm i @element-plus/icons-vue -S
```
## 分页插件中文设置，在main.js中引入：
```
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
app.use(ElementPlus, {
  locale: zhCn,
})
```
# 登录页面搭建
## 安装axios 封装request.js
```
npm i axios -S
```
## 安装缓存持久化插件
安装插件
```
npm i pinia-plugin-persistedstate -S
```
在main.js引用
```
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
```