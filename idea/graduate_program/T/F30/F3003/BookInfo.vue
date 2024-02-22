<template>
  <div>
    <el-drawer
        title="书籍信息"
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
      <div style="width: 90%;height: 90%;overflow: auto;margin-left: 4%;margin-bottom: 20px">
        <el-card class="box-card" v-for="(book,index) in books" :key="index" style="margin-bottom: 5px">
          <div slot="header" class="clearfix">
            <span style="font-size: 22px">{{book.name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="deleteBook(book)">删除</el-button>
          </div>
          <div>
            <el-descriptions class="margin-top" :column="1" id="form_size">
              <span slot="title" style="font-size: 22px;font-family: 'Microsoft YaHei UI';font-weight: bolder">{{book.name}}</span>
              <el-descriptions-item label="作者/出版社/出版时间"><span class="span_desc">{{book.author.toString().substring(0,book.author.lastIndexOf('/'))}}</span></el-descriptions-item>
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
              <el-descriptions-item label="梗概">
                      <span class="span_desc">
                        {{book.detail}}
                      </span>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </div>
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "BookInfo",
  data(){
    return {
      drawer:false,
      direction:"rtl",
      books:[],
      description:dayjs().format('YYYY-MM-DD HH:mm'),
    }
  },
  mounted(){
    self = this
  },
  methods: {
    submit(){
      if(self.books.length === 0){
        self.MtshowError("请先选择图书")
      }else{
        $.post("/F3003/ajax6",{books:JSON.stringify(self.books),description:self.description},r=>{
          res = JSON.parse(r)
          self.books = []
          self.$emit("ok",self.books)
          self.drawer = false
        })
      }
    },
    deleteBook(book){
      self.books = self.books.filter((item)=>{
        return item.Id !== book.Id
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            self.$emit("ok",self.books)
            done();
          })
          .catch(_ => {});
    },
    show(books){
      self.books = JSON.parse(JSON.stringify(books))
      self.description = dayjs().format('YYYY-MM-DD HH:mm')
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

