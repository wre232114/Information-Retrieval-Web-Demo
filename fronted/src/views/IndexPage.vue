<!--  -->
<template>
  <div>
    <el-col :span="6" v-if="!isLoaded">
        <span style="color:#F56C6C;font-size:0.74em;">
          <i class="el-icon-warning"></i>
          请您导入文件，加载初始数据后再进行操作</span>
    </el-col>
    <el-col :span="8" :offset="1">
      <el-input
        placeholder="请输入您要查询的关键字"
        clearable
        v-on:input="inputChange"
        suffix-icon="el-icon-search"
        v-model="inputCoreWord"
        :disabled="!isLoaded">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
    </el-col>

    <el-table
      :data="searchResult"
      v-loading="isLoading"
      element-loading-text="正在拼命地加载索引，请您稍等。。。。"
      element-loading-spinner="el-icon-loading"
      stripe
      style="width: 100%"
      header-row-class-name="table-header-style">
      <el-table-column
        label="出生日期"
        width="180">
        <template slot-scope="scope">
          <span v-html="scope.row.出生日期"></span>
        </template>
      </el-table-column>
      <el-table-column
        label="姓名"
        width="180">
        <template slot-scope="scope">
          <span v-html="scope.row.姓名"></span>
        </template>
      </el-table-column>
      <el-table-column
        label="学号"
        width="180">
        <template slot-scope="scope">
          <span v-html="scope.row.学号"></span>
        </template>
      </el-table-column>
      <el-table-column
        label="描述"
        min-width="180">
        <template slot-scope="scope">
          <span v-html="scope.row.描述"></span>
        </template>
      </el-table-column>
      <el-table-column
        label="专业"
        width="180">
        <template slot-scope="scope">
          <span v-html="scope.row.专业"></span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

export default {
  name: '',
  data () {
    return {
      isLoaded: this.$store.state.isLoaded,
      inputCoreWord: '',
      searchResult: [],
        // 加载websocket
      // websocket地址
      ws_address: 'ws://118.25.74.217:8080/websocket/search',
      websocket: null,
      sendedMessage:{},//发送给websocket的消息
      isLoading: false
    };
  },

  components: {

  },

  computed: {},

  mounted() {
    // 初始化websocket
    if(!('WebSocket' in window)) {
      alert('当前浏览器版本过低，不支持WebSocket，请升级浏览器后重试!');
      return;
    }
    // 只有当加载了文件以后才会建立连接
    if(this.$store.state.isLoaded === true){
      this.websocket = new WebSocket(this.ws_address);
      this.websocket.onopen = (event) =>{
        console.log('连接成功');
        // 建立连接的时候发送文件完整路劲给服务器
        let tempFile = this.$store.state.tempFile;
        let filedir = tempFile.destination.substring(0,tempFile.destination.lastIndexOf('/'));
        let filepath = filedir+'/'+tempFile.savename+'.schs';
        let data = {
          firstOpen: 'yes',
          content: filepath
        }
        this.isLoading = true;
        this.websocket.send(JSON.stringify(data));
      }

      this.websocket.onmessage = (event) => {
        console.log(event.data);
        if(JSON.parse(event.data) == null){
          this.searchResult = null;
        }else if("brightnoconfloaded" in JSON.parse(event.data)){
          this.isLoading = false;
        }else{
          this.searchResult = JSON.parse(event.data);
        }
        
      }
      
      this.websocket.onerror = function(event) {
        console.log(event);
      }

      this.websocket.onclose = function(event) {
        console.log('连接关闭');
      }

      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
      window.onbeforeunload = function() {  
          this.websocket.close();  
      }
    }
    
  },
  destroyed(){
    if(this.websocket != null)
      this.websocket.close();
  },
  methods: {
    // 输入改变时调用
    inputChange(event){
      let data = {
        firstOpen: 'no',
        content: event
      }
      this.websocket.send(JSON.stringify(data));
    }
  }
}

</script>
<style scoped>
</style>