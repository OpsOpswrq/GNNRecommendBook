<template>
  <div>
    <el-card class="box-card" v-for="(book,index2) in Books" style="margin:12px" :key="index2">
      <div slot="header" class="clearfix">
        <span style="font-size: 18px;position: relative;left: 1%">{{book.name}}</span>
      </div>
      <div @click="">
        <div style="display: flex;flex-direction: row">
          <div style="position: relative;left: 3%;">
            <el-image
                style="width: 160px; height: 250px;"
                :src="book.picture_URL"
                :preview-src-list="[book.picture_URL]">
            </el-image>
          </div>
          <div style="position: relative;left: 8%;width: 80%">
            <el-descriptions class="margin-top" :column="1" id="form_size">
              <span slot="title" style="font-size: 22px;font-family: 'Microsoft YaHei UI';font-weight: bolder">{{book.name}}</span>
              <el-descriptions-item label="作者/出版社/出版时间"><span class="span_desc">{{book.author}}</span></el-descriptions-item>
              <el-descriptions-item label="售价"><span style="color: #ff9900;font-size: 20px">¥&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="span_desc">{{book.price}}</span></el-descriptions-item>
              <el-descriptions-item label="类别"><span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}&nbsp;&nbsp;/&nbsp;&nbsp;{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span></el-descriptions-item>
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
              <el-descriptions-item label="梗概">
                      <span class="span_desc">
                        {{book.detail}}
                      </span>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </div>
    </el-card>
    <el-pagination
        @size-change="(val)=>{pageSize = val;loadData()}"
        @current-change="(val)=>{currentPage = val;loadData()}"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200, 300]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      ID:0,
      currentPage:1,
      pageSize:10,
      Books:[],
      total:100,
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
    loadData(){
      if(!self.LoginJudge()){
        self.MtshowError("请先登录")
      }else{
        $.post("/F2016/ajax1",{ID:self.ID,currentPage:self.currentPage,pageSize:self.pageSize},r=>{
          res = JSON.parse(r)
          self.Books = res.data
        })
      }
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
        return false;
      }else{
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
#form_size .el-descriptions-item__label{
  font-size: 18px;
  font-weight: bold;
}
.span_desc{
  font-size: 18px;
}
</style>

