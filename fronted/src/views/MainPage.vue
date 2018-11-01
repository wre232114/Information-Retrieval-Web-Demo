<!-- 登陆后的主页，是一个容器，用于容纳子视图和navmenu -->
<template>
  <div>
    <el-container>
      <el-header>
        <page-header/>
      </el-header>
      <el-container class="page-head">
          <el-aside style="width:25%;">
            <nav-menu/>
          </el-aside>
          <el-main>
            <router-view/>
          </el-main>
      </el-container>
      <el-footer>
         <page-footer/>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import PageHeader from '@/components/PageHeader'
import PageFooter from '@/components/PageFooter'
import NavMenu from '@/components/NavMenu'
export default {
  name: '',
  data () {
    return {
    };
  },

  components: {
    PageFooter,
    PageHeader,
    NavMenu
  },

  computed: {},

  mounted() {},

  methods: {}
}

</script>
<style scoped>
</style>