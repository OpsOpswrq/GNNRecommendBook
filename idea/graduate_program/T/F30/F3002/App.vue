<template>
  <div style="margin-left: 10px">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="用户信息">
        <el-input v-model="userDetail" placeholder="用户信息" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
      </el-form-item>
      <el-badge :value="cnt" class="item">
        <el-button @click="openDetail">已选用户</el-button>
      </el-badge>
      <el-button-group style="float:right;">
        <el-button @click="showUser" type="primary"><i class="el-icon-s-fold"></i>用户名单</el-button>
      </el-button-group>
    </el-form>
    <div>
      <el-card class="box-card" v-for="(item,index) in users" :key="index" style="margin-bottom: 10px;">
        <div slot="header" class="clearfix">
          <span>{{item.name}}</span>
          <el-button-group style="float: right; ">
            <el-button v-if="item.isChoosed === false" type="text" @click="addUsers(item)" style="margin-right: 20px">选择</el-button>
            <el-button type="text" @click="deleteUser(item.id)" >删除</el-button>
          </el-button-group>
        </div>
        <div style="display: flex;flex-direction: row">
          <div style="display: flex;flex-direction: column;">
            <el-avatar :size="60" :src="item.picture"></el-avatar><br/>
            <span style="font-style: italic;font-weight: bold;font-size: 20px">{{item.name}}</span>
          </div>
          <div style="margin-left: 50px">
            <el-descriptions column="2" class="form_size">
              <el-descriptions-item label="用户ID"><span class="span_desc">{{item.userId}}</span></el-descriptions-item>
              <el-descriptions-item label="用户名"><span class="span_desc">{{item.name}}</span></el-descriptions-item>
              <el-descriptions-item label="年龄"><span class="span_desc">{{item.age}}</span></el-descriptions-item>
              <el-descriptions-item label="性别" v-if="item.sex === 1"><span class="span_desc">男</span></el-descriptions-item>
              <el-descriptions-item label="性别" v-else-if="item.sex === 2"><span class="span_desc">女</span></el-descriptions-item>
              <el-descriptions-item label="邮箱地址"><span class="span_desc">{{item.email}}</span></el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-card>
    </div>
    <el-pagination
        @size-change="(val)=>{pageSize = val;loadData()}"
        @current-change="(val)=>{currentPage = val;loadData()}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200, 300, 400]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <user-detail ref="userDetail" @ok="reload"></user-detail>
    <user-print ref="userPrint"></user-print>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return{
      currentPage:1,
      total:100,
      pageSize:10,
      userDetail:"",
      cnt:0,
      users:[{}],
      chooseUsers:[],
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  watch: { // 事件监听
    'userDetail':{
      immediate:true,
      handler(){
        self.loadData()
      }
    }
  },
  methods: {
    deleteUser(id){
      this.$confirm('确认删除？')
          .then(_ => {
            $.post("/F3002/ajax4",{ID:id},r=>{
              res = JSON.parse(r)
              self.loadData()
            })
            done();
          })
          .catch(_ => {});
    },
    showUser(){
      self.$refs.userPrint.drawer = true
      self.$refs.userPrint.show()
    },
    reload(users_son){
      self.chooseUsers = JSON.parse(JSON.stringify(users_son))
      block1:for(let i = 0;i < self.users.length;i++){
        if(users_son.length === 0){
          self.users[i].isChoosed = false
        }
        block2:for(let j = 0;j < users_son.length;j++){
          if(self.users[i].id === users_son[j].id){
            self.users[i].isChoosed = true
            break block1 // 标签跳转
          }else{
            self.users[i].isChoosed = false
          }
        }
      }
      self.cnt = self.chooseUsers.length
    },
    openDetail(){
      self.$refs.userDetail.drawer = true
      self.$refs.userDetail.show(self.chooseUsers)
    },
    addUsers(user){
      user.isChoosed = true
      self.chooseUsers.push(user)
      self.cnt = self.chooseUsers.length
    },
    loadData(){
      $.post("/F3002/ajax1",{userDetail:self.userDetail,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
        res = JSON.parse(r)
        self.total = res.data.total
        self.users = JSON.parse(JSON.stringify(res.data.users))
        for(let i = 0;i < self.users.length;i++){
          self.users[i].isChoosed = false
        }
      })
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
  components:{
    "userDetail":"url:/T/F30/F3002/UserDetail.vue",
    "userPrint":"url:/T/F30/F3002/userPrint.vue",
  }
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
.form_size .el-descriptions-item__label{
  font-size: 18px;
  font-weight: bold;
}
.span_desc{
  font-size: 18px;
}
</style>

