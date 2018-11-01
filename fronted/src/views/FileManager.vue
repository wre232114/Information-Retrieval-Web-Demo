<!-- 文件管理页面，用于进行文件上传，选择文件类型，查看上传文件状态 -->
<template>
  <div>
    <el-row>
        <span class="my-tips">当前选择的文件：</span>
        <i>文件名：<span class="my-file-info">{{fileInfoList.filename}}</span>文件类型：<span class="my-file-info">
            {{fileInfoList.filetype}}</span></i>
    </el-row>

    <el-card class="box-card">
        <div slot="header">
            <el-row>
                <el-col :span="10" :offset="2">
                    <el-upload
                        class="upload-demo"
                        :action="uploadLocation"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        :headers="authHeader"
                        multiple
                        :limit="1"
                        :on-exceed="handleExceed"
                        :data="uploadExData"
                        :file-list="fileList">
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">目前只支持json文件...</div>
                    </el-upload>
                </el-col>
                <el-col :span="6" :offset="2">
                    <span class="my-tips">请在左侧选择需要上传解析的文件。。。</span>
                </el-col>
            </el-row>
        </div>
        <div v-if="isLoaded === true" class="my-info-list">
            <p>文件名称：{{fileInfoList.filename}}</p>
            <p>文件类型：{{fileInfoList.filetype}}</p>
            <p>文件大小：{{fileInfoList.filesize}}</p>
            <p>上传时间：{{fileInfoList.uploadtime}}</p>

        </div>
        <div v-else class="my-info-list">
            <p>请<span style="color:#E6A23C">在下表中</span>先选择你要导入的文件</p>
        </div>
    </el-card>
    
    <el-card class="my-card-partition card-table-style">
        <div slot="header">
            您已经上传的文件如下：
        </div>
        <div>
            <el-table
                :data="fileUploadedList"
                stripe
                header-row-class-name="table-header-style">
                <el-table-column
                prop="filename"
                label="名称"
                min-width="180">
                </el-table-column>
                <el-table-column
                prop="filetype"
                label="类型"
                width="180">
                </el-table-column>
                <el-table-column
                prop="filesize"
                label="大小"
                width="180">
                </el-table-column>
                <el-table-column
                prop="uploadtime"
                label="上传时间"
                width="200">
                </el-table-column>
                <el-table-column
                label="操作"
                width="100">
                    <template slot-scope="scope">
                        <el-button type="text" @click="chooseFile(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
        fileInfoList: this.$store.state.tempFile,
        isLoaded: this.$store.state.isLoaded,
        uploadLocation: 'http://118.25.74.217:8080/upload/file',
        fileList: [],
        authHeader:{
            authorization: this.$store.state.authorization
        },
        uploadExData: {
            username: this.$store.state.user.userName,
            uploadtime: new Date().getTime()
        },
        fileUploadedList: [{
          filename: "null",
          filetype: "xml",
          filesize: '10kb',
          uploadtime: "2018-10-01"
        }]
    };
  },

  components: {},

  computed: {},

  mounted() {
      //this.axios.get('/upload/getJson');
      this.axios.get('/upload/getAllFile',{
          params:{
              username: this.$store.state.user.userName
          }
      }).then(res => {
          this.fileUploadedList = res.data.content;
          this.fileUploadedList.forEach(element => {
              element.filesize = Number.parseInt(element.filesize/1024) + 'KB';
          });
      }).catch(error => {
          this.$message({
              type:'error',
              message: '获取数据失败，请刷新重试'
          })
      })
  },

  methods: {
      handleRemove(file, fileList) {
        //console.log(file, fileList);
      },
      handlePreview(file) {
        //console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      chooseFile(row){
          this.$store.state.tempFile = row;
          this.fileInfoList = row;
          this.$store.state.isLoaded = true;
          this.isLoaded = true;
      }
  }
}

</script>
<style scoped>
.my-tips {
    color: #909399;
}
.my-file-info {
    padding-right: 10px;
    color: #67C23A;
}
.el-row {
    margin-bottom: 10px;
}

.my-info-list {
    text-align: start;
    color:  #606266;
}
.my-card-partition{
    margin-top: 5px;
}
</style>