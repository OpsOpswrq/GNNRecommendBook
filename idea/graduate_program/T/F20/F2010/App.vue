<template>
  <div style="width: 100%;height: 100%">
    <div>
      <el-menu
          class="el-menu-demo"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
        <div style="float: left">
          <el-menu-item><span class="logo" @click="goBack">WLibary</span></el-menu-item>
        </div>
        <div style="float: right;position: relative;left: -40px">
          <el-menu-item>
            <el-dropdown>
              <template v-if="isLogin === false">
                <el-avatar :size="40" :src="defaultAvatar"></el-avatar>
              </template>
              <template v-else-if="isLogin === true">
                <el-avatar :size="40" :src="this.user.picture"></el-avatar>
              </template>
              <el-dropdown-menu slot="dropdown">
                <div>
                  <template v-if="isLogin === false">
                    <el-dropdown-item><i class="el-icon-s-promotion"></i><el-button type="text" @click="login">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</el-button></el-dropdown-item>
                    <el-dropdown-item><i class="el-icon-position"></i><el-button type="text" @click="register">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</el-button></el-dropdown-item>
                  </template>
                  <template v-else-if="isLogin === true">
                    <el-dropdown-item style="font-size: 16px;font-family: 'Arial Black';color: #999933">用户名：{{this.user.name}}</el-dropdown-item>
                    <el-dropdown-item><i class="el-icon-user-solid"></i><el-button type="text" @click="()=>{navSrc=navSrcArray[1]}">用户信息</el-button></el-dropdown-item>
                    <el-dropdown-item><i class="el-icon-switch-button"></i><el-button type="text" @click="logOut">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销</el-button></el-dropdown-item>
                  </template>
                </div>
              </el-dropdown-menu>
            </el-dropdown>
          </el-menu-item>
        </div>
      </el-menu>
    </div>
    <div class="div_all">
      <div @mouseover="()=>{isCollapse = false}" @mouseleave="()=>{isCollapse = true}" class="div_menu">
        <el-menu default-active="1-1" class="el-menu-vertical-demo"  :collapse="isCollapse">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title" style="font-size: 22px;font-weight: bold">主页</span>
            </template>
            <el-menu-item-group>
              <span slot="title">个人数据图表</span>
              <el-menu-item index="1-1" @click="()=>{navSrc=navSrcArray[8]}"><span style="font-size: 16px;">数据图表</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">图书分类推荐主页</span>
              <el-menu-item index="1-2" @click="()=>{navSrc=navSrcArray[0]}"><span style="font-size: 16px;">图书分类推荐</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">猜你喜欢</span>
              <el-menu-item index="1-3" @click="()=>{navSrc=navSrcArray[6]}"><span style="font-size: 16px;">图书推荐</span></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title" style="font-size: 22px;font-weight: bold">管理</span>
            </template>
            <el-menu-item-group>
              <span slot="title">收藏管理</span>
              <el-menu-item index="2-1" @click="()=>{navSrc=navSrcArray[2]}"><span style="font-size: 16px;">图书收藏</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">评论管理</span>
              <el-menu-item index="2-2" @click="()=>{navSrc=navSrcArray[3]}"><span style="font-size: 16px;">发表评论</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">图书搜索</span>
              <el-menu-item index="2-3" @click="()=>{navSrc=navSrcArray[4]}"><span style="font-size: 16px;">图书搜索</span></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span slot="title" style="font-size: 22px;font-weight: bold">个人信息</span>
            </template>
            <el-menu-item-group>
              <span slot="title">个人信息页</span>
              <el-menu-item index="3-1" @click="()=>{navSrc=navSrcArray[1]}"><span style="font-size: 16px;">个人信息</span></el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <span slot="title">地址管理页</span>
              <el-menu-item index="3-2" @click="()=>{navSrc=navSrcArray[5]}"><span style="font-size: 16px;">地址信息</span></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </div>
      <div class="div_display">
        <iframe :src="navSrc" style="width: 100.5%;height: 100%;border: none"></iframe>
      </div>
    </div>
  </div>
</template>
<script>
let self = null
module.exports = {
  name: "App",
  data(){
    return{
      navSrc:"http://localhost:7878/F2017",
      navSrcArray:[
        "http://localhost:7878/F2000",
        "http://localhost:7878/F2011",
        "http://localhost:7878/F2012",
        "http://localhost:7878/F2013",
        "http://localhost:7878/F2014",
        "http://localhost:7878/F2015",
        "http://localhost:7878/F2016",
        "http://localhost:7878/F2017",
      ],
      user:{},
      defaultAvatar:"/T/UserData/default.png",
      isCollapse:true,
      isLogin:false,
      loading:false,
      ID:-1
    }
  },
  mounted(){
    self = this
    if(this.LoginJudge()){
      this.loadData()
    }
  },
  methods: {
    login(){
      window.location.href="http://localhost:7878/F1010"
    },
    register(){
      window.location.href="http://localhost:7878/F1011"
    },
    logOut(){
      document.cookie = "ID=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
      window.location.href="http://localhost:7878/"
    },
    loadData(){
      $.post("/F2010/ajax1",{ID:this.ID},r=>{
        res = JSON.parse(r)
        if(res.code === 200){
          this.user = res.data
        }
      })
    },
    LoginJudge(){
      let arrStr = document.cookie.split("; ");
      for (let i = 0; i < arrStr.length; i++) {
        let temp = arrStr[i].split("=");
        if (temp[0] === "ID"){
          this.ID = temp[1]
        }
      }
      if((this.ID - 985) / 380 < 0){
        this.isLogin = false;
        return false;
      }else{
        this.isLogin = true;
        return true;
      }
    },
    goBack(){
      window.location.href="http://localhost:7878/"
    },
    MtshowOK(msg) {
      this.$message({
        message: msg,
        type: 'success',
        duration: 1000,
      });
    },
    MtshowError(msg) {
      this.$message({
        message: msg,
        type: 'error',
        duration: 1000,
      });
    }
  },
}
</script>
<style scoped>
.el-divider--vertical{
  display:inline-block;
  width:3px;
  height:400px;  //更改竖向分割线长度
  margin:0 8px;
  vertical-align:middle;
  position:relative;
}
.logo{
  font-size: 28px;
  font-family:"Microsoft YaHei";
  font-style:italic;
  color: #01AAED;
}
.div_menu{
  width: 3.4%;
  margin-right: 5px;
  height: 100%;
}
.div_menu:hover{
  width: 14%;
  margin-right: 5px;
  height: 100%;
}
.div_all{
  display: flex;
  flex-direction: row;
  position: absolute;
  left: 0;
  width: 99%;
  height: 92%;
}
.div_display{
  position: relative;
  width: 100%;
  height: 100%;
}
</style>

