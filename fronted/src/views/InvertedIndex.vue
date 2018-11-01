<!-- 倒排索引表 -->
<template>
  <div style="text-align:start;">
      <h1>索引列表如下：</h1>
      <h4>&nbsp;&nbsp;每一项的第一个是词项，下面的是其倒排索引表</h4>
      <ul>
          <li v-for="(value,key) in invertedIndex" :key="key">
              {{key}}&nbsp;&nbsp;
              <p v-for="item in value" :key="item.personID+item.attrID">DocID: {{item.personID}}&nbsp;&nbsp;attrID: {{item.attrID}}&nbsp;&nbsp;
                  startPos: {{item.startPos}}&nbsp;&nbsp;endPos: {{item.endPos}}
              </p>
          </li>
      </ul>
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
        invertedIndex:{

        }
    };
  },

  components: {},

  computed: {},

  mounted() {
    let tempFile = this.$store.state.tempFile;
    let filedir = tempFile.destination.substring(0,tempFile.destination.lastIndexOf('/'));
    let filepath = filedir+'/'+tempFile.savename+'.schs';
    this.axios.get('/upload/getInvertIndex',{
        params:{
            path: filepath
        }
    }).then(res => {
        this.invertedIndex = res.data.content;
    }).catch(err => {
        this.$message('出现了一些错误，请刷新后重试！');
    })
  },

  methods: {}
}

</script>
<style scoped>
</style>