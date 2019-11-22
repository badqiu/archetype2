<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu :show-timeout="200" :default-active="$route.path" :router="true" :collapse="isCollapse" :collapse-transition="false" class="el-menu-vertical-demo">
      <el-menu-item index="/index/user" class="{'is-active':item.selected}">
        <i class="el-icon-document"/>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="/index/user" class="{'is-active':item.selected}">
        <i class="el-icon-document"/>
        <span slot="title">用户管理2</span>
      </el-menu-item>
    </el-menu>
  </el-scrollbar>
</template>

<style rel="stylesheet/scss" lang="scss" scoped>
  .scrollbar-wrapper {
    background:#f5f7fa;
    .el-menu{
      background: none;
    }
  }
</style>

<script>
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      loading: true,
      formLabelWidth: '30%'
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
  }
}
</script>
