<template>
  <div>
    <div>
      <el-card class="box-card" v-for="(item,index) in comments" :key="index" style="margin: 12px">
        <div slot="header" class="clearfix">
          <span>{{item.bookName}}</span>
          <el-button @click="deleteComment(item.id)" style="float: right;position: relative;left: -3%;top:-10px"><i class="el-icon-delete-solid"></i></el-button>
        </div>
        <div style="display: flex;flex-direction: row" @click="openDetail(item.bookId)">
          <div style="display: flex;flex-direction: column;width: 8%;position: relative;left: 2%">
            <el-avatar :size="50" :src="user.picture"></el-avatar><br>
            <span style="font-size: 18px;font-weight: bold;font-style: italic">{{user.name}}</span>
          </div>
          <div style="width: 80%">
            <el-descriptions class="margin-top" :title="item.bookName" :column="2" :size="size" id="form_size">
              <el-descriptions-item label="评分">
                <el-rate
                    v-model="item.rate"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value}">
                </el-rate>
              </el-descriptions-item>
              <el-descriptions-item label="时间">
                <span class="span_desc">{{new Date(item.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(item.date).toTimeString().substr(0, 8)}}</span>
              </el-descriptions-item>
              <el-descriptions-item label="内容">
                <span class="span_desc">{{item.content}}</span>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-card>
    </div>
    <el-pagination
        @size-change="(val)=>{pageSize = val;loadData()}"
        @current-change="(val)=>{currentPage = val;loadData()}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <detail ref="detail" @ok="sonLoadData"></detail>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      pageSize:10,
      currentPage:1,
      total:100,
      ID:0,
      isLogin:false,
      comments:[],
      user:{},
      size:"mini"
    }
  },
  mounted(){
    self = this
    if(!self.LoginJudge()){
      self.MtshowError("请先登录")
    }else{
      self.loadData()
    }
  },
  methods: {
    sonLoadData(){

    },
    openDetail(bookId){
      self.$refs.detail.show(bookId)
      self.$refs.detail.drawer = true
    },
    deleteComment(ID){
      $.post("/F2013/ajax3",{ID:ID},r=>{
        res = JSON.parse(r)
        self.loadData()
      })
    },
    loadData(){
      $.post("/F2013/ajax2",{userId:self.ID},r=>{
        res = JSON.parse(r)
        self.user = res.data
      })
      $.post("/F2013/ajax1",{userId:self.ID,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
        res = JSON.parse(r)
        self.total = res.data.total
        self.comments = res.data.comments
      })
    },
    LoginJudge(){
      let arrStr = document.cookie.split("; ");
      for (let i = 0; i < arrStr.length; i++) {
        let temp = arrStr[i].split("=");
        if (temp[0] === "ID"){
          self.ID = temp[1]
        }
      }
      if((self.ID - 985) / 380 < 0){
        self.isLogin = false
        return false;
      }else{
        self.isLogin = true
        return true;
      }
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
    "detail":"url:/T/F20/F2013/CommentDetail.vue"
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
.span_desc{
   font-size: 18px;
 }
#form_size .el-descriptions-item__label{
  font-size: 18px;
  font-weight: bold;
}
</style>

