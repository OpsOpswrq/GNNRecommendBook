<template>
  <div style="margin-left: 10px">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="评论信息">
        <el-input v-model="commentDetail" placeholder="评论信息" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-card class="box-card" v-for="(item,index) in comments" :key="index" style="margin-bottom: 10px">
        <div slot="header" class="clearfix">
          <span>{{item.bookName}}</span>
          <span style="margin-left: 30px">发表于：{{new Date(item.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(item.date).toTimeString().substr(0, 8)}}</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="deleteComment(item.commentId)">删除评论</el-button>
        </div>
        <div style="display: flex;flex-direction: row" @click="openDetail(item.bookId)">
          <div style="display: flex;flex-direction: column;width: 8%;position: relative;left: 2%">
            <el-avatar :size="50" :src="item.userPicture"></el-avatar><br>
            <span style="font-size: 18px;font-weight: bold;font-style: italic">{{item.userName}}</span>
          </div>
          <div style="width: 80%">
            <el-descriptions class="margin-top" :title="item.bookName" :column="3" :size="size" id="form_size">
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
              <el-descriptions-item label="类型">
                <span class="span_desc">{{BClass1_Icon[item.bclass1 - 1]}}\{{BClass2_Icon[item.bclass1 - 1][item.bclass2 - 1]}}</span>
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
        :page-sizes="[10,20,50,100, 200, 300, 400]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <detail ref="detail" @ok="loadData"></detail>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return{
      commentDetail:"",
      pageSize:10,
      total:100,
      currentPage:1,
      comments:[],
      size:"mini",
      BClass1_Icon:[
        "文学",
        "流行",
        "文化",
        "生活",
        "经管",
        "科技"
      ],
      BClass2_Icon:[
        ["小说","文学","外国文学","经典","中国文学"],
        ["漫画","推理","绘本","悬疑","科幻"],
        ["历史","心理学","哲学","社会学","传记"],
        ["生活","爱情","成长","心理","旅行"],
        ["经济学","管理","商业","金融","投资"],
        ["科普","互联网","科学","编程","算法"]
      ],
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    sonLoadData(){

    },
    openDetail(bookId){
      self.$refs.detail.show(bookId)
      self.$refs.detail.drawer = true
    },
    loadData(){
      $.post("/F3005/ajax1",{commentDetail:self.commentDetail,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
        res = JSON.parse(r)
        self.total = res.data.total
        self.comments = res.data.comments
      })
    },
    deleteComment(commentId){
      this.$confirm('确认删除？')
          .then(_ => {
            $.post("/F2000/ajax5",{commentId:commentId},r=>{
              res = JSON.parse(r)
              self.loadData()
            })
            done();
          })
          .catch(_ => {});
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
    "detail":"url:/T/F30/F3005/CommentDetail.vue"
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

