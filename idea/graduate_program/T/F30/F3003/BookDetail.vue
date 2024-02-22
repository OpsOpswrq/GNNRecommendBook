<template>
  <div>
    <el-drawer
        title="图书详情"
        :visible.sync="drawer"
        :direction="direction"
        size="40%"
        :before-close="handleClose">
      <div style="width: 80%;">
        <div style="position: relative;left:20%;top:3%;">
          <el-upload
              v-if="isModify === true"
              class="avatar-uploader"
              action="http://localhost:7878/F3003/ajax4"
              :data="{ID:book.Id}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <el-image
                style="width: 200px; height: 300px;position: relative;left: 70%"
                :src="book.picture_URL">
            </el-image>
          </el-upload>
          <el-image
              v-else-if="isModify === false"
              style="width: 200px; height: 300px;margin-left: 25%"
              :src="book.picture_URL"
              :preview-src-list="[book.picture_URL]">
          </el-image>
          <el-descriptions class="margin-top" :column="1" id="form_size" style="position: relative;left: -10%;top:20px;width: 99%" v-if="isModify === false">
            <template slot="extra">
              <el-button type="text" @click="()=>{isModify = true}">修改内容</el-button>
            </template>
            <span slot="title" style="font-size: 22px;font-family: 'Microsoft YaHei UI';font-weight: bolder">{{book.name}}</span>
            <el-descriptions-item label="作者/出版社/出版时间"><span class="span_desc">{{book.author}}</span></el-descriptions-item>
            <el-descriptions-item label="类别"><span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}&nbsp;&nbsp;/&nbsp;&nbsp;{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span></el-descriptions-item>
            <el-descriptions-item label="售价"><span style="color: #ff9900;font-size: 20px">¥&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="span_desc">{{book.price}}</span></el-descriptions-item>
            <el-descriptions-item label="库存"><span style="color: #ff9900;font-size: 20px"></span><span class="span_desc">{{book.left}}本</span></el-descriptions-item>
            <el-descriptions-item label="评分">
              <el-rate
                  v-model="book.rate"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}">
              </el-rate>
            </el-descriptions-item>
            <el-descriptions-item label="梗概"><span class="span_desc">{{book.detail}}</span></el-descriptions-item>
          </el-descriptions>
          <el-form :model="book" :rules="rules" ref="book" label-width="110px" class="demo-ruleForm" v-else-if="isModify === true" style="position: relative;left: -10%;top:20px;width: 99%" id="form_item">
            <el-form-item label="作者/出版社/出版时间" prop="author">
              <el-input v-model="book.author" clearable class="span_desc"></el-input>
            </el-form-item>
            <el-form-item label="类别">
              <span class="span_desc">{{BClass1_Icon[book.bclass1 - 1]}}&nbsp;&nbsp;/&nbsp;&nbsp;{{BClass2_Icon[book.bclass1 - 1][book.bclass2 - 1]}}</span>
            </el-form-item>
            <el-form-item label="选择类型">
              <el-cascader :options="options" v-model="book.chooseNode" clearable :props="{ checkStrictly: true }">
                <template slot-scope="{ node, data }">
                  <span>{{ data.label }}</span>
                  <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
                </template>
              </el-cascader>
            </el-form-item>
            <el-form-item label="售价" prop="price">
              <el-input v-model="book.price" clearable class="span_desc"></el-input>
            </el-form-item>
            <el-form-item label="库存" prop="left">
              <el-input-number v-model="book.left" :step="1"></el-input-number><span class="span_desc">&nbsp;&nbsp;本</span>
            </el-form-item>
            <el-form-item label="评分" prop="rate">
              <el-rate
                  v-model="book.rate"
                  show-score
                  text-color="#ff9900"
                  score-template="{value}">
              </el-rate>
            </el-form-item>
            <el-form-item label="梗概" prop="detail">
              <el-input v-model="book.detail" type="textarea" :rows="4" resize="none" clearable style="width: 500px;" class="span_desc"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button-group>
                <el-button type="primary" @click="submit" style="margin-right: 20px"><i class="el-icon-s-promotion"></i>修改</el-button>
                <el-button @click="cancel"><i class="el-icon-close"></i>取消</el-button>
              </el-button-group>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-drawer>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "BookDetail",
  data(){
    return{
      rules:{
        author:[
          { required: true, message: '请输入内容', trigger: 'blur' },
        ],
        price:[
          { required: true, message: '请输入价格', trigger: 'blur' },
        ],
        left:[
          { required: true, message: '请选择库存', trigger: 'change' },
        ],
        detail:[
          { required: true, message: '请输入梗概', trigger: 'blur' },
        ],
        rate:[
          { required: true, message: '请输入评分', trigger: 'blur' },
        ],
        chooseNode: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
      },
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
      direction:"rtl",
      drawer:false,
      book:{},
      BBook:{},
      isModify:false,
      options: [{
        value: '1',
        label: '文字',
        children: [{
          value: '1',
          label: '小说',
        }, {
          value: '2',
          label: '文学',
        }, {
          value: '3',
          label: '外国文学',
        }, {
          value: '4',
          label: '经典',
        }, {
          value: '5',
          label: '中国文学',
        }]
      }, {
        value: '2',
        label: '流行',
        children: [{
          value: '1',
          label: '漫画',
        }, {
          value: '2',
          label: '推理',
        }, {
          value: '3',
          label: '绘本',
        }, {
          value: '4',
          label: '悬疑',
        }, {
          value: '5',
          label: '科幻',
        }]
      }, {
        value: '3',
        label: '文化',
        children: [{
          value: '1',
          label: '历史'
        }, {
          value: '2',
          label: '心理学'
        }, {
          value: '3',
          label: '哲学'
        },{
          value: '4',
          label: '社会学'
        },{
          value: '5',
          label: '传记'
        }]
      },{
        value: '4',
        label: '生活',
        children: [{
          value: '1',
          label: '生活'
        }, {
          value: '2',
          label: '爱情'
        }, {
          value: '3',
          label: '成长'
        },{
          value: '4',
          label: '心理'
        },{
          value: '5',
          label: '旅行'
        }]
      },{
        value: '5',
        label: '经管',
        children: [{
          value: '1',
          label: '经济学'
        }, {
          value: '2',
          label: '管理'
        }, {
          value: '3',
          label: '商业'
        },{
          value: '4',
          label: '金融'
        },{
          value: '5',
          label: '投资'
        }]
      },{
        value: '6',
        label: '科技',
        children: [{
          value: '1',
          label: '科普'
        }, {
          value: '2',
          label: '互联网'
        }, {
          value: '3',
          label: '科学'
        },{
          value: '4',
          label: '编程'
        },{
          value: '5',
          label: '算法'
        }]
      }],
    }
  },
  mounted(){
    self = this
  },
  methods: {
    submit(){
      let book = JSON.parse(JSON.stringify(self.book))
      book.bclass1 = self.book.chooseNode[0]
      book.bclass2 = self.book.chooseNode[1]
      book.rate = self.book.rate + 5
      $.post("/F3003/ajax5",{book:JSON.stringify(book)},r=>{
        res = JSON.parse(r)
        self.loadData()
        self.isModify = false
        self.BBook = JSON.parse(JSON.stringify(self.book))
      })
    },
    cancel(){
      self.isModify = false
      self.book = JSON.parse(JSON.stringify(self.BBook))
    },
    handleAvatarSuccess(){
      self.loadData()
    },
    beforeAvatarUpload(file) { // 控制文件类型的
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.MtshowError('上传头像图片只能是 JPG 格式或 PNG 格式!');
      }
      if (!isLt2M) {
        this.MtshowError('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    show(book){
      self.book = JSON.parse(JSON.stringify(book))
      self.book.rate = self.book.rate - 5
      self.book.chooseNode = [self.book.bclass1,self.book.bclass2]
      self.BBook = JSON.parse(JSON.stringify(self.book))
    },
    loadData(){
      $.post("/F2013/ajax4",{bookId:self.book.Id},r=>{
        res = JSON.parse(r)
        self.book = res.data
        self.book.rate = self.book.rate - 5
        self.book.chooseNode = [self.book.bclass1,self.book.bclass2]
        self.BBook = JSON.parse(JSON.stringify(self.book))
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            self.$emit("ok")
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

