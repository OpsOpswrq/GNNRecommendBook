<template>
  <div>
    <el-drawer
        title="图书详情"
        :visible.sync="drawer"
        :direction="direction"
        size="80%">
<!--        :before-close="handleClose">-->
      <div style="display: flex;flex-direction: row;">
        <div style="display: flex;flex-direction: column;height: 100%;width: 45%">
          <div style="position: relative;left:20%;top:3%;">
            <el-image
                style="width: 300px; height: 400px"
                :src="book.picture_URL"
                :preview-src-list="[book.picture_URL]">
            </el-image>
          </div>
          <div style="position: relative;left: 8%;margin-top:30px;width: 80%">
            <el-descriptions class="margin-top" :column="1" :size="size" id="form_size">
              <template slot="extra"> <!--收藏系统-->
                <div v-if="isLogin === false || collected.isCollected === false">
                  <el-tooltip class="item" effect="dark" content="进入收藏" placement="top">
                    <i class="el-icon-plus" @click="collectIng" ></i>
                  </el-tooltip>
                </div>
                <div v-else-if="isLogin === true && collected.isCollected === true" style="display: flex;flex-direction: column">
                  <el-tooltip class="item" effect="dark" content="取消收藏" placement="top">
                    <span @click="cancelCollecting">{{collected.date}}</span>
                  </el-tooltip>
                </div>
              </template>
              <span slot="title" style="font-size: 22px;font-family: 'Microsoft YaHei UI';font-weight: bolder">{{book.name}}</span>
              <el-descriptions-item label="作者/出版社/出版时间"><span class="span_desc">{{book.author}}</span></el-descriptions-item>
              <el-descriptions-item label="类别"><span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}&nbsp;&nbsp;/&nbsp;&nbsp;{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span></el-descriptions-item>
              <el-descriptions-item label="售价"><span style="color: #ff9900;font-size: 20px">¥&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="span_desc">{{book.price}}</span></el-descriptions-item>
              <el-descriptions-item label="库存"><span style="color: #ff9900;font-size: 20px"></span><span class="span_desc">{{book.left}}本</span></el-descriptions-item>
              <el-descriptions-item label="评分">
                <el-rate
                    v-model="book.rate - 5"
                    disabled
                    show-score
                    text-color="#ff9900"
                    score-template="{value}">
                </el-rate>
              </el-descriptions-item>
              <el-descriptions-item label="本站评分">
                <el-rate
                    v-model="(avg.avgRate - 5).toFixed(1)"
                    disabled
                    show-score
                    :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                    score-template="{value}"
                    text-color="#ff9900">
                </el-rate>
              </el-descriptions-item>
              <el-descriptions-item label="梗概">
                      <span class="span_desc">
                        {{book.detail}}
                      </span>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <div style="width: 51%;">
          <el-tabs type="border-card" style="margin-bottom: 30px">
            <el-tab-pane label="评论详情">
              <el-card class="box-card" v-for="(item,index) in comments" style="margin: 5px;width: 99%;" :key="index">
                <div v-if="(ID - 985) / 380 === item.id" style="position: absolute;float:right;left: 88%">
                  <el-button @click="deleteComment(item.commentId)"><i class="el-icon-delete-solid"></i></el-button>
                </div>
                <div style="display: flex;flex-direction: row;">
                  <div style="width: 14%">
                    <el-avatar :size="50" :src="item.picture"></el-avatar><br>
                    <span style="font-weight: bold;font-style: italic">{{item.name}}</span>
                  </div>
                  <div style="width: 70%">
                    <el-descriptions class="margin-top" :column="1" :size="size" id="form_size">
                      <el-descriptions-item label="评分">
                        <el-rate
                            v-model="item.rate"
                            disabled
                            show-score
                            :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                            score-template="{value}"
                            text-color="#ff9900">
                        </el-rate>
                      </el-descriptions-item>
                      <el-descriptions-item label="评论">
                        <span>{{item.content}}</span>
                      </el-descriptions-item>
                      <el-descriptions-item label="时间">
                        <span>{{new Date(item.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(item.date).toTimeString().substr(0, 8)}}</span>
                      </el-descriptions-item>
                    </el-descriptions>
                  </div>
                </div>
              </el-card>
            </el-tab-pane>
            <el-form ref="form" :model="commentNow" label-width="100px" :rules="rules" id="form_item" style="display: flex;flex-direction: row;">
              <div style="display: flex;flex-direction: column;width: 60%;margin-top: 10px">
                <el-form-item label="发表评论" prop="content">
                  <el-input v-model="commentNow.content" clearable style="width: 120%;"></el-input>
                </el-form-item>
                <el-form-item label="发表评分" prop="rate">
                  <el-rate
                      v-model="commentNow.rate"
                      show-score
                      text-color="#ff9900"
                      score-template="{value}"
                      style="margin-top: 10px">
                  </el-rate>
                </el-form-item>
              </div>
              <el-form-item style="width: 50%;margin-top: 30px;display: flex;flex-direction: column">
                <el-button type="primary" @click="submit"><i class="el-icon-s-promotion"></i>发表</el-button>
                <el-button @click="()=>{this.$refs[`form`].resetFields();}">取消</el-button>
              </el-form-item>
            </el-form>
          </el-tabs>
          <div class="block">
            <span class="demonstration">评论总数</span>
            <el-pagination
                @current-change="()=>{loadData()}"
                :current-page.sync="currentPage"
                :page-size="3"
                layout="total, prev, pager, next"
                :total="total">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "CommentDetail",
  data(){
    return{
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
      isLogin:false,
      collected:{
        isCollected:false,
        date:"",
      },
      direction:"rtl",
      drawer:false,
      book:{},
      size:"",
      total:100,
      currentPage:1,
      commentNow:{content:"",rate:0},
      comments:[{}],
      avg:{avgRate:5},
      ID:0,
      rules:{
        content: [
          { required: true, message: '请输入评论', trigger: 'blur' },
        ],
        rate: [
          { required: true, message: '请选择评分', trigger: 'blur' },
        ],
      }
    }
  },
  mounted(){
    self = this
  },
  methods: {
    cancelCollecting(){
      if(self.LoginJudge()){
        $.post("/F2000/ajax7",{userId:self.ID,bookId:self.book.Id},r=>{
          res = JSON.parse(r)
          self.loadData()
        })
      }
    },
    collectIng(){
      if(self.LoginJudge()){
        $.post("/F2000/ajax6",{userId:self.ID,bookId:self.book.Id},r=>{
          res = JSON.parse(r)
          self.loadData()
        })
      }
    },
    deleteComment(commentId){
      $.post("/F2000/ajax5",{commentId:commentId},r=>{
        res = JSON.parse(r)
        self.loadData()
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
    submit(){
      if(self.LoginJudge()){
        this.$refs["form"].validate((valid) => {
          if(valid){
            $.post("/F2000/ajax4",{content:self.commentNow.content,rate:self.commentNow.rate,userId:self.ID,bookId:self.book.Id},r=>{
              res = JSON.parse(r)
              this.$refs[`form`].resetFields();
              self.loadData()
            })
          }
        })
      }else{
        this.MtshowError("请先登录")
      }
    },
    show(ID){
      self.loadBook(ID)
      self.loadData1(ID)
    },
    loadBook(bookId){
      $.post("/F2013/ajax4",{bookId:bookId},r=>{
        res = JSON.parse(r)
        self.book = res.data
      })
    },
    loadData(){
      if(self.LoginJudge()){ // 已经登录了账号
        $.post("/F2000/ajax3",{userId:self.ID,bookId:parseInt(self.book.Id),currentPage:self.currentPage},r=>{
          res = JSON.parse(r)
          // console.log(res)
          self.avg.avgRate = parseFloat(res.data.avg.toString().substring(0,3))
          self.comments = res.data.comment
          self.total = res.data.total
          if(res.data.collection.cnt === 0){
            self.collected.isCollected = false
          }else{
            self.collected.isCollected = true
            self.collected.date = new Date(res.data.collection.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(res.data.collection.date).toTimeString().substr(0, 8)
          }

        })
      }else{
        $.post("/F2000/ajax2",{bookId:parseInt(self.book.Id),currentPage:self.currentPage},r=>{
          res = JSON.parse(r)
          self.avg.avgRate = parseFloat(res.data.avg).toFixed(1)
          self.comments = res.data.comment
          self.total = res.data.total
        })
      }
    },
    loadData1(ID){
      if(self.LoginJudge()){ // 已经登录了账号
        $.post("/F2000/ajax3",{userId:self.ID,bookId:ID,currentPage:self.currentPage},r=>{
          res = JSON.parse(r)
          // console.log(res)
          self.avg.avgRate = parseFloat(res.data.avg).toFixed(1)
          self.comments = res.data.comment
          self.total = res.data.total
          if(res.data.collection.cnt === 0){
            self.collected.isCollected = false
          }else{
            self.collected.isCollected = true
            self.collected.date = new Date(res.data.collection.date).toLocaleDateString().replace(/\//g, "-") + " " + new Date(res.data.collection.date).toTimeString().substr(0, 8)
          }

        })
      }else{
        $.post("/F2000/ajax2",{bookId:parseInt(self.book.Id),currentPage:self.currentPage},r=>{
          res = JSON.parse(r)
          self.avg.avgRate = parseFloat(res.data.avg).toFixed(1)
          self.comments = res.data.comment
          self.total = res.data.total
        })
      }
    },
    // handleClose(done) {
    //   this.$confirm('确认关闭？')
    //       .then(_ => {
    //         done();
    //         self.$emit("ok")
    //       })
    //       .catch(_ => {});
    // },
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
#form_item .el-form-item__label{
  font-size: 16px;
}
#form_size .el-descriptions-item__label{
  font-size: 16px;
  font-weight: bold;
}
.span_desc{
  font-size: 18px;
}
</style>

