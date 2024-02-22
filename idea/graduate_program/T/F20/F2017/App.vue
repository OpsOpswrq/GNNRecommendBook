<template>
  <div>
    <div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>数据图表</span>
        </div>
        <div style="display: flex;flex-direction: row;">
          <div id="EChart1" style="width: 600px;height: 600px;margin-right: 100px;"></div>
          <div id="EChart2" style="width: 600px;height: 600px;margin-right: 100px;"></div>
          <div id="EChart3" style="width: 600px;height: 600px;margin-right: 100px;"></div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
let self = null;
module.exports = {
  name: "App",
  data(){
    return {
      BClass1_Icon:[
        "文学",
        "流行",
        "文化",
        "生活",
        "经管",
        "科技"
      ],
      ID:0,
      hot:[],
      wed_hot:[],
    }
  },
  mounted(){
    self = this
    self.loadData()
  },
  methods: {
    getRenderer3(){
      let EChart = echarts.init(document.getElementById("EChart3"))
      let option = {
        backgroundColor: '#fff', // canvas背景颜色
        // canvas标题配置项
        title: {
          text: '网站近期喜爱书籍',
          top: '0%',
          left: '-1%',
          textStyle: {
            fontSize: 20,
            color: '#3B3E41',
            fontWeight: 'normal'
          }
        },
        series: [
          {
            type: 'wordCloud',
            left: '-5%',                 // X轴偏移量
            top: '0%',                  // Y轴偏移量
            width: '100%',               // canvas宽度大小
            height: '100%',              // canvas高度大小
            sizeRange: [12, 50],         //  词典字体大小范围配置
            rotationRange: [0, 0],       // 词典字体旋转角度配置，默认不旋转
            gridSize: 25,                // 词典字体间距配置
            layoutAnimation: true,       // 为false词典过度会阻塞
            textStyle: {                 // 词典样式配置
              normal: {
                color() {
                  // 颜色随机渐变
                  let colors = ['#ff9a8bb3', '#f19a8bb3', '#f29a8b03', '#8E87FFb3', '#8E87FFb3', '#3E87FFb3', '#3e9a8bb3', '#3e9a8bb3', '#0e9a8bb3', '#03DDFF']
                  return colors[parseInt(Math.random() * 10)]
                }
              }
            },
            // 渲染词典数据
            data: [{
              value: self.wed_hot[0].cnt,          // 词典大小配置
              name: self.wed_hot[0].name,    // 词典名称配置
              textStyle: {          // 单独配置某个词典样式
                shadowBlur: 4,
                shadowOffsetY: 14,
                color: '#BDBEFA'
              }
            },
              { value: self.wed_hot[1].cnt, name: self.wed_hot[1].name },
              { value: self.wed_hot[2].cnt, name: self.wed_hot[2].name },
              { value: self.wed_hot[3].cnt, name: self.wed_hot[3].name },
              { value: self.wed_hot[4].cnt, name: self.wed_hot[4].name },
              { value: self.wed_hot[5].cnt, name: self.wed_hot[5].name },
              { value: self.wed_hot[6].cnt, name: self.wed_hot[6].name },
              { value: self.wed_hot[7].cnt, name: self.wed_hot[7].name },
              { value: self.wed_hot[8].cnt, name: self.wed_hot[8].name },
              { value: self.wed_hot[9].cnt, name: self.wed_hot[9].name },
            ]
          }
        ]
      }
      EChart.setOption(option)
    },
    getRenderer2(){
      let EChart = echarts.init(document.getElementById("EChart2"))
      let option = {
        backgroundColor: '#fff', // canvas背景颜色
        // canvas标题配置项
        title: {
          text: '用户近期喜爱书籍分类',
          top: '0%',
          left: '-1%',
          textStyle: {
            fontSize: 20,
            color: '#3B3E41',
            fontWeight: 'normal'
          }
        },
        series: [
          {
            type: 'wordCloud',
            left: '-5%',                 // X轴偏移量
            top: '0%',                  // Y轴偏移量
            width: '100%',               // canvas宽度大小
            height: '100%',              // canvas高度大小
            sizeRange: [12, 50],         //  词典字体大小范围配置
            rotationRange: [0, 0],       // 词典字体旋转角度配置，默认不旋转
            gridSize: 25,                // 词典字体间距配置
            layoutAnimation: true,       // 为false词典过度会阻塞
            textStyle: {                 // 词典样式配置
              normal: {
                color() {
                  // 颜色随机渐变
                  let colors = ['#fe9a8bb3', '#fe9a8bb3', '#fe9a8b03', '#9E87FFb3', '#9E87FFb3', '#9E87FFb3']
                  return colors[parseInt(Math.random() * 10)]
                }
              }
            },
            // 渲染词典数据
            data: [{
              value: self.hot[0].cnt,          // 词典大小配置
              name: self.BClass1_Icon[self.hot[0].bclass1 - 1],    // 词典名称配置
              textStyle: {          // 单独配置某个词典样式
                shadowBlur: 4,
                shadowOffsetY: 14,
                color: '#BDBEFA'
              }
            },
              { value: self.hot[1].cnt, name: self.BClass1_Icon[self.hot[1].bclass1 - 1] },
              { value: self.hot[2].cnt, name: self.BClass1_Icon[self.hot[2].bclass1 - 1] },
              { value: self.hot[3].cnt, name: self.BClass1_Icon[self.hot[3].bclass1 - 1] },
              { value: self.hot[4].cnt, name: self.BClass1_Icon[self.hot[4].bclass1 - 1] },
              { value: self.hot[5].cnt, name: self.BClass1_Icon[self.hot[5].bclass1 - 1] }]
          }
        ]
      }
      EChart.setOption(option)
    },
    getRenderer1(){
      let EChart = echarts.init(document.getElementById("EChart1"))
      let option = {
        title: {
          text: '用户近期喜爱书籍分类',
          left: 'center',
          top: 0,
          textStyle: {
            color: '#000000'
          }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '近期喜爱书籍分类',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            padAngle: 5,
            itemStyle: {
              borderRadius: 10
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: self.hot[0].cnt, name: self.BClass1_Icon[self.hot[0].bclass1 - 1] },
              { value: self.hot[1].cnt, name: self.BClass1_Icon[self.hot[1].bclass1 - 1] },
              { value: self.hot[2].cnt, name: self.BClass1_Icon[self.hot[2].bclass1 - 1] },
              { value: self.hot[3].cnt, name: self.BClass1_Icon[self.hot[3].bclass1 - 1] },
              { value: self.hot[4].cnt, name: self.BClass1_Icon[self.hot[4].bclass1 - 1] },
              { value: self.hot[5].cnt, name: self.BClass1_Icon[self.hot[5].bclass1 - 1] },
            ]
          }
        ]
      };
      EChart.setOption(option)
    },
    loadData(){
      if(!self.LoginJudge()){
        self.MtshowError("请先登录")
      }else{
        $.post("/F2017/ajax1",{ID:self.ID},r=>{
          res = JSON.parse(r)
          self.hot = res.data.hot1
          self.wed_hot = res.data.hot2
          console.log(res)
          self.getRenderer1()
          self.getRenderer2()
          self.getRenderer3()
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
</style>

