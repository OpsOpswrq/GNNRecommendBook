<template>
  <div>
    <el-drawer
        title="用户信息"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
        size="60%">
      <el-form label-width="80px" style="position: absolute;left: 12%;top:15px">
        <el-form-item label="描述信息">
          <el-input v-model="description" style="width: 300px;" placeholder="填写时间和其他信息"></el-input>
        </el-form-item>
      </el-form>
      <el-button-group style="position:absolute;left: 50%;top:18px;">
        <el-button style="margin-right: 20px" type="primary" @click="submit" size="small"><i class="el-icon-download"></i>保存名单</el-button>
      </el-button-group>
      <div style="width: 90%;height: 85%;overflow: auto;margin-left: 4%;margin-bottom: 20px">
        <el-card class="box-card" v-for="(item,index) in users" :key="index" style="margin-bottom: 10px;">
          <div slot="header" class="clearfix">
            <span>{{item.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="deleteUser(item.id)">删除</el-button>
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
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "UserDetail",
  data(){
    return {
      drawer:false,
      direction:"rtl",
      users:[],
      description:dayjs().format('YYYY-MM-DD HH:mm'),
    }
  },
  mounted(){
    self = this
  },
  methods: {
    submit(){
      if(self.users.length === 0){
        self.MtshowError("请先选择用户")
      }else{
        $.post("/F3002/ajax2",{users:JSON.stringify(self.users),description:self.description},r=>{
          res = JSON.parse(r)
          self.users = []
          self.$emit("ok",self.users)
          self.drawer = false
        })
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            self.$emit("ok",self.users)
            done();
          })
          .catch(_ => {});
    },
    show(users){
      self.users = users
      self.description = dayjs().format('YYYY-MM-DD HH:mm')
    },
    deleteUser(id){
      var users_b = self.users.filter((item)=>{
        return item.id !== id
      })
      self.users = users_b
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
</style>

