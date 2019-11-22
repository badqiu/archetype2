<template>
  <el-row>
    <el-col>
      <el-menu :default-active="$route.path" :router="true" mode="horizontal" class="side-menu">
        <el-menu-item class="ivu-menu-item" index="/index">
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item :class="[isActive, isSort]" index="/first">
          <span slot="title">一级导航</span>
        </el-menu-item>
        <el-menu-item :class="[isSecond, isSort]" index="/second">
          <span slot="title">二级导航</span>
        </el-menu-item>
        <el-menu-item class="ivu-menu-item" index="/ztree">
          <span slot="title">ztree树导航</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
// import { SidebarItemList } from '@/api/table'
export default {
  name: 'Headernav',
  data() {
    return {
      projectId: '',
      projectName: '',
      isActive: '',
      isSecond: '',
      isSort: 'ivu-menu-item'
    }
  },
  created() {},
  mounted() {
    this.SidebarItemList()
  },
  methods: {
    SidebarItemList() {
      // SidebarItemList().then(response => {
      //   if (response) {
      //     this.projects = response
      //     this.groups = this.projects.data.items
      //     const projectId = this.groups[0].projectId
      //     const projectName = this.groups[0].projectName
      //     const name = this.$route.name
      //     this.name = name
      //     this.projectId = projectId
      //     this.projectName = projectName
      //     if (this.name === 'custom') {
      //       this.isActive = 'is-active'
      //     } else if (this.name === 'secondary') {
      //       this.isSecondary = 'is-active'
      //     }
      //   }
      // })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.side-menu{
  float:left;
  background: none;
  .ivu-menu-item{
    color:#fff;
    padding:0 20px;
    display: inline-block;
    font-size:14px;
    border-bottom:none;
    margin-right:1px;
    height:50px;
    line-height: 50px;
    &.is-active,
    &:hover{
      background: rgba(255, 255, 255, 0.2);
      color:#fff;
    }
    a{
      line-height: 50px;
      display: block;
    }
  }
}
</style>
